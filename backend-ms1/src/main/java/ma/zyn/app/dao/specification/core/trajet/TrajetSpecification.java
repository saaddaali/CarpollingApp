package  ma.zyn.app.dao.specification.core.trajet;

import ma.zyn.app.dao.criteria.core.trajet.TrajetCriteria;
import ma.zyn.app.bean.core.trajet.Trajet;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class TrajetSpecification extends  AbstractSpecification<TrajetCriteria, Trajet>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("horaireDepart", criteria.getHoraireDepart(), criteria.getHoraireDepartFrom(), criteria.getHoraireDepartTo());
        addPredicate("horaireArrive", criteria.getHoraireArrive(), criteria.getHoraireArriveFrom(), criteria.getHoraireArriveTo());
        addPredicateInt("placesDisponibles", criteria.getPlacesDisponibles(), criteria.getPlacesDisponiblesMin(), criteria.getPlacesDisponiblesMax());
        addPredicate("dateCreation", criteria.getDateCreation(), criteria.getDateCreationFrom(), criteria.getDateCreationTo());
        addPredicateFk("villeDepart","id", criteria.getVilleDepart()==null?null:criteria.getVilleDepart().getId());
        addPredicateFk("villeDepart","id", criteria.getVilleDeparts());
        addPredicateFk("villeDepart","code", criteria.getVilleDepart()==null?null:criteria.getVilleDepart().getCode());
        addPredicateFk("villeDestination","id", criteria.getVilleDestination()==null?null:criteria.getVilleDestination().getId());
        addPredicateFk("villeDestination","id", criteria.getVilleDestinations());
        addPredicateFk("villeDestination","code", criteria.getVilleDestination()==null?null:criteria.getVilleDestination().getCode());
        addPredicateFk("driver","id", criteria.getDriver()==null?null:criteria.getDriver().getId());
        addPredicateFk("driver","id", criteria.getDrivers());
        addPredicateFk("driver","email", criteria.getDriver()==null?null:criteria.getDriver().getEmail());
        addPredicateFk("localisationSource","id", criteria.getLocalisationSource()==null?null:criteria.getLocalisationSource().getId());
        addPredicateFk("localisationSource","id", criteria.getLocalisationSources());
        addPredicateFk("localisationSource","code", criteria.getLocalisationSource()==null?null:criteria.getLocalisationSource().getCode());
        addPredicateFk("localisationDestination","id", criteria.getLocalisationDestination()==null?null:criteria.getLocalisationDestination().getId());
        addPredicateFk("localisationDestination","id", criteria.getLocalisationDestinations());
        addPredicateFk("localisationDestination","code", criteria.getLocalisationDestination()==null?null:criteria.getLocalisationDestination().getCode());
    }

    public TrajetSpecification(TrajetCriteria criteria) {
        super(criteria);
    }

    public TrajetSpecification(TrajetCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
