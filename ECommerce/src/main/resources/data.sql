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
        image_name,
        image_type,
        pic_byte
    )
VALUES ('Fruits', 'Banana', 100, 3, NULL, NULL, NULL),
    ('Fruits', 'Apple', 1000, 2, NULL, NULL, NULL),
    ('Fruits', 'Orange', 100, 4, NULL, NULL, NULL),
    ('Vegetables', 'Tomato', 100, 3, NULL, NULL, NULL);