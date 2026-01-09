-- États de vols
INSERT INTO Etat_Vol(etat_vol, nom_etat) VALUES
(1, 'En cours'),
(2, 'Arrivé'),
(3, 'Annulé');

-- États des billets
INSERT INTO Etat_Billet(id_etat_billet, nom_etat) VALUES
(1, 'Valide'),
(2, 'Annulé'),
(3, 'Utilisé');

-- Types de billets
INSERT INTO Type_Billet(id_type_billet, nom_type) VALUES
(1, 'Économique'),
(2, 'Business'),
(3, 'Première');

-- États des avions
INSERT INTO Etat_Avion(etat_avion, nom_etat) VALUES
(1, 'Disponible'),
(2, 'En vol'),
(3, 'Maintenance');


INSERT INTO company(nom_company, adresse_comp) VALUES
('Air Mada', 'Antananarivo, Madagascar'),
('Fly Express', 'Toamasina, Madagascar');

INSERT INTO client(nom_client, prenom_client) VALUES
('Rakoto', 'Jean'),
('Rabe', 'Marie'),
('Andrian', 'Lucie');


INSERT INTO avion(modele, capacite, etat_avion, id_company) VALUES
('Boeing 737', 180, 1, 'CO001'),
('Airbus A320', 150, 1, 'CO001'),
('ATR 72', 70, 1, 'CO002');

INSERT INTO avion(modele, capacite, etat_avion, id_company) VALUES
('Boeing 777', 300, 1, 'CO001'),  
('Airbus A350', 280, 1, 'CO002');

INSERT INTO aeroport(nom_aeroport, ville, pays) VALUES
('Ivato', 'Antananarivo', 'Madagascar'),
('Fascene', 'Nosy Be', 'Madagascar'),
('Tolagnaro', 'Fort-Dauphin', 'Madagascar');

INSERT INTO aeroport(nom_aeroport, ville, pays) VALUES
('Ivato', 'Antananarivo', 'Madagascar'),
('Charles de Gaulle', 'Paris', 'France'),
('JFK', 'New York', 'USA'),
('Heathrow', 'London', 'UK');


-- Vol international 1 : Antananarivo → Paris
INSERT INTO vol(depart, destination, date_depart, date_arrivee, id_company, etat_vol) VALUES
('Antananarivo', 'Paris', '2026-02-01 21:00', '2026-02-02 06:00', 'CO001', 1);

-- Vol international 2 : Paris → New York
INSERT INTO vol(depart, destination, date_depart, date_arrivee, id_company, etat_vol) VALUES
('Paris', 'New York', '2026-02-05 10:00', '2026-02-05 18:00', 'CO001', 1);

-- Vol international 3 : Antananarivo → London
INSERT INTO vol(depart, destination, date_depart, date_arrivee, id_company, etat_vol) VALUES
('Antananarivo', 'London', '2026-02-10 22:00', '2026-02-11 06:00', 'CO002', 1);

-- Clients existants CL001, CL002, CL003
INSERT INTO reservation(id_client, id_vol) VALUES
('CL001', 'VO003'), 
('CL002', 'VO004'), 
('CL003', 'VO005'); 

INSERT INTO billet(id_reservation, id_etat_billet, id_type_billet, prix) VALUES
('RS004', 1, 3, 1200.00), -- Première classe
('RS005', 1, 2, 900.00),  -- Business
('RS006', 1, 1, 700.00);  -- Économique


INSERT INTO vol(depart, destination, date_depart, date_arrivee, id_company, etat_vol) VALUES
('Antananarivo', 'Nosy Be', '2026-01-10 08:00', '2026-01-10 10:00', 'CO001', 1),
('Nosy Be', 'Tolagnaro', '2026-01-11 07:00', '2026-01-11 12:00', 'CO002', 1);


-- Réservations mises à jour avec id_reservation pour correspondre aux billets
INSERT INTO reservation(id_client, id_vol, date_reservation) VALUES
('CL001', 'VO001', CURRENT_TIMESTAMP), 
('CL002', 'VO001', CURRENT_TIMESTAMP), 
('CL003', 'VO002', CURRENT_TIMESTAMP);



INSERT INTO billet(id_reservation, id_etat_billet, id_type_billet, prix) VALUES
('RS004', 1, 1, 150.00),
('RS005', 1, 2, 300.00),
('RS006', 1, 1, 120.00);


-- Vol VO001 : Antananarivo → Nosy Be
INSERT INTO escale(id_vol, id_aeroport, id_avion, ordre_escale, heure_depart, heure_arrivee) VALUES
('VO001', 'AP001', 'AV001', 1, '2026-01-10 08:00', '2026-01-10 08:30'), -- départ Antananarivo
('VO001', 'AP002', 'AV001', 2, '2026-01-10 09:30', '2026-01-10 10:00'); -- arrivée Nosy Be

-- Vol VO002 : Nosy Be → Tolagnaro
INSERT INTO escale(id_vol, id_aeroport, id_avion, ordre_escale, heure_depart, heure_arrivee) VALUES
('VO002', 'AP002', 'AV003', 1, '2026-01-11 07:00', '2026-01-11 07:30'), -- départ Nosy Be
('VO002', 'AP003', 'AV003', 2, '2026-01-11 11:30', '2026-01-11 12:00'); -- arrivée Tolagnaro

-- VO003 : Antananarivo → Paris
INSERT INTO escale(id_vol, id_aeroport, id_avion, ordre_escale, heure_depart, heure_arrivee) VALUES
('VO003', 'AP001', 'AV004', 1, '2026-02-01 21:00', '2026-02-01 21:30'), 
('VO003', 'AP004', 'AV004', 2, '2026-02-02 05:30', '2026-02-02 06:00'); 

-- VO004 : Paris → New York
INSERT INTO escale(id_vol, id_aeroport, id_avion, ordre_escale, heure_depart, heure_arrivee) VALUES
('VO004', 'AP004', 'AV004', 1, '2026-02-05 10:00', '2026-02-05 10:30'), 
('VO004', 'AP005', 'AV004', 2, '2026-02-05 17:30', '2026-02-05 18:00'); 

-- VO005 : Antananarivo → London
INSERT INTO escale(id_vol, id_aeroport, id_avion, ordre_escale, heure_depart, heure_arrivee) VALUES
('VO005', 'AP001', 'AV005', 1, '2026-02-10 22:00', '2026-02-10 22:30'), -- départ Antananarivo
('VO005', 'AP006', 'AV005', 2, '2026-02-11 05:30', '2026-02-11 06:00'); -- arrivée London


