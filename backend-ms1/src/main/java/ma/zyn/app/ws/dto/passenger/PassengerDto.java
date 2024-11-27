package  ma.zyn.app.ws.dto.passenger;

import com.fasterxml.jackson.annotation.JsonInclude;

import ma.zyn.app.utils.security.bean.Role;
import java.util.Collection;
import ma.zyn.app.utils.security.ws.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;


import ma.zyn.app.ws.dto.paiement.CarteBancaireDto;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class PassengerDto  extends UserDto {

    private String photo  ;
    private String adresse  ;
    private String dateInscription ;
    private BigDecimal evaluation  ;

    private CarteBancaireDto carteBancaire ;



    private Collection<Role> roles;
    public PassengerDto(){
        super();
    }




    public String getPhoto(){
        return this.photo;
    }
    public void setPhoto(String photo){
        this.photo = photo;
    }


    public String getAdresse(){
        return this.adresse;
    }
    public void setAdresse(String adresse){
        this.adresse = adresse;
    }


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getDateInscription(){
        return this.dateInscription;
    }
    public void setDateInscription(String dateInscription){
        this.dateInscription = dateInscription;
    }


    public BigDecimal getEvaluation(){
        return this.evaluation;
    }
    public void setEvaluation(BigDecimal evaluation){
        this.evaluation = evaluation;
    }










    public CarteBancaireDto getCarteBancaire(){
        return this.carteBancaire;
    }

    public void setCarteBancaire(CarteBancaireDto carteBancaire){
        this.carteBancaire = carteBancaire;
    }







    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
