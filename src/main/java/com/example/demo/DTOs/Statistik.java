package com.example.demo.DTOs;

public class Statistik {
    private int nummer;
    private int anzahlPlaetze;
    private int anzahlFreiPlzAutos;
    private int anzahlFreiPlzMotorraeder;
    private int anzahlFreieplaetze;
    private int prozentsatzReserviertePlz;


    public Statistik(int nummer, int anzahlPlaetze, int anzahlFreiPlzAutos, int anzahlFreiPlzMotorraeder) {
        this.nummer = nummer;
        this.anzahlPlaetze = anzahlPlaetze;
        this.anzahlFreiPlzAutos = anzahlFreiPlzAutos;
        this.anzahlFreiPlzMotorraeder = anzahlFreiPlzMotorraeder;
    }

    public Statistik(){};

    public int getAnzahlFreiPlzAutos() {
        return anzahlFreiPlzAutos;
    }

    public int getAnzahlPlaetze() {
        return anzahlPlaetze;
    }

    public int getAnzahlFreiPlzMotorraeder() {
        return anzahlFreiPlzMotorraeder;
    }

    public int getNummer() {
        return nummer;
    }

    public int getAnzahlFreieplaetze() {
        return anzahlFreieplaetze;
    }

    public int getProzentsatzReserviertePlz() {
        return prozentsatzReserviertePlz;
    }

    public void setAnzahlFreiPlzAutos(int anzahlFreiPlzAutos) {
        this.anzahlFreiPlzAutos = anzahlFreiPlzAutos;
    }

    public void setAnzahlFreiPlzMotorraeder(int anzahlFreiPlzMotorraeder) {
        this.anzahlFreiPlzMotorraeder = anzahlFreiPlzMotorraeder;
    }

    public void setAnzahlPlaetze(int anzahlPlaetze) {
        this.anzahlPlaetze = anzahlPlaetze;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    public void setProzentsatz() {
        this.prozentsatzReserviertePlz = (int)((( (double) anzahlPlaetze - (anzahlFreiPlzAutos + anzahlFreiPlzMotorraeder)) / (double) anzahlPlaetze) * 100);
        System.out.println(prozentsatzReserviertePlz);
    }


    public void setAnzahlFreieplaetze() {
        this.anzahlFreieplaetze =  anzahlFreiPlzAutos + anzahlFreiPlzMotorraeder;
    }

}
