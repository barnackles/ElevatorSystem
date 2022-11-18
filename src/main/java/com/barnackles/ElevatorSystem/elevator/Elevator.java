package com.barnackles.ElevatorSystem.elevator;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Queue;


@Data
@NoArgsConstructor
public class Elevator {

    private Long carId;
    private int currentFloor;
    private int destinationFloor;
    private int numberOfFloors;
    private int direction; // heading up, down or idle
//    private List<Integer> destinationsList;
    private Queue<Integer> destinationsQueue;

}
