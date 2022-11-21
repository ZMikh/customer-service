CREATE SEQUENCE IF NOT EXISTS executor_sequence;

CREATE TABLE claim_executor (
    id      BIGINT PRIMARY KEY default nextval('executor_sequence'),
    name    VARCHAR
);
