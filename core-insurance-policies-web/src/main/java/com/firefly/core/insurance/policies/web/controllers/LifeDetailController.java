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
import com.firefly.core.insurance.policies.core.services.LifeDetailService;
import com.firefly.core.insurance.policies.interfaces.dtos.LifeDetailDTO;
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
 * REST API for life assured and sum assured on a life policy.
 */
@RestController
@RequestMapping("/api/v1/policies/{policyId}/life-details")
@RequiredArgsConstructor
@Validated
@Tag(name = "Life Details", description = "Life assured and sum assured on a life policy")
public class LifeDetailController {

    private final LifeDetailService lifeDetailService;

    @PostMapping("/filter")
    @Operation(summary = "Filter lifeDetails", description = "Retrieve a page of lifeDetails matching the filter")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Page of matching records"),
        @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<LifeDetailDTO>>> filterLifeDetails(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Filter criteria", required = true)
            @Valid @RequestBody FilterRequest<LifeDetailDTO> filterRequest) {
        return lifeDetailService.filterLifeDetails(policyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create LifeDetail", description = "Create a LifeDetail")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "LifeDetail created", content = @Content(schema = @Schema(implementation = LifeDetailDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    public Mono<ResponseEntity<LifeDetailDTO>> createLifeDetail(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "LifeDetail to create", required = true)
            @Valid @RequestBody LifeDetailDTO dto) {
        return lifeDetailService.createLifeDetail(policyId, dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @GetMapping("/{lifeDetailId}")
    @Operation(summary = "Get LifeDetail by id", description = "Retrieve one LifeDetail")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "LifeDetail found", content = @Content(schema = @Schema(implementation = LifeDetailDTO.class))),
        @ApiResponse(responseCode = "404", description = "LifeDetail not found", content = @Content)
    })
    public Mono<ResponseEntity<LifeDetailDTO>> getLifeDetailById(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the LifeDetail", required = true)
            @PathVariable UUID lifeDetailId) {
        return lifeDetailService.getLifeDetailById(policyId, lifeDetailId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{lifeDetailId}")
    @Operation(summary = "Update LifeDetail", description = "Update an existing LifeDetail")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "LifeDetail updated", content = @Content(schema = @Schema(implementation = LifeDetailDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
        @ApiResponse(responseCode = "404", description = "LifeDetail not found", content = @Content)
    })
    public Mono<ResponseEntity<LifeDetailDTO>> updateLifeDetail(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the LifeDetail", required = true)
            @PathVariable UUID lifeDetailId,
            @Parameter(description = "Updated LifeDetail", required = true)
            @Valid @RequestBody LifeDetailDTO dto) {
        return lifeDetailService.updateLifeDetail(policyId, lifeDetailId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{lifeDetailId}")
    @Operation(summary = "Delete LifeDetail", description = "Delete a LifeDetail")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "LifeDetail deleted"),
        @ApiResponse(responseCode = "404", description = "LifeDetail not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deleteLifeDetail(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the LifeDetail", required = true)
            @PathVariable UUID lifeDetailId) {
        return lifeDetailService.deleteLifeDetail(policyId, lifeDetailId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
