package com.barnackles.ElevatorSystem.systemManager;

import com.barnackles.ElevatorSystem.elevator.Elevator;
import com.barnackles.ElevatorSystem.elevator.ElevatorService;
import com.barnackles.ElevatorSystem.simRunner.RunSimulationTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Queue;


@Controller
@RequiredArgsConstructor
@Slf4j
public class SystemManagerController {

    private final ThreadPoolTaskScheduler scheduler;
    private final ElevatorService elevatorService;



    @GetMapping("/")
    public String setup() {
        return "systemSetup";
    }


    @GetMapping("/home")
    public String home(Model model) {

    model.addAttribute("elevatorList", elevatorService.getAllElevators());
    model.addAttribute("floors", elevatorService.getElevatorById(1L).getNumberOfFloors());

        return "systemManager";
    }


//    @GetMapping("/add-elevators/{numberOfElevators}/{numberOfFloors}")
    @PostMapping("/add-elevators")

    public String setElevators(@RequestParam(value = "numberOfElevators") int numberOfElevators,
                               @RequestParam(value = "numberOfFloors") int numberOfFloors) {
        log.info("values: {}, {} ",numberOfElevators, numberOfFloors);
        elevatorService.addNumberOfElevators(numberOfElevators, numberOfFloors);
        return "redirect:/home";
    }

//    @GetMapping("/call/{id}/{destinationFloor}")
    @PostMapping("/call")
    public String callElevator(@RequestParam(value = "id") Long id, @RequestParam(value = "actionFloor") int actionFloor,
                               @RequestParam(value = "destinationFloor") int destinationFloor) {

        Elevator calledElevator = elevatorService.getElevatorById(id);
//        List<Integer> calledElevatorDestinations = calledElevator.getDestinationsList();
        Queue<Integer> calledElevatorDestinations = calledElevator.getDestinationsQueue();
        calledElevatorDestinations.add(actionFloor);
        calledElevatorDestinations.add(destinationFloor);
        elevatorService.updateElevator(id, calledElevator);
        log.info("Destination Floor: {} added for elevator number: {}. Action Floor: {} \n Destinations: {}",
                destinationFloor, actionFloor, id, elevatorService.getElevatorById(id).getDestinationsQueue().toString());

        return "redirect:/home";
    }

    @GetMapping("/step")

    public String step() {

        elevatorService.simulationStep();

        return "redirect:/home";
    }

    @GetMapping("/simulate")
    @ResponseBody
    public String startSimulation() {

        scheduler.scheduleAtFixedRate(new RunSimulationTask(), 1000);
        return "simulate";
    }




}
