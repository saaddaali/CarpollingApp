import {Component, OnInit, Input} from '@angular/core';
import {ConfirmationService, MessageService} from 'primeng/api';
import {FileTempDto} from 'src/app/zynerator/dto/FileTempDto.model';
import {DatePipe} from '@angular/common';
import {Router} from '@angular/router';
import {Inject, Injectable, PLATFORM_ID} from '@angular/core';

import {environment} from 'src/environments/environment';

import {RoleService} from 'src/app/zynerator/security/shared/service/Role.service';
import {StringUtilService} from 'src/app/zynerator/util/StringUtil.service';
import {ServiceLocator} from 'src/app/zynerator/service/ServiceLocator';


import {DriverPassengerService} from 'src/app/shared/service/passenger/driver/DriverPassenger.service';
import {DriverDto} from 'src/app/shared/model/driver/Driver.model';
import {DriverCriteria} from 'src/app/shared/criteria/driver/DriverCriteria.model';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";


@Component({
    selector: 'app-driver-edit-passenger',
    styleUrls: ['./driver-edit-passenger.component.scss'],
    templateUrl: './driver-edit-passenger.component.html'
})
export class DriverEditPassengerComponent implements OnInit {

    protected _submitted = false;
    protected _errorMessages = new Array<string>();


    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;
    private _activeTab = 0;
    private _file: any;
    private _files: any;


    verificationForm: FormGroup;
    selectedFile: File | null = null;
    uploadedFiles: any[] = [];


    constructor(private service: DriverPassengerService, private fb: FormBuilder,@Inject(PLATFORM_ID) private platformId? ) {
        this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.stringUtilService = ServiceLocator.injector.get(StringUtilService);

        this.verificationForm = this.fb.group({
            idDocument: [null, Validators.required]
        });
    }

    ngOnInit(): void {

    }




    onUpload(event: any) {
        for(let file of event.files) {
            this.uploadedFiles.push(file);
        }
    }


    onSelect(event: any) {
        this.selectedFile = event.files[0];  // Take only the first file since multiple is false
        this.uploadedFiles = event.files;
    }

    verifyDocument(): void {
        if (this.selectedFile) {
            const formData = new FormData();
            formData.append('cinImage', this.selectedFile);
            formData.append('fullName', 'Saad Daali');

            this.service.verifyDriver(formData).subscribe({
                next: (response: any) => {
                    console.log('Verification successful:', response);
                    // Show success message using your message service (e.g., PrimeNG Toast)
                    this.messageService.add({
                        severity: 'success',
                        summary: 'Success',
                        detail: 'Document verified successfully'
                    });
                   // this.verificationComplete = true;
                },
                error: (error) => {
                    console.error('Verification failed:', error);
                    // Show error message
                    this.messageService.add({
                        severity: 'error',
                        summary: 'Error',
                        detail: 'Failed to verify document. Please try again.'
                    });
                },
                complete: () => {
                    // Optional: Clean up or reset form
                    this.selectedFile = null;
                    this.verificationForm.reset();
                }
            });
        } else {
            // Show validation error message
            this.messageService.add({
                severity: 'warn',
                summary: 'Warning',
                detail: 'Please fill in all required fields and select a document'
            });
        }
    }

    public edit(): void {
        this.submitted = true;
        //this.prepareEdit();
        this.validateForm();
        if (this.errorMessages.length === 0) {
            this.editWithShowOption(false);
        } else {
            this.messageService.add({
                severity: 'error',
                summary: 'Erreurs',
                detail: 'Merci de corrigé les erreurs sur le formulaire'
            });
        }
    }

    public editWithShowOption(showList: boolean) {
        this.service.edit().subscribe(religion => {
            const myIndex = this.items.findIndex(e => e.id === this.item.id);
            this.items[myIndex] = religion;
            this.editDialog = false;
            this.submitted = false;
            this.item = new DriverDto();
        }, error => {
            console.log(error);
        });
    }

    public hideEditDialog() {
        this.editDialog = false;
        this.setValidation(true);
    }


    public setValidation(value: boolean) {
    }


    public validateForm(): void {
        this.errorMessages = new Array<string>();
    }


    get items(): Array<DriverDto> {
        return this.service.items;
    }

    set items(value: Array<DriverDto>) {
        this.service.items = value;
    }

    get item(): DriverDto {
        return this.service.item;
    }

    set item(value: DriverDto) {
        this.service.item = value;
    }

    get editDialog(): boolean {
        return this.service.editDialog;
    }

    set editDialog(value: boolean) {
        this.service.editDialog = value;
    }

    get criteria(): DriverCriteria {
        return this.service.criteria;
    }

    set criteria(value: DriverCriteria) {
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


    protected readonly document = document;
}
