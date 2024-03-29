package flights;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Flight {
    private int number;
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
        this.number = 0;
        this.source = new Airport();
        this.destination = new Airport();
        this.scheduledDepartTime = "";
        this.actualDepartTime = "";
        this.scheduledArrivalTime = "";
        this.actualArrivalTime = "";
        this.aircraft = new Aircraft();
        this.airline = new Airline();
    }

    public Flight(int number, Airport source, Airport destination, String scheduledDepartTime, String actualDepartTime, String scheduledArrivalTime, String actualArrivalTime, Aircraft aircraft, Airline airline){
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

    public void setSource(Airport source) {this.source = source;}

    public Airport getDestination(){
        return this.destination;
    }

    public void setDestination(Airport destination) {this.destination = destination;}

    public Aircraft getAircraft(){
        return this.aircraft;
    }

    public int getNumber() { return this.number;}

    public void setNumber(int number) { this.number = number;}

    public String getScheduledDepartTime() {
        return scheduledDepartTime;
    }

    public void setScheduledDepartTime(String scheduledDepartTime) {
        this.scheduledDepartTime = scheduledDepartTime;
    }

    public String getActualDepartTime() {
        return actualDepartTime;
    }

    public void setActualDepartTime(String actualDepartTime) {
        this.actualDepartTime = actualDepartTime;
    }

    public String getScheduledArrivalTime() {
        return scheduledArrivalTime;
    }

    public void setScheduledArrivalTime(String scheduledArrivalTime) {
        this.scheduledArrivalTime = scheduledArrivalTime;
    }

    public String getActualArrivalTime() {
        return actualArrivalTime;
    }

    public void setActualArrivalTime(String actualArrivalTime) {
        this.actualArrivalTime = actualArrivalTime;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public String toString(){
        return "\nFlight #: " + this.number + "\nSource: " + this.source.getCode() + "\nDestination: " + this.destination.getCode() + "\nScheduled Depart Time: " + this.scheduledDepartTime + "\nActual Depart Time: " + this.actualDepartTime
                + "\nScheduled Arrival Time: " + this.scheduledArrivalTime + "\nActual Arrival Time: " + this.actualArrivalTime + "\nAirline: " + this.airline.getName() + "\nAircraft ID: " + this.aircraft.getIdentifier()
                + "\nAircraft Status: " + this.aircraft.getStatus() + "\nPrivate Flight: " + this.aircraft.getIsPrivate() + "\nCity: " + this.destination.getCity().getName() + "\nCountry: " + this.destination.getCity().getCountry()
                + "\nTemperature: " + this.destination.getCity().getTemperature() + "C\n";
    }

    public String displayNonRegisteredUser(){
        return "\nFlight #: " + this.number + "\nSource: " + this.source.getCode() + "\nDestination: " + this.destination.getCode() + "\n";
    }
}
