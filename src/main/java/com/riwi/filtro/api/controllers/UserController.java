package com.riwi.filtro.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.filtro.api.dto.errors.ErrorResponse;
import com.riwi.filtro.api.dto.request.UserRequest;
import com.riwi.filtro.api.dto.response.UserResponse;
import com.riwi.filtro.infrastructure.abstracts.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class UserController {

  @Autowired
  private final IUserService iUserService;

  @Operation(summary = "Show the users in pages")
  @ApiResponse(responseCode = "400", description = "When the params send are invalid", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
  @GetMapping
  public ResponseEntity<Page<UserResponse>> showAll(@RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "3") int size) {

    return ResponseEntity.ok(this.iUserService.getAll(size, page - 1));
  }

  @Operation(summary = "Show the user according to the id given")
  @ApiResponse(responseCode = "400", description = "When the id given is not found", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
  @GetMapping(path = "/{id}")
  public ResponseEntity<UserResponse> showUser(@PathVariable Long id) {

    return ResponseEntity.ok(this.iUserService.getById(id));
  }

  @Operation(summary = "Create an user with the information summoned")
  @ApiResponse(responseCode = "400", description = "When the information is incorrect", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
  @PostMapping(path = "/add")
  public ResponseEntity<UserResponse> addUser(@Validated @RequestBody UserRequest userRequest) {

    return ResponseEntity.ok(this.iUserService.create(userRequest));
  }

  @Operation(summary = "Update an user according to the id given and the information summoned")
  @ApiResponse(responseCode = "400", description = "When the id given is not found or the information is incorrect", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
  @PutMapping(path = "/update/{id}")
  public ResponseEntity<UserResponse> updateUser(@Validated @RequestBody UserRequest userRequest,
      @PathVariable Long id) {

    return ResponseEntity.ok(this.iUserService.update(id, userRequest));
  }

  @Operation(summary = "Delete an user according to the id given")
  @ApiResponse(responseCode = "400", description = "When the id given is not found", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

    this.iUserService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
