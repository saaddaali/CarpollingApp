import {DriverCriteria} from '../driver/DriverCriteria.model';
import {PassengerCriteria} from '../passenger/PassengerCriteria.model';
import {ConversationCriteria} from './ConversationCriteria.model';
import {TrajetCriteria} from '../trajet/TrajetCriteria.model';

import {BaseCriteria} from 'src/app/zynerator/criteria/BaseCriteria.model';

export class MessageCriteria extends BaseCriteria {

    public id: number;
    public contenu: string;
    public contenuLike: string;
    public dateEnvoi: Date;
    public dateEnvoiFrom: Date;
    public dateEnvoiTo: Date;
    public isPassenger: boolean;
    public trajet: TrajetCriteria;
    public trajets: Array<TrajetCriteria>;
    public driver: DriverCriteria;
    public drivers: Array<DriverCriteria>;
    public passenger: PassengerCriteria;
    public passengers: Array<PassengerCriteria>;
    public conversation: ConversationCriteria;
    public conversations: Array<ConversationCriteria>;

}
