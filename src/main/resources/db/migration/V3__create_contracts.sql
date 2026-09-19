CREATE TABLE contracts (
    id BIGSERIAL PRIMARY KEY,

    provider_id BIGINT NOT NULL,
    client_id BIGINT NOT NULL,

    CONSTRAINT fk_contract_provider
        FOREIGN KEY (provider_id)
        REFERENCES providers(id),

    CONSTRAINT fk_contract_client
        FOREIGN KEY (client_id)
        REFERENCES clients(id)
);