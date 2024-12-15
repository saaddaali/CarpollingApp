package ma.zyn.app.bean.core.passenger;


import java.time.LocalDateTime;


import ma.zyn.app.bean.core.paiement.CarteBancaire;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import java.util.Objects;
import java.math.BigDecimal;
import ma.zyn.app.utils.security.bean.User;

@Entity
@Table(name = "passenger")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="passenger_seq",sequenceName="passenger_seq",allocationSize=1, initialValue = 1)
public class Passenger  extends User    {


    public Passenger(String username) {
        super(username);
    }


    @Column(length = 500)
    private String photo;

    private String adresse;

    private LocalDateTime dateInscription ;

    private BigDecimal evaluation = BigDecimal.ZERO;



    private CarteBancaire carteBancaire ;


    public Passenger(){
        super();
    }

    public Passenger(Long id){
        this.id = id;
    }

    public Passenger(Long id,String email){
        this.id = id;
        this.email = email ;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="passenger_seq")
      @Override
    public Long getId(){
        return this.id;
    }
        @Override
    public void setId(Long id){
        this.id = id;
    }
    public String getPhoto(){
        return this.photo;
    }
    public void setPhoto(String photo){
        this.photo = photo;
    }
      @Column(columnDefinition="TEXT")
    public String getAdresse(){
        return this.adresse;
    }
    public void setAdresse(String adresse){
        this.adresse = adresse;
    }
    public LocalDateTime getDateInscription(){
        return this.dateInscription;
    }
    public void setDateInscription(LocalDateTime dateInscription){
        this.dateInscription = dateInscription;
    }
    public BigDecimal getEvaluation(){
        return this.evaluation;
    }
    public void setEvaluation(BigDecimal evaluation){
        this.evaluation = evaluation;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carte_bancaire")
    public CarteBancaire getCarteBancaire(){
        return this.carteBancaire;
    }
    public void setCarteBancaire(CarteBancaire carteBancaire){
        this.carteBancaire = carteBancaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return id != null && id.equals(passenger.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

