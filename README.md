# Smart Warehouse Management System

Console-based warehouse management project built with Java 21, Maven, PostgreSQL, JDBC, and Docker Compose.

## Features

- PostgreSQL database initialized automatically with Docker Compose
- Product CRUD: add, view, update, delete
- Product search by custom hash table and binary search
- FIFO shipment processing with a custom queue
- Urgent shipment processing with a custom max-heap priority queue
- Product sorting with merge sort and BST inorder traversal
- Warehouse shortest route calculation with graph + Dijkstra
- Undo support for product add, update, and delete operations
- Session stock logs stored in a custom linked list and persisted to `stock_logs`

## Technologies

- Java 21
- Maven
- PostgreSQL 16
- Docker Compose
- JDBC
- Console UI

## Data Structures

- Hash Table: `CustomHashTable`, separate chaining with custom entry nodes
- Queue: `CustomQueue`, FIFO shipment flow
- Priority Queue: `CustomPriorityQueue`, max heap
- Binary Search Tree: `CustomBST`
- Linked List: `CustomLinkedList`, stock log storage
- Stack: `CustomStack`, undo history
- Graph: `CustomGraph`, adjacency list warehouse map

## Algorithms

- Binary Search: product lookup on a sorted product list
- Merge Sort: product sorting by name, price, and stock
- Dijkstra: shortest warehouse path calculation

## Database Schema

- `products(product_id, name, category, price, stock_quantity, expiration_date, shelf_id)`
- `shipments(shipment_id, customer_name, priority, status, created_at)`
- `warehouse_paths(path_id, from_node, to_node, distance)`
- `stock_logs(log_id, product_id, action, quantity_change, log_time)`

Seed data is inserted from `src/main/resources/db/init/002_seed.sql`.

## How to Run

Build and run everything:

```bash
docker compose up --build
```

PostgreSQL is exposed on host port `5433` to avoid conflicts with local PostgreSQL installations. Inside Docker, the app connects to `postgres:5432`.

Run only the database:

```bash
docker compose up -d postgres
```

Run locally if Maven is installed:

```bash
mvn clean package
mvn exec:java
```

## Console Flow

On startup the app loads data into the required structures:

```text
Database connected successfully
Products loaded into Hash Table
Shipments loaded into Queue
Urgent shipments loaded into Priority Queue
Warehouse paths loaded into Graph
Console menu starts
```

## Complexity Analysis

- Hash Table search: average `O(1)`, worst case `O(n)`
- Binary Search: `O(log n)`
- Merge Sort: `O(n log n)`
- Queue enqueue/dequeue: `O(1)`
- Stack push/pop: `O(1)`
- Priority Queue insert/extract max: `O(log n)`
- BST search/insert/delete: average `O(log n)`, worst case `O(n)`
- Dijkstra in this implementation: `O(V^2 + E)`

## Test Checklist

- Product CRUD
- Duplicate product ID validation
- Negative stock validation
- Hash table search
- Binary search
- Merge sort display
- FIFO shipment processing
- Priority shipment processing
- Shortest route calculation
- Undo add/update/delete
- Stock log display
