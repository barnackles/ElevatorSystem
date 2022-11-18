package com.barnackles.ElevatorSystem.elevator;

import java.util.List;
import java.util.Optional;


public interface ElevatorRepository {

    public List<Elevator> getList();

    public Optional<Elevator> getElevator(Long id);

    public void addElevator(int numberOfFloors);

    public void addNumberOfElevators(int number, int numberOfFloors);

    public void deleteElevator(Long id);

    public void deleteAllElevators();

    public void updateElevator(Long id, Elevator elevator);


}
