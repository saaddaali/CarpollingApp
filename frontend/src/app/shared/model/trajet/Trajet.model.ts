import {DriverDto} from '../driver/Driver.model';
import {VilleDto} from './Ville.model';

import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';


export class TrajetDto extends BaseDto{

   public horaireDepart: Date;

   public horaireArrive: Date;

    public placesDisponibles: null | number;

   public dateCreation: Date;

    public villeDepart: VilleDto ;
    public villeDestination: VilleDto ;
    public driver: DriverDto ;
    public localisationSource: VilleDto ;
    public localisationDestination: VilleDto ;

    public prix: number;


    constructor() {
        super();

        this.horaireDepart = null;
        this.horaireArrive = null;
        this.placesDisponibles = null;
        this.dateCreation = null;
        this.villeDepart = new VilleDto() ;
        this.villeDestination = new VilleDto() ;
        this.localisationSource = new VilleDto() ;
        this.localisationDestination = new VilleDto() ;
        this.prix= 0;

        }

}
