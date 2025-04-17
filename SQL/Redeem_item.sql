CREATE TABLE Redeem_Item (
  redeem_item_id INT IDENTITY(1,1) PRIMARY KEY,
  redeem_id INT,
  product_id INT,
  quantity INT NOT NULL,
  used_miles INT NOT NULL,
  FOREIGN KEY (redeem_id) REFERENCES Redeem(redeem_id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) REFERENCES Products(product_id) ON DELETE CASCADE
);