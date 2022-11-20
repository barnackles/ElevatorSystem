package com.barnackles.ElevatorSystem.systemManager;

import com.barnackles.ElevatorSystem.call.InsideCallDto;
import com.barnackles.ElevatorSystem.call.OutsideCallDto;
import com.barnackles.ElevatorSystem.dtos.ElevatorSystemSetupDto;
import com.barnackles.ElevatorSystem.elevator.Elevator;
import com.barnackles.ElevatorSystem.elevator.ElevatorService;
import com.barnackles.ElevatorSystem.simRunner.RunSimulationTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Queue;


@Controller
@RequiredArgsConstructor
@Slf4j
public class SystemManagerController {

    private final ThreadPoolTaskScheduler scheduler;
    private final ElevatorService elevatorService;





    @GetMapping("/")
    public String showSetupForm(Model model) {

        model.addAttribute("elevatorSystemSetupDto", new ElevatorSystemSetupDto());
        return "systemSetup";
    }

    @PostMapping("/")

    public String setUp(@Valid ElevatorSystemSetupDto elevatorSystemSetupDto, BindingResult result) {

        if (result.hasErrors()) {
            return "systemSetup";
        }

        log.info("values: {}, {} ", elevatorSystemSetupDto.getNumberOfElevators(), elevatorSystemSetupDto.getNumberOfFloors());
        elevatorService.addNumberOfElevators(elevatorSystemSetupDto.getNumberOfElevators(), elevatorSystemSetupDto.getNumberOfFloors());
        return "redirect:/home";
    }


    @GetMapping("/home")
    public String home(Model model) {

    model.addAttribute("elevatorList", elevatorService.getAllElevators());
    model.addAttribute("floors", elevatorService.getElevatorById(1L).getNumberOfFloors());
    model.addAttribute("outsideCallDto", new OutsideCallDto());
    model.addAttribute("insideCallDto", new InsideCallDto());

        return "systemManager";

    }



    @PostMapping("/outside")
    public String callElevatorFromOutside(@Valid OutsideCallDto callDto, BindingResult result, Model model) {


        if (result.hasErrors()) {
            model.addAttribute("elevatorList", elevatorService.getAllElevators());
            model.addAttribute("floors", elevatorService.getElevatorById(1L).getNumberOfFloors());
            model.addAttribute("insideCallDto", new InsideCallDto());
            return "systemManager";
        }

        Elevator calledElevator = elevatorService.getElevatorById(callDto.getElevatorId());
        Queue<Integer> calledElevatorDestinations = calledElevator.getDestinationsQueue();

        // leave only estimations which are different from action floor
        List<Integer> refinedDestinations =  callDto.getDestinationFloors().stream()
                .filter(t -> !(t.equals(callDto.getActionFloor())))
                .toList();

        calledElevatorDestinations.add(callDto.getActionFloor());
        // Add all destinations. Multiple destinations request made from one floor come in already sorted
        calledElevatorDestinations.addAll(refinedDestinations);


        elevatorService.updateElevator(callDto.getElevatorId(), calledElevator);
        log.info("Destination Floor: {} added for elevator number: {}. From floor: {} \n Destinations: {}",
                callDto.getDestinationFloors(), callDto.getActionFloor(), callDto.getElevatorId(),
                elevatorService.getElevatorById(callDto.getElevatorId()).getDestinationsQueue().toString());

        return "redirect:/home";
    }


    @PostMapping("/inside")
    public String callElevatorFromInside(@Valid InsideCallDto callDto, BindingResult result, Model model) {


        if (result.hasErrors()) {
            model.addAttribute("elevatorList", elevatorService.getAllElevators());
            model.addAttribute("floors", elevatorService.getElevatorById(1L).getNumberOfFloors());
            model.addAttribute("outsideCallDto", new OutsideCallDto());
            return "systemManager";
        }

        Elevator calledElevator = elevatorService.getElevatorById(callDto.getElevatorId());
//        List<Integer> calledElevatorDestinations = calledElevator.getDestinationsList();
        Queue<Integer> calledElevatorDestinations = calledElevator.getDestinationsQueue();

        // Add all destinations. Multiple destinations request made from one floor come in already sorted
        calledElevatorDestinations.addAll(callDto.getDestinationFloors());


        elevatorService.updateElevator(callDto.getElevatorId(), calledElevator);
        log.info("Destination Floor: {} added for elevator number: {} from inside. \n Destinations: {}",
                callDto.getDestinationFloors(), callDto.getElevatorId(),
                elevatorService.getElevatorById(callDto.getElevatorId()).getDestinationsQueue().toString());

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
