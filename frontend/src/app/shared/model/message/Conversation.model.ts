import {DriverDto} from '../driver/Driver.model';
import {PassengerDto} from '../passenger/Passenger.model';

import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';


export class ConversationDto extends BaseDto{

    public code: string;

    public libelle: string;

    public description: string;

    public driver: DriverDto ;
    public passenger: PassengerDto ;


    constructor() {
        super();

        this.code = '';
        this.libelle = '';
        this.description = '';

        }

}
