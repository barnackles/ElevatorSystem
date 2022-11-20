package com.barnackles.ElevatorSystem.call;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data

public class Call {

    @NotNull
    private Long elevatorId;
    @NotBlank
    private int actionFloor;
    @NotEmpty
    private List<Integer> destinationFloors;


}
