  -- §I´«­q³æ
  CREATE TABLE Redeem (
  redeem_id INT IDENTITY(1,1) PRIMARY KEY,
  member_id INT,
  redeem_status NVARCHAR(20),
  create_at DATE,
  is_deleted BIT DEFAULT 0,  -- ³n§R°£
  FOREIGN KEY (member_id) REFERENCES Member(member_id) ON DELETE SET NULL
);