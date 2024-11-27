package  ma.zyn.app.ws.dto.trajet;

import ma.zyn.app.utils.dto.AuditBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;


import ma.zyn.app.ws.dto.driver.DriverDto;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrajetDto  extends AuditBaseDto {

    private String horaireDepart ;
    private String horaireArrive ;
    private Integer placesDisponibles  = 0 ;
    private Integer placesMax = 0 ;

    private String dateCreation ;

    private VilleDto villeDepart ;
    private VilleDto villeDestination ;
    private DriverDto driver ;
    private VilleDto localisationSource ;
    private VilleDto localisationDestination ;
    private BigDecimal prix ;



    public TrajetDto(){
        super();
    }




    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getHoraireDepart(){
        return this.horaireDepart;
    }
    public void setHoraireDepart(String horaireDepart){
        this.horaireDepart = horaireDepart;
    }


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getHoraireArrive(){
        return this.horaireArrive;
    }
    public void setHoraireArrive(String horaireArrive){
        this.horaireArrive = horaireArrive;
    }


    public Integer getPlacesDisponibles(){
        return this.placesDisponibles;
    }
    public void setPlacesDisponibles(Integer placesDisponibles){
        this.placesDisponibles = placesDisponibles;
    }

    public Integer getPlacesMax() {
        return placesMax;
    }

    public void setPlacesMax(Integer placesMax) {
        this.placesMax = placesMax;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getDateCreation(){
        return this.dateCreation;
    }
    public void setDateCreation(String dateCreation){
        this.dateCreation = dateCreation;
    }


    public VilleDto getVilleDepart(){
        return this.villeDepart;
    }

    public void setVilleDepart(VilleDto villeDepart){
        this.villeDepart = villeDepart;
    }
    public VilleDto getVilleDestination(){
        return this.villeDestination;
    }

    public void setVilleDestination(VilleDto villeDestination){
        this.villeDestination = villeDestination;
    }
    public DriverDto getDriver(){
        return this.driver;
    }

    public void setDriver(DriverDto driver){
        this.driver = driver;
    }
    public VilleDto getLocalisationSource(){
        return this.localisationSource;
    }

    public void setLocalisationSource(VilleDto localisationSource){
        this.localisationSource = localisationSource;
    }
    public VilleDto getLocalisationDestination(){
        return this.localisationDestination;
    }

    public void setLocalisationDestination(VilleDto localisationDestination){
        this.localisationDestination = localisationDestination;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }
}
