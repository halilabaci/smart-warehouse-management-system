INSERT INTO products VALUES
('P001', 'Laptop', 'Electronics', 25000, 15, '2027-01-01', 'Shelf-A1'),
('P002', 'Keyboard', 'Electronics', 900, 40, '2027-05-10', 'Shelf-A2'),
('P003', 'Milk', 'Food', 45, 80, '2026-06-01', 'Shelf-C1');

INSERT INTO shipments VALUES
('S001', 'Alice', 2, 'waiting'),
('S002', 'Bob', 5, 'urgent');

INSERT INTO warehouse_paths VALUES
('Entrance', 'Shelf-A1', 5),
('Shelf-A1', 'Shelf-A2', 3),
('Shelf-A2', 'PackingStation', 4),
('PackingStation', 'LoadingDock', 6);
