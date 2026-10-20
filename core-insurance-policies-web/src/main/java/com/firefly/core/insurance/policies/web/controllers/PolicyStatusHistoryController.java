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
import com.firefly.core.insurance.policies.core.services.PolicyStatusHistoryService;
import com.firefly.core.insurance.policies.interfaces.dtos.PolicyStatusHistoryDTO;
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
 * REST API for status change recorded against a policy.
 */
@RestController
@RequestMapping("/api/v1/policies/{policyId}/status-history")
@RequiredArgsConstructor
@Validated
@Tag(name = "Policy Status History", description = "Status change recorded against a policy")
public class PolicyStatusHistoryController {

    private final PolicyStatusHistoryService policyStatusHistoryService;

    @PostMapping("/filter")
    @Operation(summary = "Filter policyStatusHistories", description = "Retrieve a page of policyStatusHistories matching the filter")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Page of matching records"),
        @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<PolicyStatusHistoryDTO>>> filterPolicyStatusHistories(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Filter criteria", required = true)
            @Valid @RequestBody FilterRequest<PolicyStatusHistoryDTO> filterRequest) {
        return policyStatusHistoryService.filterPolicyStatusHistories(policyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create PolicyStatusHistory", description = "Create a PolicyStatusHistory")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "PolicyStatusHistory created", content = @Content(schema = @Schema(implementation = PolicyStatusHistoryDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    public Mono<ResponseEntity<PolicyStatusHistoryDTO>> createPolicyStatusHistory(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "PolicyStatusHistory to create", required = true)
            @Valid @RequestBody PolicyStatusHistoryDTO dto) {
        return policyStatusHistoryService.createPolicyStatusHistory(policyId, dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @GetMapping("/{policyStatusHistoryId}")
    @Operation(summary = "Get PolicyStatusHistory by id", description = "Retrieve one PolicyStatusHistory")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "PolicyStatusHistory found", content = @Content(schema = @Schema(implementation = PolicyStatusHistoryDTO.class))),
        @ApiResponse(responseCode = "404", description = "PolicyStatusHistory not found", content = @Content)
    })
    public Mono<ResponseEntity<PolicyStatusHistoryDTO>> getPolicyStatusHistoryById(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the PolicyStatusHistory", required = true)
            @PathVariable UUID policyStatusHistoryId) {
        return policyStatusHistoryService.getPolicyStatusHistoryById(policyId, policyStatusHistoryId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{policyStatusHistoryId}")
    @Operation(summary = "Update PolicyStatusHistory", description = "Update an existing PolicyStatusHistory")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "PolicyStatusHistory updated", content = @Content(schema = @Schema(implementation = PolicyStatusHistoryDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
        @ApiResponse(responseCode = "404", description = "PolicyStatusHistory not found", content = @Content)
    })
    public Mono<ResponseEntity<PolicyStatusHistoryDTO>> updatePolicyStatusHistory(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the PolicyStatusHistory", required = true)
            @PathVariable UUID policyStatusHistoryId,
            @Parameter(description = "Updated PolicyStatusHistory", required = true)
            @Valid @RequestBody PolicyStatusHistoryDTO dto) {
        return policyStatusHistoryService.updatePolicyStatusHistory(policyId, policyStatusHistoryId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{policyStatusHistoryId}")
    @Operation(summary = "Delete PolicyStatusHistory", description = "Delete a PolicyStatusHistory")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "PolicyStatusHistory deleted"),
        @ApiResponse(responseCode = "404", description = "PolicyStatusHistory not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deletePolicyStatusHistory(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the PolicyStatusHistory", required = true)
            @PathVariable UUID policyStatusHistoryId) {
        return policyStatusHistoryService.deletePolicyStatusHistory(policyId, policyStatusHistoryId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
