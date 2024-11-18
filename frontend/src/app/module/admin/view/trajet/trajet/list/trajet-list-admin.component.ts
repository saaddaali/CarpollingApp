import {Component, OnInit} from '@angular/core';
import {TrajetAdminService} from 'src/app/shared/service/admin/trajet/TrajetAdmin.service';
import {TrajetDto} from 'src/app/shared/model/trajet/Trajet.model';
import {TrajetCriteria} from 'src/app/shared/criteria/trajet/TrajetCriteria.model';


import {ConfirmationService, MessageService,MenuItem} from 'primeng/api';
import {FileTempDto} from 'src/app/zynerator/dto/FileTempDto.model';
import {DatePipe} from '@angular/common';
import {Router} from '@angular/router';
import {Inject, Injectable, PLATFORM_ID} from '@angular/core';

import {environment} from 'src/environments/environment';

import {RoleService} from 'src/app/zynerator/security/shared/service/Role.service';
import {AbstractService} from 'src/app/zynerator/service/AbstractService';
import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';
import {BaseCriteria} from 'src/app/zynerator/criteria/BaseCriteria.model';
import {StringUtilService} from 'src/app/zynerator/util/StringUtil.service';
import {ServiceLocator} from 'src/app/zynerator/service/ServiceLocator';

import {AuthService} from 'src/app/zynerator/security/shared/service/Auth.service';
import {ExportService} from 'src/app/zynerator/util/Export.service';


import {DriverDto} from 'src/app/shared/model/driver/Driver.model';
import {DriverAdminService} from 'src/app/shared/service/admin/driver/DriverAdmin.service';
import {VilleDto} from 'src/app/shared/model/trajet/Ville.model';
import {VilleAdminService} from 'src/app/shared/service/admin/trajet/VilleAdmin.service';


@Component({
  selector: 'app-trajet-list-admin',
  templateUrl: './trajet-list-admin.component.html'
})
export class TrajetListAdminComponent implements OnInit {

    protected fileName = 'Trajet';

    protected findByCriteriaShow = false;
    protected cols: any[] = [];
    protected excelPdfButons: MenuItem[];
    protected exportData: any[] = [];
    protected criteriaData: any[] = [];
    protected _totalRecords = 0;
    private _pdfName: string;


    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;
    protected authService: AuthService;
    protected exportService: ExportService;
    protected excelFile: File | undefined;
    protected enableSecurity = false;


    villeDeparts: Array<VilleDto>;
    villeDestinations: Array<VilleDto>;
    drivers: Array<DriverDto>;
    localisationSources: Array<VilleDto>;
    localisationDestinations: Array<VilleDto>;


    constructor( private service: TrajetAdminService  , private driverService: DriverAdminService, private villeService: VilleAdminService, @Inject(PLATFORM_ID) private platformId?) {
        this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.authService = ServiceLocator.injector.get(AuthService);
        this.exportService = ServiceLocator.injector.get(ExportService);
    }

    ngOnInit(): void {
        this.findPaginatedByCriteria();
        this.initExport();
        this.initCol();
        this.loadVilleDepart();
        this.loadVilleDestination();
        this.loadDriver();
        this.loadLocalisationSource();
        this.loadLocalisationDestination();

    }




    public onExcelFileSelected(event: any): void {
        const input = event.target as HTMLInputElement;
        if (input.files && input.files.length > 0) {
            this.excelFile = input.files[0];
        }
    }

    public importExcel(): void {
        if (this.excelFile) {
            this.service.importExcel(this.excelFile).subscribe(
                response => {
                    this.items = response;
                    this.messageService.add({
                        severity: 'success',
                        summary: 'Success',
                        detail: 'File uploaded successfully!',
                        life: 3000
                    });
                },
                error => {
                    this.messageService.add({
                        severity: 'error',
                        summary: 'Error',
                        detail: 'File uploaded with Error!',
                        life: 3000
                    });
                }
            );
        }
    }

    public findPaginatedByCriteria() {
        this.service.findPaginatedByCriteria(this.criteria).subscribe(paginatedItems => {
            this.items = paginatedItems.list;
            this.totalRecords = paginatedItems.dataSize;
            this.selections = new Array<TrajetDto>();
        }, error => console.log(error));
    }

    public onPage(event: any) {
        this.criteria.page = event.page;
        this.criteria.maxResults = event.rows;
        this.findPaginatedByCriteria();
    }

    public async edit(dto: TrajetDto) {
        this.service.findByIdWithAssociatedList(dto).subscribe(res => {
            this.item = res;
            console.log(res);
            this.editDialog = true;
        });

    }

    public async view(dto: TrajetDto) {
        this.service.findByIdWithAssociatedList(dto).subscribe(res => {
            this.item = res;
            this.viewDialog = true;
        });
    }

    public async openCreate() {
        this.item = new TrajetDto();
        this.createDialog = true;
    }

    public async deleteMultiple() {
        this.confirmationService.confirm({
            message: 'Voulez-vous supprimer ces éléments ?',
            header: 'Confirmation',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                this.service.deleteMultiple().subscribe(() => {
                    for (let selection of this.selections) {
                        let index = this.items.findIndex(element => element.id === selection.id);
                        this.items.splice(index,1);
                    }
                    this.selections = new Array<TrajetDto>();
                    this.messageService.add({
                        severity: 'success',
                        summary: 'Succès',
                        detail: 'Les éléments sélectionnés ont été supprimés',
                        life: 3000
                    });

                }, error => console.log(error));
            }
        });
    }


    public isSelectionDisabled(): boolean {
        return this.selections == null || this.selections.length == 0;
    }


    public async delete(dto: TrajetDto) {

        this.confirmationService.confirm({
            message: 'Voulez-vous supprimer cet élément ?',
            header: 'Confirmation',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                this.service.delete(dto).subscribe(status => {
                    if (status > 0) {
                        const position = this.items.indexOf(dto);
                        position > -1 ? this.items.splice(position, 1) : false;
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Succès',
                            detail: 'Element Supprimé',
                            life: 3000
                        });
                    }

                }, error => console.log(error));
            }
        });

    }

    public async duplicate(dto: TrajetDto) {
        this.service.findByIdWithAssociatedList(dto).subscribe(
            res => {
                this.initDuplicate(res);
                this.item = res;
                this.item.id = null;
                this.createDialog = true;
            });
    }

    // TODO : check if correct
    public initExport(): void {
        this.excelPdfButons = [
            {
                label: 'CSV', icon: 'pi pi-file', command: () => {
                    this.prepareColumnExport();
                    this.exportService.exporterCSV(this.criteriaData, this.exportData, this.fileName);
                }
            },
            {
                label: 'XLS', icon: 'pi pi-file-excel', command: () => {
                    this.prepareColumnExport();
                    this.exportService.exporterExcel(this.criteriaData, this.exportData, this.fileName);
                }
            },
            {
                label: 'PDF', icon: 'pi pi-file-pdf', command: () => {
                    this.prepareColumnExport();
                    this.exportService.exporterPdf(this.criteriaData, this.exportData, this.fileName);
                }
            }
        ];
    }

    public exportPdf(dto: TrajetDto): void {
        this.service.exportPdf(dto).subscribe((data: ArrayBuffer) => {
            const blob = new Blob([data], {type: 'application/pdf'});
            const url = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            link.download = this.pdfName;
            link.setAttribute('target', '_blank'); // open link in new tab
            link.click();
            window.URL.revokeObjectURL(url);
        }, (error) => {
            console.error(error); // handle any errors that occur
        });
    }

    public showSearch(): void {
        this.findByCriteriaShow = !this.findByCriteriaShow;
    }


    update() {
        this.service.edit().subscribe(data => {
            const myIndex = this.items.findIndex(e => e.id === this.item.id);
            this.items[myIndex] = data;
            this.editDialog = false;
            this.item = new TrajetDto();
        } , error => {
            console.log(error);
        });
    }

    public save() {
        this.service.save().subscribe(item => {
            if (item != null) {
                this.items.push({...item});
                this.createDialog = false;


                this.item = new TrajetDto();
            } else {
                this.messageService.add({severity: 'error', summary: 'Erreurs', detail: 'Element existant'});
            }
        }, error => {
            console.log(error);
        });
    }

// add


    public initCol() {
        this.cols = [
            {field: 'villeDepart?.libelle', header: 'Ville depart'},
            {field: 'villeDestination?.libelle', header: 'Ville destination'},
            {field: 'horaireDepart', header: 'Horaire depart'},
            {field: 'horaireArrive', header: 'Horaire arrive'},
            {field: 'placesDisponibles', header: 'Places disponibles'},
            {field: 'driver?.email', header: 'Driver'},
            {field: 'dateCreation', header: 'Date creation'},
            {field: 'localisationSource?.libelle', header: 'Localisation source'},
            {field: 'localisationDestination?.libelle', header: 'Localisation destination'},
        ];
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
    public async loadLocalisationSource(){
        this.villeService.findAllOptimized().subscribe(localisationSources => this.localisationSources = localisationSources, error => console.log(error))
    }
    public async loadLocalisationDestination(){
        this.villeService.findAllOptimized().subscribe(localisationDestinations => this.localisationDestinations = localisationDestinations, error => console.log(error))
    }


	public initDuplicate(res: TrajetDto) {
	}


    public prepareColumnExport(): void {
        this.service.findByCriteria(this.criteria).subscribe(
            (allItems) =>{
                this.exportData = allItems.map(e => {
					return {
						'Ville depart': e.villeDepart?.libelle ,
						'Ville destination': e.villeDestination?.libelle ,
						'Horaire depart': this.datePipe.transform(e.horaireDepart , 'dd/MM/yyyy hh:mm'),
						'Horaire arrive': this.datePipe.transform(e.horaireArrive , 'dd/MM/yyyy hh:mm'),
						'Places disponibles': e.placesDisponibles ,
						'Driver': e.driver?.email ,
						'Date creation': this.datePipe.transform(e.dateCreation , 'dd/MM/yyyy hh:mm'),
						'Localisation source': e.localisationSource?.libelle ,
						'Localisation destination': e.localisationDestination?.libelle ,
					}
				});

            this.criteriaData = [{
            //'Ville depart': this.criteria.villeDepart?.libelle ? this.criteria.villeDepart?.libelle : environment.emptyForExport ,
            //'Ville destination': this.criteria.villeDestination?.libelle ? this.criteria.villeDestination?.libelle : environment.emptyForExport ,
                'Horaire depart Min': this.criteria.horaireDepartFrom ? this.datePipe.transform(this.criteria.horaireDepartFrom , this.dateFormat) : environment.emptyForExport ,
                'Horaire depart Max': this.criteria.horaireDepartTo ? this.datePipe.transform(this.criteria.horaireDepartTo , this.dateFormat) : environment.emptyForExport ,
                'Horaire arrive Min': this.criteria.horaireArriveFrom ? this.datePipe.transform(this.criteria.horaireArriveFrom , this.dateFormat) : environment.emptyForExport ,
                'Horaire arrive Max': this.criteria.horaireArriveTo ? this.datePipe.transform(this.criteria.horaireArriveTo , this.dateFormat) : environment.emptyForExport ,
                'Places disponibles Min': this.criteria.placesDisponiblesMin ? this.criteria.placesDisponiblesMin : environment.emptyForExport ,
                'Places disponibles Max': this.criteria.placesDisponiblesMax ? this.criteria.placesDisponiblesMax : environment.emptyForExport ,
            //'Driver': this.criteria.driver?.email ? this.criteria.driver?.email : environment.emptyForExport ,
                'Date creation Min': this.criteria.dateCreationFrom ? this.datePipe.transform(this.criteria.dateCreationFrom , this.dateFormat) : environment.emptyForExport ,
                'Date creation Max': this.criteria.dateCreationTo ? this.datePipe.transform(this.criteria.dateCreationTo , this.dateFormat) : environment.emptyForExport ,
            //'Localisation source': this.criteria.localisationSource?.libelle ? this.criteria.localisationSource?.libelle : environment.emptyForExport ,
            //'Localisation destination': this.criteria.localisationDestination?.libelle ? this.criteria.localisationDestination?.libelle : environment.emptyForExport ,
            }];
			}

        )
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

    get createDialog(): boolean {
        return this.service.createDialog;
    }

    set createDialog(value: boolean) {
        this.service.createDialog = value;
    }

    get editDialog(): boolean {
        return this.service.editDialog;
    }

    set editDialog(value: boolean) {
        this.service.editDialog = value;
    }

    get viewDialog(): boolean {
        return this.service.viewDialog;
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

    get dateFormat() {
        return environment.dateFormatList;
    }


    get totalRecords(): number {
        return this._totalRecords;
    }

    set totalRecords(value: number) {
        this._totalRecords = value;
    }

    get pdfName(): string {
        return this._pdfName;
    }

    set pdfName(value: string) {
        this._pdfName = value;
    }

    get createActionIsValid(): boolean {
        return this.service.createActionIsValid;
    }

    set createActionIsValid(value: boolean) {
        this.service.createActionIsValid = value;
    }


    get editActionIsValid(): boolean {
        return this.service.editActionIsValid;
    }

    set editActionIsValid(value: boolean) {
        this.service.editActionIsValid = value;
    }

    get listActionIsValid(): boolean {
        return this.service.listActionIsValid;
    }

    set listActionIsValid(value: boolean) {
        this.service.listActionIsValid = value;
    }

    get deleteActionIsValid(): boolean {
        return this.service.deleteActionIsValid;
    }

    set deleteActionIsValid(value: boolean) {
        this.service.deleteActionIsValid = value;
    }


    get viewActionIsValid(): boolean {
        return this.service.viewActionIsValid;
    }

    set viewActionIsValid(value: boolean) {
        this.service.viewActionIsValid = value;
    }

    get duplicateActionIsValid(): boolean {
        return this.service.duplicateActionIsValid;
    }

    set duplicateActionIsValid(value: boolean) {
        this.service.duplicateActionIsValid = value;
    }

    get createAction(): string {
        return this.service.createAction;
    }

    set createAction(value: string) {
        this.service.createAction = value;
    }

    get listAction(): string {
        return this.service.listAction;
    }

    set listAction(value: string) {
        this.service.listAction = value;
    }

    get editAction(): string {
        return this.service.editAction;
    }

    set editAction(value: string) {
        this.service.editAction = value;
    }

    get deleteAction(): string {
        return this.service.deleteAction;
    }

    set deleteAction(value: string) {
        this.service.deleteAction = value;
    }

    get viewAction(): string {
        return this.service.viewAction;
    }

    set viewAction(value: string) {
        this.service.viewAction = value;
    }

    get duplicateAction(): string {
        return this.service.duplicateAction;
    }

    set duplicateAction(value: string) {
        this.service.duplicateAction = value;
    }

    get entityName(): string {
        return this.service.entityName;
    }

    set entityName(value: string) {
        this.service.entityName = value;
    }
}
