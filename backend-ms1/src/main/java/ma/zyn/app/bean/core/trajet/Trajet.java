package ma.zyn.app.bean.core.trajet;


import java.math.BigDecimal;
import java.time.LocalDateTime;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


import ma.zyn.app.bean.core.driver.Driver;


import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.zynerator.bean.BaseEntity;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "trajet")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name = "trajet_seq", sequenceName = "trajet_seq", allocationSize = 1, initialValue = 1)
public class Trajet extends BaseEntity {


    private LocalDateTime horaireDepart;

    private LocalDateTime horaireArrive;

    private Integer placesDisponibles = 0;
    private Integer placesMax = 0;

    private LocalDateTime dateCreation;

    private Ville villeDepart;
    private Ville villeDestination;
    private Driver driver;
    private Ville localisationSource;
    private Ville localisationDestination;

    private BigDecimal prix;


    public Trajet() {
        super();
    }

    public Trajet(Long id) {
        this.id = id;
    }


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trajet_seq")
    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ville_depart")
    public Ville getVilleDepart() {
        return this.villeDepart;
    }

    public void setVilleDepart(Ville villeDepart) {
        this.villeDepart = villeDepart;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ville_destination")
    public Ville getVilleDestination() {
        return this.villeDestination;
    }

    public void setVilleDestination(Ville villeDestination) {
        this.villeDestination = villeDestination;
    }

    public LocalDateTime getHoraireDepart() {
        return this.horaireDepart;
    }

    public void setHoraireDepart(LocalDateTime horaireDepart) {
        this.horaireDepart = horaireDepart;
    }

    public LocalDateTime getHoraireArrive() {
        return this.horaireArrive;
    }

    public void setHoraireArrive(LocalDateTime horaireArrive) {
        this.horaireArrive = horaireArrive;
    }

    public Integer getPlacesDisponibles() {
        return this.placesDisponibles;
    }

    public void setPlacesDisponibles(Integer placesDisponibles) {
        this.placesDisponibles = placesDisponibles;
    }

    public Integer getPlacesMax() {
        return placesMax;
    }

    public void setPlacesMax(Integer placesMax) {
        this.placesMax = placesMax;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver")
    public Driver getDriver() {
        return this.driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public LocalDateTime getDateCreation() {
        return this.dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "localisation_source")
    public Ville getLocalisationSource() {
        return this.localisationSource;
    }

    public void setLocalisationSource(Ville localisationSource) {
        this.localisationSource = localisationSource;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "localisation_destination")
    public Ville getLocalisationDestination() {
        return this.localisationDestination;
    }

    public void setLocalisationDestination(Ville localisationDestination) {
        this.localisationDestination = localisationDestination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trajet trajet = (Trajet) o;
        return id != null && id.equals(trajet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

