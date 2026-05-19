# smart-warehouse-management-system

Console-based Smart Warehouse Inventory and Shipment Management System built with Java 17, Maven, PostgreSQL (JDBC), and Docker Compose.

## Structure

- `model`: Product, Shipment, Shelf, StockLog starter classes
- `datastructure`: custom data structure starter classes
- `algorithm`: BinarySearch, MergeSort, Dijkstra starter algorithm classes
- `database`: JDBC connection utility
- `service`: starter service layer classes
- `ui`: console entrypoint and app shell

## Run PostgreSQL with Docker Compose

```bash
docker compose up -d db
```

## Run the app locally

```bash
mvn exec:java
```

## Run app + database with Docker Compose

```bash
docker compose up --build
```
