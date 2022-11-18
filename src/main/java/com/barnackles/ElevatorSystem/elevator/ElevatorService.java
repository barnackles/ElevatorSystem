package com.barnackles.ElevatorSystem.elevator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
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


}
