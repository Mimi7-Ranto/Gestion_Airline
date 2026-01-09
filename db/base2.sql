-- =============================================
-- BASE DE DONNÉES COMPAGNIE AÉRIENNE - VERSION FINALE CORRIGÉE
-- PostgreSQL - Compatible Spring Boot + JPA + Flyway
-- =============================================

-- =============================================
-- 1. TABLES DE RÉFÉRENCE
-- =============================================
CREATE TABLE etat_vol (
    etat_vol INT PRIMARY KEY,
    nom_etat VARCHAR(100) NOT NULL
);

CREATE TABLE etat_avion (
    etat_avion INT PRIMARY KEY,
    nom_etat VARCHAR(100) NOT NULL
);

CREATE TABLE etat_billet (
    id_etat_billet INT PRIMARY KEY,
    nom_etat VARCHAR(100) NOT NULL
);

CREATE TABLE type_billet (
    id_type_billet INT PRIMARY KEY,
    nom_type VARCHAR(100) NOT NULL
);

-- Données de référence
INSERT INTO etat_vol (etat_vol, nom_etat) VALUES
(1, 'Programmé'), (2, 'En cours'), (3, 'Arrivé'), (4, 'Annulé'), (5, 'Retardé');

INSERT INTO etat_avion (etat_avion, nom_etat) VALUES
(1, 'Opérationnel'), (2, 'En maintenance'), (3, 'Hors service');

INSERT INTO etat_billet (id_etat_billet, nom_etat) VALUES
(1, 'Émis'), (2, 'Utilisé'), (3, 'Annulé'), (4, 'Remboursé');

INSERT INTO type_billet (id_type_billet, nom_type) VALUES
(1, 'Économie'), (2, 'Business'), (3, 'Première classe');

-- =============================================
-- 2. TABLE UTILISATEUR (AUTHENTIFICATION)
-- =============================================
CREATE TABLE utilisateur (
    id_utilisateur VARCHAR(36) PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL,
    role VARCHAR(50) DEFAULT 'CLIENT' NOT NULL,
    statut VARCHAR(50) DEFAULT 'ACTIF',
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    derniere_connexion TIMESTAMP
);

-- =============================================
-- 3. TABLES PRINCIPALES
-- =============================================
CREATE TABLE company (
    id_company VARCHAR(36) PRIMARY KEY,
    nom_company VARCHAR(100) NOT NULL,
    adresse_comp VARCHAR(255),
    telephone VARCHAR(20),
    email VARCHAR(255),
    site_web VARCHAR(255),
    code_iata VARCHAR(3) UNIQUE,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE client (
    id_client VARCHAR(36) PRIMARY KEY,
    id_utilisateur VARCHAR(36) UNIQUE,
    nom_client VARCHAR(100) NOT NULL,
    prenom_client VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    telephone VARCHAR(20),
    date_naissance DATE,
    adresse VARCHAR(255),
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur)
);

CREATE TABLE aeroport (
    id_aeroport VARCHAR(36) PRIMARY KEY,
    code_iata VARCHAR(3) UNIQUE NOT NULL,
    code_icao VARCHAR(4) UNIQUE,
    nom_aeroport VARCHAR(100) NOT NULL,
    ville VARCHAR(100) NOT NULL,
    pays VARCHAR(100) NOT NULL,
    fuseau_horaire VARCHAR(50),
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE avion (
    id_avion VARCHAR(36) PRIMARY KEY,
    modele VARCHAR(100) NOT NULL,
    numero_serie VARCHAR(50) UNIQUE,
    capacite INT NOT NULL,
    annee_fabrication INT,
    etat_avion INT NOT NULL,
    id_company VARCHAR(36) NOT NULL,
    date_derniere_maintenance DATE,
    heures_vol_totales INT DEFAULT 0,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_company) REFERENCES company(id_company),
    FOREIGN KEY (etat_avion) REFERENCES etat_avion(etat_avion),
    CONSTRAINT chk_capacite_positive CHECK (capacite > 0),
    CONSTRAINT chk_heures_positives CHECK (heures_vol_totales >= 0)
);

CREATE TABLE siege (
    id_siege VARCHAR(36) PRIMARY KEY,
    id_avion VARCHAR(36) NOT NULL,
    numero_siege VARCHAR(10) NOT NULL,
    rangee INT NOT NULL,
    lettre VARCHAR(2) NOT NULL,
    classe VARCHAR(50) NOT NULL,
    est_fenetre BOOLEAN DEFAULT FALSE,
    est_couloir BOOLEAN DEFAULT FALSE,
    est_sortie_secours BOOLEAN DEFAULT FALSE,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_avion) REFERENCES avion(id_avion),
    UNIQUE(id_avion, numero_siege),
    CONSTRAINT chk_rangee_positive CHECK (rangee > 0)
);

CREATE TABLE vol (
    id_vol VARCHAR(36) PRIMARY KEY,
    id_aeroport_depart VARCHAR(36) NOT NULL,
    id_aeroport_destination VARCHAR(36) NOT NULL,
    id_avion VARCHAR(36) NOT NULL,
    date_depart TIMESTAMP NOT NULL,
    date_arrivee TIMESTAMP NOT NULL,
    id_company VARCHAR(36) NOT NULL,
    etat_vol INT DEFAULT 1,
    arrivee_effective TIMESTAMP,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_aeroport_depart) REFERENCES aeroport(id_aeroport),
    FOREIGN KEY (id_aeroport_destination) REFERENCES aeroport(id_aeroport),
    FOREIGN KEY (id_avion) REFERENCES avion(id_avion),
    FOREIGN KEY (id_company) REFERENCES company(id_company),
    FOREIGN KEY (etat_vol) REFERENCES etat_vol(etat_vol),
    CONSTRAINT chk_aeroports_differents CHECK (id_aeroport_depart != id_aeroport_destination),
    CONSTRAINT chk_dates_vol CHECK (date_depart < date_arrivee)
);

CREATE TABLE reservation (
    id_reservation VARCHAR(36) PRIMARY KEY,
    id_client VARCHAR(36) NOT NULL,
    id_vol VARCHAR(36) NOT NULL,
    date_reservation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nombre_passagers INT NOT NULL DEFAULT 1,
    montant_total DECIMAL(10,2),
    statut VARCHAR(50) DEFAULT 'EN_ATTENTE',
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_client) REFERENCES client(id_client),
    FOREIGN KEY (id_vol) REFERENCES vol(id_vol),
    UNIQUE (id_client, id_vol),
    CONSTRAINT chk_nombre_passagers CHECK (nombre_passagers > 0)
);

CREATE TABLE passager (
    id_passager VARCHAR(36) PRIMARY KEY,
    id_reservation VARCHAR(36) NOT NULL,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    date_naissance DATE NOT NULL,
    numero_passeport VARCHAR(50) NOT NULL,
    nationalite VARCHAR(100) NOT NULL,
    email VARCHAR(255),
    telephone VARCHAR(20),
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_reservation) REFERENCES reservation(id_reservation)
);

CREATE TABLE billet (
    id_billet VARCHAR(36) PRIMARY KEY,
    id_reservation VARCHAR(36) NOT NULL,
    id_passager VARCHAR(36) NOT NULL,
    id_siege VARCHAR(36),
    id_etat_billet INT,
    id_type_billet INT,
    prix DECIMAL(10,2) NOT NULL,
    numero_billet VARCHAR(20) UNIQUE,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_reservation) REFERENCES reservation(id_reservation),
    FOREIGN KEY (id_passager) REFERENCES passager(id_passager),
    FOREIGN KEY (id_siege) REFERENCES siege(id_siege),
    FOREIGN KEY (id_etat_billet) REFERENCES etat_billet(id_etat_billet),
    FOREIGN KEY (id_type_billet) REFERENCES type_billet(id_type_billet),
    CONSTRAINT chk_prix_positif CHECK (prix > 0)
);

CREATE TABLE paiement (
    id_paiement VARCHAR(36) PRIMARY KEY,
    id_reservation VARCHAR(36) NOT NULL,
    montant DECIMAL(10,2) NOT NULL,
    methode_paiement VARCHAR(50) NOT NULL,
    statut VARCHAR(50) DEFAULT 'EN_ATTENTE',
    transaction_id VARCHAR(100) UNIQUE,
    date_paiement TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_reservation) REFERENCES reservation(id_reservation),
    CONSTRAINT chk_montant_positif CHECK (montant > 0)
);

CREATE TABLE escale (
    id_escale VARCHAR(36) PRIMARY KEY,
    id_vol VARCHAR(36) NOT NULL,
    id_aeroport VARCHAR(36) NOT NULL,
    ordre_escale INT NOT NULL,
    heure_arrivee TIMESTAMP,
    heure_depart TIMESTAMP,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_vol) REFERENCES vol(id_vol),
    FOREIGN KEY (id_aeroport) REFERENCES aeroport(id_aeroport),
    CONSTRAINT chk_ordre_positif CHECK (ordre_escale > 0)
);

-- =============================================
-- 4. SÉQUENCES
-- =============================================
CREATE SEQUENCE utilisateur_seq START 1;
CREATE SEQUENCE company_seq START 1;
CREATE SEQUENCE client_seq START 1;
CREATE SEQUENCE vol_seq START 1;
CREATE SEQUENCE reservation_seq START 1;
CREATE SEQUENCE passager_seq START 1;
CREATE SEQUENCE billet_seq START 1;
CREATE SEQUENCE avion_seq START 1;
CREATE SEQUENCE siege_seq START 1;
CREATE SEQUENCE aeroport_seq START 1;
CREATE SEQUENCE escale_seq START 1;
CREATE SEQUENCE paiement_seq START 1;

-- =============================================
-- 5. FONCTION COMMUNE : mise à jour date_modification
-- =============================================
CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.date_modification = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- =============================================
-- 6. FONCTIONS ET TRIGGERS PAR TABLE
-- =============================================
-- Utilisateur
CREATE OR REPLACE FUNCTION utilisateur_id_generate() RETURNS trigger AS $$
BEGIN
    NEW.id_utilisateur := 'US' || LPAD(nextval('utilisateur_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_utilisateur_id
    BEFORE INSERT ON utilisateur
    FOR EACH ROW EXECUTE FUNCTION utilisateur_id_generate();

CREATE TRIGGER update_utilisateur_modtime
    BEFORE UPDATE ON utilisateur
    FOR EACH ROW EXECUTE FUNCTION update_modified_column();

-- Company
CREATE OR REPLACE FUNCTION company_id_generate() RETURNS trigger AS $$
BEGIN
    NEW.id_company := 'CO' || LPAD(nextval('company_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_company_id
    BEFORE INSERT ON company
    FOR EACH ROW EXECUTE FUNCTION company_id_generate();

CREATE TRIGGER update_company_modtime
    BEFORE UPDATE ON company
    FOR EACH ROW EXECUTE FUNCTION update_modified_column();

-- Client
CREATE OR REPLACE FUNCTION client_id_generate() RETURNS trigger AS $$
BEGIN
    NEW.id_client := 'CL' || LPAD(nextval('client_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_client_id
    BEFORE INSERT ON client
    FOR EACH ROW EXECUTE FUNCTION client_id_generate();

CREATE TRIGGER update_client_modtime
    BEFORE UPDATE ON client
    FOR EACH ROW EXECUTE FUNCTION update_modified_column();

-- Vol
CREATE OR REPLACE FUNCTION vol_id_generate() RETURNS trigger AS $$
BEGIN
    NEW.id_vol := 'VO' || LPAD(nextval('vol_seq')::text, 6, '0');
    IF NEW.etat_vol IS NULL THEN
        NEW.etat_vol := 1;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_vol_id
    BEFORE INSERT ON vol
    FOR EACH ROW EXECUTE FUNCTION vol_id_generate();

CREATE TRIGGER update_vol_modtime
    BEFORE UPDATE ON vol
    FOR EACH ROW EXECUTE FUNCTION update_modified_column();

-- Reservation
CREATE OR REPLACE FUNCTION reservation_id_generate() RETURNS trigger AS $$
BEGIN
    NEW.id_reservation := 'RS' || LPAD(nextval('reservation_seq')::text, 6, '0');
    IF NEW.date_reservation IS NULL THEN
        NEW.date_reservation := CURRENT_TIMESTAMP;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_reservation_id
    BEFORE INSERT ON reservation
    FOR EACH ROW EXECUTE FUNCTION reservation_id_generate();

CREATE TRIGGER update_reservation_modtime
    BEFORE UPDATE ON reservation
    FOR EACH ROW EXECUTE FUNCTION update_modified_column();

-- Passager
CREATE OR REPLACE FUNCTION passager_id_generate() RETURNS trigger AS $$
BEGIN
    NEW.id_passager := 'PA' || LPAD(nextval('passager_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_passager_id
    BEFORE INSERT ON passager
    FOR EACH ROW EXECUTE FUNCTION passager_id_generate();

CREATE TRIGGER update_passager_modtime
    BEFORE UPDATE ON passager
    FOR EACH ROW EXECUTE FUNCTION update_modified_column();

-- Billet
CREATE OR REPLACE FUNCTION billet_id_generate() RETURNS trigger AS $$
BEGIN
    NEW.id_billet := 'BI' || LPAD(nextval('billet_seq')::text, 6, '0');
    NEW.numero_billet := 'TKT' || TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD') || LPAD(currval('billet_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_billet_id
    BEFORE INSERT ON billet
    FOR EACH ROW EXECUTE FUNCTION billet_id_generate();

CREATE TRIGGER update_billet_modtime
    BEFORE UPDATE ON billet
    FOR EACH ROW EXECUTE FUNCTION update_modified_column();

-- Avion
CREATE OR REPLACE FUNCTION avion_id_generate() RETURNS trigger AS $$
BEGIN
    NEW.id_avion := 'AV' || LPAD(nextval('avion_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_avion_id
    BEFORE INSERT ON avion
    FOR EACH ROW EXECUTE FUNCTION avion_id_generate();

CREATE TRIGGER update_avion_modtime
    BEFORE UPDATE ON avion
    FOR EACH ROW EXECUTE FUNCTION update_modified_column();

-- Siege
CREATE OR REPLACE FUNCTION siege_id_generate() RETURNS trigger AS $$
BEGIN
    NEW.id_siege := 'SI' || LPAD(nextval('siege_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_siege_id
    BEFORE INSERT ON siege
    FOR EACH ROW EXECUTE FUNCTION siege_id_generate();

-- Aeroport
CREATE OR REPLACE FUNCTION aeroport_id_generate() RETURNS trigger AS $$
BEGIN
    NEW.id_aeroport := 'AP' || LPAD(nextval('aeroport_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_aeroport_id
    BEFORE INSERT ON aeroport
    FOR EACH ROW EXECUTE FUNCTION aeroport_id_generate();

CREATE TRIGGER update_aeroport_modtime
    BEFORE UPDATE ON aeroport
    FOR EACH ROW EXECUTE FUNCTION update_modified_column();

-- Escale
CREATE OR REPLACE FUNCTION escale_id_generate() RETURNS trigger AS $$
BEGIN
    NEW.id_escale := 'ES' || LPAD(nextval('escale_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_escale_id
    BEFORE INSERT ON escale
    FOR EACH ROW EXECUTE FUNCTION escale_id_generate();

-- Paiement
CREATE OR REPLACE FUNCTION paiement_id_generate() RETURNS trigger AS $$
BEGIN
    NEW.id_paiement := 'PY' || LPAD(nextval('paiement_seq')::text, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_paiement_id
    BEFORE INSERT ON paiement
    FOR EACH ROW EXECUTE FUNCTION paiement_id_generate();

CREATE TRIGGER update_paiement_modtime
    BEFORE UPDATE ON paiement
    FOR EACH ROW EXECUTE FUNCTION update_modified_column();

-- =============================================
-- 7. INDEX POUR PERFORMANCES
-- =============================================
CREATE INDEX idx_aeroport_code_iata ON aeroport(code_iata);
CREATE INDEX idx_client_email ON client(email);
CREATE INDEX idx_vol_depart_date ON vol(id_aeroport_depart, date_depart);
CREATE INDEX idx_vol_arrivee_date ON vol(id_aeroport_destination, date_depart);
CREATE INDEX idx_reservation_client ON reservation(id_client);
CREATE INDEX idx_reservation_vol ON reservation(id_vol);
CREATE INDEX idx_billet_reservation ON billet(id_reservation);
CREATE INDEX idx_billet_siege ON billet(id_siege);

-- =============================================
-- FIN DU SCRIPT - TOUT EST PRÊT !
-- =============================================