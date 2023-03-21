package com.example.taxibooking.mapstructs.mappers;

import com.example.taxibooking.mapstructs.dtos.ReservationDto;
import com.example.taxibooking.models.Reservation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IReservationMapper {

    ReservationDto modelToDto(Reservation reservation);
    Reservation dtoToModel(ReservationDto reservationDto);
}
