DROP DATABASE IF EXISTS AIRLINE;
CREATE DATABASE AIRLINE; 

DROP DATABASE IF EXISTS BILLING;
CREATE DATABASE BILLING; 

USE AIRLINE;
	-- Inside airlineDB

	-- Create Users table
	CREATE TABLE Users (
		user_id INT PRIMARY KEY AUTO_INCREMENT,
		username VARCHAR(50) NOT NULL,
		password VARCHAR(50) NOT NULL,
		email VARCHAR(100) NOT NULL,
        is_member BOOLEAN NOT NULL
		-- other user-related columns
	);
    
	-- Create MemberPerks table
	CREATE TABLE MemberPerks (
		user_id INT,
		credit_card VARCHAR(16), -- Assuming credit card is a string for simplicity
		free_ticket BOOLEAN,
		FOREIGN KEY (user_id) REFERENCES Users(user_id),
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
        FOREIGN KEY (aircraft_id) REFERENCES Aircrafts(aircraft_id)
		-- other flight-related columns
	);
    
	-- Create Ticket table
	CREATE TABLE Ticket (
		ticket_id INT PRIMARY KEY AUTO_INCREMENT,
        price DECIMAL(10, 2) NOT NULL,
		user_id INT,
		flight_id INT,
		FOREIGN KEY (user_id) REFERENCES Users(user_id),
		FOREIGN KEY (flight_id) REFERENCES Flights(flight_id)
	);
    
	-- Create Seats table
	CREATE TABLE Seats (
		seat_id INT PRIMARY KEY AUTO_INCREMENT,
		flight_id INT,
		seat_number VARCHAR(10) NOT NULL,
		is_available BOOLEAN NOT NULL,
		-- Add other seat-related columns as needed
		FOREIGN KEY (flight_id) REFERENCES Flights(flight_id)
	);

	-- Create Aircrafts table
    	CREATE TABLE Aircrafts (
		aircraft_id INT PRIMARY KEY AUTO_INCREMENT
        
    	);
    
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
		FOREIGN KEY (user_id) REFERENCES airlineDB.Users(user_id),
		FOREIGN KEY (flight_id) REFERENCES airlineDB.Flights(flight_id)
	);



