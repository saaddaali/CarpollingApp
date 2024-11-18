import {Component, OnInit, Input} from '@angular/core';
import {ConfirmationService, MessageService} from 'primeng/api';

import {DatePipe} from '@angular/common';
import {Router} from '@angular/router';
import {Inject, Injectable, PLATFORM_ID} from '@angular/core';


import {environment} from 'src/environments/environment';

import {RoleService} from 'src/app/zynerator/security/shared/service/Role.service';
import {StringUtilService} from 'src/app/zynerator/util/StringUtil.service';
import {ServiceLocator} from 'src/app/zynerator/service/ServiceLocator';




import {ReservationDriverService} from 'src/app/shared/service/driver/reservation/ReservationDriver.service';
import {ReservationDto} from 'src/app/shared/model/reservation/Reservation.model';
import {ReservationCriteria} from 'src/app/shared/criteria/reservation/ReservationCriteria.model';
import {DriverDto} from 'src/app/shared/model/driver/Driver.model';
import {DriverDriverService} from 'src/app/shared/service/driver/driver/DriverDriver.service';
import {PassengerDto} from 'src/app/shared/model/passenger/Passenger.model';
import {PassengerDriverService} from 'src/app/shared/service/driver/passenger/PassengerDriver.service';
import {CarteBancaireDto} from 'src/app/shared/model/paiement/CarteBancaire.model';
import {CarteBancaireDriverService} from 'src/app/shared/service/driver/paiement/CarteBancaireDriver.service';
import {ConversationDto} from 'src/app/shared/model/message/Conversation.model';
import {ConversationDriverService} from 'src/app/shared/service/driver/message/ConversationDriver.service';
import {TrajetDto} from 'src/app/shared/model/trajet/Trajet.model';
import {TrajetDriverService} from 'src/app/shared/service/driver/trajet/TrajetDriver.service';
@Component({
  selector: 'app-reservation-create-driver',
  templateUrl: './reservation-create-driver.component.html'
})
export class ReservationCreateDriverComponent  implements OnInit {

	protected _submitted = false;
    protected _errorMessages = new Array<string>();

    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;
    private _activeTab = 0;




	constructor(private service: ReservationDriverService , private driverService: DriverDriverService, private passengerService: PassengerDriverService, private carteBancaireService: CarteBancaireDriverService, private conversationService: ConversationDriverService, private trajetService: TrajetDriverService, @Inject(PLATFORM_ID) private platformId? ) {
        this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.stringUtilService = ServiceLocator.injector.get(StringUtilService);
    }

    ngOnInit(): void {
        this.trajetService.findAll().subscribe((data) => this.trajets = data);
        this.passengerService.findAll().subscribe((data) => this.passengers = data);
        this.driverService.findAll().subscribe((data) => this.drivers = data);
        this.carteBancaireService.findAll().subscribe((data) => this.carteBancaires = data);
        this.conversationService.findAll().subscribe((data) => this.conversations = data);
    }



    public save(): void {
        this.submitted = true;
        this.validateForm();
        if (this.errorMessages.length === 0) {
            this.saveWithShowOption(false);
        } else {
            this.messageService.add({severity: 'error',summary: 'Erreurs',detail: 'Merci de corrigé les erreurs sur le formulaire'});
        }
    }

    public saveWithShowOption(showList: boolean) {
        this.service.save().subscribe(item => {
            if (item != null) {
                this.items.push({...item});
                this.createDialog = false;
                this.submitted = false;
                this.item = new ReservationDto();
            } else {
                this.messageService.add({severity: 'error', summary: 'Erreurs', detail: 'Element existant'});
            }

        }, error => {
            console.log(error);
        });
    }


    public hideCreateDialog() {
        this.createDialog = false;
        this.setValidation(true);
    }





    public  setValidation(value: boolean){
    }



    public  validateForm(): void{
        this.errorMessages = new Array<string>();
    }




    get carteBancaire(): CarteBancaireDto {
        return this.carteBancaireService.item;
    }
    set carteBancaire(value: CarteBancaireDto) {
        this.carteBancaireService.item = value;
    }
    get carteBancaires(): Array<CarteBancaireDto> {
        return this.carteBancaireService.items;
    }
    set carteBancaires(value: Array<CarteBancaireDto>) {
        this.carteBancaireService.items = value;
    }
    get createCarteBancaireDialog(): boolean {
        return this.carteBancaireService.createDialog;
    }
    set createCarteBancaireDialog(value: boolean) {
        this.carteBancaireService.createDialog= value;
    }
    get conversation(): ConversationDto {
        return this.conversationService.item;
    }
    set conversation(value: ConversationDto) {
        this.conversationService.item = value;
    }
    get conversations(): Array<ConversationDto> {
        return this.conversationService.items;
    }
    set conversations(value: Array<ConversationDto>) {
        this.conversationService.items = value;
    }
    get createConversationDialog(): boolean {
        return this.conversationService.createDialog;
    }
    set createConversationDialog(value: boolean) {
        this.conversationService.createDialog= value;
    }
    get driver(): DriverDto {
        return this.driverService.item;
    }
    set driver(value: DriverDto) {
        this.driverService.item = value;
    }
    get drivers(): Array<DriverDto> {
        return this.driverService.items;
    }
    set drivers(value: Array<DriverDto>) {
        this.driverService.items = value;
    }
    get createDriverDialog(): boolean {
        return this.driverService.createDialog;
    }
    set createDriverDialog(value: boolean) {
        this.driverService.createDialog= value;
    }
    get passenger(): PassengerDto {
        return this.passengerService.item;
    }
    set passenger(value: PassengerDto) {
        this.passengerService.item = value;
    }
    get passengers(): Array<PassengerDto> {
        return this.passengerService.items;
    }
    set passengers(value: Array<PassengerDto>) {
        this.passengerService.items = value;
    }
    get createPassengerDialog(): boolean {
        return this.passengerService.createDialog;
    }
    set createPassengerDialog(value: boolean) {
        this.passengerService.createDialog= value;
    }
    get trajet(): TrajetDto {
        return this.trajetService.item;
    }
    set trajet(value: TrajetDto) {
        this.trajetService.item = value;
    }
    get trajets(): Array<TrajetDto> {
        return this.trajetService.items;
    }
    set trajets(value: Array<TrajetDto>) {
        this.trajetService.items = value;
    }
    get createTrajetDialog(): boolean {
        return this.trajetService.createDialog;
    }
    set createTrajetDialog(value: boolean) {
        this.trajetService.createDialog= value;
    }






    get items(): Array<ReservationDto> {
        return this.service.items;
    }

    set items(value: Array<ReservationDto>) {
        this.service.items = value;
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

    get criteria(): ReservationCriteria {
        return this.service.criteria;
    }

    set criteria(value: ReservationCriteria) {
        this.service.criteria = value;
    }

    get dateFormat() {
        return environment.dateFormatCreate;
    }

    get dateFormatColumn() {
        return environment.dateFormatCreate;
    }

    get submitted(): boolean {
        return this._submitted;
    }

    set submitted(value: boolean) {
        this._submitted = value;
    }

    get errorMessages(): string[] {
        if (this._errorMessages == null) {
            this._errorMessages = new Array<string>();
        }
        return this._errorMessages;
    }

    set errorMessages(value: string[]) {
        this._errorMessages = value;
    }

    get validate(): boolean {
        return this.service.validate;
    }

    set validate(value: boolean) {
        this.service.validate = value;
    }


    get activeTab(): number {
        return this._activeTab;
    }

    set activeTab(value: number) {
        this._activeTab = value;
    }

}
