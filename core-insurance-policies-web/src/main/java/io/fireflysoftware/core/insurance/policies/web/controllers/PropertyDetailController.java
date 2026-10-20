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
import io.fireflysoftware.core.insurance.policies.core.services.PropertyDetailService;
import io.fireflysoftware.core.insurance.policies.interfaces.dtos.PropertyDetailDTO;
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
 * REST API for location and sums for a property policy, including home and commercial premises.
 */
@RestController
@RequestMapping("/api/v1/policies/{policyId}/property-details")
@RequiredArgsConstructor
@Validated
@Tag(name = "Property Details", description = "Location and sums for a property policy, including home and commercial premises")
public class PropertyDetailController {

    private final PropertyDetailService propertyDetailService;

    @PostMapping("/filter")
    @Operation(summary = "Filter propertyDetails", description = "Retrieve a page of propertyDetails matching the filter")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Page of matching records"),
        @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<PropertyDetailDTO>>> filterPropertyDetails(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Filter criteria", required = true)
            @Valid @RequestBody FilterRequest<PropertyDetailDTO> filterRequest) {
        return propertyDetailService.filterPropertyDetails(policyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create PropertyDetail", description = "Create a PropertyDetail")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "PropertyDetail created", content = @Content(schema = @Schema(implementation = PropertyDetailDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    public Mono<ResponseEntity<PropertyDetailDTO>> createPropertyDetail(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "PropertyDetail to create", required = true)
            @Valid @RequestBody PropertyDetailDTO dto) {
        return propertyDetailService.createPropertyDetail(policyId, dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @GetMapping("/{propertyDetailId}")
    @Operation(summary = "Get PropertyDetail by id", description = "Retrieve one PropertyDetail")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "PropertyDetail found", content = @Content(schema = @Schema(implementation = PropertyDetailDTO.class))),
        @ApiResponse(responseCode = "404", description = "PropertyDetail not found", content = @Content)
    })
    public Mono<ResponseEntity<PropertyDetailDTO>> getPropertyDetailById(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the PropertyDetail", required = true)
            @PathVariable UUID propertyDetailId) {
        return propertyDetailService.getPropertyDetailById(policyId, propertyDetailId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{propertyDetailId}")
    @Operation(summary = "Update PropertyDetail", description = "Update an existing PropertyDetail")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "PropertyDetail updated", content = @Content(schema = @Schema(implementation = PropertyDetailDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
        @ApiResponse(responseCode = "404", description = "PropertyDetail not found", content = @Content)
    })
    public Mono<ResponseEntity<PropertyDetailDTO>> updatePropertyDetail(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the PropertyDetail", required = true)
            @PathVariable UUID propertyDetailId,
            @Parameter(description = "Updated PropertyDetail", required = true)
            @Valid @RequestBody PropertyDetailDTO dto) {
        return propertyDetailService.updatePropertyDetail(policyId, propertyDetailId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{propertyDetailId}")
    @Operation(summary = "Delete PropertyDetail", description = "Delete a PropertyDetail")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "PropertyDetail deleted"),
        @ApiResponse(responseCode = "404", description = "PropertyDetail not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deletePropertyDetail(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the PropertyDetail", required = true)
            @PathVariable UUID propertyDetailId) {
        return propertyDetailService.deletePropertyDetail(policyId, propertyDetailId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
