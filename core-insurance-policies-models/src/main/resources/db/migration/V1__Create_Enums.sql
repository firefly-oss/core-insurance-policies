-- Flyway V1: enum types for core-insurance-policies

CREATE TYPE policy_status_enum AS ENUM (
    'QUOTE',
    'BOUND',
    'IN_FORCE',
    'LAPSED',
    'CANCELLED',
    'EXPIRED'
);

CREATE TYPE line_of_business_enum AS ENUM (
    'MOTOR',
    'PROPERTY',
    'LIABILITY',
    'LIFE',
    'HEALTH',
    'MARINE',
    'AVIATION',
    'ACCIDENT',
    'OTHER'
);

CREATE TYPE policy_party_role_enum AS ENUM (
    'POLICYHOLDER',
    'NAMED_INSURED',
    'BENEFICIARY',
    'PAYOR',
    'BROKER',
    'AGENT',
    'LOSS_PAYEE'
);

CREATE TYPE policy_transaction_type_enum AS ENUM (
    'NEW_BUSINESS',
    'ENDORSEMENT',
    'RENEWAL',
    'CANCELLATION',
    'REINSTATEMENT'
);

CREATE TYPE policy_transaction_status_enum AS ENUM (
    'DRAFT',
    'QUOTED',
    'BOUND',
    'ISSUED',
    'REVERSED'
);

CREATE TYPE insured_object_type_enum AS ENUM (
    'PERSON',
    'VEHICLE',
    'PROPERTY',
    'VESSEL',
    'AIRCRAFT',
    'LIABILITY',
    'OTHER'
);

CREATE TYPE certificate_status_enum AS ENUM (
    'ISSUED',
    'SUPERSEDED',
    'REVOKED'
);

CREATE TYPE vehicle_use_enum AS ENUM (
    'PRIVATE',
    'COMMERCIAL',
    'FLEET'
);

CREATE TYPE exposure_basis_enum AS ENUM (
    'TURNOVER',
    'PAYROLL',
    'UNITS',
    'OTHER'
);

