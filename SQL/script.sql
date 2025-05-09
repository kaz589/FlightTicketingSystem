USE [Easytrip]
GO
ALTER TABLE [dbo].[attractions] DROP CONSTRAINT [CK__attractio__ratin__4CA06362]
GO
ALTER TABLE [dbo].[photos] DROP CONSTRAINT [FK__photos__attracti__4F7CD00D]
GO
ALTER TABLE [dbo].[favourites] DROP CONSTRAINT [FK_Favorites_Member]
GO
ALTER TABLE [dbo].[favourites] DROP CONSTRAINT [FK_Favorites_Attraction]
GO
ALTER TABLE [dbo].[attractions] DROP CONSTRAINT [FK_attractions_city]
GO
ALTER TABLE [dbo].[members] DROP CONSTRAINT [DF__members__phone_v__59FA5E80]
GO
ALTER TABLE [dbo].[members] DROP CONSTRAINT [DF__members__email_v__59063A47]
GO
ALTER TABLE [dbo].[members] DROP CONSTRAINT [DF__members__registr__5812160E]
GO
ALTER TABLE [dbo].[members] DROP CONSTRAINT [DF__members__remaini__571DF1D5]
GO
ALTER TABLE [dbo].[members] DROP CONSTRAINT [DF__members__total_m__5629CD9C]
GO
ALTER TABLE [dbo].[favourites] DROP CONSTRAINT [DF__favourite__creat__656C112C]
GO
/****** Object:  Table [dbo].[photos]    Script Date: 02/05/2025 11:37:12 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[photos]') AND type in (N'U'))
DROP TABLE [dbo].[photos]
GO
/****** Object:  Table [dbo].[members]    Script Date: 02/05/2025 11:37:12 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[members]') AND type in (N'U'))
DROP TABLE [dbo].[members]
GO
/****** Object:  Table [dbo].[favourites]    Script Date: 02/05/2025 11:37:12 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[favourites]') AND type in (N'U'))
DROP TABLE [dbo].[favourites]
GO
/****** Object:  Table [dbo].[cities]    Script Date: 02/05/2025 11:37:12 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[cities]') AND type in (N'U'))
DROP TABLE [dbo].[cities]
GO
/****** Object:  Table [dbo].[attractions]    Script Date: 02/05/2025 11:37:12 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[attractions]') AND type in (N'U'))
DROP TABLE [dbo].[attractions]
GO
/****** Object:  Table [dbo].[attractions]    Script Date: 02/05/2025 11:37:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[attractions](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NOT NULL,
	[category] [nvarchar](255) NULL,
	[description] [nvarchar](max) NULL,
	[address] [nvarchar](255) NULL,
	[city_id] [int] NOT NULL,
	[latitude] [decimal](5, 2) NULL,
	[longitude] [decimal](5, 2) NULL,
	[rating] [decimal](2, 1) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[cities]    Script Date: 02/05/2025 11:37:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cities](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](100) NULL,
	[country] [nvarchar](100) NULL,
	[latitude] [decimal](5, 2) NULL,
	[longitude] [decimal](5, 2) NULL,
	[image] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[favourites]    Script Date: 02/05/2025 11:37:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[favourites](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[member_id] [int] NOT NULL,
	[city_id] [int] NOT NULL,
	[created_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[members]    Script Date: 02/05/2025 11:37:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[members](
	[member_id] [int] IDENTITY(1,1) NOT NULL,
	[full_name] [nvarchar](100) NULL,
	[username] [nvarchar](50) NOT NULL,
	[password] [nvarchar](100) NOT NULL,
	[email] [nvarchar](100) NULL,
	[total_miles] [int] NULL,
	[remaining_miles] [int] NULL,
	[phone_number] [nvarchar](20) NULL,
	[registration_date] [datetime] NULL,
	[email_verified] [bit] NULL,
	[phone_verified] [bit] NULL,
	[membership_level] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[member_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[photos]    Script Date: 02/05/2025 11:37:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[photos](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[attraction_id] [int] NULL,
	[url] [varchar](500) NULL,
	[caption] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[attractions] ON 

INSERT [dbo].[attractions] ([id], [name], [category], [description], [address], [city_id], [latitude], [longitude], [rating]) VALUES (1, N'台北 101', N'["建築物","購物中心"]', N'台北 101 是台灣最高建築，共 101 層樓高，在這裡除了可以欣賞到壯觀的景色之外，還可以逛逛高級精品服飾店，包括 Gucci、Prada 和 Louis Vuitton。 地下樓層還有鼎泰豐。', N'11049 台灣 台北 信義區市府路45號', 1, CAST(25.02 AS Decimal(5, 2)), CAST(121.34 AS Decimal(5, 2)), CAST(4.1 AS Decimal(2, 1)))
INSERT [dbo].[attractions] ([id], [name], [category], [description], [address], [city_id], [latitude], [longitude], [rating]) VALUES (5, N'陽明山國家公園', N'["國家公園"]', N'陽明山國家公園因受緯度及海拔之影響，氣候分屬亞熱帶氣候區與暖溫帶氣候區，且季風型氣候極為明顯。

春季2、3月，陽明山上天氣乍暖還寒，冬天的茶花、梅花陸續凋謝，山櫻、杜鵑、華八仙、臺北堇菜、山寶鐸、烏皮九芎等緊接登場，高大的喬木也抽出嫩芽，紅、粉、白、黃、綠……繽紛的色彩一掃隆冬的陰霾、單調，而將大地粧點得分外動人。夏季在西南季風的吹拂下，午後偶有雷陣雨，霧雨初晴時，山區常可見「虹橋跨立山谷」的景緻，使雨後的陽明山更加亮眼。

當秋季來臨的10月份，白背芒形成一片隨風搖曳的花海；稍晚，楓紅點綴枝頭，樹葉片片金黃，交織成一幅盛名遠播的「大屯秋色」。冬季時因受東北季風影響，陽明山區經常寒風細雨，低溫高濕，雲霧瀰漫，別具一番景緻；若遇強烈寒流來襲，七星山、竹子山、大屯山一帶偶可見白雪紛飛，成為瑞雪覆蓋的銀白世界。', N'112 台灣 北投區竹子湖1-20号', 1, CAST(25.19 AS Decimal(5, 2)), CAST(121.56 AS Decimal(5, 2)), CAST(4.3 AS Decimal(2, 1)))
INSERT [dbo].[attractions] ([id], [name], [category], [description], [address], [city_id], [latitude], [longitude], [rating]) VALUES (6, N'中正紀念堂', N'["景點和地標, 紀念碑和雕像"]', N'中正紀念堂是位於中華民國臺北市中正區的國家紀念建築，是中華民國政府為紀念已故前總統蔣中正而興建，為眾多紀念蔣中正的建築中規模最大者，管理機關為中華民國文化部轄下的「國立中正紀念堂管理處」。全區250,000平方公尺，紀念堂主堂體高70公尺；園區廣場前方兩廳院稱為「藝文廣場」，廣場南北側另建有國家戲劇院以及國家音樂廳，合稱「國家兩廳院」，由國家表演藝術中心管理。廣場除了供民眾休憩外，也是大型藝文活動的場地，常舉辦展覽及表演；邦交國元首訪臺歡迎儀式（軍禮）也在此舉行。園區主體建築、大道、廣場及牌樓等以「臺灣民主紀念園區」之名義，由行政院文化建設委員會（今文化部）指定為國定古蹟（與臺灣民主紀念館更名事件有關）；東門、中山南路部分路段及園區以「中正紀念堂」之名義由臺北市政府登錄為文化景觀[4]。', N'1004 台灣 台北 中正區 中山南路21號', 1, CAST(25.21 AS Decimal(5, 2)), CAST(121.31 AS Decimal(5, 2)), CAST(4.4 AS Decimal(2, 1)))
INSERT [dbo].[attractions] ([id], [name], [category], [description], [address], [city_id], [latitude], [longitude], [rating]) VALUES (7, N'淺草寺', N'["宗教景點"]', N'淺草寺，正式名稱為金龍山淺草寺，位於日本東京都台東區淺草二丁目，是東京都內歷史最悠久的寺院。山號為金龍山。供奉的本尊是聖觀音。原屬天台宗，於第二次世界大戰後獨立，成為聖觀音宗的總本山。觀音菩薩本尊通稱為「淺草觀音」。', N'111-0032 東京都 台東區 Asakusa 淺草2-3-1', 14, CAST(35.43 AS Decimal(5, 2)), CAST(139.47 AS Decimal(5, 2)), CAST(4.4 AS Decimal(2, 1)))
INSERT [dbo].[attractions] ([id], [name], [category], [description], [address], [city_id], [latitude], [longitude], [rating]) VALUES (8, N'心齋橋', N'["景點和地標"]', N'心齋橋（心斎橋）是位於日本大阪府大阪市中央區的商業購物區。一連排的百貨商店與各式專門店等為大阪代表性的中心商務區。狹義上是指大阪中心部向南北延伸御堂筋東側的心齋橋筋，但一般來講會包含到以大阪市營地下鐵心齋橋站為中心的御堂筋西側。以流行時裝發信地而馳名的美國村，是高級品牌專門店、時裝店及咖啡店的集中地。歷史心齋橋原為橫跨長堀川的一條橋名稱，在元和8年(1622年)長堀川開鑿時建造。當時的心齋橋是長18間（約35米）、闊2間半（約4米）的木橋。明治6年(1873年)改建成鐵橋，是當時日本罕有的建築。明治42年(1909年)，石造的心齋橋落成啟用。昭和39年(1964年)，長堀川被填平，陸橋被移築。', N'542-0085 大阪府 大阪 Chuo 中央区', 18, CAST(34.67 AS Decimal(5, 2)), CAST(135.50 AS Decimal(5, 2)), CAST(4.2 AS Decimal(2, 1)))
INSERT [dbo].[attractions] ([id], [name], [category], [description], [address], [city_id], [latitude], [longitude], [rating]) VALUES (9, N'上海外灘', N'["景觀和地標, ","景觀徒步區"]', N'外灘，上海的長廊，百餘年來，一直作為上海的象徵出現在世人面前。它是上海人心目中的驕傲，它向世人充分展示了上海的文化，以及將外來文明與本土文明有機揉合、創新、發展的卓越能力。外灘面對開闊的母親河----黃浦江，背倚造型嚴謹、風格迥異的建築群。由於其獨特的地理位置及近百年來在經濟活動領域對上海乃至中國的影響，使其具有十分豐富的文化內涵。外灘的江面、長堤、綠化帶及美輪美奐的建築群所構成的街景，是最具有特徵的上海景觀。早晨，外灘是人們的健身的場所；白天，它是繁華熱鬧的遊覽勝地；晚上，則是情侶的戀愛天地。每當華燈初上之時，外灘各棟建築物上燈光輝煌，一座座犹如水晶宮似的，令海內外遊客讚歎不已', N'中國 上海市 黃浦區 外灘', 16, CAST(31.24 AS Decimal(5, 2)), CAST(121.49 AS Decimal(5, 2)), CAST(4.6 AS Decimal(2, 1)))
INSERT [dbo].[attractions] ([id], [name], [category], [description], [address], [city_id], [latitude], [longitude], [rating]) VALUES (10, N' 伏見稻荷大社', N'["古跡","宗教景點"]', N'伏見稻荷大社是一座位於日本京都市伏見區內的神社，是遍及日本全國各地約三萬所的稻荷神社之總本社，也是近畿地方初詣參拜者最多的社寺。以境內所擁有的「千本鳥居」聞名。', N'612-0882 京都府 京都 伏见區 深草薮之内町68', 19, CAST(34.58 AS Decimal(5, 2)), CAST(135.46 AS Decimal(5, 2)), CAST(4.6 AS Decimal(2, 1)))
INSERT [dbo].[attractions] ([id], [name], [category], [description], [address], [city_id], [latitude], [longitude], [rating]) VALUES (11, N'景福宮', N'["古跡","歷史博物館"]', N'景福宮位於韓國首爾市，興建於1395年，至今已有600多年歷史，是韓鮮王朝五大宮殿中規模最大的正宮，五大宮殿包含：景福宮、昌德宮、昌慶宮、德壽宮和慶熙宮，景福宮因地處於首爾較北側的位置，因此又有"北闕"之稱，1592-1598年期間景福宮受戰事影響多數建築物均遭毀損，直至朝鮮高宗時期才逐漸修復，目前所見的宮殿大多為重建後的建築物。

景福宮佔地面積約有15萬坪，為正方型宮殿格局，正門為光化門，北邊為神武門、東為建春門、西為迎秋門，宮內主要建築有勤政殿、思政殿、千秋殿、萬春殿、交泰殿、修政殿、慶會樓、香遠亭、青瓦台等多座建築物與花園樓亭，其中勤政殿是朝鮮王朝舉行大典或召開宮廷宴會的地方，慶會樓四面環水的優美景色，則是招待外賓擺設國宴的使用場所。目前在景福宮內還建有國立古宮博物院與國立民俗博物館，介紹韓國傳統王室文化與韓國古代生活習俗和使用器具、韓服等，持景福宮門票即可免費入場參觀。韓國文化與歷史都融合在這些古代宮殿中，是首爾文化旅遊理想的地點之一。', N'韓國 首爾特別市 鐘路區 社稷路161號', 15, CAST(37.34 AS Decimal(5, 2)), CAST(126.58 AS Decimal(5, 2)), CAST(4.4 AS Decimal(2, 1)))
INSERT [dbo].[attractions] ([id], [name], [category], [description], [address], [city_id], [latitude], [longitude], [rating]) VALUES (12, N'故宮博物院', N'["建築物","博物館"]', N'故宮博物院，又稱北京故宮，是位於中華人民共和國北京紫禁城內的博物館。民國元年（1912年）清帝溥儀退位後仍居於宮內。至民國十三年（1924年）十一月五日，黃郛攝政內閣公布修正清室優待條件第五條，廢除皇帝尊號，並將溥儀驅逐出宮。政府代表李煜瀛、京師警衛司令鹿鍾麟、警察總監張璧，於六日接管皇宮，封存文物。後經攝政內閣核准，經一年之整理，故宮博物院於民國十四年國慶節（1925年10月10日）正式成立開幕。它位於北京中軸線的中心，佔地面積72萬平方米，建築面積約15萬平方米；始建於明成祖朱棣永樂四年（1406年），永樂十八年（1420年）落成；藏品也主要是以明、清兩代皇宮及其收藏為基礎。', N'100009 中國 北京市 東城區 景山前街4號', 17, CAST(39.91 AS Decimal(5, 2)), CAST(116.40 AS Decimal(5, 2)), CAST(4.4 AS Decimal(2, 1)))
INSERT [dbo].[attractions] ([id], [name], [category], [description], [address], [city_id], [latitude], [longitude], [rating]) VALUES (13, N'濱海灣花園', N'["花園","景點和地標"]', N'濱海灣花園是位於新加坡濱海灣中央的公園，於2012年落成，佔地101公頃，全土興建於填海土地上，毗連濱海蓄水池。

興建濱海灣花園的構思開始於2006年，期望可以促進新加坡由花園城市進化為「花園中的城市」', N'18 Marina Gardens Drive Supertree Grove, 新加坡', 20, CAST(1.17 AS Decimal(5, 2)), CAST(103.52 AS Decimal(5, 2)), CAST(4.7 AS Decimal(2, 1)))
SET IDENTITY_INSERT [dbo].[attractions] OFF
GO
SET IDENTITY_INSERT [dbo].[cities] ON 

INSERT [dbo].[cities] ([id], [name], [country], [latitude], [longitude], [image]) VALUES (1, N'台北', N'台灣', CAST(25.03 AS Decimal(5, 2)), CAST(121.57 AS Decimal(5, 2)), N'/images/Taipei.jpg')
INSERT [dbo].[cities] ([id], [name], [country], [latitude], [longitude], [image]) VALUES (14, N'東京', N'日本', CAST(35.68 AS Decimal(5, 2)), CAST(139.65 AS Decimal(5, 2)), N'/images/Tokyo.jpg')
INSERT [dbo].[cities] ([id], [name], [country], [latitude], [longitude], [image]) VALUES (15, N'首爾', N'韓國', CAST(37.55 AS Decimal(5, 2)), CAST(127.00 AS Decimal(5, 2)), N'/images/Seoul.jpg')
INSERT [dbo].[cities] ([id], [name], [country], [latitude], [longitude], [image]) VALUES (16, N'上海', N'中國', CAST(31.23 AS Decimal(5, 2)), CAST(121.47 AS Decimal(5, 2)), N'/images/Shanghai.jpg')
INSERT [dbo].[cities] ([id], [name], [country], [latitude], [longitude], [image]) VALUES (17, N'北京', N'中國', CAST(39.90 AS Decimal(5, 2)), CAST(116.41 AS Decimal(5, 2)), N'/images/Beijing.jpg')
INSERT [dbo].[cities] ([id], [name], [country], [latitude], [longitude], [image]) VALUES (18, N'大阪', N'日本', CAST(34.69 AS Decimal(5, 2)), CAST(135.50 AS Decimal(5, 2)), N'/images/Osaka.jpg')
INSERT [dbo].[cities] ([id], [name], [country], [latitude], [longitude], [image]) VALUES (19, N'京都', N'日本', CAST(35.12 AS Decimal(5, 2)), CAST(135.77 AS Decimal(5, 2)), N'/images/Kyoto.jpg')
INSERT [dbo].[cities] ([id], [name], [country], [latitude], [longitude], [image]) VALUES (20, N'新加坡', N'新加坡', CAST(1.21 AS Decimal(5, 2)), CAST(103.49 AS Decimal(5, 2)), N'/images/Singapore.jpg')
INSERT [dbo].[cities] ([id], [name], [country], [latitude], [longitude], [image]) VALUES (21, N'吉隆坡', N'馬來西亞', CAST(3.13 AS Decimal(5, 2)), CAST(101.68 AS Decimal(5, 2)), N'/images/KualaLumpur.jpg')
INSERT [dbo].[cities] ([id], [name], [country], [latitude], [longitude], [image]) VALUES (22, N'札幌', N'日本', CAST(43.06 AS Decimal(5, 2)), CAST(141.35 AS Decimal(5, 2)), N'/images/Sapporo.jpg')
SET IDENTITY_INSERT [dbo].[cities] OFF
GO
SET IDENTITY_INSERT [dbo].[favourites] ON 

INSERT [dbo].[favourites] ([id], [member_id], [city_id], [created_at]) VALUES (31, 1001, 1, NULL)
INSERT [dbo].[favourites] ([id], [member_id], [city_id], [created_at]) VALUES (32, 1001, 14, NULL)
INSERT [dbo].[favourites] ([id], [member_id], [city_id], [created_at]) VALUES (33, 1001, 16, NULL)
INSERT [dbo].[favourites] ([id], [member_id], [city_id], [created_at]) VALUES (34, 1001, 20, NULL)
SET IDENTITY_INSERT [dbo].[favourites] OFF
GO
SET IDENTITY_INSERT [dbo].[members] ON 

INSERT [dbo].[members] ([member_id], [full_name], [username], [password], [email], [total_miles], [remaining_miles], [phone_number], [registration_date], [email_verified], [phone_verified], [membership_level]) VALUES (1001, N'Alice Chen', N'alicec', N'Passw0rd!', N'alice@example.com', 12000, 5000, N'0912345678', CAST(N'2024-01-15T00:00:00.000' AS DateTime), 1, 1, N'Gold')
INSERT [dbo].[members] ([member_id], [full_name], [username], [password], [email], [total_miles], [remaining_miles], [phone_number], [registration_date], [email_verified], [phone_verified], [membership_level]) VALUES (1002, N'Bob Lin', N'boblin', N'Test1234', N'bob@example.com', 8000, 3000, N'0922333444', CAST(N'2023-11-02T00:00:00.000' AS DateTime), 1, 1, N'Silver')
INSERT [dbo].[members] ([member_id], [full_name], [username], [password], [email], [total_miles], [remaining_miles], [phone_number], [registration_date], [email_verified], [phone_verified], [membership_level]) VALUES (1003, N'Cathy Wang', N'cathyw', N'Hello123', N'cathy@example.com', 15000, 15000, N'0911222333', CAST(N'2024-03-22T00:00:00.000' AS DateTime), 1, 0, N'Platinum')
INSERT [dbo].[members] ([member_id], [full_name], [username], [password], [email], [total_miles], [remaining_miles], [phone_number], [registration_date], [email_verified], [phone_verified], [membership_level]) VALUES (1004, N'David Wu', N'davidwu', N'WuD2024!', N'david@example.com', 5000, 2000, N'0933444555', CAST(N'2023-09-12T00:00:00.000' AS DateTime), 0, 0, N'Bronze')
INSERT [dbo].[members] ([member_id], [full_name], [username], [password], [email], [total_miles], [remaining_miles], [phone_number], [registration_date], [email_verified], [phone_verified], [membership_level]) VALUES (1005, N'Emily Tsai', N'emilyt', N'EmilyPass1', N'emily@example.com', 9500, 1000, N'0955666777', CAST(N'2024-02-10T00:00:00.000' AS DateTime), 1, 1, N'Gold')
INSERT [dbo].[members] ([member_id], [full_name], [username], [password], [email], [total_miles], [remaining_miles], [phone_number], [registration_date], [email_verified], [phone_verified], [membership_level]) VALUES (1006, N'Frank Yeh', N'franky', N'FrankYeh@123', N'frank@example.com', 4000, 4000, N'0988776655', CAST(N'2023-12-01T00:00:00.000' AS DateTime), 0, 1, N'Silver')
INSERT [dbo].[members] ([member_id], [full_name], [username], [password], [email], [total_miles], [remaining_miles], [phone_number], [registration_date], [email_verified], [phone_verified], [membership_level]) VALUES (1007, N'Grace Lee', N'gracelee', N'Grace!789', N'grace@example.com', 20000, 18000, N'0977665544', CAST(N'2024-04-01T00:00:00.000' AS DateTime), 1, 1, N'Platinum')
INSERT [dbo].[members] ([member_id], [full_name], [username], [password], [email], [total_miles], [remaining_miles], [phone_number], [registration_date], [email_verified], [phone_verified], [membership_level]) VALUES (1008, N'Henry Kao', N'henryk', N'HenrY987', N'henry@example.com', 7000, 3500, N'0933777888', CAST(N'2023-10-20T00:00:00.000' AS DateTime), 1, 0, N'Bronze')
INSERT [dbo].[members] ([member_id], [full_name], [username], [password], [email], [total_miles], [remaining_miles], [phone_number], [registration_date], [email_verified], [phone_verified], [membership_level]) VALUES (1009, N'Ivy Huang', N'ivyh', N'IvyH@321', N'ivy@example.com', 11000, 6000, N'0922888999', CAST(N'2024-01-30T00:00:00.000' AS DateTime), 1, 1, N'Gold')
INSERT [dbo].[members] ([member_id], [full_name], [username], [password], [email], [total_miles], [remaining_miles], [phone_number], [registration_date], [email_verified], [phone_verified], [membership_level]) VALUES (1010, N'Jason Liu', N'jliu', N'JasonPW1', N'jason@example.com', 3000, 1000, N'0966554433', CAST(N'2023-11-25T00:00:00.000' AS DateTime), 0, 1, N'Silver')
SET IDENTITY_INSERT [dbo].[members] OFF
GO
SET IDENTITY_INSERT [dbo].[photos] ON 

INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (1, 1, N'/images/Taipei-101-01.jpg', N'台北 101')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (8, 1, N'/images/Taipei-101-02.jpg', N'台北 101')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (9, 1, N'/images/Taipei-101-03.jpg', N'台北 101')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (10, 5, N'/images/Yangmingshan_01.jpg', N'陽明山國家公園')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (11, 5, N'/images/Yangmingshan_02.jpg', N'陽明山國家公園')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (12, 5, N'/images/Yangmingshan_03.jpg', N'陽明山國家公園')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (13, 6, N'/images/cks_memorial_01.jpg', N'中正紀念堂')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (14, 6, N'/images/cks_memorial_02.jpg', N'中正紀念堂')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (15, 6, N'/images/cks_memorial_03.jpg', N'中正紀念堂')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (16, 7, N'/images/Asakusa-01.jpg', N'淺草寺')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (17, 7, N'/images/Asakusa-02.jpg', N'淺草寺')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (18, 7, N'/images/Asakusa-03.jpg', N'淺草寺')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (19, 8, N'/images/Shinsaibashisuji-01.jpg', N'心齋橋')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (20, 8, N'/images/Shinsaibashisuji-02.jpg', N'心齋橋')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (21, 8, N'/images/Shinsaibashisuji-03.jpg', N'心齋橋')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (22, 9, N'/images/ShanghaiBund-01.jpg', N'上海外灘')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (23, 9, N'/images/ShanghaiBund-03.jpg', N'上海外灘')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (24, 9, N'/images/ShanghaiBund-02.jpg', N'上海外灘')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (25, 10, N'/images/FushimiInari-01.jpg', N' 伏見稻荷大社')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (26, 10, N'/images/FushimiInari-02.jpg', N' 伏見稻荷大社')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (27, 10, N'/images/FushimiInari-03.jpg', N' 伏見稻荷大社')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (28, 11, N'/images/GyeongBok-01.jpg', N'景福宮')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (29, 11, N'/images/GyeongBok-02.jpg', N'景福宮')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (30, 11, N'/images/GyeongBok-03.jpg', N'景福宮')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (31, 12, N'/images/Gugong-01.jpg', N'故宮博物院')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (32, 12, N'/images/Gugong-02.jpg', N'故宮博物院')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (33, 12, N'/images/Gugong-03.jpg', N'故宮博物院')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (34, 13, N'/images/SingaporeGarden-01.jpg', N'濱海灣花園')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (35, 13, N'/images/SingaporeGarden-02.jpg', N'濱海灣花園')
INSERT [dbo].[photos] ([id], [attraction_id], [url], [caption]) VALUES (36, 13, N'/images/SingaporeGarden-03.jpg', N'濱海灣花園')
SET IDENTITY_INSERT [dbo].[photos] OFF
GO
ALTER TABLE [dbo].[favourites] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[members] ADD  DEFAULT ((0)) FOR [total_miles]
GO
ALTER TABLE [dbo].[members] ADD  DEFAULT ((0)) FOR [remaining_miles]
GO
ALTER TABLE [dbo].[members] ADD  DEFAULT (getdate()) FOR [registration_date]
GO
ALTER TABLE [dbo].[members] ADD  DEFAULT ((0)) FOR [email_verified]
GO
ALTER TABLE [dbo].[members] ADD  DEFAULT ((0)) FOR [phone_verified]
GO
ALTER TABLE [dbo].[attractions]  WITH CHECK ADD  CONSTRAINT [FK_attractions_city] FOREIGN KEY([city_id])
REFERENCES [dbo].[cities] ([id])
GO
ALTER TABLE [dbo].[attractions] CHECK CONSTRAINT [FK_attractions_city]
GO
ALTER TABLE [dbo].[favourites]  WITH CHECK ADD  CONSTRAINT [FK_Favorites_Attraction] FOREIGN KEY([city_id])
REFERENCES [dbo].[cities] ([id])
GO
ALTER TABLE [dbo].[favourites] CHECK CONSTRAINT [FK_Favorites_Attraction]
GO
ALTER TABLE [dbo].[favourites]  WITH CHECK ADD  CONSTRAINT [FK_Favorites_Member] FOREIGN KEY([member_id])
REFERENCES [dbo].[members] ([member_id])
GO
ALTER TABLE [dbo].[favourites] CHECK CONSTRAINT [FK_Favorites_Member]
GO
ALTER TABLE [dbo].[photos]  WITH CHECK ADD FOREIGN KEY([attraction_id])
REFERENCES [dbo].[attractions] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[attractions]  WITH CHECK ADD CHECK  (([rating]>=(0) AND [rating]<=(5)))
GO
