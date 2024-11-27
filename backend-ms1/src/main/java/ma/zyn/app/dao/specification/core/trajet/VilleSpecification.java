package  ma.zyn.app.dao.specification.core.trajet;

import ma.zyn.app.dao.criteria.core.trajet.VilleCriteria;
import ma.zyn.app.bean.core.trajet.Ville;
import ma.zyn.app.utils.specification.AbstractSpecification;


public class VilleSpecification extends  AbstractSpecification<VilleCriteria, Ville>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("libelle", criteria.getLibelle(),criteria.getLibelleLike());
    }

    public VilleSpecification(VilleCriteria criteria) {
        super(criteria);
    }

    public VilleSpecification(VilleCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
