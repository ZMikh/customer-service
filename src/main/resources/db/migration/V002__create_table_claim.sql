CREATE TABLE claim (
    id                          BIGINT PRIMARY KEY,
    customer_contact_info       VARCHAR,
    claim_created_time          TIMESTAMP,
    claim_type                  VARCHAR,
    claim_state                 VARCHAR,
    description                 VARCHAR,
    claim_finished_time         TIMESTAMP,
    executor_id                 BIGINT,

    CONSTRAINT fk_claim_executor FOREIGN KEY (executor_id) REFERENCES claim_executor (id)
);

CREATE SEQUENCE IF NOT EXISTS claim_sequence;