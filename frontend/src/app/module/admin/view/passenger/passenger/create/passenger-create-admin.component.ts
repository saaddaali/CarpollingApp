import {Component, OnInit, Input} from '@angular/core';
import {ConfirmationService, MessageService} from 'primeng/api';

import {DatePipe} from '@angular/common';
import {Router} from '@angular/router';
import {Inject, Injectable, PLATFORM_ID} from '@angular/core';


import {environment} from 'src/environments/environment';

import {RoleService} from 'src/app/zynerator/security/shared/service/Role.service';
import {StringUtilService} from 'src/app/zynerator/util/StringUtil.service';
import {ServiceLocator} from 'src/app/zynerator/service/ServiceLocator';




import {PassengerAdminService} from 'src/app/shared/service/admin/passenger/PassengerAdmin.service';
import {PassengerDto} from 'src/app/shared/model/passenger/Passenger.model';
import {PassengerCriteria} from 'src/app/shared/criteria/passenger/PassengerCriteria.model';
import {CarteBancaireDto} from 'src/app/shared/model/paiement/CarteBancaire.model';
import {CarteBancaireAdminService} from 'src/app/shared/service/admin/paiement/CarteBancaireAdmin.service';
@Component({
  selector: 'app-passenger-create-admin',
  templateUrl: './passenger-create-admin.component.html'
})
export class PassengerCreateAdminComponent  implements OnInit {

	protected _submitted = false;
    protected _errorMessages = new Array<string>();

    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;
    private _activeTab = 0;




	constructor(private service: PassengerAdminService , private carteBancaireService: CarteBancaireAdminService, @Inject(PLATFORM_ID) private platformId? ) {
        this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.stringUtilService = ServiceLocator.injector.get(StringUtilService);
    }

    ngOnInit(): void {
        this.carteBancaireService.findAll().subscribe((data) => this.carteBancaires = data);
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
                this.item = new PassengerDto();
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






    get items(): Array<PassengerDto> {
        return this.service.items;
    }

    set items(value: Array<PassengerDto>) {
        this.service.items = value;
    }

    get item(): PassengerDto {
        return this.service.item;
    }

    set item(value: PassengerDto) {
        this.service.item = value;
    }

    get createDialog(): boolean {
        return this.service.createDialog;
    }

    set createDialog(value: boolean) {
        this.service.createDialog = value;
    }

    get criteria(): PassengerCriteria {
        return this.service.criteria;
    }

    set criteria(value: PassengerCriteria) {
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
