-- CREATE DATABASE petclinic;

CREATE TABLE IF NOT EXISTS vet(
                                  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                  first_name VARCHAR(45) NOT NULL,
                                  last_name VARCHAR(45) NOT NULL,
                                  specialty VARCHAR(200) NOT NULL
);

INSERT INTO vet(first_name,last_name,specialty) VALUES
                                                    ('James','Carter','none'),
                                                    ('Helen','Leary','radiology'),
                                                    ('Linda','Douglas', 'companion animal'),
                                                    ('Rafael','Ortega','Lab animal medicine');

CREATE TABLE IF NOT EXISTS pet_owner(
                                        id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                        first_name VARCHAR(45) NOT NULL,
                                        last_name VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS pet(
                                  id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                  pet_name VARCHAR(45) NOT NULL,
                                  owner_id int REFERENCES pet_owner(id)
);

INSERT INTO pet_owner(first_name,last_name) VALUES
    ('Joshua','G');

INSERT INTO pet(pet_name,owner_id) VALUES
    ('Milo',1);