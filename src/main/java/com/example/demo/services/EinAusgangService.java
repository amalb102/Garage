package com.example.demo.services;

import com.example.demo.dataobjects.Parkplatz;
import com.example.demo.repositoryes.ParkhausRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class EinAusgangService {
    final private ParkhausRepository parkhausRepository;
    Logger logger = LoggerFactory.getLogger(EinAusgangService.class);


    EinAusgangService(ParkhausRepository parkhausRepository){
        this.parkhausRepository = parkhausRepository;
    }

    /**
     *Die Methode holt aus den DB ein freien Parkplatz.
     * @param typ: Typ des Fahrzeuges
     * @return aus dem DB ein freien Parkplatz
     */
    public Parkplatz findeParkPlatz(String typ) {
        return  parkhausRepository.findeFreiPlatz(typ);
    }

    /**
     *Die Funktion mreserviert ein bestimmten Parkplatz zu einem Bestimmten Fahrzeug.
     * @param parkplatz Parkplatznummer
     * @param kennenzeichnen Fahrzeugkennenzeichen
     * @return true, wenn die reservierung fehlerfrei funktioniert.
     */
    public boolean reserviereParkPlatz(Parkplatz parkplatz, String kennenzeichnen)  {
        try {
            parkhausRepository.saveFahrzeug(parkplatz.getNummer(), kennenzeichnen);
            return true;
        } catch ( DataAccessException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * Die Funktion gibt ein Parkplatz frei.
     * @param platznummer Parkplatznummer
     * @return true, wenn die Freigabe fehlerfrei funktioniert.
     */
    public boolean parkplzMitPlzNumFrigeben(int platznummer) {
        try{
            if (parkhausRepository.deleteParPlzMitPlzNum(platznummer) == 1) {
                return true;
            }
        }catch (DataAccessException e){
            logger.error(e.getMessage());
            return false;
        }
        return false;
    }

    /**
     * Die Funktion gibt ein Parkplatz frei.
     * @param kennenzeichen Fahrzeugkennenzeichen
     * @return true, wenn die Freigabe Fehlerfrei funktioniert
     */
    public boolean parkplzMitKenZeichnenFrigeben(String kennenzeichen) {
        try{
            if (parkhausRepository.deleteParPlzMitKenZeichnen(kennenzeichen) == 1) {
                return true;
            }
        }catch (DataAccessException e){
            logger.error(e.getMessage());
            return false;
        }
        return false;
    }
}
