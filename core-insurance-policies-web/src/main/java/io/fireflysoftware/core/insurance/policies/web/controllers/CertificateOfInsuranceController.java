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
import io.fireflysoftware.core.insurance.policies.core.services.CertificateOfInsuranceService;
import io.fireflysoftware.core.insurance.policies.interfaces.dtos.CertificateOfInsuranceDTO;
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
 * REST API for certificate issued from a policy transaction, reissued when the cover changes.
 */
@RestController
@RequestMapping("/api/v1/policies/{policyId}/certificates")
@RequiredArgsConstructor
@Validated
@Tag(name = "Certificates of Insurance", description = "Certificate issued from a policy transaction, reissued when the cover changes")
public class CertificateOfInsuranceController {

    private final CertificateOfInsuranceService certificateOfInsuranceService;

    @PostMapping("/filter")
    @Operation(summary = "Filter certificatesOfInsurance", description = "Retrieve a page of certificatesOfInsurance matching the filter")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Page of matching records"),
        @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<CertificateOfInsuranceDTO>>> filterCertificatesOfInsurance(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Filter criteria", required = true)
            @Valid @RequestBody FilterRequest<CertificateOfInsuranceDTO> filterRequest) {
        return certificateOfInsuranceService.filterCertificatesOfInsurance(policyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create CertificateOfInsurance", description = "Create a CertificateOfInsurance")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "CertificateOfInsurance created", content = @Content(schema = @Schema(implementation = CertificateOfInsuranceDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    public Mono<ResponseEntity<CertificateOfInsuranceDTO>> createCertificateOfInsurance(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "CertificateOfInsurance to create", required = true)
            @Valid @RequestBody CertificateOfInsuranceDTO dto) {
        return certificateOfInsuranceService.createCertificateOfInsurance(policyId, dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @GetMapping("/{certificateOfInsuranceId}")
    @Operation(summary = "Get CertificateOfInsurance by id", description = "Retrieve one CertificateOfInsurance")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "CertificateOfInsurance found", content = @Content(schema = @Schema(implementation = CertificateOfInsuranceDTO.class))),
        @ApiResponse(responseCode = "404", description = "CertificateOfInsurance not found", content = @Content)
    })
    public Mono<ResponseEntity<CertificateOfInsuranceDTO>> getCertificateOfInsuranceById(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the CertificateOfInsurance", required = true)
            @PathVariable UUID certificateOfInsuranceId) {
        return certificateOfInsuranceService.getCertificateOfInsuranceById(policyId, certificateOfInsuranceId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{certificateOfInsuranceId}")
    @Operation(summary = "Update CertificateOfInsurance", description = "Update an existing CertificateOfInsurance")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "CertificateOfInsurance updated", content = @Content(schema = @Schema(implementation = CertificateOfInsuranceDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
        @ApiResponse(responseCode = "404", description = "CertificateOfInsurance not found", content = @Content)
    })
    public Mono<ResponseEntity<CertificateOfInsuranceDTO>> updateCertificateOfInsurance(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the CertificateOfInsurance", required = true)
            @PathVariable UUID certificateOfInsuranceId,
            @Parameter(description = "Updated CertificateOfInsurance", required = true)
            @Valid @RequestBody CertificateOfInsuranceDTO dto) {
        return certificateOfInsuranceService.updateCertificateOfInsurance(policyId, certificateOfInsuranceId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{certificateOfInsuranceId}")
    @Operation(summary = "Delete CertificateOfInsurance", description = "Delete a CertificateOfInsurance")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "CertificateOfInsurance deleted"),
        @ApiResponse(responseCode = "404", description = "CertificateOfInsurance not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deleteCertificateOfInsurance(
            @Parameter(description = "Unique identifier of the Policy", required = true)
            @PathVariable UUID policyId,
            @Parameter(description = "Unique identifier of the CertificateOfInsurance", required = true)
            @PathVariable UUID certificateOfInsuranceId) {
        return certificateOfInsuranceService.deleteCertificateOfInsurance(policyId, certificateOfInsuranceId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
