package ma.zyn.app.bean.core.reservation;


import java.time.LocalDateTime;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;


import ma.zyn.app.bean.core.driver.Driver;
import ma.zyn.app.bean.core.passenger.Passenger;
import ma.zyn.app.bean.core.paiement.CarteBancaire;
import ma.zyn.app.bean.core.message.Conversation;
import ma.zyn.app.bean.core.trajet.Trajet;


import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.zynerator.bean.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;
import java.math.BigDecimal;

@Entity
@Table(name = "reservation")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="reservation_seq",sequenceName="reservation_seq",allocationSize=1, initialValue = 1)
public class Reservation  extends BaseEntity     {




    private LocalDateTime dateReservation ;

    private BigDecimal montant = BigDecimal.ZERO;

    private LocalDateTime datePaiement ;

    private BigDecimal evaluation = BigDecimal.ZERO;

    private Trajet trajet ;
    private Passenger passenger ;
    private Driver driver ;
    private CarteBancaire carteBancaire ;
    private Conversation conversation ;


    public Reservation(){
        super();
    }

    public Reservation(Long id){
        this.id = id;
    }





    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="reservation_seq")
      @Override
    public Long getId(){
        return this.id;
    }
        @Override
    public void setId(Long id){
        this.id = id;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trajet")
    public Trajet getTrajet(){
        return this.trajet;
    }
    public void setTrajet(Trajet trajet){
        this.trajet = trajet;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger")
    public Passenger getPassenger(){
        return this.passenger;
    }
    public void setPassenger(Passenger passenger){
        this.passenger = passenger;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver")
    public Driver getDriver(){
        return this.driver;
    }
    public void setDriver(Driver driver){
        this.driver = driver;
    }
    public LocalDateTime getDateReservation(){
        return this.dateReservation;
    }
    public void setDateReservation(LocalDateTime dateReservation){
        this.dateReservation = dateReservation;
    }
    public BigDecimal getMontant(){
        return this.montant;
    }
    public void setMontant(BigDecimal montant){
        this.montant = montant;
    }
    public LocalDateTime getDatePaiement(){
        return this.datePaiement;
    }
    public void setDatePaiement(LocalDateTime datePaiement){
        this.datePaiement = datePaiement;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carte_bancaire")
    public CarteBancaire getCarteBancaire(){
        return this.carteBancaire;
    }
    public void setCarteBancaire(CarteBancaire carteBancaire){
        this.carteBancaire = carteBancaire;
    }
    public BigDecimal getEvaluation(){
        return this.evaluation;
    }
    public void setEvaluation(BigDecimal evaluation){
        this.evaluation = evaluation;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation")
    public Conversation getConversation(){
        return this.conversation;
    }
    public void setConversation(Conversation conversation){
        this.conversation = conversation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation reservation = (Reservation) o;
        return id != null && id.equals(reservation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

