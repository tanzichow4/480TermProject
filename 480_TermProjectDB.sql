DROP DATABASE IF EXISTS AIRLINE;
CREATE DATABASE AIRLINE; 

DROP DATABASE IF EXISTS BILLING;
CREATE DATABASE BILLING; 

USE AIRLINE;
-- Inside airlineDB

	CREATE TABLE Users (
		user_id INT PRIMARY KEY AUTO_INCREMENT,
		username VARCHAR(50) NOT NULL,
		password VARCHAR(50) NOT NULL,
		email VARCHAR(100) NOT NULL
		-- other user-related columns
	);

	CREATE TABLE Flights (
		flight_id INT PRIMARY KEY AUTO_INCREMENT,
		flight_number VARCHAR(20) NOT NULL,
		departure_location VARCHAR(50) NOT NULL,
		arrival_location VARCHAR(50) NOT NULL,
		departure_time DATETIME NOT NULL,
		arrival_time DATETIME NOT NULL,
        FOREIGN KEY (aircraft_id) REFERENCES Aircrafts(aircraft_id)
		-- other flight-related columns
	);
    
	CREATE TABLE Ticket (
		ticket_id INT PRIMARY KEY AUTO_INCREMENT,
        price DECIMAL(10, 2) NOT NULL,
		user_id INT,
		flight_id INT,
		FOREIGN KEY (user_id) REFERENCES Users(user_id),
		FOREIGN KEY (flight_id) REFERENCES Flights(flight_id)
	);
    
	CREATE TABLE Seats (
		seat_id INT PRIMARY KEY AUTO_INCREMENT,
		flight_id INT,
		seat_number VARCHAR(10) NOT NULL,
		is_available BOOLEAN NOT NULL,
		-- Add other seat-related columns as needed
		FOREIGN KEY (flight_id) REFERENCES Flights(flight_id)
	);

    
    CREATE TABLE Aircrafts (
		aircraft_id INT PRIMARY KEY AUTO_INCREMENT
        
    );
    
USE BILLING;
	-- Inside billingDB

	CREATE TABLE Payments (
		payment_id INT PRIMARY KEY AUTO_INCREMENT,
		user_id INT,
		flight_id INT,
		payment_amount DECIMAL(10, 2) NOT NULL,
		payment_date DATETIME NOT NULL,
		credit_card_number VARCHAR(16) NOT NULL,
		expiration_date DATE NOT NULL,
		-- other payment-related columns
		FOREIGN KEY (user_id) REFERENCES airlineDB.Users(user_id),
		FOREIGN KEY (flight_id) REFERENCES airlineDB.Flights(flight_id)
	);
