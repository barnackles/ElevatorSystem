package com.barnackles.ElevatorSystem.systemManager;

import com.barnackles.ElevatorSystem.simRunner.RunSimulationTask;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequiredArgsConstructor
@RequestMapping("/system")
public class SystemManagerController {

    private final ThreadPoolTaskScheduler scheduler;

    @GetMapping("/simulate")
    @ResponseBody
    public String startSimulation() {

        scheduler.scheduleAtFixedRate(new RunSimulationTask(), 1000);
        return "simulate";
    }




}
