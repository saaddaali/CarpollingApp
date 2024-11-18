package ma.zyn.app.bean.core.trajet;








import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.zynerator.bean.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ville")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="ville_seq",sequenceName="ville_seq",allocationSize=1, initialValue = 1)
public class Ville  extends BaseEntity     {




    @Column(length = 500)
    private String code;

    @Column(length = 500)
    private String libelle;

    private String description;



    public Ville(){
        super();
    }

    public Ville(Long id){
        this.id = id;
    }

    public Ville(Long id,String libelle){
        this.id = id;
        this.libelle = libelle ;
    }
    public Ville(String libelle){
        this.libelle = libelle ;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="ville_seq")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ville ville = (Ville) o;
        return id != null && id.equals(ville.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

