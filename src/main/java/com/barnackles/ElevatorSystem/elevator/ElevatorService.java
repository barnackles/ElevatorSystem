package com.barnackles.ElevatorSystem.elevator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Queue;

@RequiredArgsConstructor
@Service
@Slf4j
public class ElevatorService {

    private final ElevatorRepository elevatorRepository;


    public List<Elevator> getAllElevators() {
        return elevatorRepository.getList();
    }

    public Elevator getElevatorById(Long id) {
        return elevatorRepository.getElevator(id).orElseThrow(() -> {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        });
    }

    public void addElevator(int numberOfFloors) {
        elevatorRepository.addElevator(numberOfFloors);
    }

    public void addNumberOfElevators(int number, int numberOfFloors) {
        elevatorRepository.addNumberOfElevators(number, numberOfFloors);
    }

    public void deleteElevatorById(Long id) {
        elevatorRepository.deleteElevator(id);
    }

    public void deleteAllElevators() {
        elevatorRepository.deleteAllElevators();
    }

    public void updateElevator(Long id, Elevator elevator) {
      elevatorRepository.updateElevator(id, elevator);
    }

    public void simulationStep() {

        for (Elevator e: getAllElevators()) {

            e.setDestinationFloor(updateDestinationFloor(e));
            updateElevator(e.getCarId(), e);
            e.setDirection(updateDirection(e));
            updateElevator(e.getCarId(), e);
            e.setCurrentFloor(updateCurrentFloor(e));
            updateElevator(e.getCarId(), e);
            e.setDestinationsQueue(updateDestinations(e));
            updateElevator(e.getCarId(), e);
        }
    }




    private int updateDestinationFloor(Elevator e) {

        // If queue is not empty and current floor is different from destination floor
        // take first element of the queue and set it as the destination floor
        if((!(e.getDestinationsQueue().isEmpty())) && (e.getCurrentFloor() != (e.getDestinationFloor()))) {
            log.info("Destination not reached. Keep current destination");
            return e.getDestinationFloor();
        } else if((!(e.getDestinationsQueue().isEmpty())) && (e.getCurrentFloor() == (e.getDestinationFloor())))  {
            // If queue is not empty and current floor is the same as destination floor
            log.info("Next destination is: {}", e.getDestinationsQueue().element());
            return e.getDestinationsQueue().element();
        } else if((e.getDestinationsQueue().isEmpty()) && (e.getCurrentFloor() != (e.getDestinationFloor())))  {
            // If queue is empty and current floor is different from destination floor
            log.info("Destination not reached. Keep current destination");
            return e.getDestinationFloor();
        } else {
            // if queue is empty and destination = current go to default.
            log.info("No destinations returning to default");
            return e.getNumberOfFloors() / 2;
        }
//

    }

    private int updateDirection(Elevator e) {
        return Integer.compare(e.getDestinationFloor(), e.getCurrentFloor());
    }

    private int updateCurrentFloor(Elevator e) {
        if (e.getDirection() == 1) {

            return e.getCurrentFloor() + 1;

        } else if (e.getDirection() == -1) {

            return e.getCurrentFloor() - 1;

        } else return e.getCurrentFloor();
    }

    private Queue<Integer> updateDestinations(Elevator e) {

        // if destination queue is not empty and current floor is the same as destination floor remove first element.
        if ((!(e.getDestinationsQueue().isEmpty())) && (e.getCurrentFloor() == e.getDestinationsQueue().element())) {
            Queue<Integer> updatedQueue = e.getDestinationsQueue();
            updatedQueue.remove();
            return updatedQueue;
        } else return e.getDestinationsQueue();
    }
}
