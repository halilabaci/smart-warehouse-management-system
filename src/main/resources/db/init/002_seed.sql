INSERT INTO products (product_id, name, category, price, stock_quantity, expiration_date, shelf_id) VALUES
    (1001, 'Rice 5kg', 'Food', 12.50, 80, '2027-02-01', 'Shelf-A1'),
    (1002, 'Olive Oil 1L', 'Food', 9.99, 45, '2026-11-15', 'Shelf-A2'),
    (1003, 'USB-C Cable', 'Electronics', 4.25, 120, NULL, 'Shelf-A2')
ON CONFLICT (product_id) DO NOTHING;

INSERT INTO shipments (customer_name, priority, status) VALUES
    ('Acme Market', 2, 'WAITING'),
    ('City Hospital', 9, 'WAITING')
ON CONFLICT DO NOTHING;

INSERT INTO warehouse_paths (from_node, to_node, distance) VALUES
    ('Entrance', 'Shelf-A1', 4),
    ('Entrance', 'Shelf-A2', 6),
    ('Shelf-A1', 'PackingStation', 5),
    ('Shelf-A2', 'PackingStation', 3),
    ('PackingStation', 'LoadingDock', 2),
    ('Shelf-A1', 'Shelf-A2', 2)
ON CONFLICT (from_node, to_node) DO NOTHING;
