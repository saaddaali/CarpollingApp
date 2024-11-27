package  ma.zyn.app.ws.dto.message;

import ma.zyn.app.utils.dto.AuditBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;



import ma.zyn.app.ws.dto.driver.DriverDto;
import ma.zyn.app.ws.dto.passenger.PassengerDto;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversationDto  extends AuditBaseDto {

    private String code  ;
    private String libelle  ;
    private String description  ;

    private DriverDto driver ;
    private PassengerDto passenger ;



    public ConversationDto(){
        super();
    }




    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }


    public String getLibelle(){
        return this.libelle;
    }
    public void setLibelle(String libelle){
        this.libelle = libelle;
    }


    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }


    public DriverDto getDriver(){
        return this.driver;
    }

    public void setDriver(DriverDto driver){
        this.driver = driver;
    }
    public PassengerDto getPassenger(){
        return this.passenger;
    }

    public void setPassenger(PassengerDto passenger){
        this.passenger = passenger;
    }






}
