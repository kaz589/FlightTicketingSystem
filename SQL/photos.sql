CREATE TABLE photos (
    id INT IDENTITY(1,1) PRIMARY KEY,
    attraction_id INT,
    url VARCHAR(500),
    caption VARCHAR(255),
    FOREIGN KEY (attraction_id) REFERENCES attractions(id) ON DELETE CASCADE
);