CREATE TABLE claim (
    id                          BIGINT PRIMARY KEY,
    customer_contact_info       VARCHAR NOT NULL,
    claim_created_time          TIMESTAMP,
    claim_type                  VARCHAR NOT NULL,
    claim_state                 VARCHAR,
    description                 VARCHAR NOT NULL,
    claim_finished_time         TIMESTAMP,
    claim_answer                VARCHAR,
    notes                       VARCHAR,
    executor_id                 BIGINT,

    CONSTRAINT fk_claim_executor FOREIGN KEY (executor_id) REFERENCES claim_executor (id)
);

CREATE SEQUENCE IF NOT EXISTS claim_sequence;