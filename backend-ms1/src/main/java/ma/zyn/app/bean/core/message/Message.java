package ma.zyn.app.bean.core.message;


import java.time.LocalDateTime;


import ma.zyn.app.bean.core.driver.Driver;
import ma.zyn.app.bean.core.passenger.Passenger;
import ma.zyn.app.bean.core.trajet.Trajet;


import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.utils.bean.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "message")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="message_seq",sequenceName="message_seq",allocationSize=1, initialValue = 1)
public class Message  extends BaseEntity     {




    private String contenu;

    private LocalDateTime dateEnvoi ;

    private Trajet trajet ;
    private Driver driver ;
    private Passenger passenger ;
    private Conversation conversation ;

    @Column(columnDefinition = "boolean default false")
    private Boolean isPassenger= false;


    public Message(){
        super();
    }

    public Message(Long id){
        this.id = id;
    }





    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="message_seq")
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
    @JoinColumn(name = "driver")
    public Driver getDriver(){
        return this.driver;
    }
    public void setDriver(Driver driver){
        this.driver = driver;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger")
    public Passenger getPassenger(){
        return this.passenger;
    }
    public void setPassenger(Passenger passenger){
        this.passenger = passenger;
    }
      @Column(columnDefinition="TEXT")
    public String getContenu(){
        return this.contenu;
    }
    public void setContenu(String contenu){
        this.contenu = contenu;
    }
    public LocalDateTime getDateEnvoi(){
        return this.dateEnvoi;
    }
    public void setDateEnvoi(LocalDateTime dateEnvoi){
        this.dateEnvoi = dateEnvoi;
    }

    public Boolean getIsPassenger(){
        return this.isPassenger;
    }
    public void setIsPassenger(Boolean isPassenger){
        this.isPassenger = isPassenger;
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
        Message message = (Message) o;
        return id != null && id.equals(message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

