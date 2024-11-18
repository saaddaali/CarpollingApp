package  ma.zyn.app.dao.criteria.core.reservation;


import ma.zyn.app.dao.criteria.core.driver.DriverCriteria;
import ma.zyn.app.dao.criteria.core.passenger.PassengerCriteria;
import ma.zyn.app.dao.criteria.core.paiement.CarteBancaireCriteria;
import ma.zyn.app.dao.criteria.core.message.ConversationCriteria;
import ma.zyn.app.dao.criteria.core.trajet.TrajetCriteria;

import ma.zyn.app.zynerator.criteria.BaseCriteria;

import java.util.List;
import java.time.LocalDateTime;
import java.time.LocalDate;

public class ReservationCriteria extends  BaseCriteria  {

    private LocalDateTime dateReservation;
    private LocalDateTime dateReservationFrom;
    private LocalDateTime dateReservationTo;
    private String montant;
    private String montantMin;
    private String montantMax;
    private LocalDateTime datePaiement;
    private LocalDateTime datePaiementFrom;
    private LocalDateTime datePaiementTo;
    private String evaluation;
    private String evaluationMin;
    private String evaluationMax;

    private TrajetCriteria trajet ;
    private List<TrajetCriteria> trajets ;
    private PassengerCriteria passenger ;
    private List<PassengerCriteria> passengers ;
    private DriverCriteria driver ;
    private List<DriverCriteria> drivers ;
    private CarteBancaireCriteria carteBancaire ;
    private List<CarteBancaireCriteria> carteBancaires ;
    private ConversationCriteria conversation ;
    private List<ConversationCriteria> conversations ;


    public LocalDateTime getDateReservation(){
        return this.dateReservation;
    }
    public void setDateReservation(LocalDateTime dateReservation){
        this.dateReservation = dateReservation;
    }
    public LocalDateTime getDateReservationFrom(){
        return this.dateReservationFrom;
    }
    public void setDateReservationFrom(LocalDateTime dateReservationFrom){
        this.dateReservationFrom = dateReservationFrom;
    }
    public LocalDateTime getDateReservationTo(){
        return this.dateReservationTo;
    }
    public void setDateReservationTo(LocalDateTime dateReservationTo){
        this.dateReservationTo = dateReservationTo;
    }
    public String getMontant(){
        return this.montant;
    }
    public void setMontant(String montant){
        this.montant = montant;
    }   
    public String getMontantMin(){
        return this.montantMin;
    }
    public void setMontantMin(String montantMin){
        this.montantMin = montantMin;
    }
    public String getMontantMax(){
        return this.montantMax;
    }
    public void setMontantMax(String montantMax){
        this.montantMax = montantMax;
    }
      
    public LocalDateTime getDatePaiement(){
        return this.datePaiement;
    }
    public void setDatePaiement(LocalDateTime datePaiement){
        this.datePaiement = datePaiement;
    }
    public LocalDateTime getDatePaiementFrom(){
        return this.datePaiementFrom;
    }
    public void setDatePaiementFrom(LocalDateTime datePaiementFrom){
        this.datePaiementFrom = datePaiementFrom;
    }
    public LocalDateTime getDatePaiementTo(){
        return this.datePaiementTo;
    }
    public void setDatePaiementTo(LocalDateTime datePaiementTo){
        this.datePaiementTo = datePaiementTo;
    }
    public String getEvaluation(){
        return this.evaluation;
    }
    public void setEvaluation(String evaluation){
        this.evaluation = evaluation;
    }   
    public String getEvaluationMin(){
        return this.evaluationMin;
    }
    public void setEvaluationMin(String evaluationMin){
        this.evaluationMin = evaluationMin;
    }
    public String getEvaluationMax(){
        return this.evaluationMax;
    }
    public void setEvaluationMax(String evaluationMax){
        this.evaluationMax = evaluationMax;
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
    public CarteBancaireCriteria getCarteBancaire(){
        return this.carteBancaire;
    }

    public void setCarteBancaire(CarteBancaireCriteria carteBancaire){
        this.carteBancaire = carteBancaire;
    }
    public List<CarteBancaireCriteria> getCarteBancaires(){
        return this.carteBancaires;
    }

    public void setCarteBancaires(List<CarteBancaireCriteria> carteBancaires){
        this.carteBancaires = carteBancaires;
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
