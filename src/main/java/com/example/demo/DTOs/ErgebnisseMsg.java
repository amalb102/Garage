package com.example.demo.DTOs;

public class ErgebnisseMsg {
    private String ergebnisse, message;

    public ErgebnisseMsg(String ergebnisse) {
        this.ergebnisse = ergebnisse;
    }

    public ErgebnisseMsg() {

    }

    public String getErgebnisse() {
        return ergebnisse;
    }

    public String getMessage() {
        return message;
    }

    public void setErgebnisse(String ergebnisse) {
        this.ergebnisse = ergebnisse;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErgebnisseMsg{" +
            "ergebnisse='" + ergebnisse + '\'' +
            ", message='" + message + '\'' +
            '}';
    }
}
