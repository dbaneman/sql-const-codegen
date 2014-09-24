ConstGen
=================

When building apps that interact with a MySQL database, I sometimes find myself thinking, "Gee, wouldn't it be nice if I could reference all these schema, table and column names using constants instead of magic strings?" ConstGen is a quick and dirty solution to that problem. It's a simple Java code generation tool that creates constants from a MySQL schema. 

## Configuration
ConstGen uses Typesafe's config library. To run ConstGen, you'll need to first create a configuration file like the one below (also available in the source code under "resources").

    const-gen {
      db {
        name = dinosaurs    # name of existing database schema
        host = "127.0.0.1"  # MySQL server host
        port = 3306         # MySQL server port 
        username = root     # MySQL server username
        password = "foobar" # MySQL server password
      }
      code {
        namespace = "com.dbaneman.constgen.example"                     # package name of generated Java class
        classname = "DinosaurSchema"                                    # name of generated Java class
        outdir = "/Users/dan/code/sql-const-codegen/src/main/resources" # directory for writing generated .java file
      }
    }
  
## Running ConstGen
Clone the source code and make sure maven is installed. Then create your config file and run the following in the terminal (replacing the sample file path with the actual file path):

    cd ~/code/constgen
    mvn clean
    mvn install
    java -jar target/constgen.jar "/home/jane/code/custom.conf"

## Example
The resources directory in the source code includes an example config file (shown above), a DDL script to generate the example schema, and the resulting generated Java file. Here's the (slightly simplified) table creation script:

    CREATE SCHEMA dinosaurs;
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

And the generated output file looks like this: 

    package com.dbaneman.constgen.example;
    
    public class DinosaurSchema {
    	public static class Dinosaurs {
    		public static final String NAME = "dinosaurs";
    		public static class Dinosaurs1 {
    			public static final String NAME = "dinosaurs.dinosaurs";
    			public static final String ID = "dinosaurs.dinosaurs.id";
    			public static final String SPECIES_NAME = "dinosaurs.dinosaurs.species_name";
    			public static final String EPOCH = "dinosaurs.dinosaurs.epoch";
    			public static final String YEAR_DISCOVERED = "dinosaurs.dinosaurs.year_discovered";
    			public static final String DISCOVERED_BY = "dinosaurs.dinosaurs.discovered_by";
    			public static final String PRIMARY_PREY = "dinosaurs.dinosaurs.primary_prey";
    		}
    		public static class Paleontologists {
    			public static final String NAME = "dinosaurs.paleontologists";
    			public static final String ID = "dinosaurs.paleontologists.id";
    			public static final String LAST_NAME = "dinosaurs.paleontologists.last_name";
    			public static final String FIRST_NAME = "dinosaurs.paleontologists.first_name";
    			public static final String RESEARCH_INSTITUTE = "dinosaurs.paleontologists.research_institute";
    		}
    		public static class ResearchInstitutes {
    			public static final String NAME = "dinosaurs.research_institutes";
    			public static final String ID = "dinosaurs.research_institutes.id";
    			public static final String INSTITUTE_NAME = "dinosaurs.research_institutes.institute_name";
    			public static final String LOCATION = "dinosaurs.research_institutes.location";
    		}
    	}
    }

Voila! Now you've got constants for your MySQL schema. All table names are qualified by the schema name, and all column names are qualified by both the schema and table names (joined by periods). Note that nested class name conflicts (e.g. schema and table with the same name) are resolved by appending a "1" to the table name.  

If you want to try it out, just run example.sql on your MySQL instance to generate this sample schema, modify example.conf to match your connection parameters, and run constgen.jar on the modified config file. 

## Features and future releases
Currently, this tool always generates Java code from a MySQL schema. In the future, I'd like to add support for additional languages and databases. I also plan to add greater configurability (e.g. other naming options besides _\<schema\>.\<table\>.\<column\>_).
