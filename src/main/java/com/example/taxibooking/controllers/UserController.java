package com.example.taxibooking.controllers;

import com.example.taxibooking.mapstructs.dtos.CustomerGetDto;
import com.example.taxibooking.mapstructs.dtos.CustomerPostDto;
import com.example.taxibooking.mapstructs.mappers.ICustomerMapper;
import com.example.taxibooking.models.CreditCard;
import com.example.taxibooking.models.users.Customer;
import com.example.taxibooking.models.users.User;
import com.example.taxibooking.payloads.ApiResponse;
import com.example.taxibooking.services.IUserService;
import com.example.taxibooking.services.implementations.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/users/")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}/")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id){
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PutMapping("{id}/")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody User user){
        return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}/")
    public ResponseEntity<ApiResponse> DeleteUser(@PathVariable(value = "id") Long id){

        ApiResponse apiResponse = userService.deleteUser(id);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

//    @PostMapping(path = "/add-credit-card")
//    public String createCreditCard(@RequestBody CreditCard creditCard){
//        userService.saveCreditCard(creditCard);
//        return"Success";
//    }
}
