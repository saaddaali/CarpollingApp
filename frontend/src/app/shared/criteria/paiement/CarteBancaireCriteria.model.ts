
import {BaseCriteria} from 'src/app/zynerator/criteria/BaseCriteria.model';

export class CarteBancaireCriteria extends BaseCriteria {

    public id: number;
    public titulaire: string;
    public titulaireLike: string;
    public numero: string;
    public numeroLike: string;
    public dateExpiration: Date;
    public dateExpirationFrom: Date;
    public dateExpirationTo: Date;
    public codeSecret: string;
    public codeSecretLike: string;

}
