package  ma.zyn.app.dao.criteria.core.vehicule;


import ma.zyn.app.dao.criteria.core.driver.DriverCriteria;

import ma.zyn.app.zynerator.criteria.BaseCriteria;

import java.util.List;

public class VehiculeCriteria extends  BaseCriteria  {

    private String marque;
    private String marqueLike;
    private String modele;
    private String modeleLike;
    private String annee;
    private String anneeMin;
    private String anneeMax;
    private String couleur;
    private String couleurLike;
    private String plaqueImmatriculation;
    private String plaqueImmatriculationLike;
    private String capacite;
    private String capaciteMin;
    private String capaciteMax;
    private String image;
    private String imageLike;

    private DriverCriteria driver ;
    private List<DriverCriteria> drivers ;


    public String getMarque(){
        return this.marque;
    }
    public void setMarque(String marque){
        this.marque = marque;
    }
    public String getMarqueLike(){
        return this.marqueLike;
    }
    public void setMarqueLike(String marqueLike){
        this.marqueLike = marqueLike;
    }

    public String getModele(){
        return this.modele;
    }
    public void setModele(String modele){
        this.modele = modele;
    }
    public String getModeleLike(){
        return this.modeleLike;
    }
    public void setModeleLike(String modeleLike){
        this.modeleLike = modeleLike;
    }

    public String getAnnee(){
        return this.annee;
    }
    public void setAnnee(String annee){
        this.annee = annee;
    }   
    public String getAnneeMin(){
        return this.anneeMin;
    }
    public void setAnneeMin(String anneeMin){
        this.anneeMin = anneeMin;
    }
    public String getAnneeMax(){
        return this.anneeMax;
    }
    public void setAnneeMax(String anneeMax){
        this.anneeMax = anneeMax;
    }
      
    public String getCouleur(){
        return this.couleur;
    }
    public void setCouleur(String couleur){
        this.couleur = couleur;
    }
    public String getCouleurLike(){
        return this.couleurLike;
    }
    public void setCouleurLike(String couleurLike){
        this.couleurLike = couleurLike;
    }

    public String getPlaqueImmatriculation(){
        return this.plaqueImmatriculation;
    }
    public void setPlaqueImmatriculation(String plaqueImmatriculation){
        this.plaqueImmatriculation = plaqueImmatriculation;
    }
    public String getPlaqueImmatriculationLike(){
        return this.plaqueImmatriculationLike;
    }
    public void setPlaqueImmatriculationLike(String plaqueImmatriculationLike){
        this.plaqueImmatriculationLike = plaqueImmatriculationLike;
    }

    public String getCapacite(){
        return this.capacite;
    }
    public void setCapacite(String capacite){
        this.capacite = capacite;
    }   
    public String getCapaciteMin(){
        return this.capaciteMin;
    }
    public void setCapaciteMin(String capaciteMin){
        this.capaciteMin = capaciteMin;
    }
    public String getCapaciteMax(){
        return this.capaciteMax;
    }
    public void setCapaciteMax(String capaciteMax){
        this.capaciteMax = capaciteMax;
    }
      
    public String getImage(){
        return this.image;
    }
    public void setImage(String image){
        this.image = image;
    }
    public String getImageLike(){
        return this.imageLike;
    }
    public void setImageLike(String imageLike){
        this.imageLike = imageLike;
    }


    public DriverCriteria getDriver(){
        return this.driver;
    }

    public void setDriver(DriverCriteria driver){
        this.driver = driver;
    }
    public List<DriverCriteria> getDrivers(){
        return this.drivers;
    }

    public void setDrivers(List<DriverCriteria> drivers){
        this.drivers = drivers;
    }
}
