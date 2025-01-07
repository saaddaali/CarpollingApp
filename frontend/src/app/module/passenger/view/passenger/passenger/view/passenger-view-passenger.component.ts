import {ChangeDetectorRef, Component, OnInit} from '@angular/core';


import {DatePipe} from '@angular/common';
import {Router} from '@angular/router';


import {environment} from 'src/environments/environment';

import {RoleService} from 'src/app/zynerator/security/shared/service/Role.service';
import {StringUtilService} from 'src/app/zynerator/util/StringUtil.service';
import {ServiceLocator} from 'src/app/zynerator/service/ServiceLocator';
import {ConfirmationService, MessageService} from 'primeng/api';


import {PassengerPassengerService} from 'src/app/shared/service/passenger/passenger/PassengerPassenger.service';
import {PassengerDto} from 'src/app/shared/model/passenger/Passenger.model';
import {PassengerCriteria} from 'src/app/shared/criteria/passenger/PassengerCriteria.model';

import {CarteBancaireDto} from 'src/app/shared/model/paiement/CarteBancaire.model';
import {CarteBancairePassengerService} from 'src/app/shared/service/passenger/paiement/CarteBancairePassenger.service';
import {AuthService} from "../../../../../../zynerator/security/shared/service/Auth.service";

@Component({
  selector: 'app-passenger-view-passenger',
  templateUrl: './passenger-view-passenger.component.html',
    styleUrls: ['./passenger-view-passenger.component.scss']
})
export class PassengerViewPassengerComponent implements OnInit {

    // Ajout au début de la classe
    public editMode = false;
    private originalItem: PassengerDto;

	protected _submitted = false;
    protected _errorMessages = new Array<string>();

    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;

    selectedLanguage: string;
    isDriver= false;

    public toggleRole() {
        this.isDriver = !this.isDriver;

        const route = this.isDriver
            ? 'app/passenger/trajet/trajets'
            : 'app/passenger';

        this.router.navigate([route]).then(() => {
            // Force Angular to detect changes
            this.cd.detectChanges();
        });
    }



    constructor(private service: PassengerPassengerService, private carteBancaireService: CarteBancairePassengerService, protected authService: AuthService, private cd: ChangeDetectorRef){
		this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.stringUtilService = ServiceLocator.injector.get(StringUtilService);
	}

    ngOnInit(): void {
        // Charger les données de l'utilisateur connecté
        this.loadCurrentUser();
    }

    public logout(): void {
        try {
            this.authService.logout();
            this.messageService.add({
                severity: 'success',
                summary: 'Déconnexion',
                detail: 'Vous avez été déconnecté avec succès'
            });
        } catch (error) {
            console.error('Erreur lors de la déconnexion:', error);
            this.messageService.add({
                severity: 'error',
                summary: 'Erreur',
                detail: 'Une erreur est survenue lors de la déconnexion'
            });
            this.router.navigate(['/login']);
        }
    }
    public loadCurrentUser(): void {
        this.service.findCurrentUser().subscribe(
            data => {
                this.item = data;
                this.originalItem = JSON.parse(JSON.stringify(data));
            },
            error => {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Erreur',
                    detail: 'Impossible de charger les informations de l\'utilisateur'
                });
            }
        );
    }

    edit() {
        this.service.edit().subscribe(
            data => {
                this.item = data;
                this.messageService.add({
                    severity: 'success',
                    summary: 'Succès',
                    detail: 'Profile mis à jour'
                });
                this.editMode = false;
            },
            error => {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Erreur',
                    detail: 'Erreur lors de la mise à jour'
                });
            }
        );
    }


    toggleEdit() {
        if (this.editMode && this.hasChanges()) {

            this.confirmationService.confirm({
                message: 'Voulez-vous sauvegarder les modifications?',
                accept: () => {
                    this.edit();

                },
                reject: () => {
                    this.loadCurrentUser();
                    this.editMode = false;
                }
            });
        } else {
            this.editMode = !this.editMode;
        }
    }

    editt(dto: PassengerDto) {
        this.service.findByIdWithAssociatedList(dto).subscribe(res => {
            this.item = res;
            console.log(res);
            this.editDialog = true;
        });

    }

    // Ajouter juste après les autres méthodes, avant les getters/setters
    onRatingChange(newRating: number): void {
        if (this.editMode) {
            this.item.evaluation = newRating;
            this.service.edit().subscribe({
                next: () => {
                    this.loadCurrentUser();
                    this.messageService.add({
                        severity: 'success',
                        summary: 'Succès',
                        detail: 'Évaluation mise à jour'
                    });
                },
                error: () => {
                    this.messageService.add({
                        severity: 'error',
                        summary: 'Erreur',
                        detail: 'Erreur lors de la mise à jour'
                    });
                }
            });
        }
    }

    set createActionIsValid(value: boolean) {
        this.service.createActionIsValid = value;
    }


    get editActionIsValid(): boolean {
        return this.service.editActionIsValid;
    }

    get editDialog(): boolean {
        return this.service.editDialog;
    }

    set editDialog(value: boolean) {
        this.service.editDialog = value;
    }


    onFileSelected(event: any) {
        const file = event.target.files[0];
        if (file) {
            // Convertir l'image en base64
            const reader = new FileReader();
            reader.onload = (e: any) => {
                this.item.photo = e.target.result;
                this.service.edit().subscribe({
                    next: () => {
                        this.loadCurrentUser();
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Succès',
                            detail: 'Photo mise à jour'
                        });
                    },
                    error: () => {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Erreur',
                            detail: 'Échec de la mise à jour'
                        });
                    }
                });
            };
            reader.readAsDataURL(file);
        }
    }

    deletePhoto() {
        this.service.delete(this.item).subscribe({
            next: () => {
                this.item.photo = null;
                this.messageService.add({
                    severity: 'success',
                    summary: 'Succès',
                    detail: 'Photo supprimée'
                });
            },
            error: (error) => {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Erreur',
                    detail: 'Erreur lors de la suppression'
                });
            }
        });
    }

    private hasChanges(): boolean {
        return JSON.stringify(this.item) !== JSON.stringify(this.originalItem);
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

    public hideViewDialog() {
        this.viewDialog = false;
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

    get viewDialog(): boolean {
        return this.service.viewDialog;
    }

    set viewDialog(value: boolean) {
        this.service.viewDialog = value;
    }

    get criteria(): PassengerCriteria {
        return this.service.criteria;
    }

    set criteria(value: PassengerCriteria) {
        this.service.criteria = value;
    }

    get dateFormat(){
        return environment.dateFormatView;
    }

    get dateFormatColumn(){
        return environment.dateFormatList;
    }


}
