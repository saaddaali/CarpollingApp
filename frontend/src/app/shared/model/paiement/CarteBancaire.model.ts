
import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';


export class CarteBancaireDto extends BaseDto{

    public titulaire: string;

    public numero: string;

   public dateExpiration: Date;

    public codeSecret: string;



    constructor() {
        super();

        this.titulaire = '';
        this.numero = '';
        this.dateExpiration = null;
        this.codeSecret = '';

        }

}
