package  ma.zyn.app.ws.dto.paiement;

import ma.zyn.app.utils.dto.AuditBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonFormat;




@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarteBancaireDto  extends AuditBaseDto {

    private String titulaire  ;
    private String numero  ;
    private String dateExpiration ;
    private String codeSecret  ;




    public CarteBancaireDto(){
        super();
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


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getDateExpiration(){
        return this.dateExpiration;
    }
    public void setDateExpiration(String dateExpiration){
        this.dateExpiration = dateExpiration;
    }


    public String getCodeSecret(){
        return this.codeSecret;
    }
    public void setCodeSecret(String codeSecret){
        this.codeSecret = codeSecret;
    }








}
