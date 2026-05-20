package com.smartwarehouse.ui;

import com.smartwarehouse.algorithm.BinarySearch;
import com.smartwarehouse.algorithm.Dijkstra;
import com.smartwarehouse.algorithm.MergeSort;
import com.smartwarehouse.database.DatabaseConnection;
import com.smartwarehouse.database.ProductDAO;
import com.smartwarehouse.database.ShipmentDAO;
import com.smartwarehouse.database.StockLogDAO;
import com.smartwarehouse.database.WarehousePathDAO;
import com.smartwarehouse.datastructure.CustomBST;
import com.smartwarehouse.datastructure.CustomGraph;
import com.smartwarehouse.datastructure.CustomHashTable;
import com.smartwarehouse.datastructure.CustomLinkedList;
import com.smartwarehouse.datastructure.CustomPriorityQueue;
import com.smartwarehouse.datastructure.CustomQueue;
import com.smartwarehouse.datastructure.CustomStack;
import com.smartwarehouse.model.Product;
import com.smartwarehouse.model.Shipment;
import com.smartwarehouse.model.StockLog;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ConsoleApplication {

    private static final int URGENT_PRIORITY_THRESHOLD = 7;

    private final ProductDAO productDAO = new ProductDAO();
    private final ShipmentDAO shipmentDAO = new ShipmentDAO();
    private final WarehousePathDAO warehousePathDAO = new WarehousePathDAO();
    private final StockLogDAO stockLogDAO = new StockLogDAO();
    private final Scanner scanner = new Scanner(System.in);
    private final CustomLinkedList<StockLog> stockLogs = new CustomLinkedList<>();
    private final CustomStack<UndoAction> undoStack = new CustomStack<>();

    private CustomHashTable<Integer, Product> productHashTable = new CustomHashTable<>();
    private CustomQueue<Shipment> shipmentQueue = new CustomQueue<>();
    private CustomPriorityQueue<Shipment> urgentShipmentQueue = new CustomPriorityQueue<>();
    private CustomBST<Product> productTree = new CustomBST<>(Comparator.comparingInt(Product::getProductId));
    private CustomGraph<String> warehouseGraph = new CustomGraph<>();

    public void start() {
        System.out.println("Smart Warehouse Management System");
        if (!DatabaseConnection.testConnection()) {
            System.out.println("Database connection not available. Check DB configuration.");
            return;
        }

        System.out.println("Database connected successfully");
        try {
            loadApplicationData();
        } catch (SQLException exception) {
            System.out.println("Startup data load failed: " + exception.getMessage());
            return;
        }

        System.out.println("Console menu starts");
        runMenu();
    }

    private void loadApplicationData() throws SQLException {
        reloadProducts();
        System.out.println("Products loaded into Hash Table");

        shipmentQueue = new CustomQueue<>();
        urgentShipmentQueue = new CustomPriorityQueue<>();
        for (Shipment shipment : shipmentDAO.getWaitingShipments()) {
            if (shipment.getPriority() >= URGENT_PRIORITY_THRESHOLD) {
                urgentShipmentQueue.insert(shipment);
            } else {
                shipmentQueue.enqueue(shipment);
            }
        }
        System.out.println("Shipments loaded into Queue");
        System.out.println("Urgent shipments loaded into Priority Queue");

        warehouseGraph = warehousePathDAO.loadGraph();
        System.out.println("Warehouse paths loaded into Graph");
    }

    private void reloadProducts() throws SQLException {
        productHashTable = new CustomHashTable<>();
        productTree = new CustomBST<>(Comparator.comparingInt(Product::getProductId));
        for (Product product : productDAO.getAllProducts()) {
            productHashTable.put(product.getProductId(), product);
            productTree.insert(product);
        }
    }

    private void runMenu() {
        while (true) {
            printMenu();
            String choice = readLine("Select option: ");
            if (choice == null) {
                System.out.println("No interactive input detected. Exiting.");
                return;
            }

            try {
                switch (choice.trim()) {
                    case "1" -> addProduct();
                    case "2" -> viewProducts();
                    case "3" -> updateProduct();
                    case "4" -> deleteProduct();
                    case "5" -> addNormalShipment();
                    case "6" -> processNormalShipment();
                    case "7" -> viewWaitingQueue();
                    case "8" -> addUrgentShipment();
                    case "9" -> processUrgentShipment();
                    case "10" -> displaySortedProducts();
                    case "11" -> findShortestRoute();
                    case "12" -> undoLastOperation();
                    case "13" -> viewStockLogs();
                    case "14" -> searchProductById();
                    case "0" -> {
                        System.out.println("Goodbye.");
                        return;
                    }
                    default -> System.out.println("Invalid option.");
                }
            } catch (SQLException exception) {
                System.out.println("Database operation failed: " + exception.getMessage());
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid input: " + exception.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("--- Smart Warehouse Menu ---");
        System.out.println("1. Add Product");
        System.out.println("2. View Products");
        System.out.println("3. Update Product");
        System.out.println("4. Delete Product");
        System.out.println("5. Add Shipment");
        System.out.println("6. Process Normal Shipment");
        System.out.println("7. View Waiting Queue");
        System.out.println("8. Add Urgent Shipment");
        System.out.println("9. Process Urgent Shipment");
        System.out.println("10. Display Sorted Products");
        System.out.println("11. Find Shortest Route");
        System.out.println("12. Undo Last Operation");
        System.out.println("13. View Stock Logs");
        System.out.println("14. Search Product By ID");
        System.out.println("0. Exit");
    }

    private void addProduct() throws SQLException {
        int productId = readNonNegativeInt("Product ID: ");
        if (productHashTable.containsKey(productId) || productDAO.existsById(productId)) {
            System.out.println("Duplicate productId is not allowed.");
            return;
        }

        Product product = readProductFields(productId, null);
        productDAO.addProduct(product);
        productHashTable.put(product.getProductId(), product);
        productTree.insert(product);
        undoStack.push(new UndoAction(OperationType.ADD, null, product));
        recordStockLog(product.getProductId(), "ADD", product.getStockQuantity());
        System.out.println("Product added.");
    }

    private void viewProducts() throws SQLException {
        List<Product> products = productDAO.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }
        products.forEach(System.out::println);
    }

    private void updateProduct() throws SQLException {
        int productId = readNonNegativeInt("Product ID to update: ");
        Product existing = productHashTable.get(productId);
        if (existing == null) {
            System.out.println("Product not found.");
            return;
        }

        Product updated = readProductFields(productId, existing);
        if (productDAO.updateProduct(updated)) {
            reloadProducts();
            undoStack.push(new UndoAction(OperationType.UPDATE, existing, updated));
            recordStockLog(productId, "UPDATE", updated.getStockQuantity() - existing.getStockQuantity());
            System.out.println("Product updated.");
        }
    }

    private void deleteProduct() throws SQLException {
        int productId = readNonNegativeInt("Product ID to delete: ");
        Product existing = productHashTable.get(productId);
        if (existing == null) {
            System.out.println("Product not found.");
            return;
        }

        recordStockLog(productId, "DELETE", -existing.getStockQuantity());
        if (productDAO.deleteProduct(productId)) {
            reloadProducts();
            undoStack.push(new UndoAction(OperationType.DELETE, existing, null));
            System.out.println("Product deleted.");
        }
    }

    private void searchProductById() throws SQLException {
        int productId = readNonNegativeInt("Product ID to search: ");
        Product hashResult = productHashTable.get(productId);
        System.out.println(hashResult == null ? "Hash Table search: not found." : "Hash Table search: " + hashResult);

        List<Product> sorted = MergeSort.sort(productDAO.getAllProducts(),
                Comparator.comparingInt(Product::getProductId));
        Product target = new Product();
        target.setProductId(productId);
        int index = BinarySearch.search(sorted, target, Comparator.comparingInt(Product::getProductId));
        System.out.println(index < 0 ? "Binary Search: not found." : "Binary Search: " + sorted.get(index));
    }

    private void addNormalShipment() throws SQLException {
        String customerName = readRequired("Customer name: ");
        int priority = readIntInRange("Priority (0-6): ", 0, URGENT_PRIORITY_THRESHOLD - 1);
        Shipment shipment = shipmentDAO.addShipment(new Shipment(0, customerName, priority, "WAITING", null));
        shipmentQueue.enqueue(shipment);
        System.out.println("Shipment added to FIFO queue: " + shipment);
    }

    private void processNormalShipment() throws SQLException {
        Shipment shipment = shipmentQueue.dequeue();
        if (shipment == null) {
            System.out.println("Normal shipment queue is empty.");
            return;
        }
        shipmentDAO.updateStatus(shipment.getShipmentId(), "PROCESSED");
        shipment.setStatus("PROCESSED");
        System.out.println("Processed normal shipment: " + shipment);
    }

    private void viewWaitingQueue() {
        if (shipmentQueue.isEmpty()) {
            System.out.println("Normal shipment queue is empty.");
            return;
        }
        shipmentQueue.toList().forEach(System.out::println);
    }

    private void addUrgentShipment() throws SQLException {
        String customerName = readRequired("Customer name: ");
        int priority = readIntInRange("Priority (7-10): ", URGENT_PRIORITY_THRESHOLD, 10);
        Shipment shipment = shipmentDAO.addShipment(new Shipment(0, customerName, priority, "WAITING", null));
        urgentShipmentQueue.insert(shipment);
        System.out.println("Urgent shipment added to priority queue: " + shipment);
    }

    private void processUrgentShipment() throws SQLException {
        Shipment shipment = urgentShipmentQueue.extractMax();
        if (shipment == null) {
            System.out.println("Urgent shipment queue is empty.");
            return;
        }
        shipmentDAO.updateStatus(shipment.getShipmentId(), "PROCESSED");
        shipment.setStatus("PROCESSED");
        System.out.println("Processed urgent shipment: " + shipment);
    }

    private void displaySortedProducts() throws SQLException {
        System.out.println("Sort by: 1) Name 2) Price 3) Stock 4) BST Product ID");
        String option = readRequired("Select sort option: ");
        List<Product> products = productDAO.getAllProducts();
        List<Product> sorted;
        switch (option.trim()) {
            case "1" -> sorted = MergeSort.sort(products,
                    Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER));
            case "2" -> sorted = MergeSort.sort(products, Comparator.comparing(Product::getPrice));
            case "3" -> sorted = MergeSort.sort(products, Comparator.comparingInt(Product::getStockQuantity));
            case "4" -> sorted = productTree.inorderTraversal();
            default -> {
                System.out.println("Invalid sort option.");
                return;
            }
        }
        sorted.forEach(System.out::println);
    }

    private void findShortestRoute() {
        System.out.println("Available nodes: " + String.join(", ", warehouseGraph.getVertices()));
        String start = readRequired("Start node: ");
        String end = readRequired("End node: ");
        if (!warehouseGraph.containsVertex(start) || !warehouseGraph.containsVertex(end)) {
            System.out.println("Unknown warehouse node.");
            return;
        }

        Dijkstra.PathResult result = Dijkstra.shortestPath(warehouseGraph, start, end);
        if (result.path().isEmpty()) {
            System.out.println("No route found.");
            return;
        }
        System.out.println("Shortest route: " + String.join(" -> ", result.path()));
        System.out.println("Total distance: " + result.distance());
    }

    private void undoLastOperation() throws SQLException {
        UndoAction action = undoStack.pop();
        if (action == null) {
            System.out.println("No operation to undo.");
            return;
        }

        switch (action.type()) {
            case ADD -> {
                recordStockLog(action.after().getProductId(), "UNDO_ADD", -action.after().getStockQuantity());
                productDAO.deleteProduct(action.after().getProductId());
            }
            case DELETE -> {
                productDAO.addProduct(action.before());
                recordStockLog(action.before().getProductId(), "UNDO_DELETE", action.before().getStockQuantity());
            }
            case UPDATE -> {
                productDAO.updateProduct(action.before());
                recordStockLog(action.before().getProductId(), "UNDO_UPDATE",
                        action.before().getStockQuantity() - action.after().getStockQuantity());
            }
        }
        reloadProducts();
        System.out.println("Undo completed.");
    }

    private void viewStockLogs() {
        if (stockLogs.isEmpty()) {
            System.out.println("No stock logs in this session.");
            return;
        }
        stockLogs.toList().forEach(System.out::println);
    }

    private Product readProductFields(int productId, Product existing) {
        String name = readRequiredOrDefault("Name" + currentValue(existing == null ? null : existing.getName()) + ": ",
                existing == null ? null : existing.getName());
        String category = readRequiredOrDefault(
                "Category" + currentValue(existing == null ? null : existing.getCategory()) + ": ",
                existing == null ? null : existing.getCategory());
        BigDecimal price = readBigDecimal("Price" + currentValue(existing == null ? null : existing.getPrice()) + ": ",
                existing == null ? null : existing.getPrice());
        int stockQuantity = readNonNegativeInt(
                "Stock quantity" + currentValue(existing == null ? null : existing.getStockQuantity()) + ": ",
                existing == null ? null : existing.getStockQuantity());
        LocalDate expirationDate = readOptionalDate("Expiration date yyyy-MM-dd"
                + currentValue(existing == null ? null : existing.getExpirationDate()) + ": ",
                existing == null ? null : existing.getExpirationDate());
        String shelfId = readRequiredOrDefault(
                "Shelf ID" + currentValue(existing == null ? null : existing.getShelfId()) + ": ",
                existing == null ? null : existing.getShelfId());
        return new Product(productId, name, category, price, stockQuantity, expirationDate, shelfId);
    }

    private void recordStockLog(Integer productId, String action, int quantityChange) throws SQLException {
        StockLog log = new StockLog(0, productId, action, quantityChange, LocalDateTime.now());
        stockLogs.add(log);
        stockLogDAO.addLog(log);
    }

    private String readRequired(String prompt) {
        while (true) {
            String value = readLine(prompt);
            if (value == null) {
                throw new IllegalArgumentException("input stream closed");
            }
            if (!value.trim().isEmpty()) {
                return value.trim();
            }
            System.out.println("Input cannot be empty.");
        }
    }

    private String readRequiredOrDefault(String prompt, String defaultValue) {
        while (true) {
            String value = readLine(prompt);
            if (value == null) {
                throw new IllegalArgumentException("input stream closed");
            }
            if (!value.trim().isEmpty()) {
                return value.trim();
            }
            if (defaultValue != null) {
                return defaultValue;
            }
            System.out.println("Input cannot be empty.");
        }
    }

    private int readNonNegativeInt(String prompt) {
        return readIntInRange(prompt, 0, Integer.MAX_VALUE);
    }

    private int readNonNegativeInt(String prompt, Integer defaultValue) {
        while (true) {
            String value = readLine(prompt);
            if (value == null) {
                throw new IllegalArgumentException("input stream closed");
            }
            if (value.trim().isEmpty() && defaultValue != null) {
                return defaultValue;
            }
            try {
                int parsed = Integer.parseInt(value.trim());
                if (parsed >= 0) {
                    return parsed;
                }
            } catch (NumberFormatException ignored) {
                // Loop until valid input is provided.
            }
            System.out.println("Enter a non-negative integer.");
        }
    }

    private int readIntInRange(String prompt, int min, int max) {
        while (true) {
            String value = readLine(prompt);
            if (value == null) {
                throw new IllegalArgumentException("input stream closed");
            }
            try {
                int parsed = Integer.parseInt(value.trim());
                if (parsed >= min && parsed <= max) {
                    return parsed;
                }
            } catch (NumberFormatException ignored) {
                // Loop until valid input is provided.
            }
            System.out.println("Enter an integer between " + min + " and " + max + ".");
        }
    }

    private BigDecimal readBigDecimal(String prompt, BigDecimal defaultValue) {
        while (true) {
            String value = readLine(prompt);
            if (value == null) {
                throw new IllegalArgumentException("input stream closed");
            }
            if (value.trim().isEmpty() && defaultValue != null) {
                return defaultValue;
            }
            try {
                // Replace comma with dot for decimal parsing (Türkçe locale support)
                String normalizedValue = value.trim().replace(',', '.');
                BigDecimal parsed = new BigDecimal(normalizedValue);
                if (parsed.compareTo(BigDecimal.ZERO) >= 0) {
                    return parsed;
                } else {
                    System.out.println("ERROR: Price cannot be negative! Use positive values like: 19.99 or 19,99");
                }
            } catch (NumberFormatException e) {
                System.out.println(
                        "ERROR: Invalid price format! Use dot or comma as decimal separator (e.g., 19.99 or 19,99)");
            }
        }
    }

    private LocalDate readOptionalDate(String prompt, LocalDate defaultValue) {
        while (true) {
            String value = readLine(prompt);
            if (value == null) {
                throw new IllegalArgumentException("input stream closed");
            }
            if (value.trim().isEmpty()) {
                return defaultValue;
            }
            try {
                return LocalDate.parse(value.trim());
            } catch (DateTimeParseException exception) {
                System.out.println("Enter date as yyyy-MM-dd, or leave blank.");
            }
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            return null;
        }
        return scanner.nextLine();
    }

    private String currentValue(Object value) {
        return value == null ? "" : " [" + value + "]";
    }

    private enum OperationType {
        ADD,
        DELETE,
        UPDATE
    }

    private record UndoAction(OperationType type, Product before, Product after) {
    }
}
