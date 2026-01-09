CREATE DATABASE company

CREATE TABLE Company(
    id_company VARCHAR(36) PRIMARY KEY,
    nom_company VARCHAR(100),
    adresse_comp VARCHAR(255)
);

CREATE TABLE Client (
    id_client VARCHAR(36) PRIMARY KEY,
    nom_client VARCHAR(100),
    prenom_client VARCHAR(100)
);

CREATE TABLE Vol (
    id_vol VARCHAR(36) PRIMARY KEY,
    depart VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    date_depart TIMESTAMP,
    date_arrivee TIMESTAMP,
    id_company VARCHAR(36) NOT NULL,
    etat_vol INT,
    arrivee_effective TIMESTAMP,
    FOREIGN KEY (id_company) REFERENCES Company(id_company),
    FOREIGN KEY (etat_vol) REFERENCES Etat_Vol(etat_vol)
);
CREATE TABLE Etat_Vol(
    etat_vol INT PRIMARY KEY,
    nom_etat VARCHAR(100)
);
CREATE TABLE Reservation (
    id_reservation VARCHAR(36) PRIMARY KEY,
    id_client VARCHAR(36) NOT NULL,
    id_vol VARCHAR(36) NOT NULL,
    date_reservation TIMESTAMP, 
    FOREIGN KEY (id_client) REFERENCES Client(id_client),
    FOREIGN KEY (id_vol) REFERENCES Vol(id_vol),
    UNIQUE(id_client, id_vol)
);


CREATE TABLE Billet (
    id_billet VARCHAR(36) PRIMARY KEY,
    id_reservation VARCHAR(36) NOT NULL,
    id_etat_billet INT,
    id_type_billet INT,
    prix DECIMAL(10,2),
    FOREIGN KEY (id_reservation) REFERENCES Reservation(id_reservation),
    FOREIGN KEY (id_etat_billet) REFERENCES Etat_Billet(id_etat_billet),
    FOREIGN KEY (id_type_billet) REFERENCES Type_Billet(id_type_billet)
);

CREATE TABLE Etat_Billet(
    id_etat_billet INT PRIMARY KEY,
    nom_etat VARCHAR(100)
);
CREATE TABLE Type_Billet(
    id_type_billet INT PRIMARY KEY,
    nom_type VARCHAR(100)
);

CREATE TABLE avion (
    id_avion VARCHAR(36) PRIMARY KEY,
    modele VARCHAR(100),
    capacite INT NOT NULL,
    etat_avion INT NOT NULL,
    id_company VARCHAR(36) NOT NULL,
    FOREIGN KEY (id_company) REFERENCES Company(id_company),
    FOREIGN KEY (etat_avion) REFERENCES Etat_avion(etat_avion)
);

CREATE TABLE Etat_Avion(
    etat_avion INT PRIMARY KEY,
    nom_etat VARCHAR(100)
);
CREATE TABLE Aeroport(
    id_aeroport VARCHAR(36) PRIMARY KEY,
    nom_aeroport VARCHAR(100),
    ville VARCHAR(100),
    pays VARCHAR(100)
);
CREATE TABLE escale (
    id_escale VARCHAR(36) PRIMARY KEY,
    id_vol VARCHAR(36) NOT NULL,
    id_aeroport VARCHAR(36) NOT NULL,
    id_avion VARCHAR(36) NOT NULL,
    ordre_escale INT NOT NULL,
    heure_arrivee TIMESTAMP,  
    heure_depart TIMESTAMP,  
    FOREIGN KEY (id_vol) REFERENCES vol(id_vol),
    FOREIGN KEY (id_aeroport) REFERENCES aeroport(id_aeroport),
    FOREIGN KEY (id_avion) REFERENCES avion(id_avion)
);

-- Séquences pour les IDs
CREATE SEQUENCE company_seq START 1;
CREATE SEQUENCE client_seq START 1;
CREATE SEQUENCE vol_seq START 1;
CREATE SEQUENCE reservation_seq START 1;
CREATE SEQUENCE billet_seq START 1;
CREATE SEQUENCE avion_seq START 1;
CREATE SEQUENCE aeroport_seq START 1;
CREATE SEQUENCE escale_seq START 1;

-- Fonction pour générer id_company
CREATE OR REPLACE FUNCTION company_id_generate() RETURNS trigger AS $$
BEGIN
    NEW.id_company := 'CO' || LPAD(nextval('company_seq')::text, 3, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger sur insert
CREATE TRIGGER trg_company_id
BEFORE INSERT ON company
FOR EACH ROW
EXECUTE FUNCTION company_id_generate();


CREATE OR REPLACE FUNCTION client_id_generate() RETURNS trigger AS $$
BEGIN
    NEW.id_client := 'CL' || LPAD(nextval('client_seq')::text, 3, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_client_id
BEFORE INSERT ON client
FOR EACH ROW
EXECUTE FUNCTION client_id_generate();


CREATE OR REPLACE FUNCTION vol_id_generate() RETURNS trigger AS $$
BEGIN
    NEW.id_vol := 'VO' || LPAD(nextval('vol_seq')::text, 3, '0');
    -- Par défaut, un vol est "En cours" (etat_vol = 1)
    IF NEW.etat_vol IS NULL THEN
        NEW.etat_vol := 1;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_vol_id
BEFORE INSERT ON vol
FOR EACH ROW
EXECUTE FUNCTION vol_id_generate();


CREATE OR REPLACE FUNCTION reservation_id_generate() RETURNS trigger AS $$
BEGIN
    NEW.id_reservation := 'RS' || LPAD(nextval('reservation_seq')::text, 3, '0');
   
    IF NEW.date_reservation IS NULL THEN
        NEW.date_reservation := CURRENT_TIMESTAMP;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_reservation_id
BEFORE INSERT ON reservation
FOR EACH ROW
EXECUTE FUNCTION reservation_id_generate();


CREATE OR REPLACE FUNCTION billet_id_generate() RETURNS trigger AS $$
BEGIN
    NEW.id_billet := 'BI' || LPAD(nextval('billet_seq')::text, 3, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_billet_id
BEFORE INSERT ON billet
FOR EACH ROW
EXECUTE FUNCTION billet_id_generate();



CREATE OR REPLACE FUNCTION avion_id_generate() RETURNS trigger AS $$
BEGIN
    NEW.id_avion := 'AV' || LPAD(nextval('avion_seq')::text, 3, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_avion_id
BEFORE INSERT ON avion
FOR EACH ROW
EXECUTE FUNCTION avion_id_generate();


CREATE OR REPLACE FUNCTION aeroport_id_generate() RETURNS trigger AS $$
BEGIN
    NEW.id_aeroport := 'AP' || LPAD(nextval('aeroport_seq')::text, 3, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_aeroport_id
BEFORE INSERT ON aeroport
FOR EACH ROW
EXECUTE FUNCTION aeroport_id_generate();


CREATE OR REPLACE FUNCTION escale_id_generate() RETURNS trigger AS $$
BEGIN
    NEW.id_escale := 'ES' || LPAD(nextval('escale_seq')::text, 3, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_escale_id
BEFORE INSERT ON escale
FOR EACH ROW
EXECUTE FUNCTION escale_id_generate();



CREATE OR REPLACE FUNCTION update_vol_arrivee() RETURNS trigger AS $$
DECLARE
    max_ordre INT;
    last_arrivee TIMESTAMP;
BEGIN
    -- Dernière escale du vol
    SELECT MAX(ordre_escale) INTO max_ordre
    FROM escale
    WHERE id_vol = NEW.id_vol;

    IF NEW.ordre_escale = max_ordre THEN
        last_arrivee := NEW.heure_arrivee;
        -- Mettre à jour le vol
        UPDATE vol
        SET arrivee_effective = last_arrivee,
            etat_vol = 2  -- 2 = Arrivé
        WHERE id_vol = NEW.id_vol;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_vol
AFTER INSERT OR UPDATE OF heure_arrivee ON escale
FOR EACH ROW
EXECUTE FUNCTION update_vol_arrivee();
