package flights;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Flight {
    private String number;
    private Airport source;
    private Airport destination;
    private String scheduledDepartTime;
    private String actualDepartTime;
    private String scheduledArrivalTime;
    private String actualArrivalTime;
    private Aircraft aircraft;
    private Airline airline;
    private static Flight instance = null;

    public Flight(){
        this.number = "";
        this.source = new Airport();
        this.destination = new Airport();
        this.scheduledDepartTime = "";
        this.actualDepartTime = "";
        this.scheduledArrivalTime = "";
        this.actualArrivalTime = "";
        this.aircraft = new Aircraft();
        this.airline = new Airline();
    }

    public Flight(String number, Airport source, Airport destination, String scheduledDepartTime, String actualDepartTime, String scheduledArrivalTime, String actualArrivalTime, Aircraft aircraft, Airline airline){
        this.number = number;
        this.source = source;
        this.destination = destination;
        this.scheduledDepartTime = scheduledDepartTime;
        this.actualDepartTime = actualDepartTime;
        this.scheduledArrivalTime = scheduledArrivalTime;
        this.actualArrivalTime = actualArrivalTime;
        this.aircraft = aircraft;
        this.airline = airline;
    }

    public Flight(Airport source, Airport destination, Aircraft aircraft){
        this.source = source;
        this.destination = destination;
        this.aircraft = aircraft;
    }

    public Airport getSource(){
        return this.source;
    }

    public Airport getDestination(){
        return this.destination;
    }

    public Aircraft getAircraft(){
        return this.aircraft;
    }

    public String toString(){
        return "Flight #: " + this.number + "\nSource: " + this.source.getCode() + "\nDestination: " + this.destination.getCode() + "\nScheduled Depart Time: " + this.scheduledDepartTime + "\nActual Depart Time: " + this.actualDepartTime
                + "\nScheduled Arrival Time: " + this.scheduledArrivalTime + "\nActual Arrival Time: " + this.actualArrivalTime + "\nAirline: " + this.airline.getName() + "\nAircraft ID: " + this.aircraft.getIdentifier()
                + "\nAircraft Status: " + this.aircraft.getStatus() + "\nPrivate Flight: " + this.aircraft.getIsPrivate() + "\nCity: " + this.destination.getCity().getName() + "\nCountry: " + this.destination.getCity().getCountry()
                + "\nTemperature: " + this.destination.getCity().getTemperature() + "C\n";
    }

    public String displayNonRegisteredUser(){
        return "Flight #: " + this.number + "\nSource: " + this.source.getCode() + "\nDestination: " + this.destination.getCode();
    }
}
