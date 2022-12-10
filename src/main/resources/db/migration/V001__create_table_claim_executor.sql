CREATE SEQUENCE IF NOT EXISTS executor_sequence;

CREATE TABLE claim_executor (
    id                  BIGINT default nextval('executor_sequence') PRIMARY KEY,
    name                VARCHAR,
    general_specialist  BOOLEAN default TRUE
);
