import {Component, Inject, OnInit, PLATFORM_ID} from '@angular/core';
import {TrajetPassengerService} from 'src/app/shared/service/passenger/trajet/TrajetPassenger.service';
import {TrajetDto} from 'src/app/shared/model/trajet/Trajet.model';
import {TrajetCriteria} from 'src/app/shared/criteria/trajet/TrajetCriteria.model';


import {ConfirmationService, MenuItem, MessageService} from 'primeng/api';
import {DatePipe} from '@angular/common';
import {Router} from '@angular/router';

import {environment} from 'src/environments/environment';

import {RoleService} from 'src/app/zynerator/security/shared/service/Role.service';
import {StringUtilService} from 'src/app/zynerator/util/StringUtil.service';
import {ServiceLocator} from 'src/app/zynerator/service/ServiceLocator';

import {AuthService} from 'src/app/zynerator/security/shared/service/Auth.service';
import {ExportService} from 'src/app/zynerator/util/Export.service';


import {DriverDto} from 'src/app/shared/model/driver/Driver.model';
import {DriverPassengerService} from 'src/app/shared/service/passenger/driver/DriverPassenger.service';
import {VilleDto} from 'src/app/shared/model/trajet/Ville.model';
import {VillePassengerService} from 'src/app/shared/service/passenger/trajet/VillePassenger.service';
import {LayoutService} from "../../../../../../layout/service/app.layout.service";
import {AppComponent} from "../../../../../../app.component";
import {ReservationDto} from "../../../../../../shared/model/reservation/Reservation.model";
import {VilleCriteria} from "../../../../../../shared/criteria/trajet/VilleCriteria.model";


@Component({
    selector: 'app-trajet-list-passenger',
    templateUrl: './trajet-list-passenger.component.html',
    styleUrls: ['./trajet-list-passenger.component.scss']
})
export class TrajetListPassengerComponent implements OnInit {

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

    vileeDepartSearch: VilleCriteria;
    villeDestinationSearch: VilleCriteria;



    constructor(public layoutService: LayoutService, public app: AppComponent, private service: TrajetPassengerService  , private driverService: DriverPassengerService, private villeService: VillePassengerService, @Inject(PLATFORM_ID) private platformId?) {
        this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);

    }


    goBack() {
        this.router.navigate(['/app/passenger/']);
    }

    reserve(trajet: any) {
        const trajetDto = new TrajetDto();
        trajetDto.id = trajet.id;
        trajetDto.driver = trajet.driver;
        trajetDto.villeDepart = trajet.villeDepart;
        trajetDto.localisationSource = trajet.localisationSource;
        trajetDto.villeDestination = trajet.villeDestination;
        trajetDto.localisationDestination = trajet.localisationDestination;
        trajetDto.horaireDepart = trajet.horaireDepart;
        trajetDto.horaireArrive = trajet.horaireArrive;
        //trajetDto.tarif = trajet.tarif;
        trajetDto.placesDisponibles = trajet.placesDisponibles;
        //trajetDto.placesReservees = trajet.placesReservees;
        this.service.item = trajetDto;
        console.log('Trajet:', trajetDto);
        this.router.navigate(['/trajet/trajet/details']);
        };



        loading = true;

    ngOnInit(): void {
        this.findPaginatedByCriteria();
        this.loadVilleDepart();
        this.loadVilleDestination();
        this.loadDriver();
        this.item = this.service.item;
    }

    private mapDtoToCriteria(dto: TrajetDto): TrajetCriteria {

        if (!dto) {
            console.error('TrajetDto is null or undefined');
            return null;
        }
        const criteria = new TrajetCriteria();

        if(!dto.villeDepart.libelle){
            criteria.villeDepart = null;
        }
        criteria.id = dto.id || null;
       //criteria. horaireDepartFrom = dto.horaireDepart || null;

        criteria.villeDepart = this.mapVilleDtoToVilleCriteria(dto.villeDepart);
        criteria.villeDestination = this.mapVilleDtoToVilleCriteria(dto.villeDestination);

        return criteria;
    }


    private mapVilleDtoToVilleCriteria(villeDto: VilleDto): VilleCriteria {
        if (!villeDto.libelle) {
            return null;
        }

        const villeCriteria = new VilleCriteria();
        villeCriteria.id = villeDto.id|| null;
        villeCriteria.libelle = villeDto.libelle|| null ;

        return villeCriteria;
    }

    public findPaginatedByCriteria() {
        if (this.item) {
            this.criteria = this.mapDtoToCriteria(this.item);
        }
        this.service.findPaginatedByCriteria(this.criteria).subscribe(paginatedItems => {
            this.items = paginatedItems.list;

            this.totalRecords = paginatedItems.dataSize;
            this.selections = new Array<TrajetDto>();

        }, error => console.log(error));
    }

    public valid() {
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
