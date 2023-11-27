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
    aircraft_name VARCHAR(50) NOT NULL,
    number_of_seats INT NOT NULL
);

-- Insert sample data into Aircrafts table
INSERT INTO Aircrafts (aircraft_name, number_of_seats)
VALUES
    ('Boeing 747', 416),
    ('Airbus A320', 170);

-- Create Users table
CREATE TABLE RegisteredUsers (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    pass VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    userType INT NOT NULL,
    -- other user-related columns
);

INSERT INTO RegisteredUsers (username, pass, email, userType) VALUES
    ('Alice123', '1234', 'alice@example.com', 0),
    ('Bob456', '1234', 'bob@example.com', 0),
    ('Charlie789', '1234', 'charlie@example.com', 0),
    ('David010', '1234', 'david@example.com', 1),
    ('Eve112', '1234', 'eve@example.com', 1);

-- Create Promos table
CREATE TABLE Promos (
    user_id INT,
    credit_card VARCHAR(16), -- Assuming credit card is a string for simplicity
    free_ticket_code INT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    PRIMARY KEY (user_id)
);

INSERT INTO Promos (user_id, credit_card, free_ticket_code)
SELECT user_id, '1234', 1111
FROM Users
WHERE is_member = TRUE;

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
    flight_id INT,
    seat_number VARCHAR(10) NOT NULL,
    booked BOOLEAN NOT NULL,
    seat_type VARCHAR(20) NOT NULL,
    -- Add other seat-related columns as needed
    FOREIGN KEY (flight_id) REFERENCES Flights(flight_id)
);

-- Assuming all seats are initially available
INSERT INTO Seats (flight_id, seat_number, booked, seat_type)
SELECT Flights.flight_id, SEAT_NUMBER.seat_number, FALSE, 'Economy'
FROM Flights
CROSS JOIN (
    SELECT ones + tens + units AS seat_number
    FROM (
        SELECT 
            0 AS ones, 10 AS tens, units
        FROM
            (SELECT 0 AS units UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) AS units_table
    ) AS SEAT_NUMBER
    WHERE ones + tens + units <= (SELECT MAX(number_of_seats) FROM Aircrafts)
) AS SEAT_NUMBER;

-- Create Tickets table
CREATE TABLE Tickets (
    ticket_id INT PRIMARY KEY AUTO_INCREMENT,
    price DECIMAL(10, 2) NOT NULL,
    user_id INT,
    flight_id INT,
    seat_id INT,
    FOREIGN KEY (seat_id) REFERENCES Seats(seat_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (flight_id) REFERENCES Flights(flight_id)
);

-- Insert sample data into Tickets table
INSERT INTO Tickets (flight_id, user_id, seat_id, price)
VALUES
    (1, 1, 1, 150.00),
    (2, 2, 2, 200.00);

    
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
		FOREIGN KEY (user_id) REFERENCES AIRLINE.Users(user_id),
		FOREIGN KEY (flight_id) REFERENCES AIRLINE.Flights(flight_id)
	);
    
CREATE USER 'user'@'localhost' IDENTIFIED BY 'password';

GRANT ALL PRIVILEGES ON AIRLINE.* TO 'user'@'localhost';

GRANT ALL PRIVILEGES ON BILLING.* TO 'user'@'localhost';

FLUSH PRIVILEGES;