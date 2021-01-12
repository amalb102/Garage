package com.example.demo.dataobjects;


public class Parkplatz {
    private int nummer, etage;
    private String typ;

    public Parkplatz(int Nummer, int Etage, String Typ){
        this.nummer = Nummer;
        this.etage = Etage;
        this.typ = Typ;
    }

    public Parkplatz() { }


    public String getTyp() {
        return typ;
    }

    public int getEtage() {
        return etage;
    }

    public int getNummer() {
        return nummer;
    }
}
