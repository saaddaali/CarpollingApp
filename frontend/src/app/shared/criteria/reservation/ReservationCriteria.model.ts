import {DriverCriteria} from '../driver/DriverCriteria.model';
import {PassengerCriteria} from '../passenger/PassengerCriteria.model';
import {CarteBancaireCriteria} from '../paiement/CarteBancaireCriteria.model';
import {ConversationCriteria} from '../message/ConversationCriteria.model';
import {TrajetCriteria} from '../trajet/TrajetCriteria.model';

import {BaseCriteria} from 'src/app/zynerator/criteria/BaseCriteria.model';

export class ReservationCriteria extends BaseCriteria {

    public id: number;
    public dateReservation: Date;
    public dateReservationFrom: Date;
    public dateReservationTo: Date;
     public montant: number;
     public montantMin: number;
     public montantMax: number;
    public datePaiement: Date;
    public datePaiementFrom: Date;
    public datePaiementTo: Date;
     public evaluation: number;
     public evaluationMin: number;
     public evaluationMax: number;
  public passenger: PassengerCriteria ;
  public passengers: Array<PassengerCriteria> ;
  public conversation: ConversationCriteria ;
  public conversations: Array<ConversationCriteria> ;

}
