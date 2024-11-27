package  ma.zyn.app.ws.dto.reservation;

import ma.zyn.app.utils.dto.AuditBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;


import ma.zyn.app.ws.dto.driver.DriverDto;
import ma.zyn.app.ws.dto.passenger.PassengerDto;
import ma.zyn.app.ws.dto.paiement.CarteBancaireDto;
import ma.zyn.app.ws.dto.message.ConversationDto;
import ma.zyn.app.ws.dto.trajet.TrajetDto;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationDto  extends AuditBaseDto {

    private String dateReservation ;
    private BigDecimal montant  ;
    private String datePaiement ;
    private BigDecimal evaluation  ;

    private TrajetDto trajet ;
    private PassengerDto passenger ;
    private DriverDto driver ;
    private CarteBancaireDto carteBancaire ;
    private ConversationDto conversation ;



    public ReservationDto(){
        super();
    }




    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getDateReservation(){
        return this.dateReservation;
    }
    public void setDateReservation(String dateReservation){
        this.dateReservation = dateReservation;
    }


    public BigDecimal getMontant(){
        return this.montant;
    }
    public void setMontant(BigDecimal montant){
        this.montant = montant;
    }


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getDatePaiement(){
        return this.datePaiement;
    }
    public void setDatePaiement(String datePaiement){
        this.datePaiement = datePaiement;
    }


    public BigDecimal getEvaluation(){
        return this.evaluation;
    }
    public void setEvaluation(BigDecimal evaluation){
        this.evaluation = evaluation;
    }


    public TrajetDto getTrajet(){
        return this.trajet;
    }

    public void setTrajet(TrajetDto trajet){
        this.trajet = trajet;
    }
    public PassengerDto getPassenger(){
        return this.passenger;
    }

    public void setPassenger(PassengerDto passenger){
        this.passenger = passenger;
    }
    public DriverDto getDriver(){
        return this.driver;
    }

    public void setDriver(DriverDto driver){
        this.driver = driver;
    }
    public CarteBancaireDto getCarteBancaire(){
        return this.carteBancaire;
    }

    public void setCarteBancaire(CarteBancaireDto carteBancaire){
        this.carteBancaire = carteBancaire;
    }
    public ConversationDto getConversation(){
        return this.conversation;
    }

    public void setConversation(ConversationDto conversation){
        this.conversation = conversation;
    }






}
