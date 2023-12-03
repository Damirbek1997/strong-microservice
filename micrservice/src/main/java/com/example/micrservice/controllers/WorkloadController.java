package com.example.micrservice.controllers;

import com.example.micrservice.models.TrainingSummaryModel;
import com.example.micrservice.models.WorkloadModel;
import com.example.micrservice.models.crud.CreateWorkloadModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/workload")
public interface WorkloadController {
    @GetMapping("/monthly-summary")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a monthly summary workload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created a workload"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    List<TrainingSummaryModel> getMonthlySummaryWorkload();

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Create a new workload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created a workload"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    WorkloadModel create(@RequestBody CreateWorkloadModel createWorkloadModel);

    @PostMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Create a list of workloads")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created a workload"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content),
    })
    List<WorkloadModel> create(@RequestBody List<CreateWorkloadModel> createWorkloadModels);
}
