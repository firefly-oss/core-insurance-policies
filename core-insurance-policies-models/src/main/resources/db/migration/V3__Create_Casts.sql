-- Flyway V3: implicit varchar casts for enum parameters

CREATE CAST (varchar AS policy_status_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS line_of_business_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS policy_party_role_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS policy_transaction_type_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS policy_transaction_status_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS insured_object_type_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS certificate_status_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS vehicle_use_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS exposure_basis_enum) WITH INOUT AS IMPLICIT;
