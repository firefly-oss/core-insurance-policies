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


package com.firefly.core.insurance.policies.web.controllers;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.insurance.policies.core.services.PolicyService;
import com.firefly.core.insurance.policies.interfaces.dtos.PolicyDTO;
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
 * REST API for insurance policy master record.
 */
@RestController
@RequestMapping("/api/v1/policies")
@RequiredArgsConstructor
@Validated
@Tag(name = "Policies", description = "Insurance policy master record")
public class PolicyController {

    private final PolicyService policyService;

    @PostMapping("/filter")
    @Operation(summary = "Filter policies", description = "Retrieve a page of policies matching the filter")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Page of matching records"),
        @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<PolicyDTO>>> filterPolicies(
            @Parameter(description = "Filter criteria", required = true)
            @Valid @RequestBody FilterRequest<PolicyDTO> filterRequest) {
        return policyService.filterPolicies(filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create Policy", description = "Create a Policy")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Policy created", content = @Content(schema = @Schema(implementation = PolicyDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    public Mono<ResponseEntity<PolicyDTO>> createPolicy(
            @Parameter(description = "Policy to create", required = true)
            @Valid @RequestBody PolicyDTO dto) {
        return policyService.createPolicy(dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @GetMapping("/{policyId}")
    @Operation(summary = "Get Policy by id", description = "Retrieve one Policy")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Policy found", content = @Content(schema = @Schema(implementation = PolicyDTO.class))),
        @ApiResponse(responseCode = "404", description = "Policy not found", content = @Content)
    })
    public Mono<ResponseEntity<PolicyDTO>> getPolicyById(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId) {
        return policyService.getPolicyById(policyId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{policyId}")
    @Operation(summary = "Update Policy", description = "Update an existing Policy")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Policy updated", content = @Content(schema = @Schema(implementation = PolicyDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
        @ApiResponse(responseCode = "404", description = "Policy not found", content = @Content)
    })
    public Mono<ResponseEntity<PolicyDTO>> updatePolicy(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Updated Policy", required = true)
            @Valid @RequestBody PolicyDTO dto) {
        return policyService.updatePolicy(policyId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{policyId}")
    @Operation(summary = "Delete Policy", description = "Delete a Policy")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Policy deleted"),
        @ApiResponse(responseCode = "404", description = "Policy not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deletePolicy(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId) {
        return policyService.deletePolicy(policyId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
