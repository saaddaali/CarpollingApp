import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import {ToastModule} from 'primeng/toast';
import {ToolbarModule} from 'primeng/toolbar';
import {TableModule} from 'primeng/table';
import {DropdownModule} from 'primeng/dropdown';
import {InputSwitchModule} from 'primeng/inputswitch';
import {InputTextareaModule} from 'primeng/inputtextarea';

import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {CalendarModule} from 'primeng/calendar';
import {PanelModule} from 'primeng/panel';
import {InputNumberModule} from 'primeng/inputnumber';
import {BadgeModule} from 'primeng/badge';
import { MultiSelectModule } from 'primeng/multiselect';

import { PasswordModule } from 'primeng/password';
import { InputTextModule } from 'primeng/inputtext';
import {ButtonModule} from 'primeng/button';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {TabViewModule} from 'primeng/tabview';
import { SplitButtonModule } from 'primeng/splitbutton';
import { MessageModule } from 'primeng/message';
import {MessagesModule} from 'primeng/messages';

import { LoginDriverComponent } from './login-driver/login-driver.component';
import { RegisterDriverComponent } from './register-driver/register-driver.component';
import { ChangePasswordDriverComponent } from './change-password-driver/change-password-driver.component';
import { ActivationDriverComponent } from './activation-driver/activation-driver.component';
import { ForgetPasswordDriverComponent } from './forget-password-driver/forget-password-driver.component';


import { TrajetDriverModule } from './view/trajet/trajet-driver.module';
import { TrajetDriverRoutingModule } from './view/trajet/trajet-driver-routing.module';
import { VehiculeDriverModule } from './view/vehicule/vehicule-driver.module';
import { VehiculeDriverRoutingModule } from './view/vehicule/vehicule-driver-routing.module';
import { PaiementDriverModule } from './view/paiement/paiement-driver.module';
import { PaiementDriverRoutingModule } from './view/paiement/paiement-driver-routing.module';
import { PassengerDriverModule } from './view/passenger/passenger-driver.module';
import { PassengerDriverRoutingModule } from './view/passenger/passenger-driver-routing.module';
import { DriverDriverModule } from './view/driver/driver-driver.module';
import { DriverDriverRoutingModule } from './view/driver/driver-driver-routing.module';
import { MessageDriverModule } from './view/message/message-driver.module';
import { MessageDriverRoutingModule } from './view/message/message-driver-routing.module';
import { ReservationDriverModule } from './view/reservation/reservation-driver.module';
import { ReservationDriverRoutingModule } from './view/reservation/reservation-driver-routing.module';

import {SecurityModule} from 'src/app/module/security/security.module';
import {SecurityRoutingModule} from 'src/app/module/security/security-routing.module';


@NgModule({
  declarations: [
   LoginDriverComponent,
   RegisterDriverComponent,
   ChangePasswordDriverComponent,
   ActivationDriverComponent,
   ForgetPasswordDriverComponent
  ],
  imports: [
    CommonModule,
    ToastModule,
    ToolbarModule,
    TableModule,
    ConfirmDialogModule,
    DialogModule,
    PasswordModule,
    InputTextModule,
    ButtonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    SplitButtonModule,
    BrowserAnimationsModule,
    DropdownModule,
    TabViewModule,
    InputSwitchModule,
    InputTextareaModule,
    CalendarModule,
    PanelModule,
    MessageModule,
    MessagesModule,
    InputNumberModule,
    BadgeModule,
    MultiSelectModule,
  TrajetDriverModule,
  TrajetDriverRoutingModule,
  VehiculeDriverModule,
  VehiculeDriverRoutingModule,
  PaiementDriverModule,
  PaiementDriverRoutingModule,
  PassengerDriverModule,
  PassengerDriverRoutingModule,
  DriverDriverModule,
  DriverDriverRoutingModule,
  MessageDriverModule,
  MessageDriverRoutingModule,
  ReservationDriverModule,
  ReservationDriverRoutingModule,
   SecurityModule,
   SecurityRoutingModule
  ],
  exports: [
    LoginDriverComponent,
    RegisterDriverComponent,
    ChangePasswordDriverComponent,
    ActivationDriverComponent,
    ForgetPasswordDriverComponent,

    TrajetDriverModule,
    VehiculeDriverModule,
    PaiementDriverModule,
    PassengerDriverModule,
    DriverDriverModule,
    MessageDriverModule,
    ReservationDriverModule,
    SecurityModule
  ],

})
export class DriverModule { }
