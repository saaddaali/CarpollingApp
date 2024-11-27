package  ma.zyn.app.dao.criteria.core.message;


import ma.zyn.app.dao.criteria.core.driver.DriverCriteria;
import ma.zyn.app.dao.criteria.core.passenger.PassengerCriteria;

import ma.zyn.app.utils.criteria.BaseCriteria;

import java.util.List;

public class ConversationCriteria extends  BaseCriteria  {

    private String code;
    private String codeLike;
    private String libelle;
    private String libelleLike;
    private String description;
    private String descriptionLike;

    private DriverCriteria driver ;
    private List<DriverCriteria> drivers ;
    private PassengerCriteria passenger ;
    private List<PassengerCriteria> passengers ;


    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCodeLike(){
        return this.codeLike;
    }
    public void setCodeLike(String codeLike){
        this.codeLike = codeLike;
    }

    public String getLibelle(){
        return this.libelle;
    }
    public void setLibelle(String libelle){
        this.libelle = libelle;
    }
    public String getLibelleLike(){
        return this.libelleLike;
    }
    public void setLibelleLike(String libelleLike){
        this.libelleLike = libelleLike;
    }

    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescriptionLike(){
        return this.descriptionLike;
    }
    public void setDescriptionLike(String descriptionLike){
        this.descriptionLike = descriptionLike;
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
    public PassengerCriteria getPassenger(){
        return this.passenger;
    }

    public void setPassenger(PassengerCriteria passenger){
        this.passenger = passenger;
    }
    public List<PassengerCriteria> getPassengers(){
        return this.passengers;
    }

    public void setPassengers(List<PassengerCriteria> passengers){
        this.passengers = passengers;
    }
}
