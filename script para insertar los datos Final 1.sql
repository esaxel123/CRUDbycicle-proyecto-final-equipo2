-- ***************************************
-- 1. Multi_Shop (20 Registros)
-- ***************************************
INSERT INTO Multi_Shop ( Location_name, Address, Contact_Name, Email_Address, Phone_Number, Manager_Name, Number_of_Employees, Additional_Services) VALUES
( 'Tienda Central Hermosillo', 'Av. Principal #100, Col. Centro', 'Ana López', 'ana.lopez@multi.com', '6621234567', 'Luis García', 8, 'Basic Adjustment'),
( 'Sucursal Sur Guaymas', 'Calle 5 #201, Col. Miramar', 'Pedro Martínez', 'pedro.m@multi.com', '6229876543', 'Marta Soto', 5, 'Helmet or Lock Rental'),
( 'Punto de Alquiler Norte', 'Blvd. Industrial #50, Zona Norte', NULL, NULL, NULL, 'Javier Peña', 3, 'General Repairs'), -- Contacto NULL, usa DEFAULT
( 'Taller de Servicio Oeste', 'Carr. Costera Km 10', 'Diego Fernández', 'diego.f@multi.com', '6624445678', NULL, 10, 'Accessory Installation'), -- Manager NULL
( 'Tienda Playa San Carlos', 'Av. Turística Lote 5', 'Eva Blanco', 'eva.b@multi.com', '6221112233', 'Roberto Morales', 4, 'Bike Wash'),
( 'Almacén Principal', 'Callejón Industrial #15', NULL, NULL, '6627778899', 'Sofía Ramos', 12, 'Bike Storage'), -- No Contact Name/Email
( 'Módulo Temporal Centro', 'Plaza Cívica Kiosko 3', 'Gloria Herrero', 'gloria.h@multi.com', '6623334455', 'Andrés Vega', 2, 'Tire Inflation'),
( 'Tienda Express Nuevo León', 'Av. Fundadores #300', 'Hugo Ibáñez', 'hugo.i@multi.com', '8112345678', 'Patricia Díaz', 6, 'Chain Lubrication'),
( 'Punto Renta La Paz', 'Malecón #1', NULL, 'irene.j@multi.com', NULL, 'Miguel Ángel', 7, 'Basic Adjustment'), -- Phone NULL
( 'Servicio a Domicilio Base', 'Calle de Servicio #500', 'Jorge Cano', 'jorge.c@multi.com', '6621002000', 'Ricardo Solís', 15, 'Roadside Assistance'),
( 'Tienda Outlet', 'Blvd. Descuentos #1', 'Karla Luna', 'karla.l@multi.com', '6622223344', 'Gabriel Neri', 9, 'General Repairs'),
( 'Renta Express Terminal', 'Calle Veloz #10', NULL, NULL, NULL, 'Hilda Soto', 3, 'Helmet or Lock Rental'),
( 'Tienda de Montaña', 'Carretera Sierra Km 5', 'Mónica Nieto', 'monica.n@multi.com', '6621115599', 'Ismael Ríos', 5, 'Battery Charging'),
( 'Sucursal Playa', 'Av. Palmeras #10', 'Néstor Ochoa', 'nestor.o@multi.com', '6225556677', 'Julieta Lara', 4, 'Bike Wash'),
( 'Punto Ciclismo Urbano', 'Calle Ciclovía #1', 'Olivia Paz', 'olivia.p@multi.com', '6629991122', 'Kevin Cruz', 6, 'Tire Inflation'),
( 'Taller Rápido', 'Zona Industrial Lote 8', 'Pablo Quintero', 'pablo.q@multi.com', '6624567890', NULL, 8, 'Accessory Installation'), -- Manager NULL
( 'Tienda Boutique', 'Av. Moda #50', 'Raquel Salas', 'raquel.s@multi.com', '6621012020', 'Marcos Rey', 5, 'Chain Lubrication'),
( 'Módulo Express', 'Calle Corta #15', NULL, 'sergio.t@multi.com', '6623034040', 'Nuria Vega', 2, 'Basic Adjustment'),
( 'Oficina Corporativa', 'Torre Ejecutiva Piso 10', 'Tania Urbina', 'tania.u@multi.com', '6625056060', 'Óscar Gil', 10, 'General Repairs'),
( 'Tienda Sur', 'Blvd. Sur #100', 'Vicente Varela', 'vicente.v@multi.com', '6627078080', 'Paula Reyes', 7, 'Bike Storage');

---

-- ***************************************
-- 2. Bicycles (20 Registros)
-- ***************************************
INSERT INTO Bicycles (Bicycle_ID, Brand, Model, Year_Manufacture, Type, Color, Frame_Size, Maintenance_Status, Last_Service_Date) VALUES
(101, 'Trek', 'Marlin 5', 2023, 'Mountain', 'Negro', 'M', 'Excellent', '2025-09-01'),
(102, 'Giant', 'Contend AR 4', 2024, 'Road', 'Azul', 'L', 'Good', '2025-08-15'),
(103, 'Specialized', 'Como 3.0', 2023, 'Electric', 'Blanco', 'S', 'Excellent', '2025-10-05'),
(104, 'Cannondale', 'Quick CX 3', 2022, 'Hybrid', 'Verde', 'M', 'Fair', '2025-07-20'),
(105, 'GT', 'Performer', 2024, 'BMX', 'Rojo', NULL, 'Excellent', '2025-10-10'), -- Frame_Size NULL
(106, 'Trek', 'FX 1 Disc', 2025, 'Hybrid', 'Gris', 'L', 'Good', '2025-10-25'),
(107, 'Giant', 'Talon 2', 2023, 'Mountain', NULL, 'S', 'Excellent', '2025-09-15'), -- Color NULL
(108, 'Specialized', 'Allez Sport', 2022, 'Road', 'Negro', 'M', 'Needs Repair', '2025-06-01'), -- Requiere reparación
(109, 'Cannondale', 'Bad Boy 3', 2024, 'Other', 'Matte Black', 'L', 'Good', '2025-08-01'),
(110, 'GT', 'Aggressor Pro', 2023, 'Mountain', 'Rojo', 'M', 'Excellent', '2025-09-20'),
(111, 'Trek', 'Domane AL 2', 2025, 'Road', 'Amarillo', 'S', 'Good', '2025-10-18'),
(112, 'Giant', 'Escape 3', 2024, 'Hybrid', 'Plata', NULL, 'Excellent', '2025-10-01'),
(113, 'Specialized', 'Kenevo SL', 2023, 'Electric', 'Verde Lima', 'M', 'Fair', '2025-07-05'),
(114, 'Cannondale', 'Trail 6', 2022, 'Mountain', 'Azul Marino', 'L', 'Good', '2025-08-25'),
(115, 'GT', 'Mach One', 2025, 'BMX', NULL, 'OS', 'Excellent', '2025-10-22'),
(116, 'Trek', 'Verve 1 Disc', 2024, 'Hybrid', 'Morado', 'S', 'Good', '2025-09-10'),
(117, 'Giant', 'Defy Advanced', 2023, 'Road', 'Negro/Rojo', 'M', 'Excellent', '2025-10-03'),
(118, 'Specialized', 'Rockhopper', 2022, 'Mountain', 'Blanco', 'L', 'Fair', '2025-07-15'),
(119, 'Cannondale', 'Quick 4', 2024, 'Hybrid', 'Gris Claro', 'M', 'Excellent', '2025-10-28'),
(120, 'GT', 'Street Performer', 2023, 'BMX', 'Azul', 'OS', 'Good', '2025-08-08');

---

-- ***************************************
-- 3. Bicycles_in_Shops (20 Registros)
-- 15 Registros con DateTime_Out (Movimiento Concluido), 5 en NULL (Activo)
-- ***************************************
INSERT INTO Bicycles_in_Shops (DateTime_In, DateTime_Out, Current_Status, Multi_Shop_ID, Bicycle_ID, Inventory_Responsible_Person) VALUES
('2025-10-01', '2025-10-02', 'Available', 1, 101, 'Ana López'),
('2025-09-20', '2025-09-25', 'Available', 1, 102, 'Ana López'),
('2025-08-15', '2025-08-18', 'Maintenance', 1, 103, NULL),
('2025-10-15', '2025-10-20', 'Rented', 1, 104, 'Ana López'),
('2025-09-05', '2025-09-08', 'Rented', 2, 105, 'Pedro Martínez'),
('2025-07-01', '2025-07-05', 'Maintenance', 2, 106, NULL),
('2025-10-15', NULL, 'Available', 3, 107, 'Javier Peña'), 			-- ACTIVO (Disponible)
('2025-09-01', '2025-10-10', 'Maintenance', 3, 108, 'Javier Peña'),
('2025-10-03', '2025-10-04', 'Available', 4, 109, 'Diego Fernández'),
('2025-10-20', '2025-10-21', 'Rented', 4, 110, NULL),
('2025-10-28', NULL, 'Rented', 5, 111, 'Eva Blanco'), 				-- ACTIVO (Rentada)
('2025-08-10', '2025-08-15', 'Available', 5, 112, 'Eva Blanco'),
('2025-10-12', '2025-10-25', 'Maintenance', 6, 113, 'Sofía Ramos'),
('2025-09-05', '2025-10-05', 'Available', 7, 114, 'Andrés Vega'),
('2025-10-22', '2025-10-23', 'Rented', 8, 115, 'Hugo Ibáñez'),
('2025-10-08', NULL, 'Rented', 9, 116, 'Miguel Ángel'), 			-- ACTIVO (Rentada)
('2025-09-07', '2025-09-10', 'Maintenance', 10, 117, 'Jorge Cano'),
('2025-10-18', NULL, 'Available', 11, 118, 'Karla Luna'), 			-- ACTIVO (Disponible)
('2025-08-01', NULL, 'Maintenance', 12, 119, NULL), 				-- ACTIVO (Mantenimiento)
('2025-10-27', '2025-10-30', 'Rented', 13, 120, 'Mónica Nieto');

---

-- ***************************************
-- 4. Ref_Payment_Status 
-- ***************************************
INSERT INTO Ref_Payment_Status (Payment_Status_Code, Payment_Status_Description) VALUES
('PAID', 'Paid'),
('PEND', 'Pending'),
('OVRD', 'Overdue'),
('CANC', 'Cancelled');


---

-- ***************************************
-- 5. Rental_Rates (20 Registros)
-- ***************************************
INSERT INTO Rental_Rates (Rental_Rates_ID, Daily_Rate, Hourly_Rate) VALUES
(1, 25.00, 5.00), (2, 30.00, 6.00), (3, 35.00, 7.50), (4, 40.00, 8.00),
(5, 50.00, 10.00), (6, 20.00, 4.00), (7, 28.00, 5.50), (8, 33.00, 6.50),
(9, 38.00, 7.00), (10, 45.00, 9.00), (11, 22.00, 4.50), (12, 27.00, 5.25),
(13, 32.00, 6.25), (14, 37.00, 6.75), (15, 42.00, 8.50), (16, 48.00, 9.50),
(17, 55.00, 11.00), (18, 60.00, 12.00), (19, 70.00, 14.00), (20, 80.00, 16.00);

---

-- ***************************************
-- 6. Renters (20 Registros)
-- ***************************************
INSERT INTO Renters (Renter_ID, Full_Name, Phone_Number, Date_of_Birth, Rental_History, Customer_Rating, Last_Rental_Date_Time, Address, Email_Address) VALUES
(1001, 'Juan Pérez', '6621112233', '1990-05-15', 10, 5, '2025-10-20', 'Calle Girasol #10', 'juan.perez@email.com'),
(1002, 'María García', '6624445566', '1985-11-20', 5, 4, '2025-09-01', NULL, 'maria.garcia@email.com'), -- Address NULL
(1003, 'Carlos López', '6627778899', '2000-01-01', 1, 3, '2025-10-25', 'Blvd. México #500', 'carlos.lopez@email.com'),
(1004, 'Laura Martínez', '6620001122', '1995-07-25', 15, 5, '2025-10-30', 'Av. Central #123', NULL), -- Email NULL
(1005, 'Roberto Sánchez', '6623334455', '1978-03-10', 2, 4, '2025-08-15', 'Colonia Obrera, Casa 7', 'roberto.sanchez@email.com'),
(1006, 'Sofía Ruiz', '6626667788', '1992-09-30', 8, 5, '2025-10-10', 'Zona Residencial, Edif. 4', 'sofia.ruiz@email.com'),
(1007, 'Andrés Díaz', '6629990011', '1980-12-12', 3, 3, '2025-09-28', 'Callejón Sur #20', 'andres.diaz@email.com'),
(1008, 'Elena Fernández', '6621239876', '2001-06-05', 0, 4, NULL, 'Av. Universitaria, Apt 3B', 'elena.fernandez@email.com'), -- Last_Rental_Date_Time NULL
(1009, 'Javier Torres', '6625432109', '1975-02-28', 20, 5, '2025-10-05', 'Carretera Nacional, Km 5', 'javier.torres@email.com'),
(1010, 'Isabel Soto', '6628765432', '1998-04-17', 6, 4, '2025-10-18', NULL, NULL), -- Address y Email NULL
(1011, 'Miguel Ramos', '6621110000', '1982-10-21', 12, 5, '2025-10-21', 'Calle Río Yaqui #55', 'miguel.ramos@email.com'),
(1012, 'Patricia Vega', '6622221111', '1993-08-08', 4, 3, '2025-09-05', 'Colonia Del Sol, Lote 12', 'patricia.vega@email.com'),
(1013, 'Ricardo Cruz', '6623332222', '1970-01-30', 25, 5, '2025-10-01', 'Zona Industrial, Nave 3', 'ricardo.cruz@email.com'),
(1014, 'Silvia Luna', '6624443333', '2002-11-11', 1, 4, '2025-11-01', NULL, 'silvia.luna@email.com'),
(1015, 'Tomás Peña', '6625554444', '1988-06-22', 7, 5, '2025-10-27', 'Av. Tecnológico #900', 'tomas.pena@email.com'),
(1016, 'Úrsula Gil', '6626665555', '1996-05-01', 9, 4, '2025-10-12', 'Calle Naranjo, Esq. Lima', NULL),
(1017, 'Víctor Rey', '6627776666', '1973-12-03', 18, 5, '2025-09-19', 'Blvd. Principal, Local 2', 'victor.rey@email.com'),
(1018, 'Wendy Neri', '6628887777', '1991-02-14', 3, 3, '2025-10-07', 'Fracc. Las Lomas, Casa 8', 'wendy.neri@email.com'),
(1019, 'Xavier Salas', '6629998888', '1984-07-07', 11, 4, '2025-10-14', NULL, NULL),
(1020, 'Yolanda Téllez', '6620102030', '1997-09-19', 0, 5, NULL, 'Plaza Mayor, Depto 1A', 'yolanda.tellez@email.com');

---

-- ***************************************
-- 7. Ref_Payment_Methods 
-- ***************************************
INSERT INTO Ref_Payment_Methods (Payment_Methods_Code, Payment_Method_Description) VALUES
(1, 'Cash'),
(2, 'Credit Card'),
(3, 'Debit Card'),
(4, 'Mobile Payment');


---

-- ***************************************
-- 8. Renters_Payment_Methods (20 Registros)
-- Coherencia CHECK: NULLs para Cardholder_Name si Payment_Methods_Code es Cash (1, 6, 10, 14, 18).
-- ***************************************
INSERT INTO Renters_Payment_Methods (Renter_Payment_Method_ID, Cardholder_Name, Expiration_Date, Issuing_Bank, Payment_Methods_Code, Renter_ID) VALUES
(1, NULL, NULL, NULL, 1, 1001), 	    -- Cash (NULL coherente)
(2, 'Maria G. Garcia', '2028-12-01', 'Bank of Cards', 2, 1002),
(3, 'Carlos M. Lopez', '2026-05-01', 'Debit Corp', 3, 1003),
(4, 'Laura S. Martinez', '2027-01-01', 'Mobile Bank', 4, 1004),
(5, 'Roberto A. Sanchez', '2029-03-01', 'Bank of Cards', 2, 1005),
(6, NULL, NULL, NULL, 1, 1006), 	    -- Cash (NULL coherente)
(7, 'Andres P. Diaz', '2030-07-01', 'Debit Corp', 3, 1007),
(8, 'Elena V. Fernandez', '2026-11-01', 'Mobile Bank', 4, 1008),
(9, 'Javier M. Torres', '2028-04-01', 'Bank of Cards', 2, 1009),
(10, NULL, NULL, NULL, 1, 1010), 	    -- Cash (NULL coherente)
(11, 'Miguel R. Ramos', '2027-08-01', 'Debit Corp', 3, 1011),
(12, 'Patricia E. Vega', '2029-01-01', 'Mobile Bank', 4, 1012),
(13, 'Ricardo G. Cruz', '2030-05-01', 'Bank of Cards', 2, 1013),
(14, NULL, NULL, NULL, 1, 1014), 	    -- Cash (NULL coherente)
(15, 'Tomas H. Pena', '2026-09-01', 'Debit Corp', 3, 1015),
(16, 'Ursula L. Gil', '2028-11-01', 'Mobile Bank', 4, 1016),
(17, 'Victor M. Rey', '2027-02-01', 'Bank of Cards', 2, 1017),
(18, NULL, NULL, NULL, 1, 1018), 	    -- Cash (NULL coherente)
(19, 'Xavier P. Salas', '2029-06-01', 'Debit Corp', 3, 1019),
(20, 'Yolanda A. Tellez', '2030-10-01', 'Mobile Bank', 4, 1020);

---

-- ***************************************
-- 9. Rentals (20 Registros)
-- Uso estratégico de NULL en Actual_Dates y Rental_Payment_Made según Payment_Status_Code
-- ***************************************
INSERT INTO Rentals (Rental_ID, All_Day_Rental_YN, Booked_Start_Date_Time, Booked_End_Date_Time, Actual_Start_Date_Time, Actual_End_Date_Time, Rental_Payment_Due, Rental_Payment_Made, Employee_in_Charge, Renter_ID, Bicycle_ID, Payment_Status_Code, Rental_Rates_ID, Renter_Payment_Method_ID) VALUES
(1, 'Y', '2025-10-20', '2025-10-21', '2025-10-20', '2025-10-21', 25.00, 25.00, 'Luis García', 1001, 101, 'PAID', 1, 1),
(2, 'N', '2025-10-22', '2025-10-22', '2025-10-22', '2025-10-22', 12.00, 12.00, 'Marta Soto', 1002, 105, 'PAID', 2, 2),
(3, 'N', '2025-10-23', '2025-10-23', '2025-10-23', '2025-10-23', 7.50, 7.50, 'Javier Peña', 1003, 107, 'PAID', 3, 3),
(4, 'Y', '2025-10-24', '2025-10-25', '2025-10-24', '2025-10-25', 80.00, 80.00, 'Elena Castro', 1004, 109, 'PAID', 4, 4),
(5, 'N', '2025-10-25', '2025-10-25', '2025-10-25', '2025-10-25', 10.00, NULL, 'Roberto Morales', 1005, 111, 'OVRD', 5, 5), -- Overdue: Rental_Payment_Made IS NULL
(6, 'Y', '2025-10-26', '2025-10-27', '2025-10-26', NULL, 20.00, 0.00, 'Sofía Ramos', 1006, 113, 'PEND', 6, 6), -- Pending/In-progress: Actual_End_Date_Time IS NULL
(7, 'N', '2025-10-27', '2025-10-27', '2025-10-27', '2025-10-27', 11.00, 11.00, 'Andrés Vega', 1007, 115, 'PAID', 7, 7),
(8, 'Y', '2025-10-28', '2025-10-29', '2025-10-28', '2025-10-29', 66.00, NULL, 'Patricia Díaz', 1008, 117, 'OVRD', 8, 8),
(9, 'N', '2025-10-29', '2025-10-29', '2025-10-29', '2025-10-29', 7.00, 7.00, NULL, 1009, 119, 'PAID', 9, 9), -- Employee_in_Charge NULL
(10, 'Y', '2025-10-30', '2025-10-31', NULL, NULL, 45.00, NULL, 'Ricardo Solís', 1010, 102, 'CANC', 10, 10), -- Cancelled: Actual_Dates NULL
(11, 'N', '2025-11-01', '2025-11-01', '2025-11-01', '2025-11-01', 9.00, 9.00, 'Gabriel Neri', 1011, 104, 'PAID', 11, 11),
(12, 'Y', '2025-11-02', '2025-11-03', '2025-11-02', NULL, 54.00, 0.00, 'Hilda Soto', 1012, 106, 'PEND', 12, 12),
(13, 'N', '2025-11-03', '2025-11-03', '2025-11-03', '2025-11-03', 12.50, 12.50, 'Ismael Ríos', 1013, 108, 'PAID', 13, 13),
(14, 'Y', '2025-11-04', '2025-11-05', NULL, NULL, 74.00, NULL, 'Julieta Lara', 1014, 110, 'PEND', 14, 14), -- Future booking: Actual_Dates NULL
(15, 'N', '2025-11-05', '2025-11-05', '2025-11-05', '2025-11-05', 8.50, 8.50, 'Kevin Cruz', 1015, 112, 'PAID', 15, 15),
(16, 'Y', '2025-11-06', '2025-11-07', '2025-11-06', '2025-11-07', 96.00, 0.00, 'Laura Gómez', 1016, 114, 'OVRD', 16, 16),
(17, 'N', '2025-11-07', '2025-11-07', NULL, NULL, 11.00, NULL, 'Marcos Rey', 1017, 116, 'CANC', 17, 17),
(18, 'Y', '2025-11-08', '2025-11-09', NULL, NULL, 120.00, NULL, 'Nuria Vega', 1018, 118, 'PEND', 18, 18),
(19, 'N', '2025-11-09', '2025-11-09', '2025-11-09', NULL, 14.00, 0.00, 'Óscar Gil', 1019, 120, 'PEND', 19, 19),
(20, 'Y', '2025-11-10', '2025-11-11', NULL, NULL, 160.00, NULL, 'Paula Reyes', 1020, 103, 'PEND', 20, 20);