CREATE SEQUENCE IF NOT EXISTS user_name_seq START WITH 1 INCREMENT BY 1;
CREATE EXTENSION pgcrypto;

ALTER TABLE group_persons
    ADD CONSTRAINT fk_group
        FOREIGN KEY (id_group) REFERENCES "group" (id)
            ON DELETE CASCADE;

ALTER TABLE purchase
    ADD CONSTRAINT fk_group
        FOREIGN KEY (id_group) REFERENCES "group" (id) ON DELETE CASCADE;


ALTER TABLE basket
    ADD CONSTRAINT fk_purchase
        FOREIGN KEY (id_purchase) REFERENCES purchase (id) ON DELETE CASCADE;