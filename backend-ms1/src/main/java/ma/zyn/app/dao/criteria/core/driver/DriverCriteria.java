package  ma.zyn.app.dao.criteria.core.driver;



import ma.zyn.app.zynerator.security.dao.criteria.core.UserCriteria;

import java.util.List;
import java.time.LocalDateTime;
import java.time.LocalDate;

public class DriverCriteria extends UserCriteria  {

    private String photo;
    private String photoLike;
    private String adresse;
    private String adresseLike;
    private LocalDateTime dateInscription;
    private LocalDateTime dateInscriptionFrom;
    private LocalDateTime dateInscriptionTo;
    private String evaluation;
    private String evaluationMin;
    private String evaluationMax;
    private String password;
    private String passwordLike;
    private Boolean accountNonLocked;
    private Boolean passwordChanged;
    private String username;
    private String usernameLike;
    private Boolean accountNonExpired;
    private Boolean credentialsNonExpired;
    private Boolean enabled;
    private String email;
    private String emailLike;



    public String getPhoto(){
        return this.photo;
    }
    public void setPhoto(String photo){
        this.photo = photo;
    }
    public String getPhotoLike(){
        return this.photoLike;
    }
    public void setPhotoLike(String photoLike){
        this.photoLike = photoLike;
    }

    public String getAdresse(){
        return this.adresse;
    }
    public void setAdresse(String adresse){
        this.adresse = adresse;
    }
    public String getAdresseLike(){
        return this.adresseLike;
    }
    public void setAdresseLike(String adresseLike){
        this.adresseLike = adresseLike;
    }

    public LocalDateTime getDateInscription(){
        return this.dateInscription;
    }
    public void setDateInscription(LocalDateTime dateInscription){
        this.dateInscription = dateInscription;
    }
    public LocalDateTime getDateInscriptionFrom(){
        return this.dateInscriptionFrom;
    }
    public void setDateInscriptionFrom(LocalDateTime dateInscriptionFrom){
        this.dateInscriptionFrom = dateInscriptionFrom;
    }
    public LocalDateTime getDateInscriptionTo(){
        return this.dateInscriptionTo;
    }
    public void setDateInscriptionTo(LocalDateTime dateInscriptionTo){
        this.dateInscriptionTo = dateInscriptionTo;
    }
    public String getEvaluation(){
        return this.evaluation;
    }
    public void setEvaluation(String evaluation){
        this.evaluation = evaluation;
    }   
    public String getEvaluationMin(){
        return this.evaluationMin;
    }
    public void setEvaluationMin(String evaluationMin){
        this.evaluationMin = evaluationMin;
    }
    public String getEvaluationMax(){
        return this.evaluationMax;
    }
    public void setEvaluationMax(String evaluationMax){
        this.evaluationMax = evaluationMax;
    }
      
    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPasswordLike(){
        return this.passwordLike;
    }
    public void setPasswordLike(String passwordLike){
        this.passwordLike = passwordLike;
    }

    public Boolean getAccountNonLocked(){
        return this.accountNonLocked;
    }
    public void setAccountNonLocked(Boolean accountNonLocked){
        this.accountNonLocked = accountNonLocked;
    }
    public Boolean getPasswordChanged(){
        return this.passwordChanged;
    }
    public void setPasswordChanged(Boolean passwordChanged){
        this.passwordChanged = passwordChanged;
    }
    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsernameLike(){
        return this.usernameLike;
    }
    public void setUsernameLike(String usernameLike){
        this.usernameLike = usernameLike;
    }

    public Boolean getAccountNonExpired(){
        return this.accountNonExpired;
    }
    public void setAccountNonExpired(Boolean accountNonExpired){
        this.accountNonExpired = accountNonExpired;
    }
    public Boolean getCredentialsNonExpired(){
        return this.credentialsNonExpired;
    }
    public void setCredentialsNonExpired(Boolean credentialsNonExpired){
        this.credentialsNonExpired = credentialsNonExpired;
    }
    public Boolean getEnabled(){
        return this.enabled;
    }
    public void setEnabled(Boolean enabled){
        this.enabled = enabled;
    }
    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmailLike(){
        return this.emailLike;
    }
    public void setEmailLike(String emailLike){
        this.emailLike = emailLike;
    }


}
