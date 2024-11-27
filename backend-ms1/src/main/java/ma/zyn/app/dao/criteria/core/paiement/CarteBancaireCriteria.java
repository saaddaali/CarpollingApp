package  ma.zyn.app.dao.criteria.core.paiement;



import ma.zyn.app.utils.criteria.BaseCriteria;

import java.time.LocalDateTime;

public class CarteBancaireCriteria extends  BaseCriteria  {

    private String titulaire;
    private String titulaireLike;
    private String numero;
    private String numeroLike;
    private LocalDateTime dateExpiration;
    private LocalDateTime dateExpirationFrom;
    private LocalDateTime dateExpirationTo;
    private String codeSecret;
    private String codeSecretLike;



    public String getTitulaire(){
        return this.titulaire;
    }
    public void setTitulaire(String titulaire){
        this.titulaire = titulaire;
    }
    public String getTitulaireLike(){
        return this.titulaireLike;
    }
    public void setTitulaireLike(String titulaireLike){
        this.titulaireLike = titulaireLike;
    }

    public String getNumero(){
        return this.numero;
    }
    public void setNumero(String numero){
        this.numero = numero;
    }
    public String getNumeroLike(){
        return this.numeroLike;
    }
    public void setNumeroLike(String numeroLike){
        this.numeroLike = numeroLike;
    }

    public LocalDateTime getDateExpiration(){
        return this.dateExpiration;
    }
    public void setDateExpiration(LocalDateTime dateExpiration){
        this.dateExpiration = dateExpiration;
    }
    public LocalDateTime getDateExpirationFrom(){
        return this.dateExpirationFrom;
    }
    public void setDateExpirationFrom(LocalDateTime dateExpirationFrom){
        this.dateExpirationFrom = dateExpirationFrom;
    }
    public LocalDateTime getDateExpirationTo(){
        return this.dateExpirationTo;
    }
    public void setDateExpirationTo(LocalDateTime dateExpirationTo){
        this.dateExpirationTo = dateExpirationTo;
    }
    public String getCodeSecret(){
        return this.codeSecret;
    }
    public void setCodeSecret(String codeSecret){
        this.codeSecret = codeSecret;
    }
    public String getCodeSecretLike(){
        return this.codeSecretLike;
    }
    public void setCodeSecretLike(String codeSecretLike){
        this.codeSecretLike = codeSecretLike;
    }


}
