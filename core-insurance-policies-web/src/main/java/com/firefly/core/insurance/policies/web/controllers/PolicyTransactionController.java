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
import com.firefly.core.insurance.policies.core.services.PolicyTransactionService;
import com.firefly.core.insurance.policies.interfaces.dtos.PolicyTransactionDTO;
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
 * REST API for new business, endorsement, renewal, cancellation, or reinstatement on a policy.
 */
@RestController
@RequestMapping("/api/v1/policies/{policyId}/transactions")
@RequiredArgsConstructor
@Validated
@Tag(name = "Policy Transactions", description = "New business, endorsement, renewal, cancellation, or reinstatement on a policy")
public class PolicyTransactionController {

    private final PolicyTransactionService policyTransactionService;

    @PostMapping("/filter")
    @Operation(summary = "Filter policyTransactions", description = "Retrieve a page of policyTransactions matching the filter")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Page of matching records"),
        @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<PolicyTransactionDTO>>> filterPolicyTransactions(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Filter criteria", required = true)
            @Valid @RequestBody FilterRequest<PolicyTransactionDTO> filterRequest) {
        return policyTransactionService.filterPolicyTransactions(policyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create PolicyTransaction", description = "Create a PolicyTransaction")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "PolicyTransaction created", content = @Content(schema = @Schema(implementation = PolicyTransactionDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    public Mono<ResponseEntity<PolicyTransactionDTO>> createPolicyTransaction(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "PolicyTransaction to create", required = true)
            @Valid @RequestBody PolicyTransactionDTO dto) {
        return policyTransactionService.createPolicyTransaction(policyId, dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @GetMapping("/{policyTransactionId}")
    @Operation(summary = "Get PolicyTransaction by id", description = "Retrieve one PolicyTransaction")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "PolicyTransaction found", content = @Content(schema = @Schema(implementation = PolicyTransactionDTO.class))),
        @ApiResponse(responseCode = "404", description = "PolicyTransaction not found", content = @Content)
    })
    public Mono<ResponseEntity<PolicyTransactionDTO>> getPolicyTransactionById(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the PolicyTransaction", required = true)
            @PathVariable UUID policyTransactionId) {
        return policyTransactionService.getPolicyTransactionById(policyId, policyTransactionId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{policyTransactionId}")
    @Operation(summary = "Update PolicyTransaction", description = "Update an existing PolicyTransaction")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "PolicyTransaction updated", content = @Content(schema = @Schema(implementation = PolicyTransactionDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
        @ApiResponse(responseCode = "404", description = "PolicyTransaction not found", content = @Content)
    })
    public Mono<ResponseEntity<PolicyTransactionDTO>> updatePolicyTransaction(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the PolicyTransaction", required = true)
            @PathVariable UUID policyTransactionId,
            @Parameter(description = "Updated PolicyTransaction", required = true)
            @Valid @RequestBody PolicyTransactionDTO dto) {
        return policyTransactionService.updatePolicyTransaction(policyId, policyTransactionId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{policyTransactionId}")
    @Operation(summary = "Delete PolicyTransaction", description = "Delete a PolicyTransaction")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "PolicyTransaction deleted"),
        @ApiResponse(responseCode = "404", description = "PolicyTransaction not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deletePolicyTransaction(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the PolicyTransaction", required = true)
            @PathVariable UUID policyTransactionId) {
        return policyTransactionService.deletePolicyTransaction(policyId, policyTransactionId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
