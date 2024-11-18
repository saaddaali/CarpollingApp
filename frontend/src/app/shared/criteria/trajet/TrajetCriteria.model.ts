import {DriverCriteria} from '../driver/DriverCriteria.model';
import {VilleCriteria} from './VilleCriteria.model';

import {BaseCriteria} from 'src/app/zynerator/criteria/BaseCriteria.model';

export class TrajetCriteria extends BaseCriteria {

    public id: number;
    public horaireDepart: Date;
    public horaireDepartFrom: Date;
    public horaireDepartTo: Date;
    public horaireArrive: Date;
    public horaireArriveFrom: Date;
    public horaireArriveTo: Date;
     public placesDisponibles: number;
     public placesDisponiblesMin: number;
     public placesDisponiblesMax: number;
    public dateCreation: Date;
    public dateCreationFrom: Date;
    public dateCreationTo: Date;
  public villeDepart: VilleCriteria ;
  public villeDeparts: Array<VilleCriteria> ;
  public villeDestination: VilleCriteria ;
  public villeDestinations: Array<VilleCriteria> ;
  public localisationSource: VilleCriteria ;
  public localisationSources: Array<VilleCriteria> ;
  public localisationDestination: VilleCriteria ;
  public localisationDestinations: Array<VilleCriteria> ;

}
