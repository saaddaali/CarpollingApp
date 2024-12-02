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
import {ConfirmationService, MessageService, MenuItem} from 'primeng/api';
import {FileTempDto} from 'src/app/zynerator/dto/FileTempDto.model';


import {ConversationPassengerService} from 'src/app/shared/service/passenger/message/ConversationPassenger.service';
import {ConversationDto} from 'src/app/shared/model/message/Conversation.model';
import {ConversationCriteria} from 'src/app/shared/criteria/message/ConversationCriteria.model';

import {DriverDto} from 'src/app/shared/model/driver/Driver.model';
import {DriverPassengerService} from 'src/app/shared/service/passenger/driver/DriverPassenger.service';
import {PassengerDto} from 'src/app/shared/model/passenger/Passenger.model';
import {PassengerPassengerService} from 'src/app/shared/service/passenger/passenger/PassengerPassenger.service';

@Component({
    selector: 'app-conversation-view-passenger',
    templateUrl: './conversation-view-passenger.component.html',
    styleUrls: ['./conversation-view-passenger.component.css']
})
export class ConversationViewPassengerComponent implements OnInit {


    protected _submitted = false;
    protected _errorMessages = new Array<string>();

    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;

    protected _totalRecords = 0;


    constructor(private service: ConversationPassengerService, private driverService: DriverPassengerService, private passengerService: PassengerPassengerService) {
        this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.stringUtilService = ServiceLocator.injector.get(StringUtilService);
    }

    ngOnInit(): void {
        this.findPaginatedByCriteria();
    }


    messages: Array<{ text: string, sender: string, time: string }> = [];
    showDate = false;
    currentDate = '';

    sendMessage(input: HTMLInputElement) {
        const userMessage = input.value.trim();
        if (userMessage) {
            const currentTime = this.formatTime(new Date());
            this.messages.push({ text: userMessage, sender: 'user', time: currentTime });
            input.value = '';

            // Simulate bot response
            setTimeout(() => {
                const botTime = this.formatTime(new Date());
                this.messages.push({ text: 'This is a bot response!', sender: 'bot', time: botTime });
            }, 1000);

            // Determine if date should be displayed
            this.updateDate();
        }
    }

    public findPaginatedByCriteria() {
        this.service.findPaginatedByCriteria(this.criteria).subscribe(paginatedItems => {
            this.items = paginatedItems.list;
            this.totalRecords = paginatedItems.dataSize;
            this.selections = new Array<ConversationDto>();
        }, error => console.log(error));
    }

    formatTime(date: Date): string {
        const hours = date.getHours().toString().padStart(2, '0');
        const minutes = date.getMinutes().toString().padStart(2, '0');
        return `${hours}:${minutes}`;
    }

    updateDate() {
        const today = new Date().toDateString();
        const lastMessageDate = new Date(this.messages[this.messages.length - 1]?.time).toDateString();

        if (today !== lastMessageDate) {
            this.showDate = true;
            this.currentDate = new Date().toLocaleDateString(); // Format: "MM/DD/YYYY"
        } else {
            this.showDate = false;
        }
    }

    closeChat() {
        const chatContainer = document.querySelector('.chat-container');
        if (chatContainer) {
            chatContainer.classList.add('hidden'); // Hide the chat when closing
        }
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

    public hideViewDialog() {
        this.viewDialog = false;
    }

    get items(): Array<ConversationDto> {
        return this.service.items;
    }

    set items(value: Array<ConversationDto>) {
        this.service.items = value;
    }

    get item(): ConversationDto {
        return this.service.item;
    }

    set item(value: ConversationDto) {
        this.service.item = value;
    }

    get viewDialog(): boolean {
        return this.service.viewDialog;
    }

    set viewDialog(value: boolean) {
        this.service.viewDialog = value;
    }

    get criteria(): ConversationCriteria {
        return this.service.criteria;
    }

    set criteria(value: ConversationCriteria) {
        this.service.criteria = value;
    }

    get dateFormat() {
        return environment.dateFormatView;
    }

    get dateFormatColumn() {
        return environment.dateFormatList;
    }


    get totalRecords(): number {
        return this._totalRecords;
    }

    set totalRecords(value: number) {
        this._totalRecords = value;
    }

    get selections(): Array<ConversationDto> {
        return this.service.selections;
    }

    set selections(value: Array<ConversationDto>) {
        this.service.selections = value;
    }


}
