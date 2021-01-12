package com.example.demo.controllers;

import com.example.demo.DTOs.Statistik;
import com.example.demo.dataobjects.Fahrzeug;
import com.example.demo.dataobjects.Parkplatz;
import com.example.demo.repositoryes.ParkhausRepository;
import com.example.demo.services.VerwaltungService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VerwaltungController {

    final private VerwaltungService service;
    final private ParkhausRepository repository;

    VerwaltungController (VerwaltungService service, ParkhausRepository repository){
        this.service = service;
        this.repository = repository;
    }

    /**
     * @return Verwaltungs HTML template
     */
    @GetMapping("/")
    String getHomePage(){
        return "verwaltungPage";
    }

    /**
     * Die Methode dient zum suchen nach ähnliche Fahrzeugkennenzeichen
     * @param kennenZeichnen gesuchte Kennenzeichen
     * @return Liste von im System gespeicherte FahrzeugKennenzeichen
     */
    @PostMapping("/livesearch")
    @ResponseBody
    public List<Fahrzeug> findeKennenzeichen(@RequestParam String kennenZeichnen ){
        return service.findeKennenzeichen(kennenZeichnen);
    }

    /**
     * Die Funktion dint zum Suchen nach dem Parkplatz bestimmtes Fahrzeug.
     * @param kennenZeichnen Kennenzeichen des gesuchtes Fahrzeug
     * @return Parkplatz des gesuchte Fahrzeug
     */
    @PostMapping("/autosuchen")
    @ResponseBody
    public Parkplatz findeParkplatzdesFahrzeuges(@RequestParam String kennenZeichnen ){
        return service.sucheParkplatz(kennenZeichnen);
    }

    /**
     * Die Methode dient zum aufrufen der Zahlen
     * nich frei Parkplätze und der belegte von Autos
     * und von Motorräder
     * @return zahlen Object
     */
    @PostMapping("/statistiken")
    @ResponseBody
    public List<Statistik> getStatistiken(){
        return service.getBerechneStatistiken();
    }

    @PostMapping("/fuegeParkplaete")
    public String fuegParkplaetze(@RequestParam int etage,@RequestParam int anzahl,@RequestParam String typ){
        service.fuegeParkplaetze(etage,anzahl,typ);
        return "redirect:/";
    }

    @GetMapping("/deleteAll")
    public String deleteAll(){
        service.deleteAll();
        return "redirect:/";
    }

}
