package  ma.zyn.app.dao.specification.core.message;

import ma.zyn.app.dao.criteria.core.message.MessageCriteria;
import ma.zyn.app.bean.core.message.Message;
import ma.zyn.app.utils.specification.AbstractSpecification;


public class MessageSpecification extends  AbstractSpecification<MessageCriteria, Message>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("dateEnvoi", criteria.getDateEnvoi(), criteria.getDateEnvoiFrom(), criteria.getDateEnvoiTo());
        addPredicateFk("trajet","id", criteria.getTrajet()==null?null:criteria.getTrajet().getId());
        addPredicateFk("trajet","id", criteria.getTrajets());
        addPredicateFk("driver","id", criteria.getDriver()==null?null:criteria.getDriver().getId());
        addPredicateFk("driver","id", criteria.getDrivers());
        addPredicateFk("driver","email", criteria.getDriver()==null?null:criteria.getDriver().getEmail());
        addPredicateFk("passenger","id", criteria.getPassenger()==null?null:criteria.getPassenger().getId());
        addPredicateFk("passenger","id", criteria.getPassengers());
        addPredicateFk("passenger","email", criteria.getPassenger()==null?null:criteria.getPassenger().getEmail());
        addPredicateFk("conversation","id", criteria.getConversation()==null?null:criteria.getConversation().getId());
        addPredicateFk("conversation","id", criteria.getConversations());
        addPredicateFk("conversation","code", criteria.getConversation()==null?null:criteria.getConversation().getCode());
    }

    public MessageSpecification(MessageCriteria criteria) {
        super(criteria);
    }

    public MessageSpecification(MessageCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
