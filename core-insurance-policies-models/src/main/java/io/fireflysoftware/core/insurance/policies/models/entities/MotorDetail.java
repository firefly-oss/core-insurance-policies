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


package io.fireflysoftware.core.insurance.policies.models.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import io.fireflysoftware.core.insurance.policies.interfaces.enums.VehicleUse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Vehicle and principal driver for a motor policy.
 * Maps to the 'motor_detail' table.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("motor_detail")
public class MotorDetail {

    @Id
    @Column("motor_detail_id")
    private UUID motorDetailId;

    @Column("policy_id")
    private UUID policyId;

    @Column("insured_object_id")
    private UUID insuredObjectId;

    @Column("vehicle_identifier")
    private String vehicleIdentifier;

    @Column("make")
    private String make;

    @Column("model")
    private String model;

    @Column("model_year")
    private Integer modelYear;

    @Column("vehicle_use")
    private VehicleUse vehicleUse;

    @Column("driver_party_id")
    private UUID driverPartyId;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
