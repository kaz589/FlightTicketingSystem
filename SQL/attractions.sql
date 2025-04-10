CREATE TABLE attractions (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
	category NVARCHAR(255),
    description NVARCHAR(MAX),
    address NVARCHAR(255),
    city_id INT NOT NULL,
    latitude DECIMAL(5, 2),
    longitude DECIMAL(5, 2),
	rating DECIMAL(2, 1) CHECK (rating >= 0 AND rating <= 5)
    CONSTRAINT FK_attractions_city FOREIGN KEY (city_id) REFERENCES cities(ID)
);
