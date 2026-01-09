-- =============================================
-- 1. Companies
-- =============================================
INSERT INTO company (nom_company, adresse_comp, telephone, email, site_web, code_iata)
VALUES
('Air Madagascar', 'Antananarivo, Madagascar', '+261 20 22 12345', 'contact@airmadagascar.mg', 'www.airmadagascar.mg', 'MDG'),
('Air France', 'Paris, France', '+33 1 40 60 12 34', 'contact@airfrance.fr', 'www.airfrance.fr', 'AFR');

-- =============================================
-- 2. Aeroports
-- =============================================
INSERT INTO aeroport (code_iata, code_icao, nom_aeroport, ville, pays, fuseau_horaire)
VALUES
('TNR', 'FMMI', 'Aéroport d\Ivato', 'Antananarivo', 'Madagascar', 'Indian/Mauritius'),
('CDG', 'LFPG', 'Aéroport Charles de Gaulle', 'Paris', 'France', 'Europe/Paris'),
('JNB', 'FAOR', 'OR Tambo International', 'Johannesburg', 'South Africa', 'Africa/Johannesburg');

-- =============================================
-- 3. Avions
-- =============================================
INSERT INTO avion (modele, numero_serie, capacite, etat_avion, id_company, annee_fabrication)
VALUES
('Boeing 737', 'SN12345', 180, 1, 'CO000001', 2015),
('Airbus A320', 'SN54321', 150, 1, 'CO000002', 2018);

-- =============================================
-- 4. Clients
-- =============================================
INSERT INTO utilisateur (email, mot_de_passe, role)
VALUES
('rakoto@gmail.com', 'password1', 'CLIENT'),
('rabe@gmail.com', 'password2', 'CLIENT');

INSERT INTO client (id_utilisateur, nom_client, prenom_client, email, telephone, date_naissance, adresse)
VALUES
('US000005', 'Rakoto', 'Jean', 'rakoto@gmail.com', '+261 33 12 345 67', '1990-05-12', 'Antananarivo'),
('US000006', 'Rabe', 'Marie', 'rabe@gmail.com', '+261 33 76 543 21', '1985-11-23', 'Antananarivo');

-- =============================================
-- 5. Vols
-- =============================================
INSERT INTO vol (numero_vol, id_aeroport_depart, id_aeroport_destination, id_avion, date_depart, date_arrivee, prix_base, id_company)
VALUES
('MDG001', 'AP000001', 'AP000002', 'AV000001', '2026-01-15 08:00', '2026-01-15 14:00', 500.00, 'CO000001'),
('AFR123', 'AP000002', 'AP000003', 'AV000002', '2026-01-20 09:30', '2026-01-20 18:00', 700.00, 'CO000002');

-- =============================================
-- 6. Reservations
-- =============================================
INSERT INTO reservation (id_client, id_vol, nombre_passagers, montant_total)
VALUES
('CL000003', 'VO000001', 2, 1000.00),
('CL000004', 'VO000002', 1, 700.00);

-- =============================================
-- 7. Passagers
-- =============================================
INSERT INTO passager (id_reservation, nom, prenom, date_naissance, numero_passeport, nationalite)
VALUES
('RS000003', 'Rakoto', 'Jean', '1990-05-12', 'P1234567', 'Malagasy'),
('RS000003', 'Rakoto', 'Anna', '1992-08-03', 'P2345678', 'Malagasy'),
('RS000004', 'Rabe', 'Marie', '1985-11-23', 'P3456789', 'Malagasy');

-- =============================================
-- 8. Billets
-- =============================================
INSERT INTO billet (id_reservation, id_passager, id_siege, id_etat_billet, id_type_billet, prix)
VALUES
('RS000003', 'PA000004', 'SI000001', 1, 1, 500.00),
('RS000003', 'PA000005', 'SI000002', 1, 1, 500.00),
('RS000004', 'PA000006', 'SI000003', 1, 2, 700.00);

-- =============================================
-- 9. Paiements
-- =============================================
INSERT INTO paiement (id_reservation, montant, methode_paiement, statut)
VALUES
('RS000003', 1000.00, 'Carte bancaire', 'PAYE'),
('RS000003', 700.00, 'Virement', 'EN_ATTENTE');


-- =============================================
-- 10. Sièges pour Avion AV000001 (Boeing 737)
-- =============================================
INSERT INTO siege (id_avion, numero_siege, rangee, lettre, classe, est_fenetre, est_couloir, est_sortie_secours)
VALUES
('AV000001', '1A', 1, 'A', 'Économie', TRUE, FALSE, FALSE),
('AV000001', '1B', 1, 'B', 'Économie', FALSE, TRUE, FALSE),
('AV000001', '1C', 1, 'C', 'Économie', FALSE, FALSE, FALSE),
('AV000001', '2A', 2, 'A', 'Économie', TRUE, FALSE, FALSE),
('AV000001', '2B', 2, 'B', 'Économie', FALSE, TRUE, FALSE),
('AV000001', '2C', 2, 'C', 'Économie', FALSE, FALSE, FALSE);

-- =============================================
-- 11. Sièges pour Avion AV000002 (Airbus A320)
-- =============================================
INSERT INTO siege (id_avion, numero_siege, rangee, lettre, classe, est_fenetre, est_couloir, est_sortie_secours)
VALUES
('AV000002', '1A', 1, 'A', 'Business', TRUE, FALSE, FALSE),
('AV000002', '1B', 1, 'B', 'Business', FALSE, TRUE, FALSE),
('AV000002', '1C', 1, 'C', 'Business', FALSE, FALSE, FALSE),
('AV000002', '2A', 2, 'A', 'Économie', TRUE, FALSE, FALSE),
('AV000002', '2B', 2, 'B', 'Économie', FALSE, TRUE, FALSE),
('AV000002', '2C', 2, 'C', 'Économie', FALSE, FALSE, FALSE);
