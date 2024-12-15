import {Component, OnInit} from '@angular/core';
import {ReservationPassengerService} from 'src/app/shared/service/passenger/reservation/ReservationPassenger.service';
import {ReservationDto} from 'src/app/shared/model/reservation/Reservation.model';
import {ReservationCriteria} from 'src/app/shared/criteria/reservation/ReservationCriteria.model';


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
import {DriverPassengerService} from 'src/app/shared/service/passenger/driver/DriverPassenger.service';
import {PassengerDto} from 'src/app/shared/model/passenger/Passenger.model';
import {PassengerPassengerService} from 'src/app/shared/service/passenger/passenger/PassengerPassenger.service';
import {CarteBancaireDto} from 'src/app/shared/model/paiement/CarteBancaire.model';
import {CarteBancairePassengerService} from 'src/app/shared/service/passenger/paiement/CarteBancairePassenger.service';
import {ConversationDto} from 'src/app/shared/model/message/Conversation.model';
import {ConversationPassengerService} from 'src/app/shared/service/passenger/message/ConversationPassenger.service';
import {TrajetDto} from 'src/app/shared/model/trajet/Trajet.model';
import {TrajetPassengerService} from 'src/app/shared/service/passenger/trajet/TrajetPassenger.service';


@Component({
  selector: 'app-reservation-list-passenger',
  templateUrl: './reservation-list-passenger.component.html',
    styleUrls: ['./reservation-list-passenger.component.scss']
})
export class ReservationListPassengerComponent implements OnInit {

    protected fileName = 'Reservation';

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


    trajets: Array<TrajetDto>;
    passengers: Array<PassengerDto>;
    drivers: Array<DriverDto>;
    carteBancaires: Array<CarteBancaireDto>;
    conversations: Array<ConversationDto>;


    constructor( private service: ReservationPassengerService  , private driverService: DriverPassengerService, private passengerService: PassengerPassengerService, private carteBancaireService: CarteBancairePassengerService, private conversationService: ConversationPassengerService, private trajetService: TrajetPassengerService, @Inject(PLATFORM_ID) private platformId?) {
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
        this.loadTrajet();
        this.loadPassenger();
        this.loadDriver();
        this.loadCarteBancaire();
        this.loadConversation();

    }


    public initCriteria() {
        this.criteria = new ReservationCriteria();
        this.findPaginatedByCriteria();
    }

    getStatusSeverity(reservation: any): string {
        // Exemple de logique pour déterminer le statut
        if (reservation.datePaiement) {
            return 'success';
        }
        return 'warning';
    }

    getStatusLabel(reservation: any): string {
        // Exemple de logique pour l'étiquette du statut
        if (reservation.datePaiement) {
            return 'Payé';
        }
        return 'En attente';
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
            this.selections = new Array<ReservationDto>();
        }, error => console.log(error));
    }

    public onPage(event: any) {
        this.criteria.page = event.page;
        this.criteria.maxResults = event.rows;
        this.findPaginatedByCriteria();
    }

    public async edit(dto: ReservationDto) {
        this.service.findByIdWithAssociatedList(dto).subscribe(res => {
            this.item = res;
            console.log(res);
            this.editDialog = true;
        });

    }

    public async view(dto: ReservationDto) {
        this.service.findByIdWithAssociatedList(dto).subscribe(res => {
            this.item = res;
            this.viewDialog = true;
        });
    }

    public async openCreate() {
        this.item = new ReservationDto();
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
                    this.selections = new Array<ReservationDto>();
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


    public async delete(dto: ReservationDto) {

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

    public async duplicate(dto: ReservationDto) {
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

    public exportPdf(dto: ReservationDto): void {
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
            this.item = new ReservationDto();
        } , error => {
            console.log(error);
        });
    }

    public save() {
        this.service.save().subscribe(item => {
            if (item != null) {
                this.items.push({...item});
                this.createDialog = false;


                this.item = new ReservationDto();
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
            {field: 'trajet?.id', header: 'Trajet'},
            {field: 'passenger?.email', header: 'Passenger'},
            {field: 'driver?.email', header: 'Driver'},
            {field: 'dateReservation', header: 'Date reservation'},
            {field: 'montant', header: 'Montant'},
            {field: 'datePaiement', header: 'Date paiement'},
            {field: 'carteBancaire?.id', header: 'Carte bancaire'},
            {field: 'evaluation', header: 'Evaluation'},
            {field: 'conversation?.libelle', header: 'Conversation'},
        ];
    }


    public async loadTrajet(){
        this.trajetService.findAll().subscribe(trajets => this.trajets = trajets, error => console.log(error))
    }
    public async loadPassenger(){
        this.passengerService.findAllOptimized().subscribe(passengers => this.passengers = passengers, error => console.log(error))
    }
    public async loadDriver(){
        this.driverService.findAllOptimized().subscribe(drivers => this.drivers = drivers, error => console.log(error))
    }
    public async loadCarteBancaire(){
        this.carteBancaireService.findAll().subscribe(carteBancaires => this.carteBancaires = carteBancaires, error => console.log(error))
    }
    public async loadConversation(){
        this.conversationService.findAllOptimized().subscribe(conversations => this.conversations = conversations, error => console.log(error))
    }


	public initDuplicate(res: ReservationDto) {
	}


    public prepareColumnExport(): void {
        this.service.findByCriteria(this.criteria).subscribe(
            (allItems) =>{
                this.exportData = allItems.map(e => {
					return {
						'Trajet': e.trajet?.id ,
						'Passenger': e.passenger?.email ,
						'Driver': e.driver?.email ,
						'Date reservation': this.datePipe.transform(e.dateReservation , 'dd/MM/yyyy hh:mm'),
						'Montant': e.montant ,
						'Date paiement': this.datePipe.transform(e.datePaiement , 'dd/MM/yyyy hh:mm'),
						'Carte bancaire': e.carteBancaire?.id ,
						'Evaluation': e.evaluation ,
						'Conversation': e.conversation?.libelle ,
					}
				});

            this.criteriaData = [{
            //'Trajet': this.criteria.trajet?.id ? this.criteria.trajet?.id : environment.emptyForExport ,
            //'Passenger': this.criteria.passenger?.email ? this.criteria.passenger?.email : environment.emptyForExport ,
            //'Driver': this.criteria.driver?.email ? this.criteria.driver?.email : environment.emptyForExport ,
                'Date reservation Min': this.criteria.dateReservationFrom ? this.datePipe.transform(this.criteria.dateReservationFrom , this.dateFormat) : environment.emptyForExport ,
                'Date reservation Max': this.criteria.dateReservationTo ? this.datePipe.transform(this.criteria.dateReservationTo , this.dateFormat) : environment.emptyForExport ,
                'Montant Min': this.criteria.montantMin ? this.criteria.montantMin : environment.emptyForExport ,
                'Montant Max': this.criteria.montantMax ? this.criteria.montantMax : environment.emptyForExport ,
                'Date paiement Min': this.criteria.datePaiementFrom ? this.datePipe.transform(this.criteria.datePaiementFrom , this.dateFormat) : environment.emptyForExport ,
                'Date paiement Max': this.criteria.datePaiementTo ? this.datePipe.transform(this.criteria.datePaiementTo , this.dateFormat) : environment.emptyForExport ,
            //'Carte bancaire': this.criteria.carteBancaire?.id ? this.criteria.carteBancaire?.id : environment.emptyForExport ,
                'Evaluation Min': this.criteria.evaluationMin ? this.criteria.evaluationMin : environment.emptyForExport ,
                'Evaluation Max': this.criteria.evaluationMax ? this.criteria.evaluationMax : environment.emptyForExport ,
            //'Conversation': this.criteria.conversation?.libelle ? this.criteria.conversation?.libelle : environment.emptyForExport ,
            }];
			}

        )
    }


    get items(): Array<ReservationDto> {
        return this.service.items;
    }

    set items(value: Array<ReservationDto>) {
        this.service.items = value;
    }

    get selections(): Array<ReservationDto> {
        return this.service.selections;
    }

    set selections(value: Array<ReservationDto>) {
        this.service.selections = value;
    }

    get item(): ReservationDto {
        return this.service.item;
    }

    set item(value: ReservationDto) {
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

    get criteria(): ReservationCriteria {
        return this.service.criteria;
    }

    set criteria(value: ReservationCriteria) {
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
