package com.barnackles.ElevatorSystem.elevator;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service // no database therefore service
public class ElevatorRepositoryImp implements ElevatorRepository {

    private List<Elevator> elevatorList;
    private static Long currId = 1L;

    public ElevatorRepositoryImp(List<Elevator> elevatorList) {
        this.elevatorList = new ArrayList<>();
    }

    @Override
    public List<Elevator> getList() {
        return elevatorList;
    }

    @Override
    public Optional<Elevator> getElevator(Long id) {
        return elevatorList.stream()
                .filter(t -> id.equals(t.getCarId()))
                .findFirst();
    }

    @Override
    public void addElevator(int numberOfFloors) {
        Elevator elevator = new Elevator();
        elevator.setCarId(currId);
        elevator.setCurrentFloor(numberOfFloors / 2);
        elevator.setDestinationFloor(numberOfFloors / 2);
        elevator.setNumberOfFloors(numberOfFloors);
        elevator.setDirection(0);
//        elevator.setDestinationsList(new ArrayList<>());
        elevator.setDestinationsQueue(new LinkedList<>());
        elevatorList.add(elevator);
        currId++;
    }

    @Override
    public void addNumberOfElevators(int number, int numberOfFloors) {
        for (int i = 0; i < number; i++) {
            addElevator(numberOfFloors);
        }
    }


    @Override
    public void deleteElevator(Long id) {
        elevatorList = elevatorList.stream()
                .filter(t -> !id.equals((t.getCarId())))
                .toList();
    }


    @Override
    public void deleteAllElevators() {
        this.elevatorList = new ArrayList<>();
    }

    @Override
    public void updateElevator(Long id, Elevator elevator) {

    }
}
