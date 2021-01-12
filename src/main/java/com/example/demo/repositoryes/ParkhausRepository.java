package com.example.demo.repositoryes;

import com.example.demo.DTOs.Statistik;
import com.example.demo.dataobjects.Fahrzeug;
import com.example.demo.dataobjects.Parkplatz;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ParkhausRepository {

    private JdbcTemplate db;

    ParkhausRepository(JdbcTemplate db){
        this.db = db;
    }

    /**
     * @param typ: Fahrzeug Typ
     * @return ein im freien Parkplatz, ansonsten null.
     */
    public Parkplatz findeFreiPlatz(String typ) {
        try {
            return db.queryForObject(
                "SELECT *"
                    + " FROM PARKPLATZ "
                    + "WHERE Typ = ? AND ParkplatzNummer NOT IN ("
                    + " SELECT ParkplatzNummer FROM FAHRZEUG)"
                    + " order by EtageNummer LIMIT 1",
                this::rowZuParkplatzMapper
                ,
                typ
            );
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    /**
     * @param nummer Parkplatznummer
     * @param kennenzeichnen Fahrzeugkennenzeichen
     * @throws DataAccessException BD Zugriff Fehler
     */
    public void saveFahrzeug(int nummer, String kennenzeichnen) throws DataAccessException  {
            db.update(
                "INSERT INTO FAHRZEUG VALUE (?,?)",
                kennenzeichnen,nummer
            );
    }

    public int deleteParPlzMitPlzNum(int platznummer) throws DataAccessException{
        return  db.update(
            "DELETE FROM FAHRZEUG WHERE ParkplatzNummer = ?",
            platznummer
        );
    }

    public int deleteParPlzMitKenZeichnen(String kennenzeichen) throws DataAccessException {
        return db.update(
            "DELETE FROM FAHRZEUG WHERE KennenZeichen = ?",
            kennenzeichen
        );
    }

    public Parkplatz findeParkplatzDesFahrzeuges(String kennenzeichen) {
        return db.queryForObject(
            "SELECT FAHRZEUG.ParkplatzNummer,EtageNummer,Typ FROM PARKPLATZ JOIN FAHRZEUG "
                + "on PARKPLATZ.ParkplatzNummer = FAHRZEUG.ParkplatzNummer "
                + "WHERE KennenZeichen = ?",
            this::rowZuParkplatzMapper
            ,
            kennenzeichen);
    }

    /**
     * @param kennenzeichen Fahrzeugkennenzeichen
     * @return liste der Fahrzeuge mit ähnlichr Kennenzeichen
     * @throws DataAccessException DB zugriff Fehler.
     */
    public List<Fahrzeug> findeFahrzeug(String kennenzeichen) throws DataAccessException{
        return db.query(
            "SELECT * FROM FAHRZEUG WHERE KennenZeichen LIKE ?",
            this::rowZuFahrzeugMapper
            ,
            "%" + kennenzeichen + "%");
    }

    public List<Statistik> zaehleEtagen() {
        return db.query(
            "SELECT EtageNummer,COUNT(ParkplatzNummer) AS anzahl FROM PARKPLATZ GROUP BY EtageNummer "
            ,this::rowZuEtage
        );
    }

    private Statistik rowZuEtage(ResultSet resultSet, int i) throws SQLException {
        Statistik temp = new Statistik();
        temp.setNummer(resultSet.getInt("EtageNummer"));
        temp.setAnzahlPlaetze(resultSet.getInt("anzahl"));
        return temp;
    }

    private Fahrzeug rowZuFahrzeugMapper(ResultSet resultSet, int i) throws SQLException {
        return new Fahrzeug(
            resultSet.getString("KennenZeichen"),
            resultSet.getInt("ParkplatzNummer")
        );
    }

    private Parkplatz rowZuParkplatzMapper(ResultSet resultSet, int i) throws SQLException {
        return new Parkplatz(
            resultSet.getInt("ParkplatzNummer"),
            resultSet.getInt("EtageNummer"),
            resultSet.getString("Typ")
        );
    }


    public int zahleFreieAutosInEtage(int nummer) {
        return db.queryForObject(
            "SELECT COUNT(*) AS anzahl FROM  PARKPLATZ "
                + "WHERE EtageNummer = ? AND Typ = 'AUTO' "
                + "AND ParkplatzNummer NOT IN ("
                + "SELECT ParkplatzNummer FROM FAHRZEUG"
                + ")",
            (rs, n) -> {
                return rs.getInt("anzahl");
            },
            nummer);
    }


    public int zahleFreieMotorraederInEtage(int nummer) {
        return db.queryForObject(
            "SELECT COUNT(*) AS anzahl FROM  PARKPLATZ "
                + "WHERE EtageNummer = ? AND Typ = 'MOTORRAD' "
                + "AND ParkplatzNummer NOT IN ("
                + "SELECT ParkplatzNummer FROM FAHRZEUG"
                + ")",
            (rs, n) -> {
                return rs.getInt("anzahl");
            },
            nummer);
    }

    public int saveParkplatz(int etage, String typ) {
        return db.update(
            "INSERT INTO PARKPLATZ (EtageNummer,Typ) VALUE (?,?)",
            etage,typ
        );
    }

    public void deleteAll() {
         db.update("DELETE FROM FAHRZEUG");
         deleteAll_2_();
    }

    private void deleteAll_2_() {
        db.update("DELETE FROM PARKPLATZ");
    }

}
