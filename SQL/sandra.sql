
	CREATE TABLE Member(
	member_id INT IDENTITY(1,1) PRIMARY KEY,
	full_name NVARCHAR(100) ,
	username VARCHAR(50),
	password VARCHAR(255),
	email VARCHAR(255),
	total_miles INT,
	remaining_miles INT,
	phone_number VARCHAR(20),
	registration_date DATETIME,
	email_verified Bit,
	phone_verified Bit,
	membership_level VARCHAR(20),
);

Insert into Member (username,total_miles,remaining_miles)values('Sandra2644',20000,10000);
select*from member;




--商品類別表()
CREATE TABLE Category (
	category_id INT IDENTITY(1,1)	 PRIMARY KEY,
	category_name	NVARCHAR(100)	NOT NULL
);
INSERT INTO Category(category_name)
   VALUES ('wine');

select*from Category;

--商品表
CREATE TABLE Products (
	product_id INT IDENTITY(1,1)		PRIMARY KEY,
	category_id	INT	,	
	product_name NVARCHAR(100) NOT NULL,
	product_desc NVARCHAR(400),
	needmiles  INT	NOT NULL,
	quantity	 INT	 NOT NULL,
	product_image NVARCHAR(250),
	FOREIGN KEY (category_id) REFERENCES  Category (category_id)ON DELETE SET NULL
);
INSERT Products(category_id,product_name,product_desc,needmiles,quantity	,product_image)
	 VALUES (1,'高粱','a bottle of 高粱',20000,30,'高粱.jpg');
	 
select*from Products;

delete from products
--where product_name='高粱';




  -- 兌換訂單
  CREATE TABLE Redeem (
  redeem_id INT IDENTITY(1,1) PRIMARY KEY,
  member_id INT,
  redeem_status NVARCHAR(20),
  create_at DATE,
  is_deleted BIT DEFAULT 0,  -- 軟刪除
  redeem_total_miles int,
  FOREIGN KEY (member_id) REFERENCES Member(member_id) ON DELETE SET NULL
);

INSERT INTO Redeem (member_id, redeem_status, create_at) 
VALUES (1, '處理中', GETDATE()
);
SELECT * FROM Redeem;

-- 兌換明細
CREATE TABLE Redeem_Item (
  redeem_item_id INT IDENTITY(1,1) PRIMARY KEY,
  redeem_id INT,
  product_id INT,
  quantity INT NOT NULL,
  used_miles INT NOT NULL,
  FOREIGN KEY (redeem_id) REFERENCES Redeem(redeem_id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) REFERENCES Products(product_id) ON DELETE CASCADE
);

Insert into Redeem_Item (redeem_id,product_id,quantity,used_miles)values(1,5,1,9000);
SELECT * FROM Redeem_Item;


--格式化顯示商品ID
SELECT
'W'+RIGHT('0000'+CAST(product_id AS VARCHAR),4)AS formatted_id,
	
	product_name,
	needmiles
	from Products;

--商品管理
SELECT 
 c.category_name,'W'+RIGHT('0000'+CAST(p.product_id AS VARCHAR),4)AS formatted_id
 ,p.product_name,p.product_desc,p.needmiles,p.quantity	,p.product_image
 FROM Products p JOIN Category c ON p.category_id=c.category_id;


 --購物車
 CREATE TABLE cart (
    cart_id INT PRIMARY KEY IDENTITY(1,1),
    member_id INT NOT NULL,
    created_at DATETIME DEFAULT GETDATE()
    FOREIGN KEY (member_id) REFERENCES member(member_id)
);
insert into cart (member_id)values(1);
select*from cart;

--購物項目
CREATE TABLE cart_item (
    cart_item_id INT PRIMARY KEY IDENTITY(1,1),
    cart_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    added_at DATETIME DEFAULT GETDATE(),

    FOREIGN KEY (cart_id) REFERENCES cart(cart_id),
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);
insert into cart_item (cart_id,product_id,quantity)values(1,3,10);

select*from cart_item;


--查某筆訂單的所有商品
select  ri.redeem_item_id,    -- 兌換明細的 ID
    ri.quantity,              -- 商品數量
    p.product_name,           -- 商品名稱
    p.needmiles               -- 兌換此商品所需的里程數
from
Redeem r
Join Redeem_item ri ON r.redeem_id = ri.redeem_id
Join Products p On  ri.product_id = p. product_id 
WHERE 
    r.redeem_id = 1           -- 指定訂單 ID，這裡是 1
    AND r.is_deleted = 0;     -- 只查詢未被刪除的訂單
	



--讓identity從頭開始
--DBCC CHECKIDENT ('Products', RESEED, 0);

ALTER TABLE redeem
ALTER COLUMN create_at DATETIME;





