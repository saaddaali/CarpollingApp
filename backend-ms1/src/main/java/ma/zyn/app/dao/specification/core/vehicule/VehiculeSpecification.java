package  ma.zyn.app.dao.specification.core.vehicule;

import ma.zyn.app.dao.criteria.core.vehicule.VehiculeCriteria;
import ma.zyn.app.bean.core.vehicule.Vehicule;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class VehiculeSpecification extends  AbstractSpecification<VehiculeCriteria, Vehicule>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("marque", criteria.getMarque(),criteria.getMarqueLike());
        addPredicate("modele", criteria.getModele(),criteria.getModeleLike());
        addPredicateInt("annee", criteria.getAnnee(), criteria.getAnneeMin(), criteria.getAnneeMax());
        addPredicate("couleur", criteria.getCouleur(),criteria.getCouleurLike());
        addPredicate("plaqueImmatriculation", criteria.getPlaqueImmatriculation(),criteria.getPlaqueImmatriculationLike());
        addPredicateInt("capacite", criteria.getCapacite(), criteria.getCapaciteMin(), criteria.getCapaciteMax());
        addPredicate("image", criteria.getImage(),criteria.getImageLike());
        addPredicateFk("driver","id", criteria.getDriver()==null?null:criteria.getDriver().getId());
        addPredicateFk("driver","id", criteria.getDrivers());
        addPredicateFk("driver","email", criteria.getDriver()==null?null:criteria.getDriver().getEmail());
    }

    public VehiculeSpecification(VehiculeCriteria criteria) {
        super(criteria);
    }

    public VehiculeSpecification(VehiculeCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
