package  ma.zyn.app.dao.specification.core.message;

import ma.zyn.app.dao.criteria.core.message.ConversationCriteria;
import ma.zyn.app.bean.core.message.Conversation;
import ma.zyn.app.utils.specification.AbstractSpecification;


public class ConversationSpecification extends  AbstractSpecification<ConversationCriteria, Conversation>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("libelle", criteria.getLibelle(),criteria.getLibelleLike());
        addPredicateFk("driver","id", criteria.getDriver()==null?null:criteria.getDriver().getId());
        addPredicateFk("driver","id", criteria.getDrivers());
        addPredicateFk("driver","email", criteria.getDriver()==null?null:criteria.getDriver().getEmail());
        addPredicateFk("passenger","id", criteria.getPassenger()==null?null:criteria.getPassenger().getId());
        addPredicateFk("passenger","id", criteria.getPassengers());
        addPredicateFk("passenger","email", criteria.getPassenger()==null?null:criteria.getPassenger().getEmail());
    }

    public ConversationSpecification(ConversationCriteria criteria) {
        super(criteria);
    }

    public ConversationSpecification(ConversationCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
