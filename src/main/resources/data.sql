-- Dummy Data for Product API Presentation
-- This script provides sample data for demonstrating CRUD operations

-- Electronics Category
INSERT INTO products (name, description, price, stock) VALUES
('iPhone 15 Pro Max', 'Latest iPhone with titanium design and A17 Pro chip. Features 48MP camera system and Action button.', 19999999, 25),
('Samsung Galaxy S24 Ultra', 'Flagship Android phone with S Pen, 200MP camera, and AI-powered features.', 18999999, 30),
('MacBook Pro 16"', 'Powerful laptop with M3 Pro chip, 18GB RAM, and 512GB SSD. Perfect for developers.', 45999999, 15),
('Dell XPS 13', 'Ultrabook with Intel Core i7, 16GB RAM, and 13.4-inch InfinityEdge display.', 24999999, 20),
('iPad Air', 'Versatile tablet with M2 chip, 10.9-inch Liquid Retina display, and Apple Pencil support.', 12999999, 35),
('Sony WH-1000XM5', 'Premium noise-canceling wireless headphones with 30-hour battery life.', 5999999, 50),
('Apple Watch Series 9', 'Smartwatch with advanced health features and S9 SiP chip.', 7999999, 40),
('Nintendo Switch OLED', 'Gaming console with 7-inch OLED screen and enhanced audio.', 4999999, 25);

-- Fashion & Accessories Category
INSERT INTO products (name, description, price, stock) VALUES
('Nike Air Jordan 1', 'Classic basketball shoes with premium leather construction and iconic design.', 2499999, 45),
('Ray-Ban Aviator', 'Timeless sunglasses with gold frame and gradient lenses.', 2999999, 60),
('Levi''s 501 Original Fit', 'Classic straight-leg jeans made from premium denim.', 1499999, 80),
('Coach Shoulder Bag', 'Luxurious leather handbag with brass hardware and multiple compartments.', 8999999, 20),
('Casio G-Shock', 'Rugged digital watch with shock resistance and 200M water resistance.', 1599999, 55),
('Adidas Ultraboost 22', 'Running shoes with responsive Boost midsole and Primeknit upper.', 2199999, 70);

-- Home & Living Category
INSERT INTO products (name, description, price, stock) VALUES
('Philips Air Fryer', 'Digital air fryer with rapid air technology for healthy cooking.', 1899999, 30),
('IKEA MALM Bed Frame', 'Modern bed frame made of sustainable oak with clean design.', 2999999, 15),
('Dyson V15 Detect', 'Cordless vacuum cleaner with laser dust detection and LCD screen.', 8999999, 25),
('Bose Smart Speaker 500', 'Premium smart speaker with Alexa built-in and 360-degree sound.', 3999999, 35),
('Tefal Non-Stick Pan Set', '3-piece cookware set with ceramic coating and even heat distribution.', 899999, 50);

-- Books & Stationery Category
INSERT INTO products (name, description, price, stock) VALUES
('Clean Code', 'Robert Martin''s handbook of agile software craftsmanship.', 750000, 100),
('The Pragmatic Programmer', 'Essential reading for software developers and programmers.', 699999, 85),
('Moleskine Classic Notebook', 'Premium hardcover notebook with acid-free paper.', 399999, 200),
('Fountain Pen Set', 'Elegant fountain pen with ink bottles and leather case.', 1299999, 40),
('Programming Python', 'Comprehensive guide to Python programming by Mark Lutz.', 899999, 60);

-- Sports & Fitness Category
INSERT INTO products (name, description, price, stock) VALUES
('Yoga Mat Premium', 'Non-slip exercise mat with extra cushioning for comfort.', 499999, 100),
('Dumbbell Set', 'Adjustable dumbbells with weight range from 5kg to 25kg.', 1999999, 30),
('Treadmill Electric', 'Motorized treadmill with incline and heart rate monitor.', 8999999, 20),
('Resistance Bands Set', '5-piece resistance bands set for strength training.', 299999, 150),
('Smart Fitness Watch', 'Fitness tracker with GPS, heart rate monitor, and 7-day battery.', 1499999, 80);

-- Food & Beverages Category
INSERT INTO products (name, description, price, stock) VALUES
('Arabica Coffee Beans', 'Premium single-origin coffee beans from Ethiopian highlands.', 299999, 200),
('Organic Honey', 'Pure raw honey harvested from sustainable bee farms.', 149999, 180),
('Artisan Chocolate Set', 'Luxury chocolate collection from Belgian chocolatiers.', 499999, 75),
('Green Tea Premium', 'Organic matcha green tea powder from Japan.', 399999, 120),
('Wine Collection', 'Curated selection of red and white wines from vineyards worldwide.', 2599999, 45);