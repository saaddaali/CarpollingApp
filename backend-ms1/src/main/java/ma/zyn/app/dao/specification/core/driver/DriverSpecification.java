package  ma.zyn.app.dao.specification.core.driver;

import ma.zyn.app.dao.criteria.core.driver.DriverCriteria;
import ma.zyn.app.bean.core.driver.Driver;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class DriverSpecification extends  AbstractSpecification<DriverCriteria, Driver>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("photo", criteria.getPhoto(),criteria.getPhotoLike());
        addPredicate("dateInscription", criteria.getDateInscription(), criteria.getDateInscriptionFrom(), criteria.getDateInscriptionTo());
        addPredicateBigDecimal("evaluation", criteria.getEvaluation(), criteria.getEvaluationMin(), criteria.getEvaluationMax());
        addPredicate("password", criteria.getPassword(),criteria.getPasswordLike());
        addPredicateBool("accountNonLocked", criteria.getAccountNonLocked());
        addPredicateBool("passwordChanged", criteria.getPasswordChanged());
        addPredicate("username", criteria.getUsername(),criteria.getUsernameLike());
        addPredicateBool("accountNonExpired", criteria.getAccountNonExpired());
        addPredicateBool("credentialsNonExpired", criteria.getCredentialsNonExpired());
        addPredicateBool("enabled", criteria.getEnabled());
        addPredicate("email", criteria.getEmail(),criteria.getEmailLike());
    }

    public DriverSpecification(DriverCriteria criteria) {
        super(criteria);
    }

    public DriverSpecification(DriverCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
