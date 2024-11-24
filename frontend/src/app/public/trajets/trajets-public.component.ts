import {Component, Inject, OnInit, PLATFORM_ID} from '@angular/core';
import {LayoutService} from "../../layout/service/app.layout.service";
import {AppComponent} from "../../app.component";
import {ConfirmationService, MenuItem, MessageService} from "primeng/api";
import {DatePipe} from "@angular/common";
import {RoleService} from "../../zynerator/security/shared/service/Role.service";
import {Router} from "@angular/router";
import {StringUtilService} from "../../zynerator/util/StringUtil.service";
import {AuthService} from "../../zynerator/security/shared/service/Auth.service";
import {ExportService} from "../../zynerator/util/Export.service";
import {VilleDto} from "../../shared/model/trajet/Ville.model";
import {DriverDto} from "../../shared/model/driver/Driver.model";
import {TrajetPassengerService} from "../../shared/service/passenger/trajet/TrajetPassenger.service";
import {DriverPassengerService} from "../../shared/service/passenger/driver/DriverPassenger.service";
import {VillePassengerService} from "../../shared/service/passenger/trajet/VillePassenger.service";
import {ServiceLocator} from "../../zynerator/service/ServiceLocator";
import {TrajetDto} from "../../shared/model/trajet/Trajet.model";
import {environment} from "../../../environments/environment";
import {TrajetCriteria} from "../../shared/criteria/trajet/TrajetCriteria.model";


@Component({
    selector: 'app-home',
    templateUrl: './trajets-public.component.html',
    styleUrls: ['./trajets-public.component.scss']
})
export class TrajetsPublicComponent implements OnInit {

    protected fileName = 'Trajet';

    protected findByCriteriaShow = false;
    protected cols: any[] = [];
    protected excelPdfButons: MenuItem[];
    protected exportData: any[] = [];
    protected criteriaData: any[] = [];
    protected _totalRecords = 0;


    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;
    protected authService: AuthService;
    protected exportService: ExportService;
    protected excelFile: File | undefined;

    villeDeparts: Array<VilleDto>;
    villeDestinations: Array<VilleDto>;
    drivers: Array<DriverDto>;
    localisationSources: Array<VilleDto>;
    localisationDestinations: Array<VilleDto>;


    constructor(public layoutService: LayoutService, public app: AppComponent, private service: TrajetPassengerService  , private driverService: DriverPassengerService, private villeService: VillePassengerService, @Inject(PLATFORM_ID) private platformId?) {
        this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
    }


    goBack() {
        window.history.back();
    }


    loading = true;

    ngOnInit(): void {
        //this.findPaginatedByCriteria();
        this.loadVilleDepart();
        this.loadVilleDestination();
        this.loadDriver();
        this.item = this.service.item;
        console.log('Item:', this.item);
        this.criteria.villeDepart.libelle=this.item.villeDepart.libelle;
        this.criteria.villeDestination.libelle=this.item.villeDestination.libelle;
        console.log("cret"+this.criteria.villeDepart.libelle);



    }
    public findPaginatedByCriteria() {
        this.service.findPaginatedByCriteria(this.criteria).subscribe(paginatedItems => {
            this.items = paginatedItems.list;
            this.totalRecords = paginatedItems.dataSize;
            this.selections = new Array<TrajetDto>();
        }, error => console.log(error));
    }



    public async view(dto: TrajetDto) {
        this.service.findByIdWithAssociatedList(dto).subscribe(res => {
            this.item = res;
            this.viewDialog = true;
        });
    }






    public async loadVilleDepart(){
        this.villeService.findAllOptimized().subscribe(villeDeparts => this.villeDeparts = villeDeparts, error => console.log(error))
    }
    public async loadVilleDestination(){
        this.villeService.findAllOptimized().subscribe(villeDestinations => this.villeDestinations = villeDestinations, error => console.log(error))
    }
    public async loadDriver(){
        this.driverService.findAllOptimized().subscribe(drivers => this.drivers = drivers, error => console.log(error))
    }

    get items(): Array<TrajetDto> {
        return this.service.items;
    }

    set items(value: Array<TrajetDto>) {
        this.service.items = value;
    }

    get selections(): Array<TrajetDto> {
        return this.service.selections;
    }

    set selections(value: Array<TrajetDto>) {
        this.service.selections = value;
    }

    get item(): TrajetDto {
        return this.service.item;
    }

    set item(value: TrajetDto) {
        this.service.item = value;
    }


    set viewDialog(value: boolean) {
        this.service.viewDialog = value;
    }

    get criteria(): TrajetCriteria {
        return this.service.criteria;
    }

    set criteria(value: TrajetCriteria) {
        this.service.criteria = value;
    }

    set totalRecords(value: number) {
        this._totalRecords = value;
    }

}
