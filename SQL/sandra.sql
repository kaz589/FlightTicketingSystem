
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




--�ӫ~���O��()
CREATE TABLE Category (
	category_id INT IDENTITY(1,1)	 PRIMARY KEY,
	category_name	NVARCHAR(100)	NOT NULL
);
INSERT INTO Category(category_name)
   VALUES ('wine');

select*from Category;

--�ӫ~��
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
	 VALUES (1,'���d','a bottle of ���d',20000,30,'���d.jpg');
	 
select*from Products;

delete from products
--where product_name='���d';




  -- �I���q��
  CREATE TABLE Redeem (
  redeem_id INT IDENTITY(1,1) PRIMARY KEY,
  member_id INT,
  redeem_status NVARCHAR(20),
  create_at DATE,
  is_deleted BIT DEFAULT 0,  -- �n�R��
  redeem_total_miles int,
  FOREIGN KEY (member_id) REFERENCES Member(member_id) ON DELETE SET NULL
);

INSERT INTO Redeem (member_id, redeem_status, create_at) 
VALUES (1, '�B�z��', GETDATE()
);
SELECT * FROM Redeem;

-- �I������
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


--�榡����ܰӫ~ID
SELECT
'W'+RIGHT('0000'+CAST(product_id AS VARCHAR),4)AS formatted_id,
	
	product_name,
	needmiles
	from Products;

--�ӫ~�޲z
SELECT 
 c.category_name,'W'+RIGHT('0000'+CAST(p.product_id AS VARCHAR),4)AS formatted_id
 ,p.product_name,p.product_desc,p.needmiles,p.quantity	,p.product_image
 FROM Products p JOIN Category c ON p.category_id=c.category_id;


 --�ʪ���
 CREATE TABLE cart (
    cart_id INT PRIMARY KEY IDENTITY(1,1),
    member_id INT NOT NULL,
    created_at DATETIME DEFAULT GETDATE()
    FOREIGN KEY (member_id) REFERENCES member(member_id)
);
insert into cart (member_id)values(1);
select*from cart;

--�ʪ�����
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


--�d�Y���q�檺�Ҧ��ӫ~
select  ri.redeem_item_id,    -- �I�����Ӫ� ID
    ri.quantity,              -- �ӫ~�ƶq
    p.product_name,           -- �ӫ~�W��
    p.needmiles               -- �I�����ӫ~�һݪ����{��
from
Redeem r
Join Redeem_item ri ON r.redeem_id = ri.redeem_id
Join Products p On  ri.product_id = p. product_id 
WHERE 
    r.redeem_id = 1           -- ���w�q�� ID�A�o�̬O 1
    AND r.is_deleted = 0;     -- �u�d�ߥ��Q�R�����q��
	



--��identity�q�Y�}�l
--DBCC CHECKIDENT ('Products', RESEED, 0);

ALTER TABLE redeem
ALTER COLUMN create_at DATETIME;





