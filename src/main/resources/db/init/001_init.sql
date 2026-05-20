CREATE TABLE IF NOT EXISTS products (
    product_id INTEGER PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    price NUMERIC(10, 2) NOT NULL CHECK (price >= 0),
    stock_quantity INTEGER NOT NULL CHECK (stock_quantity >= 0),
    expiration_date DATE,
    shelf_id VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS shipments (
    shipment_id SERIAL PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    priority INTEGER NOT NULL CHECK (priority >= 0),
    status VARCHAR(50) NOT NULL DEFAULT 'WAITING',
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS warehouse_paths (
    path_id SERIAL PRIMARY KEY,
    from_node VARCHAR(100) NOT NULL,
    to_node VARCHAR(100) NOT NULL,
    distance INTEGER NOT NULL CHECK (distance > 0),
    UNIQUE (from_node, to_node)
);

CREATE TABLE IF NOT EXISTS stock_logs (
    log_id SERIAL PRIMARY KEY,
    product_id INTEGER REFERENCES products(product_id) ON DELETE SET NULL,
    action VARCHAR(50) NOT NULL,
    quantity_change INTEGER NOT NULL,
    log_time TIMESTAMP NOT NULL DEFAULT NOW()
);
