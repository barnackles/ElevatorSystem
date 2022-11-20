package com.barnackles.ElevatorSystem.dtos;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ElevatorSystemSetupDto {

    @NotNull(message = "Number of elevators must be between 1 and 16")
    @Min(value = 1, message = "Number of elevators must be between 1 and 16")
    @Max(value = 16, message = "Number of elevators must be between 1 and 16")
    private Integer numberOfElevators;
    @NotNull(message = "Number of floors must be between 2 and 1000")
    @Min(value = 2, message = "Number of floors must be between 2 and 1000")
    @Max(value = 1000, message = "Number of floors must be between 2 and 1000")
    private Integer numberOfFloors;

}
