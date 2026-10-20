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
import io.fireflysoftware.core.insurance.policies.core.services.CoverageService;
import io.fireflysoftware.core.insurance.policies.interfaces.dtos.CoverageDTO;
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
 * REST API for coverage terms recorded against a policy transaction.
 */
@RestController
@RequestMapping("/api/v1/policies/{policyId}/coverages")
@RequiredArgsConstructor
@Validated
@Tag(name = "Coverages", description = "Coverage terms recorded against a policy transaction")
public class CoverageController {

    private final CoverageService coverageService;

    @PostMapping("/filter")
    @Operation(summary = "Filter coverages", description = "Retrieve a page of coverages matching the filter")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Page of matching records"),
        @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<CoverageDTO>>> filterCoverages(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Filter criteria", required = true)
            @Valid @RequestBody FilterRequest<CoverageDTO> filterRequest) {
        return coverageService.filterCoverages(policyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create Coverage", description = "Create a Coverage")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Coverage created", content = @Content(schema = @Schema(implementation = CoverageDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    public Mono<ResponseEntity<CoverageDTO>> createCoverage(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Coverage to create", required = true)
            @Valid @RequestBody CoverageDTO dto) {
        return coverageService.createCoverage(policyId, dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @GetMapping("/{coverageId}")
    @Operation(summary = "Get Coverage by id", description = "Retrieve one Coverage")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Coverage found", content = @Content(schema = @Schema(implementation = CoverageDTO.class))),
        @ApiResponse(responseCode = "404", description = "Coverage not found", content = @Content)
    })
    public Mono<ResponseEntity<CoverageDTO>> getCoverageById(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the Coverage", required = true)
            @PathVariable UUID coverageId) {
        return coverageService.getCoverageById(policyId, coverageId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{coverageId}")
    @Operation(summary = "Update Coverage", description = "Update an existing Coverage")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Coverage updated", content = @Content(schema = @Schema(implementation = CoverageDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
        @ApiResponse(responseCode = "404", description = "Coverage not found", content = @Content)
    })
    public Mono<ResponseEntity<CoverageDTO>> updateCoverage(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the Coverage", required = true)
            @PathVariable UUID coverageId,
            @Parameter(description = "Updated Coverage", required = true)
            @Valid @RequestBody CoverageDTO dto) {
        return coverageService.updateCoverage(policyId, coverageId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{coverageId}")
    @Operation(summary = "Delete Coverage", description = "Delete a Coverage")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Coverage deleted"),
        @ApiResponse(responseCode = "404", description = "Coverage not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deleteCoverage(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the Coverage", required = true)
            @PathVariable UUID coverageId) {
        return coverageService.deleteCoverage(policyId, coverageId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
