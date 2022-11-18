package com.barnackles.ElevatorSystem.simRunner;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Data
@RequiredArgsConstructor

public class RunSimulationTask implements Runnable {


    @Override
    public void run() {

        //simulating step
        System.out.println("Step: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));


    }

}
