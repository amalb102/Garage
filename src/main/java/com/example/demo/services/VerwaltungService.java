package com.example.demo.services;

import com.example.demo.DTOs.Statistik;
import com.example.demo.dataobjects.Fahrzeug;
import com.example.demo.dataobjects.Parkplatz;
import com.example.demo.repositoryes.ParkhausRepository;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class VerwaltungService {

    final private ParkhausRepository parkhausRepository;
    Logger logger = LoggerFactory.getLogger(VerwaltungService.class);

    VerwaltungService(ParkhausRepository parkhausRepository) {
        this.parkhausRepository = parkhausRepository;
    }

    /**
     *
     * @param kennenzeichen
     * @return
     */
    public List<Fahrzeug> findeKennenzeichen(String kennenzeichen) {
        List<Fahrzeug> fahrzeuge = new ArrayList<>();
        try {
            fahrzeuge= parkhausRepository.findeFahrzeug(kennenzeichen);
        }catch (DataAccessException e){
            logger.error(e.getMessage());
        }
        return fahrzeuge;
    }

    /**
     * @param kennenZeichnen Kennenzeichen des gesuchten Fahrzeug
     * @return Parkplatz
     */
    public Parkplatz sucheParkplatz(String kennenZeichnen) {
        Parkplatz parkplatz = new Parkplatz();
        try {
            parkplatz = parkhausRepository.findeParkplatzDesFahrzeuges(kennenZeichnen);
        } catch ( DataAccessException e){
            logger.error(e.getMessage());
        }
        return parkplatz;
    }

    /**
     * Die methode berchnet wie viele noch frei Parkplätze sowie im gesamten
     * Parkhaus als auch im jede Etage.
     * @return Liste von Zahlen Objekte
     */
    public List<Statistik> getBerechneStatistiken() {
        List<Statistik> etagenStatistiken = parkhausRepository.zaehleEtagen();
        if(etagenStatistiken.isEmpty()){
            return etagenStatistiken;
        }
        etagenStatistiken.forEach(etage -> {
            etage.setAnzahlFreiPlzAutos(parkhausRepository.zahleFreieAutosInEtage(etage.getNummer()));
            etage.setAnzahlFreiPlzMotorraeder(parkhausRepository.zahleFreieMotorraederInEtage(etage.getNummer()));
            etage.setProzentsatz();
            etage.setAnzahlFreieplaetze();
        });
        fuegeParkplatzStatistik(etagenStatistiken);
        return etagenStatistiken;
    }

    /**
     * Die Methode berechnet (anhand der Zahlen der Fahrzeuge in jede Etage) die Zahlen
     * der gesamten Parkhaus.
     * @param etagenStatistiken zahlen in jede Etage
     */
    private void fuegeParkplatzStatistik(List<Statistik> etagenStatistiken) {
        int anzahlPlaetze = 0;
        int anzahlAutos = 0;
        int anzahlMotorraeder = 0;
        for (Statistik etageStatistik : etagenStatistiken) {
            anzahlPlaetze += etageStatistik.getAnzahlPlaetze();
            anzahlAutos += etageStatistik.getAnzahlFreiPlzAutos();
            anzahlMotorraeder += etageStatistik.getAnzahlFreiPlzMotorraeder();
        }
        Statistik parkplatzStatistik =
            new Statistik(0, anzahlPlaetze, anzahlAutos, anzahlMotorraeder);
        parkplatzStatistik.setAnzahlFreieplaetze();
        parkplatzStatistik.setProzentsatz();
        etagenStatistiken.add(0, parkplatzStatistik);
    }

    /**
     * Die Methode dient zum hinzufügen von neuen Parkplätze.
     * @param etage in welche Etage soll die Parkplatz sein
     * @param anzahl wie viele Parkplätze sollen eingerichtet
     * @param typ (für Autos | Motorräder)
     * @return true, wenn das Einrichten alle Parkplätze Fehlerfrei funktioniert
     */
    public boolean fuegeParkplaetze(int etage, int anzahl, String typ) {
        int anzahlUpdate = 0;
        for (int i = 0; i<anzahl;i++){
            anzahlUpdate += parkhausRepository.saveParkplatz(etage,typ);
        }
        if (anzahlUpdate == anzahl){
            return true;
        }
        logger.error("fuegeParkplaetze: anzahl der geforderte Parkplätze != die gespeichert worden");
        return false;
    }

    public void deleteAll() {
        parkhausRepository.deleteAll();
    }
}
