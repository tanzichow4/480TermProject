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
		price DECIMAL(10, 2) NOT NULL
		-- other flight-related columns
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



