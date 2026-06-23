-- Run this in MySQL Workbench or terminal before starting the app

CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);

INSERT INTO users (username, password) VALUES ('admin', 'admin123');

CREATE TABLE IF NOT EXISTS books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    author VARCHAR(100) NOT NULL,
    genre VARCHAR(100) NOT NULL,
    price DOUBLE NOT NULL,
    quantity INT NOT NULL,
    added_date DATE NOT NULL
);

-- Sample data
INSERT INTO books (title, author, genre, price, quantity, added_date) VALUES
('Clean Code',               'Robert C. Martin', 'Programming',  499.00, 5,  '2023-01-10'),
('The Alchemist',            'Paulo Coelho',      'Fiction',      299.00, 8,  '2022-06-15'),
('Atomic Habits',            'James Clear',       'Self-Help',    399.00, 3,  '2023-03-20'),
('Data Structures in Java',  'Mark Allen Weiss',  'Programming',  650.00, 0,  '2021-11-05'),
('Wings of Fire',            'A.P.J. Abdul Kalam','Biography',    250.00, 10, '2022-09-01');
