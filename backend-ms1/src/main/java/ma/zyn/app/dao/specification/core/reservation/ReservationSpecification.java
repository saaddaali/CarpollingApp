package  ma.zyn.app.dao.specification.core.reservation;

import ma.zyn.app.dao.criteria.core.reservation.ReservationCriteria;
import ma.zyn.app.bean.core.reservation.Reservation;
import ma.zyn.app.utils.specification.AbstractSpecification;


public class ReservationSpecification extends  AbstractSpecification<ReservationCriteria, Reservation>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("dateReservation", criteria.getDateReservation(), criteria.getDateReservationFrom(), criteria.getDateReservationTo());
        addPredicateBigDecimal("montant", criteria.getMontant(), criteria.getMontantMin(), criteria.getMontantMax());
        addPredicate("datePaiement", criteria.getDatePaiement(), criteria.getDatePaiementFrom(), criteria.getDatePaiementTo());
        addPredicateBigDecimal("evaluation", criteria.getEvaluation(), criteria.getEvaluationMin(), criteria.getEvaluationMax());
        addPredicateFk("trajet","id", criteria.getTrajet()==null?null:criteria.getTrajet().getId());
        addPredicateFk("trajet","id", criteria.getTrajets());
        addPredicateFk("passenger","id", criteria.getPassenger()==null?null:criteria.getPassenger().getId());
        addPredicateFk("passenger","id", criteria.getPassengers());
        addPredicateFk("passenger","email", criteria.getPassenger()==null?null:criteria.getPassenger().getEmail());
        addPredicateFk("driver","id", criteria.getDriver()==null?null:criteria.getDriver().getId());
        addPredicateFk("driver","id", criteria.getDrivers());
        addPredicateFk("driver","email", criteria.getDriver()==null?null:criteria.getDriver().getEmail());
        addPredicateFk("carteBancaire","id", criteria.getCarteBancaire()==null?null:criteria.getCarteBancaire().getId());
        addPredicateFk("carteBancaire","id", criteria.getCarteBancaires());
        addPredicateFk("conversation","id", criteria.getConversation()==null?null:criteria.getConversation().getId());
        addPredicateFk("conversation","id", criteria.getConversations());
        addPredicateFk("conversation","code", criteria.getConversation()==null?null:criteria.getConversation().getCode());
    }

    public ReservationSpecification(ReservationCriteria criteria) {
        super(criteria);
    }

    public ReservationSpecification(ReservationCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
