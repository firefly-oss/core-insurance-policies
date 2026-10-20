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
import com.firefly.core.insurance.policies.core.services.PolicyPartyService;
import com.firefly.core.insurance.policies.interfaces.dtos.PolicyPartyDTO;
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
 * REST API for party role on a policy. The party itself is owned by customer management..
 */
@RestController
@RequestMapping("/api/v1/policies/{policyId}/parties")
@RequiredArgsConstructor
@Validated
@Tag(name = "Policy Parties", description = "Party role on a policy. The party itself is owned by customer management.")
public class PolicyPartyController {

    private final PolicyPartyService policyPartyService;

    @PostMapping("/filter")
    @Operation(summary = "Filter policyParties", description = "Retrieve a page of policyParties matching the filter")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Page of matching records"),
        @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<PolicyPartyDTO>>> filterPolicyParties(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Filter criteria", required = true)
            @Valid @RequestBody FilterRequest<PolicyPartyDTO> filterRequest) {
        return policyPartyService.filterPolicyParties(policyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create PolicyParty", description = "Create a PolicyParty")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "PolicyParty created", content = @Content(schema = @Schema(implementation = PolicyPartyDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    public Mono<ResponseEntity<PolicyPartyDTO>> createPolicyParty(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "PolicyParty to create", required = true)
            @Valid @RequestBody PolicyPartyDTO dto) {
        return policyPartyService.createPolicyParty(policyId, dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @GetMapping("/{policyPartyId}")
    @Operation(summary = "Get PolicyParty by id", description = "Retrieve one PolicyParty")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "PolicyParty found", content = @Content(schema = @Schema(implementation = PolicyPartyDTO.class))),
        @ApiResponse(responseCode = "404", description = "PolicyParty not found", content = @Content)
    })
    public Mono<ResponseEntity<PolicyPartyDTO>> getPolicyPartyById(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the PolicyParty", required = true)
            @PathVariable UUID policyPartyId) {
        return policyPartyService.getPolicyPartyById(policyId, policyPartyId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{policyPartyId}")
    @Operation(summary = "Update PolicyParty", description = "Update an existing PolicyParty")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "PolicyParty updated", content = @Content(schema = @Schema(implementation = PolicyPartyDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
        @ApiResponse(responseCode = "404", description = "PolicyParty not found", content = @Content)
    })
    public Mono<ResponseEntity<PolicyPartyDTO>> updatePolicyParty(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the PolicyParty", required = true)
            @PathVariable UUID policyPartyId,
            @Parameter(description = "Updated PolicyParty", required = true)
            @Valid @RequestBody PolicyPartyDTO dto) {
        return policyPartyService.updatePolicyParty(policyId, policyPartyId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{policyPartyId}")
    @Operation(summary = "Delete PolicyParty", description = "Delete a PolicyParty")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "PolicyParty deleted"),
        @ApiResponse(responseCode = "404", description = "PolicyParty not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deletePolicyParty(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the PolicyParty", required = true)
            @PathVariable UUID policyPartyId) {
        return policyPartyService.deletePolicyParty(policyId, policyPartyId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
