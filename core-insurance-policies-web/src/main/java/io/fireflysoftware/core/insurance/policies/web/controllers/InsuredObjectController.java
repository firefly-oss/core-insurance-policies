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
import io.fireflysoftware.core.insurance.policies.core.services.InsuredObjectService;
import io.fireflysoftware.core.insurance.policies.interfaces.dtos.InsuredObjectDTO;
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
 * REST API for risk object attached to a policy transaction.
 */
@RestController
@RequestMapping("/api/v1/policies/{policyId}/insured-objects")
@RequiredArgsConstructor
@Validated
@Tag(name = "Insured Objects", description = "Risk object attached to a policy transaction")
public class InsuredObjectController {

    private final InsuredObjectService insuredObjectService;

    @PostMapping("/filter")
    @Operation(summary = "Filter insuredObjects", description = "Retrieve a page of insuredObjects matching the filter")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Page of matching records"),
        @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<InsuredObjectDTO>>> filterInsuredObjects(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Filter criteria", required = true)
            @Valid @RequestBody FilterRequest<InsuredObjectDTO> filterRequest) {
        return insuredObjectService.filterInsuredObjects(policyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create InsuredObject", description = "Create a InsuredObject")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "InsuredObject created", content = @Content(schema = @Schema(implementation = InsuredObjectDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    public Mono<ResponseEntity<InsuredObjectDTO>> createInsuredObject(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "InsuredObject to create", required = true)
            @Valid @RequestBody InsuredObjectDTO dto) {
        return insuredObjectService.createInsuredObject(policyId, dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @GetMapping("/{insuredObjectId}")
    @Operation(summary = "Get InsuredObject by id", description = "Retrieve one InsuredObject")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "InsuredObject found", content = @Content(schema = @Schema(implementation = InsuredObjectDTO.class))),
        @ApiResponse(responseCode = "404", description = "InsuredObject not found", content = @Content)
    })
    public Mono<ResponseEntity<InsuredObjectDTO>> getInsuredObjectById(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the InsuredObject", required = true)
            @PathVariable UUID insuredObjectId) {
        return insuredObjectService.getInsuredObjectById(policyId, insuredObjectId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{insuredObjectId}")
    @Operation(summary = "Update InsuredObject", description = "Update an existing InsuredObject")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "InsuredObject updated", content = @Content(schema = @Schema(implementation = InsuredObjectDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
        @ApiResponse(responseCode = "404", description = "InsuredObject not found", content = @Content)
    })
    public Mono<ResponseEntity<InsuredObjectDTO>> updateInsuredObject(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the InsuredObject", required = true)
            @PathVariable UUID insuredObjectId,
            @Parameter(description = "Updated InsuredObject", required = true)
            @Valid @RequestBody InsuredObjectDTO dto) {
        return insuredObjectService.updateInsuredObject(policyId, insuredObjectId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{insuredObjectId}")
    @Operation(summary = "Delete InsuredObject", description = "Delete a InsuredObject")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "InsuredObject deleted"),
        @ApiResponse(responseCode = "404", description = "InsuredObject not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deleteInsuredObject(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the InsuredObject", required = true)
            @PathVariable UUID insuredObjectId) {
        return insuredObjectService.deleteInsuredObject(policyId, insuredObjectId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
