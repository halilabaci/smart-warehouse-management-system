CREATE TABLE products (
    product_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(100) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    stock_quantity INTEGER NOT NULL,
    expiration_date DATE,
    shelf_id VARCHAR(50)
);

CREATE TABLE shipments (
    shipment_id VARCHAR(50) PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    priority INTEGER NOT NULL,
    status VARCHAR(50) NOT NULL
);

CREATE TABLE warehouse_paths (
    from_location VARCHAR(50),
    to_location VARCHAR(50),
    distance INTEGER,
    PRIMARY KEY (from_location, to_location)
);
