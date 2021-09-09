package de.hsrm.mi.web.projekt.foto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Foto{
    
    @OneToMany(mappedBy="foto", orphanRemoval = true, cascade = CascadeType.ALL )
    private List<Kommentar> kommentare = new ArrayList<Kommentar>();

    @NotEmpty
    @Size(min = 3)
    private String mimetype = "";

    private String dateiname = "";

    private String ort = "";

    @PastOrPresent
    private LocalDateTime zeitstempel = LocalDateTime.MIN;

    private double geolaenge = 0;
    private double geobreite = 0;

    @JsonIgnore
    @Lob
    private byte[] fotodaten;

    @Id
    @GeneratedValue
    private long id;

    @Version
    private long version;
    
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Kommentar> getKommentare() {
        return kommentare;
    }

    public void addKommentare(Kommentar kommentar) {
        this.kommentare.add(kommentar);
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getVersion() {
        return version;
    }
    public void setVersion(long version) {
        this.version = version;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dateiname == null) ? 0 : dateiname.hashCode());
        result = prime * result + Arrays.hashCode(fotodaten);
        long temp;
        temp = Double.doubleToLongBits(geobreite);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(geolaenge);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((mimetype == null) ? 0 : mimetype.hashCode());
        result = prime * result + ((ort == null) ? 0 : ort.hashCode());
        result = prime * result + ((zeitstempel == null) ? 0 : zeitstempel.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Foto other = (Foto) obj;
        if (dateiname == null) {
            if (other.dateiname != null)
                return false;
        } else if (!dateiname.equals(other.dateiname))
            return false;
        if (!Arrays.equals(fotodaten, other.fotodaten))
            return false;
        if (Double.doubleToLongBits(geobreite) != Double.doubleToLongBits(other.geobreite))
            return false;
        if (Double.doubleToLongBits(geolaenge) != Double.doubleToLongBits(other.geolaenge))
            return false;
        if (mimetype == null) {
            if (other.mimetype != null)
                return false;
        } else if (!mimetype.equals(other.mimetype))
            return false;
        if (ort == null) {
            if (other.ort != null)
                return false;
        } else if (!ort.equals(other.ort))
            return false;
        if (zeitstempel == null) {
            if (other.zeitstempel != null)
                return false;
        } else if (!zeitstempel.equals(other.zeitstempel))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Foto [dateiname=" + dateiname + ", fotodaten=" + Arrays.toString(fotodaten) + ", geobreite=" + geobreite
                + ", geolaenge=" + geolaenge + ", mimetype=" + mimetype + ", ort=" + ort + ", zeitstempel="
                + zeitstempel + "]";
    }
    public String getMimetype() {
        return mimetype;
    }
    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }
    public String getDateiname() {
        return dateiname;
    }
    public void setDateiname(String dateiname) {
        this.dateiname = dateiname;
    }
    public String getOrt() {
        return ort;
    }
    public void setOrt(Optional<String> ort) {
        if(ort == null){
            this.ort = "";
        }
        this.ort = ort.toString();
    }
    public void setOrt(String ort) {
        this.ort = ort;
    }

    public LocalDateTime getZeitstempel() {
        return zeitstempel;
    }
    public void setZeitstempel(LocalDateTime zeitstempel) {
        this.zeitstempel = zeitstempel;
    }
    public double getGeolaenge() {
        return geolaenge;
    }
    public void setGeolaenge(double geolaenge) {
        this.geolaenge = geolaenge;
    }
    public double getGeobreite() {
        return geobreite;
    }
    public void setGeobreite(double geobreite) {
        this.geobreite = geobreite;
    }
    public byte[] getFotodaten() {
        return fotodaten;
    }
    public void setFotodaten(byte[] fotodaten) {
        this.fotodaten = fotodaten;
    }
    
}
