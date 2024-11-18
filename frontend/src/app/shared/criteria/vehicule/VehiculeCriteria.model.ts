import {DriverCriteria} from '../driver/DriverCriteria.model';

import {BaseCriteria} from 'src/app/zynerator/criteria/BaseCriteria.model';

export class VehiculeCriteria extends BaseCriteria {

    public id: number;
    public marque: string;
    public marqueLike: string;
    public modele: string;
    public modeleLike: string;
     public annee: number;
     public anneeMin: number;
     public anneeMax: number;
    public couleur: string;
    public couleurLike: string;
    public plaqueImmatriculation: string;
    public plaqueImmatriculationLike: string;
     public capacite: number;
     public capaciteMin: number;
     public capaciteMax: number;
    public image: string;
    public imageLike: string;
  public driver: DriverCriteria ;
  public drivers: Array<DriverCriteria> ;

}
