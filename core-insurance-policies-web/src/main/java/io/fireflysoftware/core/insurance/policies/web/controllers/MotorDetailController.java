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


package io.fireflysoftware.core.insurance.policies.web.controllers;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.insurance.policies.core.services.MotorDetailService;
import io.fireflysoftware.core.insurance.policies.interfaces.dtos.MotorDetailDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * REST API for vehicle and principal driver for a motor policy.
 */
@RestController
@RequestMapping("/api/v1/policies/{policyId}/motor-details")
@RequiredArgsConstructor
@Validated
@Tag(name = "Motor Details", description = "Vehicle and principal driver for a motor policy")
public class MotorDetailController {

    private final MotorDetailService motorDetailService;

    @PostMapping("/filter")
    @Operation(summary = "Filter motorDetails", description = "Retrieve a page of motorDetails matching the filter")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Page of matching records"),
        @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<MotorDetailDTO>>> filterMotorDetails(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Filter criteria", required = true)
            @Valid @RequestBody FilterRequest<MotorDetailDTO> filterRequest) {
        return motorDetailService.filterMotorDetails(policyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create MotorDetail", description = "Create a MotorDetail")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "MotorDetail created", content = @Content(schema = @Schema(implementation = MotorDetailDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    public Mono<ResponseEntity<MotorDetailDTO>> createMotorDetail(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "MotorDetail to create", required = true)
            @Valid @RequestBody MotorDetailDTO dto) {
        return motorDetailService.createMotorDetail(policyId, dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @GetMapping("/{motorDetailId}")
    @Operation(summary = "Get MotorDetail by id", description = "Retrieve one MotorDetail")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "MotorDetail found", content = @Content(schema = @Schema(implementation = MotorDetailDTO.class))),
        @ApiResponse(responseCode = "404", description = "MotorDetail not found", content = @Content)
    })
    public Mono<ResponseEntity<MotorDetailDTO>> getMotorDetailById(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the MotorDetail", required = true)
            @PathVariable UUID motorDetailId) {
        return motorDetailService.getMotorDetailById(policyId, motorDetailId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{motorDetailId}")
    @Operation(summary = "Update MotorDetail", description = "Update an existing MotorDetail")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "MotorDetail updated", content = @Content(schema = @Schema(implementation = MotorDetailDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
        @ApiResponse(responseCode = "404", description = "MotorDetail not found", content = @Content)
    })
    public Mono<ResponseEntity<MotorDetailDTO>> updateMotorDetail(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the MotorDetail", required = true)
            @PathVariable UUID motorDetailId,
            @Parameter(description = "Updated MotorDetail", required = true)
            @Valid @RequestBody MotorDetailDTO dto) {
        return motorDetailService.updateMotorDetail(policyId, motorDetailId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{motorDetailId}")
    @Operation(summary = "Delete MotorDetail", description = "Delete a MotorDetail")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "MotorDetail deleted"),
        @ApiResponse(responseCode = "404", description = "MotorDetail not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deleteMotorDetail(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the MotorDetail", required = true)
            @PathVariable UUID motorDetailId) {
        return motorDetailService.deleteMotorDetail(policyId, motorDetailId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
