DROP SCHEMA IF EXISTS dinosaurs;
CREATE SCHEMA dinosaurs;
USE dinosaurs;
CREATE TABLE research_institutes (
	id INT PRIMARY KEY,
	institute_name VARCHAR(50),
	location VARCHAR(50)
);
CREATE TABLE paleontologists (
    id INT PRIMARY KEY,
    last_name VARCHAR(50),
    first_name VARCHAR(50),
	research_institute INT,
	FOREIGN KEY (research_institute) REFERENCES research_institutes(id) ON DELETE CASCADE
);
CREATE TABLE dinosaurs (
    id INT PRIMARY KEY,
    species_name VARCHAR(50),
    epoch VARCHAR(30),
    year_discovered YEAR,
	discovered_by INT,
	primary_prey INT,
    FOREIGN KEY (discovered_by) REFERENCES paleontologists(id) ON DELETE CASCADE,
	FOREIGN KEY (primary_prey) REFERENCES paleontologists(id) ON DELETE CASCADE
);