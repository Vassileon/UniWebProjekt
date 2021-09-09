package de.hsrm.mi.web.projekt.sichtung;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import de.hsrm.mi.web.projekt.validierung.Siebzehnhaft;

public class Sichtung {

    @Size(min = 3, message = "{name.fehler}")
    @NotBlank
    @NotEmpty
    String name;

    @NotBlank
    @NotEmpty
    private String ort;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datum;

    @Siebzehnhaft(message = "{beschreibung.fehler}")
    @Size(max = 80, message = "{zulang}")
    @NotEmpty
    private String beschreibung;

    public Sichtung(){
    }

    public Sichtung(String name,String ort, LocalDate datum, String beschreibung){
        this.name = name;
        this.ort = ort;
        this.datum = datum;
        this.beschreibung = beschreibung;
    }

    public String toString(){
        return "name:"+ name+", ort:"+ort+", datum"+datum+", beschreibung:"+beschreibung;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrt() {
        return this.ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getBeschreibung() {
        return this.beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

}
