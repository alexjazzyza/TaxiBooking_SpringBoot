package com.example.taxibooking.mapstructs.mappers;

import com.example.taxibooking.mapstructs.dtos.DriverGetDto;
import com.example.taxibooking.mapstructs.dtos.DriverPostDto;
import com.example.taxibooking.models.users.Driver;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IDriverMapper {

    DriverGetDto modelToDto(Driver driver);

    Driver dtoToModel(DriverPostDto driver);
}
