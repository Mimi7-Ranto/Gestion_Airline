-- Modify reservation trigger to not overwrite ID if already set
DROP TRIGGER IF EXISTS trg_reservation_id ON reservation;

CREATE OR REPLACE FUNCTION reservation_id_generate() RETURNS trigger AS $$
BEGIN
    -- Only generate ID if it's NULL (not already set by Hibernate)
    IF NEW.id_reservation IS NULL THEN
        NEW.id_reservation := 'RS' || LPAD(nextval('reservation_seq')::text, 6, '0');
    END IF;
    IF NEW.date_reservation IS NULL THEN
        NEW.date_reservation := CURRENT_TIMESTAMP;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_reservation_id
    BEFORE INSERT ON reservation
    FOR EACH ROW EXECUTE FUNCTION reservation_id_generate();
