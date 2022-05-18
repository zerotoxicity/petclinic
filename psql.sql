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
