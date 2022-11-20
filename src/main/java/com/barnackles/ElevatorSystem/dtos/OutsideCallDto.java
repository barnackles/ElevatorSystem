package com.barnackles.ElevatorSystem.dtos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OutsideCallDto {

    @NotNull(message = "YOu have to provide elevator id.")
    private Long elevatorId;
    @NotNull(message = "You have to provide floor from which the elevator is called.")
    private Integer actionFloor;
    @NotEmpty(message = "You have to provide at least one destination floor.")
    private List<Integer> destinationFloors;


}
