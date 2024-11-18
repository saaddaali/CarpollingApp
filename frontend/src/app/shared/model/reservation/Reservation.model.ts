import {DriverDto} from '../driver/Driver.model';
import {PassengerDto} from '../passenger/Passenger.model';
import {CarteBancaireDto} from '../paiement/CarteBancaire.model';
import {ConversationDto} from '../message/Conversation.model';
import {TrajetDto} from '../trajet/Trajet.model';

import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';


export class ReservationDto extends BaseDto{

   public dateReservation: Date;

    public montant: null | number;

   public datePaiement: Date;

    public evaluation: null | number;

    public trajet: TrajetDto ;
    public passenger: PassengerDto ;
    public driver: DriverDto ;
    public carteBancaire: CarteBancaireDto ;
    public conversation: ConversationDto ;


    constructor() {
        super();

        this.dateReservation = null;
        this.montant = null;
        this.datePaiement = null;
        this.evaluation = null;
        this.passenger = new PassengerDto() ;
        this.conversation = new ConversationDto() ;

        }

}
