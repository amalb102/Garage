package com.example.demo.dataobjects;

public class Fahrzeug {
    private String kennenZeichen;
    private int parkplatzNummer;

    public Fahrzeug(String kennenZeichen, int parkplatzNummer){
        this.kennenZeichen = kennenZeichen;
        this.parkplatzNummer = parkplatzNummer;
    }

    public int getParkplatzNummer() {
        return parkplatzNummer;
    }

    public String getKennenZeichen() {
        return kennenZeichen;
    }

    public void setKennenZeichen(String kennenZeichen) {
        this.kennenZeichen = kennenZeichen;
    }

    public void setParkplatzNummer(int parkplatzNummer) {
        this.parkplatzNummer = parkplatzNummer;
    }

    @Override
    public String toString() {
        return "Fahrzeug{" +
            "kennenZeichen='" + kennenZeichen + '\'' +
            ", parkplatzNummer=" + parkplatzNummer +
            '}';
    }
}
