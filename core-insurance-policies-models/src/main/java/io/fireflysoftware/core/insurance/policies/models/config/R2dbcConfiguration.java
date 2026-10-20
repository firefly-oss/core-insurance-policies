/*
 * Copyright 2026 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.fireflysoftware.core.insurance.policies.models.config;

import io.fireflysoftware.core.insurance.policies.interfaces.enums.PolicyStatus;
import io.fireflysoftware.core.insurance.policies.interfaces.enums.LineOfBusiness;
import io.fireflysoftware.core.insurance.policies.interfaces.enums.PolicyPartyRole;
import io.fireflysoftware.core.insurance.policies.interfaces.enums.PolicyTransactionType;
import io.fireflysoftware.core.insurance.policies.interfaces.enums.PolicyTransactionStatus;
import io.fireflysoftware.core.insurance.policies.interfaces.enums.InsuredObjectType;
import io.fireflysoftware.core.insurance.policies.interfaces.enums.CertificateStatus;
import io.fireflysoftware.core.insurance.policies.interfaces.enums.VehicleUse;
import io.fireflysoftware.core.insurance.policies.interfaces.enums.ExposureBasis;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.postgresql.client.SSLMode;
import io.r2dbc.postgresql.codec.EnumCodec;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Registers every PostgreSQL enum used by this service with the R2DBC codec.
 * Without this, the driver binds enum parameters as varchar and Postgres rejects the cast.
 */
@Configuration
public class R2dbcConfiguration extends AbstractR2dbcConfiguration {

    @Value("${DB_HOST:localhost}")
    private String host;

    @Value("${DB_PORT:5432}")
    private int port;

    @Value("${DB_NAME:postgres}")
    private String database;

    @Value("${DB_USERNAME:postgres}")
    private String username;

    @Value("${DB_PASSWORD:postgres}")
    private String password;

    @Value("${DB_SSL_MODE:disable}")
    private String sslMode;

    @WritingConverter
    static class PolicyStatusConverter implements Converter<PolicyStatus, PolicyStatus> {
        @Override
        public PolicyStatus convert(PolicyStatus source) {
            return source;
        }
    }

    @WritingConverter
    static class LineOfBusinessConverter implements Converter<LineOfBusiness, LineOfBusiness> {
        @Override
        public LineOfBusiness convert(LineOfBusiness source) {
            return source;
        }
    }

    @WritingConverter
    static class PolicyPartyRoleConverter implements Converter<PolicyPartyRole, PolicyPartyRole> {
        @Override
        public PolicyPartyRole convert(PolicyPartyRole source) {
            return source;
        }
    }

    @WritingConverter
    static class PolicyTransactionTypeConverter implements Converter<PolicyTransactionType, PolicyTransactionType> {
        @Override
        public PolicyTransactionType convert(PolicyTransactionType source) {
            return source;
        }
    }

    @WritingConverter
    static class PolicyTransactionStatusConverter implements Converter<PolicyTransactionStatus, PolicyTransactionStatus> {
        @Override
        public PolicyTransactionStatus convert(PolicyTransactionStatus source) {
            return source;
        }
    }

    @WritingConverter
    static class InsuredObjectTypeConverter implements Converter<InsuredObjectType, InsuredObjectType> {
        @Override
        public InsuredObjectType convert(InsuredObjectType source) {
            return source;
        }
    }

    @WritingConverter
    static class CertificateStatusConverter implements Converter<CertificateStatus, CertificateStatus> {
        @Override
        public CertificateStatus convert(CertificateStatus source) {
            return source;
        }
    }

    @WritingConverter
    static class VehicleUseConverter implements Converter<VehicleUse, VehicleUse> {
        @Override
        public VehicleUse convert(VehicleUse source) {
            return source;
        }
    }

    @WritingConverter
    static class ExposureBasisConverter implements Converter<ExposureBasis, ExposureBasis> {
        @Override
        public ExposureBasis convert(ExposureBasis source) {
            return source;
        }
    }

    @Override
    protected List<Object> getCustomConverters() {
        List<Object> converters = new ArrayList<>();
        converters.add(new PolicyStatusConverter());
        converters.add(new LineOfBusinessConverter());
        converters.add(new PolicyPartyRoleConverter());
        converters.add(new PolicyTransactionTypeConverter());
        converters.add(new PolicyTransactionStatusConverter());
        converters.add(new InsuredObjectTypeConverter());
        converters.add(new CertificateStatusConverter());
        converters.add(new VehicleUseConverter());
        converters.add(new ExposureBasisConverter());
        return converters;
    }

    @Bean
    @Primary
    @Override
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host(host)
                        .port(port)
                        .username(username)
                        .password(password)
                        .database(database)
                        .sslMode(SSLMode.valueOf(sslMode.toUpperCase()))
                        .codecRegistrar(EnumCodec.builder()
                                .withEnum("policy_status_enum", PolicyStatus.class)
                                .withEnum("line_of_business_enum", LineOfBusiness.class)
                                .withEnum("policy_party_role_enum", PolicyPartyRole.class)
                                .withEnum("policy_transaction_type_enum", PolicyTransactionType.class)
                                .withEnum("policy_transaction_status_enum", PolicyTransactionStatus.class)
                                .withEnum("insured_object_type_enum", InsuredObjectType.class)
                                .withEnum("certificate_status_enum", CertificateStatus.class)
                                .withEnum("vehicle_use_enum", VehicleUse.class)
                                .withEnum("exposure_basis_enum", ExposureBasis.class)
                                .build())
                        .build()
        );
    }
}
