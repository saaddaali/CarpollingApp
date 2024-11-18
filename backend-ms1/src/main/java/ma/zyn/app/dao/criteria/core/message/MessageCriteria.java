package  ma.zyn.app.dao.criteria.core.message;


import ma.zyn.app.dao.criteria.core.driver.DriverCriteria;
import ma.zyn.app.dao.criteria.core.passenger.PassengerCriteria;
import ma.zyn.app.dao.criteria.core.trajet.TrajetCriteria;

import ma.zyn.app.zynerator.criteria.BaseCriteria;

import java.util.List;
import java.time.LocalDateTime;
import java.time.LocalDate;

public class MessageCriteria extends  BaseCriteria  {

    private String contenu;
    private String contenuLike;
    private LocalDateTime dateEnvoi;
    private LocalDateTime dateEnvoiFrom;
    private LocalDateTime dateEnvoiTo;

    private TrajetCriteria trajet ;
    private List<TrajetCriteria> trajets ;
    private DriverCriteria driver ;
    private List<DriverCriteria> drivers ;
    private PassengerCriteria passenger ;
    private List<PassengerCriteria> passengers ;
    private ConversationCriteria conversation ;
    private List<ConversationCriteria> conversations ;


    public String getContenu(){
        return this.contenu;
    }
    public void setContenu(String contenu){
        this.contenu = contenu;
    }
    public String getContenuLike(){
        return this.contenuLike;
    }
    public void setContenuLike(String contenuLike){
        this.contenuLike = contenuLike;
    }

    public LocalDateTime getDateEnvoi(){
        return this.dateEnvoi;
    }
    public void setDateEnvoi(LocalDateTime dateEnvoi){
        this.dateEnvoi = dateEnvoi;
    }
    public LocalDateTime getDateEnvoiFrom(){
        return this.dateEnvoiFrom;
    }
    public void setDateEnvoiFrom(LocalDateTime dateEnvoiFrom){
        this.dateEnvoiFrom = dateEnvoiFrom;
    }
    public LocalDateTime getDateEnvoiTo(){
        return this.dateEnvoiTo;
    }
    public void setDateEnvoiTo(LocalDateTime dateEnvoiTo){
        this.dateEnvoiTo = dateEnvoiTo;
    }

    public TrajetCriteria getTrajet(){
        return this.trajet;
    }

    public void setTrajet(TrajetCriteria trajet){
        this.trajet = trajet;
    }
    public List<TrajetCriteria> getTrajets(){
        return this.trajets;
    }

    public void setTrajets(List<TrajetCriteria> trajets){
        this.trajets = trajets;
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
    public ConversationCriteria getConversation(){
        return this.conversation;
    }

    public void setConversation(ConversationCriteria conversation){
        this.conversation = conversation;
    }
    public List<ConversationCriteria> getConversations(){
        return this.conversations;
    }

    public void setConversations(List<ConversationCriteria> conversations){
        this.conversations = conversations;
    }
}
