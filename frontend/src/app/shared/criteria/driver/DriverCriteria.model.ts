
import {BaseCriteria} from 'src/app/zynerator/criteria/BaseCriteria.model';

export class DriverCriteria extends BaseCriteria {

    public photo: string;
    public photoLike: string;
    public adresse: string;
    public adresseLike: string;
    public dateInscription: Date;
    public dateInscriptionFrom: Date;
    public dateInscriptionTo: Date;
     public evaluation: number;
     public evaluationMin: number;
     public evaluationMax: number;
    public password: string;
    public passwordLike: string;
    public accountNonLocked: null | boolean;
    public passwordChanged: null | boolean;
    public username: string;
    public usernameLike: string;
    public accountNonExpired: null | boolean;
    public credentialsNonExpired: null | boolean;
    public enabled: null | boolean;
    public email: string;
    public emailLike: string;

}
