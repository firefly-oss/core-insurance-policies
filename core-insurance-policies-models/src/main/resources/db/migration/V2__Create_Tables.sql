-- Flyway V2: tables for core-insurance-policies

-- Insurance policy master record
CREATE TABLE policy (
    policy_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL,
    contract_id UUID NOT NULL,
    product_id UUID NOT NULL,
    policy_number VARCHAR(64) NOT NULL UNIQUE,
    line_of_business line_of_business_enum NOT NULL,
    policy_status policy_status_enum NOT NULL,
    currency VARCHAR(3) NOT NULL,
    inception_date DATE NOT NULL,
    expiry_date DATE NULL,
    renewal_date DATE NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Party role on a policy. The party itself is owned by customer management.
CREATE TABLE policy_party (
    policy_party_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    policy_id UUID NOT NULL REFERENCES policy (policy_id) ON DELETE CASCADE,
    party_id UUID NOT NULL,
    role policy_party_role_enum NOT NULL,
    effective_from DATE NULL,
    effective_to DATE NULL,
    active BOOLEAN NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- New business, endorsement, renewal, cancellation, or reinstatement on a policy
CREATE TABLE policy_transaction (
    policy_transaction_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    policy_id UUID NOT NULL REFERENCES policy (policy_id) ON DELETE CASCADE,
    transaction_number VARCHAR(64) NOT NULL,
    transaction_type policy_transaction_type_enum NOT NULL,
    transaction_status policy_transaction_status_enum NOT NULL,
    effective_date DATE NOT NULL,
    expiry_date DATE NULL,
    sequence_number INTEGER NOT NULL,
    out_of_sequence BOOLEAN NULL,
    notice_days INTEGER NULL,
    reason TEXT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Coverage terms recorded against a policy transaction
CREATE TABLE coverage (
    coverage_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    policy_id UUID NOT NULL REFERENCES policy (policy_id) ON DELETE CASCADE,
    policy_transaction_id UUID NOT NULL REFERENCES policy_transaction (policy_transaction_id) ON DELETE CASCADE,
    coverage_code VARCHAR(64) NOT NULL,
    coverage_name VARCHAR(255) NULL,
    limit_amount DECIMAL(19, 4) NULL,
    deductible_amount DECIMAL(19, 4) NULL,
    currency VARCHAR(3) NOT NULL,
    premium_amount DECIMAL(19, 4) NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Risk object attached to a policy transaction
CREATE TABLE insured_object (
    insured_object_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    policy_id UUID NOT NULL REFERENCES policy (policy_id) ON DELETE CASCADE,
    policy_transaction_id UUID NOT NULL REFERENCES policy_transaction (policy_transaction_id) ON DELETE CASCADE,
    object_type insured_object_type_enum NOT NULL,
    party_id UUID NULL,
    description TEXT NULL,
    identifier VARCHAR(128) NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Status change recorded against a policy
CREATE TABLE policy_status_history (
    policy_status_history_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    policy_id UUID NOT NULL REFERENCES policy (policy_id) ON DELETE CASCADE,
    policy_status policy_status_enum NOT NULL,
    status_reason VARCHAR(500) NULL,
    changed_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Certificate issued from a policy transaction, reissued when the cover changes
CREATE TABLE certificate_of_insurance (
    certificate_of_insurance_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    policy_id UUID NOT NULL REFERENCES policy (policy_id) ON DELETE CASCADE,
    certificate_number VARCHAR(64) NOT NULL,
    policy_transaction_id UUID NOT NULL REFERENCES policy_transaction (policy_transaction_id) ON DELETE CASCADE,
    holder_party_id UUID NOT NULL,
    certificate_status certificate_status_enum NOT NULL,
    issued_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Location and sums for a property policy, including home and commercial premises
CREATE TABLE property_detail (
    property_detail_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    policy_id UUID NOT NULL REFERENCES policy (policy_id) ON DELETE CASCADE,
    insured_object_id UUID NOT NULL REFERENCES insured_object (insured_object_id) ON DELETE CASCADE,
    occupancy VARCHAR(128) NULL,
    construction VARCHAR(128) NULL,
    year_built INTEGER NULL,
    address_line VARCHAR(255) NULL,
    city VARCHAR(100) NULL,
    postal_code VARCHAR(20) NULL,
    country_code VARCHAR(2) NULL,
    buildings_sum DECIMAL(19, 4) NULL,
    contents_sum DECIMAL(19, 4) NULL,
    currency VARCHAR(3) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Vehicle and principal driver for a motor policy
CREATE TABLE motor_detail (
    motor_detail_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    policy_id UUID NOT NULL REFERENCES policy (policy_id) ON DELETE CASCADE,
    insured_object_id UUID NOT NULL REFERENCES insured_object (insured_object_id) ON DELETE CASCADE,
    vehicle_identifier VARCHAR(64) NULL,
    make VARCHAR(64) NULL,
    model VARCHAR(64) NULL,
    model_year INTEGER NULL,
    vehicle_use vehicle_use_enum NOT NULL,
    driver_party_id UUID NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Exposure for a liability policy
CREATE TABLE liability_detail (
    liability_detail_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    policy_id UUID NOT NULL REFERENCES policy (policy_id) ON DELETE CASCADE,
    insured_object_id UUID NOT NULL REFERENCES insured_object (insured_object_id) ON DELETE CASCADE,
    exposure_basis exposure_basis_enum NOT NULL,
    exposure_amount DECIMAL(19, 4) NULL,
    territory VARCHAR(128) NULL,
    currency VARCHAR(3) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Life assured and sum assured on a life policy
CREATE TABLE life_detail (
    life_detail_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    policy_id UUID NOT NULL REFERENCES policy (policy_id) ON DELETE CASCADE,
    insured_object_id UUID NOT NULL REFERENCES insured_object (insured_object_id) ON DELETE CASCADE,
    life_assured_party_id UUID NOT NULL,
    sum_assured DECIMAL(19, 4) NOT NULL,
    term_years INTEGER NULL,
    currency VARCHAR(3) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Member cover on a health policy
CREATE TABLE health_detail (
    health_detail_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    policy_id UUID NOT NULL REFERENCES policy (policy_id) ON DELETE CASCADE,
    insured_object_id UUID NOT NULL REFERENCES insured_object (insured_object_id) ON DELETE CASCADE,
    member_party_id UUID NOT NULL,
    plan_code VARCHAR(64) NOT NULL,
    cover_start DATE NOT NULL,
    cover_end DATE NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_policy_tenant_id ON policy (tenant_id);
CREATE INDEX idx_policy_contract_id ON policy (contract_id);
CREATE INDEX idx_policy_product_id ON policy (product_id);
CREATE INDEX idx_policy_party_policy_id ON policy_party (policy_id);
CREATE INDEX idx_policy_party_party_id ON policy_party (party_id);
CREATE INDEX idx_policy_transaction_policy_id ON policy_transaction (policy_id);
CREATE INDEX idx_coverage_policy_id ON coverage (policy_id);
CREATE INDEX idx_coverage_policy_transaction_id ON coverage (policy_transaction_id);
CREATE INDEX idx_insured_object_policy_id ON insured_object (policy_id);
CREATE INDEX idx_insured_object_policy_transaction_id ON insured_object (policy_transaction_id);
CREATE INDEX idx_insured_object_party_id ON insured_object (party_id);
CREATE INDEX idx_policy_status_history_policy_id ON policy_status_history (policy_id);
CREATE INDEX idx_certificate_of_insurance_policy_id ON certificate_of_insurance (policy_id);
CREATE INDEX idx_certificate_of_insurance_policy_transaction_id ON certificate_of_insurance (policy_transaction_id);
CREATE INDEX idx_certificate_of_insurance_holder_party_id ON certificate_of_insurance (holder_party_id);
CREATE INDEX idx_property_detail_policy_id ON property_detail (policy_id);
CREATE INDEX idx_property_detail_insured_object_id ON property_detail (insured_object_id);
CREATE INDEX idx_motor_detail_policy_id ON motor_detail (policy_id);
CREATE INDEX idx_motor_detail_insured_object_id ON motor_detail (insured_object_id);
CREATE INDEX idx_motor_detail_driver_party_id ON motor_detail (driver_party_id);
CREATE INDEX idx_liability_detail_policy_id ON liability_detail (policy_id);
CREATE INDEX idx_liability_detail_insured_object_id ON liability_detail (insured_object_id);
CREATE INDEX idx_life_detail_policy_id ON life_detail (policy_id);
CREATE INDEX idx_life_detail_insured_object_id ON life_detail (insured_object_id);
CREATE INDEX idx_life_detail_life_assured_party_id ON life_detail (life_assured_party_id);
CREATE INDEX idx_health_detail_policy_id ON health_detail (policy_id);
CREATE INDEX idx_health_detail_insured_object_id ON health_detail (insured_object_id);
CREATE INDEX idx_health_detail_member_party_id ON health_detail (member_party_id);
COMMENT ON COLUMN policy.tenant_id IS 'Logical reference: Firefly deployment tenant. No foreign key across services.';
COMMENT ON COLUMN policy.contract_id IS 'Logical reference: core-common-contract-mgmt contract. No foreign key across services.';
COMMENT ON COLUMN policy.product_id IS 'Logical reference: core-common-product-mgmt product. No foreign key across services.';
COMMENT ON COLUMN policy_party.party_id IS 'Logical reference: core-common-customer-mgmt party. No foreign key across services.';
COMMENT ON COLUMN insured_object.party_id IS 'Logical reference: core-common-customer-mgmt party, when the object is a person. No foreign key across services.';
COMMENT ON COLUMN certificate_of_insurance.holder_party_id IS 'Logical reference: core-common-customer-mgmt party. No foreign key across services.';
COMMENT ON COLUMN motor_detail.driver_party_id IS 'Logical reference: core-common-customer-mgmt party. No foreign key across services.';
COMMENT ON COLUMN life_detail.life_assured_party_id IS 'Logical reference: core-common-customer-mgmt party. No foreign key across services.';
COMMENT ON COLUMN health_detail.member_party_id IS 'Logical reference: core-common-customer-mgmt party. No foreign key across services.';
