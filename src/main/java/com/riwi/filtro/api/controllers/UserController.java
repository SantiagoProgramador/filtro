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

import com.riwi.filtro.api.dto.request.UserRequest;
import com.riwi.filtro.api.dto.response.UserResponse;
import com.riwi.filtro.infrastructure.abstracts.IUserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class UserController {

  @Autowired
  private final IUserService iUserService;

  @GetMapping
  public ResponseEntity<Page<UserResponse>> showAll(@RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "3") int size) {

    return ResponseEntity.ok(this.iUserService.getAll(size, page - 1));
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<UserResponse> showUser(@PathVariable Long id) {

    return ResponseEntity.ok(this.iUserService.getById(id));
  }

  @PostMapping(path = "/add")
  public ResponseEntity<UserResponse> addUser(@Validated @RequestBody UserRequest userRequest) {

    return ResponseEntity.ok(this.iUserService.create(userRequest));
  }

  @PutMapping(path = "/update/{id}")
  public ResponseEntity<UserResponse> updateUser(@Validated @RequestBody UserRequest userRequest,
      @PathVariable Long id) {

    return ResponseEntity.ok(this.iUserService.update(id, userRequest));
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

    this.iUserService.delete(id);
    return ResponseEntity.noContent().build();
  }
}