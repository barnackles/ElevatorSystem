# ElevatorSystem

Elevator system.

Application is written in Java with use of Spring boot (MVC).
Simulation data is stored in memory (non-persistent).

System interface allows to:
1) set up simulation (number of elevators and floors)
2) request pick-ups from inside and outside the elevator cars/cabins
   which assumes that destination input from outside of elevator is possible.
3) choose destination floor or floors
4) perform single simulation step and update data
5) display actual state of the system in the form of a table
6) display logs for the system additionally indicating elevator velocity
7) clear simulation data


To run the app use command:
*mvn spring-boot:run*
in the root directory of the application.

Then go to: http://localhost:8080/


