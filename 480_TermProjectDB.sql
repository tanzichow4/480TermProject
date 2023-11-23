/* F23 ENSF 480 Term Project Databases
 
 */
DROP DATABASE IF EXISTS AIRLINE;
CREATE DATABASE AIRLINE; 

DROP DATABASE IF EXISTS BILLING;
CREATE DATABASE BILLING; 

USE AIRLINE;
	-- Inside airlineDB

	-- Create Aircrafts table
    	CREATE TABLE Aircrafts (
		aircraft_id INT PRIMARY KEY AUTO_INCREMENT,
		aircraft_name VARCHAR(50) NOT NULL
    	);
    
    	-- Insert sample data into Aircrafts table
	INSERT INTO Aircrafts (aircraft_name) VALUES
		('Boeing 747'),
		('Airbus A320');
        
	-- Create Users table
	CREATE TABLE Users (
		user_id INT PRIMARY KEY AUTO_INCREMENT,
		username VARCHAR(50) NOT NULL,
		pass VARCHAR(50) NOT NULL,
		email VARCHAR(100) NOT NULL,
        is_member BOOLEAN NOT NULL
		-- other user-related columns
	);
    
    	INSERT INTO Users (username, pass, email, is_member) VALUES
		('Alice123', '1234', 'alice@example.com', TRUE),
		('Bob456', '1234', 'bob@example.com', TRUE),
		('Charlie789', '1234', 'charlie@example.com', TRUE),
		('David010', '1234', 'david@example.com', TRUE),
		('Eve112', '1234', 'eve@example.com', TRUE);
    
	-- Create MemberPerks table
	CREATE TABLE MemberPerks (
		user_id INT,
		credit_card VARCHAR(16), -- Assuming credit card is a string for simplicity
		free_ticket BOOLEAN,
		FOREIGN KEY (user_id) REFERENCES Users(user_id),
		PRIMARY KEY (user_id)
	);
    
    	INSERT INTO MemberPerks (username, pass, email, is_member) VALUES
		('Alice123', '1234hello', 'alice@example.com', FALSE),
		('Bob456', '1234hello', 'bob@example.com', FALSE),
		('Charlie789', '1234hello', 'charlie@example.com', TRUE),
		('David010', '1234hello', 'david@example.com', TRUE),
		('Eve112', '1234hello', 'eve@example.com', TRUE);
    
    	-- Insert corresponding data into MemberPerks table
	INSERT INTO MemberPerks (user_id, credit_card, free_ticket)
	SELECT user_id, '1234', TRUE
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
        
	FOREIGN KEY (aircraft_id) REFERENCES Aircrafts(aircraft_id)
		-- other flight-related columns
	);
    
    	-- Insert sample data into Flights table
	INSERT INTO Flights (flight_number, departure_location, arrival_location, departure_time, arrival_time, aircraft_id)
	VALUES
		('FL123', 'Chicago', 'New York', '2023-11-23 08:00:00', '2023-11-23 10:00:00', 1),
		('FL456', 'Calgary', 'Quebec', '2023-11-23 12:00:00', '2023-11-23 14:00:00', 2);

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
