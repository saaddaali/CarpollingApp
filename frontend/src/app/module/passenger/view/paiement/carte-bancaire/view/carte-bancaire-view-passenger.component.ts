import {Component, OnInit} from '@angular/core';


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
import {ConfirmationService, MessageService,MenuItem} from 'primeng/api';
import {FileTempDto} from 'src/app/zynerator/dto/FileTempDto.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';


import {CarteBancairePassengerService} from 'src/app/shared/service/passenger/paiement/CarteBancairePassenger.service';
import {CarteBancaireDto} from 'src/app/shared/model/paiement/CarteBancaire.model';
import {CarteBancaireCriteria} from 'src/app/shared/criteria/paiement/CarteBancaireCriteria.model';

@Component({
  selector: 'app-carte-bancaire-view-passenger',
  templateUrl: './carte-bancaire-view-passenger.component.html'
})
export class CarteBancaireViewPassengerComponent implements OnInit {


	protected _submitted = false;
    protected _errorMessages = new Array<string>();

    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;



    constructor(private service: CarteBancairePassengerService, private fb: FormBuilder,){
		this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.stringUtilService = ServiceLocator.injector.get(StringUtilService);
        this.initializeForms();
	}

    ngOnInit(): void {
    }


    bookingFor: 'self' | 'other' = 'other';
    bookingForm: FormGroup;
    paymentForm: FormGroup;
    savedCards = [
        { type: 'Visa', number: '3679', expiry: '12/24' },
        { type: 'MasterCard', number: '2193', expiry: '03/22' }
    ];


// Ajoutez cette méthode
    private initializeForms() {
        this.bookingForm = this.fb.group({
            fullName: ['', Validators.required],
            age: ['', [Validators.required, Validators.min(0)]],
            phoneNumber: ['', Validators.required],
            notes: ['']
        });

        this.paymentForm = this.fb.group({
            paymentMethod: ['new', Validators.required],
            cardNumber: ['', [Validators.required, Validators.pattern('^[0-9]{16}$')]],
            cardholderName: ['', Validators.required],
            expiryDate: ['', [Validators.required, Validators.pattern('^(0[1-9]|1[0-2])\/([0-9]{2})$')]],
            cvc: ['', [Validators.required, Validators.pattern('^[0-9]{3,4}$')]],
            saveCard: [false]
        });
    }

// Modifiez votre méthode onSubmit existante
    public onSubmit() {
        if (this.bookingForm.valid && this.paymentForm.valid) {
            const paymentData = {
                ...this.paymentForm.value,
                bookingDetails: this.bookingForm.value
            };

            // Utilisez votre service existant pour traiter le paiement
            this.messageService.add({
                severity: 'success',
                summary: 'Succès',
                detail: 'Paiement traité avec succès'
            });
            this.hideViewDialog();
        } else {
            this.messageService.add({
                severity: 'error',
                summary: 'Erreur',
                detail: 'Veuillez remplir tous les champs requis'
            });
        }
    }

// Ajoutez cette méthode pour la navigation
    public goBack() {
        this.router.navigate(['/previous-page']);
    }

    public hideViewDialog() {
        this.viewDialog = false;
    }

    get items(): Array<CarteBancaireDto> {
        return this.service.items;
    }

    set items(value: Array<CarteBancaireDto>) {
        this.service.items = value;
    }

    get item(): CarteBancaireDto {
        return this.service.item;
    }

    set item(value: CarteBancaireDto) {
        this.service.item = value;
    }

    get viewDialog(): boolean {
        return this.service.viewDialog;
    }

    set viewDialog(value: boolean) {
        this.service.viewDialog = value;
    }

    get criteria(): CarteBancaireCriteria {
        return this.service.criteria;
    }

    set criteria(value: CarteBancaireCriteria) {
        this.service.criteria = value;
    }

    get dateFormat(){
        return environment.dateFormatView;
    }

    get dateFormatColumn(){
        return environment.dateFormatList;
    }


}
