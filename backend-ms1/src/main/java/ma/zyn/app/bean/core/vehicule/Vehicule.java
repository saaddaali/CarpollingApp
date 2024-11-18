package ma.zyn.app.bean.core.vehicule;






import ma.zyn.app.bean.core.driver.Driver;


import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.zynerator.bean.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "vehicule")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="vehicule_seq",sequenceName="vehicule_seq",allocationSize=1, initialValue = 1)
public class Vehicule  extends BaseEntity     {




    @Column(length = 500)
    private String marque;

    @Column(length = 500)
    private String modele;

    private Integer annee = 0;

    @Column(length = 500)
    private String couleur;

    @Column(length = 500)
    private String plaqueImmatriculation;

    private Integer capacite = 0;

    @Column(length = 500)
    private String image;

    private Driver driver ;


    public Vehicule(){
        super();
    }

    public Vehicule(Long id){
        this.id = id;
    }

    public Vehicule(Long id,String plaqueImmatriculation){
        this.id = id;
        this.plaqueImmatriculation = plaqueImmatriculation ;
    }
    public Vehicule(String plaqueImmatriculation){
        this.plaqueImmatriculation = plaqueImmatriculation ;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="vehicule_seq")
      @Override
    public Long getId(){
        return this.id;
    }
        @Override
    public void setId(Long id){
        this.id = id;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver")
    public Driver getDriver(){
        return this.driver;
    }
    public void setDriver(Driver driver){
        this.driver = driver;
    }
    public String getMarque(){
        return this.marque;
    }
    public void setMarque(String marque){
        this.marque = marque;
    }
    public String getModele(){
        return this.modele;
    }
    public void setModele(String modele){
        this.modele = modele;
    }
    public Integer getAnnee(){
        return this.annee;
    }
    public void setAnnee(Integer annee){
        this.annee = annee;
    }
    public String getCouleur(){
        return this.couleur;
    }
    public void setCouleur(String couleur){
        this.couleur = couleur;
    }
    public String getPlaqueImmatriculation(){
        return this.plaqueImmatriculation;
    }
    public void setPlaqueImmatriculation(String plaqueImmatriculation){
        this.plaqueImmatriculation = plaqueImmatriculation;
    }
    public Integer getCapacite(){
        return this.capacite;
    }
    public void setCapacite(Integer capacite){
        this.capacite = capacite;
    }
    public String getImage(){
        return this.image;
    }
    public void setImage(String image){
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicule vehicule = (Vehicule) o;
        return id != null && id.equals(vehicule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

