import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {InputTextModule} from 'primeng/inputtext';
import {SidebarModule} from 'primeng/sidebar';
import {BadgeModule} from 'primeng/badge';
import {RadioButtonModule} from 'primeng/radiobutton';
import {InputSwitchModule} from 'primeng/inputswitch';
import {RippleModule} from 'primeng/ripple';

import {RouterModule} from '@angular/router';
import {PassengerLayoutComponent} from "./passenger.layout.component";
import {PanelMenuModule} from "primeng/panelmenu";
import {TabViewModule} from "primeng/tabview";
import {DialogModule} from "primeng/dialog";
import {TranslateModule} from "@ngx-translate/core";
import {ButtonModule} from "primeng/button";
import {PasswordModule} from "primeng/password";
import {MenuModule} from "primeng/menu";
import {SplitButtonModule} from "primeng/splitbutton";
import {DropdownModule} from "primeng/dropdown";

import {AppConfigModule} from "../config/config.module";
import {PassengerMenuitemComponent} from "./menuItem/passenger.menuitem.component";
import {PassengerTopbarComponent} from "./topBar/passenger.topbar.component";
import {PassengerFooterComponent} from "./footer/passenger.footer.component";
import {PassengerMenuComponent} from "./menu/passenger.menu.component";
import {PassengerSidebarComponent} from "./sideBar/passenger.sidebar.component";

@NgModule({
    declarations: [
        PassengerMenuitemComponent,
        PassengerTopbarComponent,
        PassengerFooterComponent,
        PassengerMenuComponent,
        PassengerSidebarComponent,
        PassengerLayoutComponent,
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        BrowserAnimationsModule,
        InputTextModule,
        SidebarModule,
        BadgeModule,
        RadioButtonModule,
        InputSwitchModule,
        RippleModule,
        RouterModule,
        AppConfigModule,
        PanelMenuModule,
        TabViewModule,
        DialogModule,
        TranslateModule,
        ButtonModule,
        PasswordModule,
        MenuModule,
        SplitButtonModule,
        DropdownModule
    ],
    exports: [PassengerLayoutComponent, PassengerTopbarComponent]
})
export class PassengerLayoutModule {
}
