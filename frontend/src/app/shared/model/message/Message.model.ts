import {DriverDto} from '../driver/Driver.model';
import {PassengerDto} from '../passenger/Passenger.model';
import {ConversationDto} from './Conversation.model';
import {TrajetDto} from '../trajet/Trajet.model';

import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';


export class MessageDto extends BaseDto{

    public contenu: string;

   public dateEnvoi: Date;

    public trajet: TrajetDto ;
    public driver: DriverDto ;
    public passenger: PassengerDto ;
    public conversation: ConversationDto ;
    public isPassenger:  null | boolean;

    constructor() {
        super();
        this.contenu = '';
        this.dateEnvoi = null;
        this.trajet = new TrajetDto() ;
        this.driver = new DriverDto() ;
        this.passenger = new PassengerDto() ;
        this.conversation = new ConversationDto() ;
        this.isPassenger = null;

        }

}
