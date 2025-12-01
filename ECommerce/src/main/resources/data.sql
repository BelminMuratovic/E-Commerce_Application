INSERT INTO orders (name, address, date, products)
VALUES (
        'Person 1',
        'Main Street 10',
        '2025-01-01',
        '[]'::jsonb
    ),
    (
        'Person 2',
        'Main Street 1',
        '2025-01-03',
        '[{"name":"Apple","quantity":10,"price":2},{"name":"Banana","quantity":5,"price":3}]'::jsonb
    );
INSERT INTO products (
        type,
        name,
        quantity,
        price,
        image
    )
VALUES ('Fruits', 'Banana', 100, 3, 'banana.jpg'),
    ('Fruits', 'Apple', 1000, 2, 'apple.jpg'),
    ('Fruits', 'Orange', 100, 4, 'orange.jpg'),
    ('Vegetables', 'Tomato', 100, 3, 'tomato.jpg');