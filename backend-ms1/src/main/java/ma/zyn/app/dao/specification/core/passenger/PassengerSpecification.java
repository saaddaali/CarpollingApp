package  ma.zyn.app.dao.specification.core.passenger;

import ma.zyn.app.dao.criteria.core.passenger.PassengerCriteria;
import ma.zyn.app.bean.core.passenger.Passenger;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class PassengerSpecification extends  AbstractSpecification<PassengerCriteria, Passenger>  {

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
        addPredicateFk("carteBancaire","id", criteria.getCarteBancaire()==null?null:criteria.getCarteBancaire().getId());
        addPredicateFk("carteBancaire","id", criteria.getCarteBancaires());
    }

    public PassengerSpecification(PassengerCriteria criteria) {
        super(criteria);
    }

    public PassengerSpecification(PassengerCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
