package com.example.taxibooking.mapstructs.mappers;

import com.example.taxibooking.mapstructs.dtos.CustomerGetDto;
import com.example.taxibooking.mapstructs.dtos.CustomerPostDto;
import com.example.taxibooking.models.users.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICustomerMapper {

    CustomerGetDto modelToDto(Customer customer);

    Customer dtoToModel(CustomerPostDto customerPostDto);
}
