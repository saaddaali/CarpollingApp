
import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';


export class DriverDto extends BaseDto{

    public photo: string;

    public adresse: string;

   public dateInscription: Date;

    public evaluation: null | number;

    public password: string;

   public accountNonLocked: null | boolean;

   public passwordChanged: null | boolean;

    public username: string;

   public accountNonExpired: null | boolean;

   public credentialsNonExpired: null | boolean;

   public enabled: null | boolean;

    public email: string;



    constructor() {
        super();

        this.photo = '';
        this.adresse = '';
        this.dateInscription = null;
        this.evaluation = null;
        this.password = '';
        this.accountNonLocked = null;
        this.passwordChanged = null;
        this.username = '';
        this.accountNonExpired = null;
        this.credentialsNonExpired = null;
        this.enabled = null;
        this.email = '';

        }

}
