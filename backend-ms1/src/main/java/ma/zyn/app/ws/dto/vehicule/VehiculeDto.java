package  ma.zyn.app.ws.dto.vehicule;

import ma.zyn.app.utils.dto.AuditBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;



import ma.zyn.app.ws.dto.driver.DriverDto;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehiculeDto  extends AuditBaseDto {

    private String marque  ;
    private String modele  ;
    private Integer annee  = 0 ;
    private String couleur  ;
    private String plaqueImmatriculation  ;
    private Integer capacite  = 0 ;
    private String image  ;

    private DriverDto driver ;



    public VehiculeDto(){
        super();
    }




    public String getMarque(){
        return this.marque;
    }
    public void setMarque(String marque){
        this.marque = marque;
    }


    public String getModele(){
        return this.modele;
    }
    public void setModele(String modele){
        this.modele = modele;
    }


    public Integer getAnnee(){
        return this.annee;
    }
    public void setAnnee(Integer annee){
        this.annee = annee;
    }


    public String getCouleur(){
        return this.couleur;
    }
    public void setCouleur(String couleur){
        this.couleur = couleur;
    }


    public String getPlaqueImmatriculation(){
        return this.plaqueImmatriculation;
    }
    public void setPlaqueImmatriculation(String plaqueImmatriculation){
        this.plaqueImmatriculation = plaqueImmatriculation;
    }


    public Integer getCapacite(){
        return this.capacite;
    }
    public void setCapacite(Integer capacite){
        this.capacite = capacite;
    }


    public String getImage(){
        return this.image;
    }
    public void setImage(String image){
        this.image = image;
    }


    public DriverDto getDriver(){
        return this.driver;
    }

    public void setDriver(DriverDto driver){
        this.driver = driver;
    }






}
