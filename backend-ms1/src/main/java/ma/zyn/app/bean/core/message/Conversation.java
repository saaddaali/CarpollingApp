package ma.zyn.app.bean.core.message;






import ma.zyn.app.bean.core.driver.Driver;
import ma.zyn.app.bean.core.passenger.Passenger;


import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.zynerator.bean.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "conversation")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="conversation_seq",sequenceName="conversation_seq",allocationSize=1, initialValue = 1)
public class Conversation  extends BaseEntity     {




    @Column(length = 500)
    private String code;

    @Column(length = 500)
    private String libelle;

    private String description;

    private Driver driver ;
    private Passenger passenger ;


    public Conversation(){
        super();
    }

    public Conversation(Long id){
        this.id = id;
    }

    public Conversation(Long id,String libelle){
        this.id = id;
        this.libelle = libelle ;
    }
    public Conversation(String libelle){
        this.libelle = libelle ;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="conversation_seq")
      @Override
    public Long getId(){
        return this.id;
    }
        @Override
    public void setId(Long id){
        this.id = id;
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
      @Column(columnDefinition="TEXT")
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversation conversation = (Conversation) o;
        return id != null && id.equals(conversation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

