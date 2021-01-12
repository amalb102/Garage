package com.example.demo.controllers;

import com.example.demo.DTOs.ErgebnisseMsg;
import com.example.demo.dataobjects.Parkplatz;
import com.example.demo.services.EinAusgangService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EinAusgangController {

    final private EinAusgangService service;

    EinAusgangController(EinAusgangService service){
        this.service = service;
    }

    /**
     * @return Eingang HTMLtemplate
     */
    @GetMapping("/eingang")
    public String getEingangPage(){
        return "eingangPage";
    }

    /**
     *
     * @param typ Faahrzeug Typ (AUTO | MOTORRAD )
     * @param kennenzeichnen Fahrzeug Kennenzechen
     * @return (gefundenen Platz | kein freien Platz)
     */
    @PostMapping("/pltzfinden")
    @ResponseBody
    public ErgebnisseMsg findeParkplatz(@RequestParam String typ, @RequestParam String kennenzeichnen ){
        ErgebnisseMsg erg= new ErgebnisseMsg();
        Parkplatz parkplatz = service.findeParkPlatz(typ);
        if (parkplatz != null){
            if (service.reserviereParkPlatz(parkplatz,kennenzeichnen)){
                erg.setErgebnisse("SUCCESS");
                erg.setMessage("Fahrzeug Wurde regestriert<br> Etage: "
                    +parkplatz.getEtage() +"<br>Pakplatznummer:"+parkplatz.getNummer());
            } else {
                erg.setErgebnisse("FAILD");
                erg.setMessage("Fahrzeug konnte nicht regestrirt werde<br>"
                    + "Bitte melden Sie sich bei der Verwaltung");
            }
        } else {
            erg.setErgebnisse("FAILD");
            erg.setMessage("leider haben wir momentan keine freie Parkplätze\n");
        }
        return erg;
    }

    /**
     * @return Ausgang  HTML template
     */
    @GetMapping("/ausgang")
    public String getAusgangPage(){
        return "auagangPage";
    }

    /**
     * Die Methode dient zum Verlaaseen des Parkplatzes durch Eingabe des Parkplatzes.
     * @param platzNummer Platznummer der zum verlsassenes Parklatz
     * @return ergebnisse des Verlaasenproccess
     */
    @PostMapping("/verlassemitplatznummer")
    @ResponseBody
    public ErgebnisseMsg verlasseParkplatzMitPlatznumer(@RequestParam String platzNummer ) {
        ErgebnisseMsg erg = new ErgebnisseMsg();
        int platznummer = Integer.parseInt(platzNummer);
        if (service.parkplzMitPlzNumFrigeben(platznummer)) {
            erg.setErgebnisse("SUCCESS");
            erg.setMessage("Fahrzeug Wurde erfolgreich abgemeldet<br>");
        } else {
            erg.setErgebnisse("FAILD");
            erg.setMessage(
                "Fahrzeug konnte nicht abgemeldet werden<br>bitte melden Sie sich bei der Verwaltung.");
        }
        return erg;
    }

    /**
     * Die Methode dient zum Verlaaseen des Parkplatzes durch Eingabe des Fahrzeugkennenzeichen.
     * @param kennenzeichen
     * @return
     */
    @PostMapping("/verlassemitkennenzeichen")
    @ResponseBody
    public ErgebnisseMsg verlasseParkplatzMitKenZeichnen(@RequestParam String kennenzeichen ){
        ErgebnisseMsg erg = new ErgebnisseMsg();
        if (service.parkplzMitKenZeichnenFrigeben(kennenzeichen)){
            erg.setErgebnisse("SUCCESS");
            erg.setMessage("Fahrzeug Wurde erfolgreich abgemeldet<br>");
        } else {
            erg.setErgebnisse("FAILD");
            erg.setMessage(
                "Fahrzeug konnte nicht abgemeldet werden<br>bitte melden Sie sich bei der Verwaltung.");

        }
        return erg;
    }

}
