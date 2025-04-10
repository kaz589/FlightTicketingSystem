CREATE TABLE cities (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100),
    country NVARCHAR(100),
    latitude DECIMAL(5, 2),
    longitude DECIMAL(5, 2)
);