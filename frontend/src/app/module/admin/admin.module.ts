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

import { LoginAdminComponent } from './login-admin/login-admin.component';
import { RegisterAdminComponent } from './register-admin/register-admin.component';
import { ChangePasswordAdminComponent } from './change-password-admin/change-password-admin.component';
import { ActivationAdminComponent } from './activation-admin/activation-admin.component';
import { ForgetPasswordAdminComponent } from './forget-password-admin/forget-password-admin.component';


import { TrajetAdminModule } from './view/trajet/trajet-admin.module';
import { TrajetAdminRoutingModule } from './view/trajet/trajet-admin-routing.module';
import { VehiculeAdminModule } from './view/vehicule/vehicule-admin.module';
import { VehiculeAdminRoutingModule } from './view/vehicule/vehicule-admin-routing.module';
import { PaiementAdminModule } from './view/paiement/paiement-admin.module';
import { PaiementAdminRoutingModule } from './view/paiement/paiement-admin-routing.module';
import { PassengerAdminModule } from './view/passenger/passenger-admin.module';
import { PassengerAdminRoutingModule } from './view/passenger/passenger-admin-routing.module';
import { DriverAdminModule } from './view/driver/driver-admin.module';
import { DriverAdminRoutingModule } from './view/driver/driver-admin-routing.module';
import { MessageAdminModule } from './view/message/message-admin.module';
import { MessageAdminRoutingModule } from './view/message/message-admin-routing.module';

import {SecurityModule} from 'src/app/module/security/security.module';
import {SecurityRoutingModule} from 'src/app/module/security/security-routing.module';


@NgModule({
  declarations: [
   LoginAdminComponent,
   RegisterAdminComponent,
   ChangePasswordAdminComponent,
   ActivationAdminComponent,
   ForgetPasswordAdminComponent
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
  TrajetAdminModule,
  TrajetAdminRoutingModule,
  VehiculeAdminModule,
  VehiculeAdminRoutingModule,
  PaiementAdminModule,
  PaiementAdminRoutingModule,
  PassengerAdminModule,
  PassengerAdminRoutingModule,
  DriverAdminModule,
  DriverAdminRoutingModule,
  MessageAdminModule,
  MessageAdminRoutingModule,
   SecurityModule,
   SecurityRoutingModule
  ],
  exports: [
    LoginAdminComponent,
    RegisterAdminComponent,
    ChangePasswordAdminComponent,
    ActivationAdminComponent,
    ForgetPasswordAdminComponent,

    TrajetAdminModule,
    VehiculeAdminModule,
    PaiementAdminModule,
    PassengerAdminModule,
    DriverAdminModule,
    MessageAdminModule,
    SecurityModule
  ],

})
export class AdminModule { }
