package ma.zyn.app.bean.core.paiement;


import java.time.LocalDateTime;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;




import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.zynerator.bean.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "carte_bancaire")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="carte_bancaire_seq",sequenceName="carte_bancaire_seq",allocationSize=1, initialValue = 1)
public class CarteBancaire  extends BaseEntity     {




    @Column(length = 500)
    private String titulaire;

    @Column(length = 500)
    private String numero;

    private LocalDateTime dateExpiration ;

    @Column(length = 500)
    private String codeSecret;



    public CarteBancaire(){
        super();
    }

    public CarteBancaire(Long id){
        this.id = id;
    }





    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="carte_bancaire_seq")
      @Override
    public Long getId(){
        return this.id;
    }
        @Override
    public void setId(Long id){
        this.id = id;
    }
    public String getTitulaire(){
        return this.titulaire;
    }
    public void setTitulaire(String titulaire){
        this.titulaire = titulaire;
    }
    public String getNumero(){
        return this.numero;
    }
    public void setNumero(String numero){
        this.numero = numero;
    }
    public LocalDateTime getDateExpiration(){
        return this.dateExpiration;
    }
    public void setDateExpiration(LocalDateTime dateExpiration){
        this.dateExpiration = dateExpiration;
    }
    public String getCodeSecret(){
        return this.codeSecret;
    }
    public void setCodeSecret(String codeSecret){
        this.codeSecret = codeSecret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarteBancaire carteBancaire = (CarteBancaire) o;
        return id != null && id.equals(carteBancaire.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

