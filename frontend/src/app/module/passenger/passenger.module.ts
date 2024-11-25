import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { TableModule } from 'primeng/table';
import { DropdownModule } from 'primeng/dropdown';
import { InputSwitchModule } from 'primeng/inputswitch';
import { InputTextareaModule } from 'primeng/inputtextarea';

import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CalendarModule } from 'primeng/calendar';
import { PanelModule } from 'primeng/panel';
import { InputNumberModule } from 'primeng/inputnumber';
import { BadgeModule } from 'primeng/badge';
import { MultiSelectModule } from 'primeng/multiselect';

import { PasswordModule } from 'primeng/password';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TabViewModule } from 'primeng/tabview';
import { SplitButtonModule } from 'primeng/splitbutton';
import { MessageModule } from 'primeng/message';
import { MessagesModule } from 'primeng/messages';

import { LoginPassengerComponent } from './login-passenger/login-passenger.component';
import { RegisterPassengerComponent } from './register-passenger/register-passenger.component';
import { ChangePasswordPassengerComponent } from './change-password-passenger/change-password-passenger.component';
import { ActivationPassengerComponent } from './activation-passenger/activation-passenger.component';
import { ForgetPasswordPassengerComponent } from './forget-password-passenger/forget-password-passenger.component';

import { TrajetPassengerModule } from './view/trajet/trajet-passenger.module';
import { TrajetPassengerRoutingModule } from './view/trajet/trajet-passenger-routing.module';
import { VehiculePassengerModule } from './view/vehicule/vehicule-passenger.module';
import { VehiculePassengerRoutingModule } from './view/vehicule/vehicule-passenger-routing.module';
import { PaiementPassengerModule } from './view/paiement/paiement-passenger.module';
import { PaiementPassengerRoutingModule } from './view/paiement/paiement-passenger-routing.module';
import { PassengerPassengerModule } from './view/passenger/passenger-passenger.module';
import { PassengerPassengerRoutingModule } from './view/passenger/passenger-passenger-routing.module';
import { DriverPassengerModule } from './view/driver/driver-passenger.module';
import { DriverPassengerRoutingModule } from './view/driver/driver-passenger-routing.module';
import { MessagePassengerModule } from './view/message/message-passenger.module';
import { MessagePassengerRoutingModule } from './view/message/message-passenger-routing.module';
import { ReservationPassengerModule } from './view/reservation/reservation-passenger.module';
import { ReservationPassengerRoutingModule } from './view/reservation/reservation-passenger-routing.module';

<<<<<<< HEAD
import { SecurityModule } from 'src/app/module/security/security.module';
import { SecurityRoutingModule } from 'src/app/module/security/security-routing.module';
import { HomePassengerComponent } from './view/home/home-passenger.component';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { PassengerLayoutModule } from '../../layout/passenger/passenger.layout.module';
import { AdminLayoutModule } from '../../layout/admin/admin.layout.module';
import { SearchBarPassengerComponent } from './view/SearchBar/searchBar-passenger.component';

@NgModule({
    declarations: [
        LoginPassengerComponent,
        RegisterPassengerComponent,
        ChangePasswordPassengerComponent,
        ActivationPassengerComponent,
        HomePassengerComponent,
        ForgetPasswordPassengerComponent,
        SearchBarPassengerComponent,
    ],
=======
import {SecurityModule} from 'src/app/module/security/security.module';
import {SecurityRoutingModule} from 'src/app/module/security/security-routing.module';
import {HomePassengerComponent} from "./view/home/home-passenger.component";
import {AutoCompleteModule} from "primeng/autocomplete";
import {PassengerLayoutModule} from "../../layout/passenger/passenger.layout.module";
import {AdminLayoutModule} from "../../layout/admin/admin.layout.module";
import {SearchBarPassengerComponent} from "./view/SearchBar/searchBar-passenger.component";
import {TopBarComponent} from "./view/topBar/topBar.component";


@NgModule({
  declarations: [
   LoginPassengerComponent,
   RegisterPassengerComponent,
   ChangePasswordPassengerComponent,
   ActivationPassengerComponent,
   HomePassengerComponent,
   ForgetPasswordPassengerComponent,
      SearchBarPassengerComponent,
      TopBarComponent
  ],
>>>>>>> bd835fd (Add home and list and view in trajet passenger and add search in compo)
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
        TrajetPassengerModule,
        TrajetPassengerRoutingModule,
        VehiculePassengerModule,
        VehiculePassengerRoutingModule,
        PaiementPassengerModule,
        PaiementPassengerRoutingModule,
        PassengerPassengerModule,
        PassengerPassengerRoutingModule,
        DriverPassengerModule,
        DriverPassengerRoutingModule,
        MessagePassengerModule,
        MessagePassengerRoutingModule,
        ReservationPassengerModule,
        ReservationPassengerRoutingModule,
        SecurityModule,
        SecurityRoutingModule,
        AutoCompleteModule,
        PassengerLayoutModule,
        AdminLayoutModule,
    ],
    exports: [
        LoginPassengerComponent,
        RegisterPassengerComponent,
        ChangePasswordPassengerComponent,
        ActivationPassengerComponent,
        ForgetPasswordPassengerComponent,

        TrajetPassengerModule,
        VehiculePassengerModule,
        PaiementPassengerModule,
        PassengerPassengerModule,
        DriverPassengerModule,
        MessagePassengerModule,
        ReservationPassengerModule,
        HomePassengerComponent,
        SecurityModule,
    ],
})
export class PassengerModule {}
