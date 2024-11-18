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


import {TrajetDriverService} from 'src/app/shared/service/driver/trajet/TrajetDriver.service';
import {TrajetDto} from 'src/app/shared/model/trajet/Trajet.model';
import {TrajetCriteria} from 'src/app/shared/criteria/trajet/TrajetCriteria.model';

import {DriverDto} from 'src/app/shared/model/driver/Driver.model';
import {DriverDriverService} from 'src/app/shared/service/driver/driver/DriverDriver.service';
import {VilleDto} from 'src/app/shared/model/trajet/Ville.model';
import {VilleDriverService} from 'src/app/shared/service/driver/trajet/VilleDriver.service';
@Component({
  selector: 'app-trajet-view-driver',
  templateUrl: './trajet-view-driver.component.html'
})
export class TrajetViewDriverComponent implements OnInit {


	protected _submitted = false;
    protected _errorMessages = new Array<string>();

    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;



    constructor(private service: TrajetDriverService, private driverService: DriverDriverService, private villeService: VilleDriverService){
		this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.stringUtilService = ServiceLocator.injector.get(StringUtilService);
	}

    ngOnInit(): void {
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
    get villeDestination(): VilleDto {
        return this.villeService.item;
    }
    set villeDestination(value: VilleDto) {
        this.villeService.item = value;
    }
    get villeDestinations(): Array<VilleDto> {
        return this.villeService.items;
    }
    set villeDestinations(value: Array<VilleDto>) {
        this.villeService.items = value;
    }
    get localisationDestination(): VilleDto {
        return this.villeService.item;
    }
    set localisationDestination(value: VilleDto) {
        this.villeService.item = value;
    }
    get localisationDestinations(): Array<VilleDto> {
        return this.villeService.items;
    }
    set localisationDestinations(value: Array<VilleDto>) {
        this.villeService.items = value;
    }
    get villeDepart(): VilleDto {
        return this.villeService.item;
    }
    set villeDepart(value: VilleDto) {
        this.villeService.item = value;
    }
    get villeDeparts(): Array<VilleDto> {
        return this.villeService.items;
    }
    set villeDeparts(value: Array<VilleDto>) {
        this.villeService.items = value;
    }
    get localisationSource(): VilleDto {
        return this.villeService.item;
    }
    set localisationSource(value: VilleDto) {
        this.villeService.item = value;
    }
    get localisationSources(): Array<VilleDto> {
        return this.villeService.items;
    }
    set localisationSources(value: Array<VilleDto>) {
        this.villeService.items = value;
    }

    public hideViewDialog() {
        this.viewDialog = false;
    }

    get items(): Array<TrajetDto> {
        return this.service.items;
    }

    set items(value: Array<TrajetDto>) {
        this.service.items = value;
    }

    get item(): TrajetDto {
        return this.service.item;
    }

    set item(value: TrajetDto) {
        this.service.item = value;
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

    get dateFormat(){
        return environment.dateFormatView;
    }

    get dateFormatColumn(){
        return environment.dateFormatList;
    }


}
