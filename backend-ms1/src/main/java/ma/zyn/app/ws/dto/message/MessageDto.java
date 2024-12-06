package  ma.zyn.app.ws.dto.message;

import ma.zyn.app.utils.dto.AuditBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonFormat;


import ma.zyn.app.ws.dto.driver.DriverDto;
import ma.zyn.app.ws.dto.passenger.PassengerDto;
import ma.zyn.app.ws.dto.trajet.TrajetDto;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDto  extends AuditBaseDto {

    private String contenu  ;
    private String dateEnvoi ;
    private Boolean isPassenger ;
    private TrajetDto trajet ;
    private DriverDto driver ;
    private PassengerDto passenger ;
    private ConversationDto conversation ;



    public MessageDto(){
        super();
    }




    public String getContenu(){
        return this.contenu;
    }
    public void setContenu(String contenu){
        this.contenu = contenu;
    }


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getDateEnvoi(){
        return this.dateEnvoi;
    }
    public void setDateEnvoi(String dateEnvoi){
        this.dateEnvoi = dateEnvoi;
    }


    public TrajetDto getTrajet(){
        return this.trajet;
    }

    public void setTrajet(TrajetDto trajet){
        this.trajet = trajet;
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
    public ConversationDto getConversation(){
        return this.conversation;
    }

    public void setConversation(ConversationDto conversation){
        this.conversation = conversation;
    }

    public Boolean getIsPassenger() {

        return isPassenger;
    }

    public void setIsPassenger(Boolean passenger) {
        isPassenger = passenger;
    }





}
