package ma.zyn.app.ws.dto.driver;

import com.fasterxml.jackson.annotation.JsonInclude;

import ma.zyn.app.utils.security.bean.Role;

import java.util.Collection;

import ma.zyn.app.utils.security.ws.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverDto extends UserDto {

    private String photo;
    private String CIN;
    private Boolean verified;
    private String adresse;
    private String dateInscription;
    private BigDecimal evaluation;


    private Collection<Role> roles;

    public DriverDto() {
        super();
    }


    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public String getAdresse() {
        return this.adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getDateInscription() {
        return this.dateInscription;
    }

    public void setDateInscription(String dateInscription) {
        this.dateInscription = dateInscription;
    }


    public BigDecimal getEvaluation() {
        return this.evaluation;
    }

    public void setEvaluation(BigDecimal evaluation) {
        this.evaluation = evaluation;
    }


    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
}
