/* F23 ENSF 480 Term Project Databases
 
 */
DROP DATABASE IF EXISTS BILLING;
CREATE DATABASE BILLING; 

DROP DATABASE IF EXISTS AIRLINE;
CREATE DATABASE AIRLINE; 

USE AIRLINE;

-- Create Aircrafts table
CREATE TABLE Aircrafts (
    aircraft_id INT PRIMARY KEY AUTO_INCREMENT,
    aircraft_name VARCHAR(50) NOT NULL
);

-- Insert sample data into Aircrafts table
INSERT INTO Aircrafts (aircraft_name)
VALUES
    ('Boeing 747'),
    ('Airbus A320');

-- Create Users table
CREATE TABLE RegisteredUsers (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    pass VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    userType INT NOT NULL,
    isLoggedIn BOOLEAN
    -- other user-related columns
);

INSERT INTO RegisteredUsers (username, pass, email, userType, isLoggedIn) VALUES
    ('Alice123', '1234', 'alice@example.com', 0, FALSE),
    ('Bob456', '1234', 'bob@example.com', 0, FALSE),
    ('Charlie789', '1234', 'charlie@example.com', 0, FALSE),
    ('David010', '1234', 'david@example.com', 1, FALSE),
    ('Eve112', '1234', 'eve@example.com', 1, FALSE);


CREATE TABLE Promos (
    user_id INT,
    promo_code VARCHAR(8) NOT NULL,
    discount_ammount INT,
    used BOOLEAN,
    FOREIGN KEY (user_id) REFERENCES RegisteredUsers(user_id),
    PRIMARY KEY (user_id)
);

-- Create Flights table
CREATE TABLE Flights (
    flight_id INT PRIMARY KEY AUTO_INCREMENT,
    flight_number VARCHAR(20) NOT NULL,
    departure_location VARCHAR(50) NOT NULL,
    arrival_location VARCHAR(50) NOT NULL,
    departure_time DATETIME NOT NULL,
    arrival_time DATETIME NOT NULL,
    aircraft_id INT,
    base_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (aircraft_id) REFERENCES Aircrafts(aircraft_id)
    -- other flight-related columns
);

-- Insert sample data into Flights table
INSERT INTO Flights (flight_number, departure_location, arrival_location, departure_time, arrival_time, aircraft_id, base_price)
VALUES
    ('FL123', 'Chicago', 'New York', '2023-11-23 08:00:00', '2023-11-23 10:00:00', 1, 100.00),
    ('FL456', 'Calgary', 'Quebec', '2023-11-23 12:00:00', '2023-11-23 14:00:00', 2, 150.00);

-- Create Seats table
CREATE TABLE Seats (
    seat_id INT PRIMARY KEY AUTO_INCREMENT,
    -- flight_id INT,
    seat_row ENUM('A', 'B', 'C', 'D', 'E', 'F'), 
    seat_number VARCHAR(10),
    seat_type ENUM('Ordinary', 'Business', 'Comfort'),
    flight_id INT,
	booked BOOLEAN,
	FOREIGN KEY (flight_id) REFERENCES FLIGHTS(flight_id)
    -- Add other seat-related columns as needed
    -- FOREIGN KEY (flight_id) REFERENCES Flights(flight_id)
);

-- Assuming all seats are initially available
INSERT INTO Seats (seat_id, seat_row, seat_number, seat_type, booked, flight_id) VALUES
(1, 'A', '1', 'Business', FALSE, 1),
(2, 'A', '2', 'Business', FALSE, 1),
(3, 'B', '3', 'Business', FALSE, 1),
(4, 'B', '4', 'Business', FALSE, 1),
(5, 'C', '5', 'Comfort', FALSE, 1),
(6, 'C', '6', 'Comfort', FALSE, 1),
(7, 'D', '7', 'Comfort', FALSE, 1),
(8, 'D', '8', 'Comfort', FALSE, 1),
(9, 'E', '9', 'Ordinary', FALSE, 1),
(10, 'E', '10', 'Ordinary', FALSE, 1),
(11, 'F', '11', 'Ordinary', FALSE, 1),
(12, 'F', '12', 'Ordinary', FALSE, 1),
(13, 'A', '1', 'Business', FALSE, 2),
(14, 'A', '2', 'Business', FALSE, 2),
(15, 'B', '3', 'Business', FALSE, 2),
(16, 'B', '4', 'Business', FALSE, 2),
(17, 'C', '5', 'Comfort', FALSE, 2),
(18, 'C', '6', 'Comfort', FALSE, 2),
(19, 'D', '7', 'Comfort', FALSE, 2),
(20, 'D', '8', 'Comfort', FALSE, 2),
(21, 'E', '9', 'Ordinary', FALSE, 2),
(22, 'E', '10', 'Ordinary', FALSE, 2),
(23, 'F', '11', 'Ordinary', FALSE, 2),
(24, 'F', '12', 'Ordinary', FALSE, 2);

-- Create Tickets table
CREATE TABLE Tickets (
    ticket_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    flight_id INT,
    seat_id INT,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (seat_id) REFERENCES Seats(seat_id),
    FOREIGN KEY (user_id) REFERENCES RegisteredUsers(user_id),
    FOREIGN KEY (flight_id) REFERENCES Flights(flight_id)
);

-- Insert sample data into Tickets table
INSERT INTO Tickets (flight_id, user_id, seat_id, price)
VALUES
    (1, 1, 1, 100.00),
    (2, 2, 2, 100.00);

USE BILLING;
	-- Inside billingDB

	-- Create Payments table
	CREATE TABLE Payments (
		payment_id INT PRIMARY KEY AUTO_INCREMENT,
		user_id INT,
		flight_id INT,
		payment_amount DECIMAL(10, 2) NOT NULL,
		payment_date DATETIME NOT NULL,
		credit_card_number VARCHAR(16) NOT NULL,
		expiration_date DATE NOT NULL,
		-- other payment-related columns
		FOREIGN KEY (user_id) REFERENCES AIRLINE.RegisteredUsers(user_id),
		FOREIGN KEY (flight_id) REFERENCES AIRLINE.Flights(flight_id)
	);
    
-- Drop the existing user (use with caution)
DROP USER 'user'@'localhost';
    
CREATE USER 'user'@'localhost' IDENTIFIED BY 'password';

GRANT ALL PRIVILEGES ON AIRLINE.* TO 'user'@'localhost';

GRANT ALL PRIVILEGES ON BILLING.* TO 'user'@'localhost';

FLUSH PRIVILEGES;