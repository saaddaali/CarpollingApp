import {DriverDto} from '../driver/Driver.model';

import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';


export class VehiculeDto extends BaseDto{

    public marque: string;

    public modele: string;

    public annee: null | number;

    public couleur: string;

    public plaqueImmatriculation: string;

    public capacite: null | number;

    public image: string;

    public driver: DriverDto ;


    constructor() {
        super();

        this.marque = '';
        this.modele = '';
        this.annee = null;
        this.couleur = '';
        this.plaqueImmatriculation = '';
        this.capacite = null;
        this.image = '';
        this.driver = new DriverDto() ;

        }

}
