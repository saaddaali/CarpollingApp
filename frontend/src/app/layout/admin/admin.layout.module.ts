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
import {AdminLayoutComponent} from "./admin.layout.component";
import {PanelMenuModule} from "primeng/panelmenu";
import {TabViewModule} from "primeng/tabview";
import {DialogModule} from "primeng/dialog";
import {TranslateModule} from "@ngx-translate/core";
import {ButtonModule} from "primeng/button";
import {PasswordModule} from "primeng/password";
import {MenuModule} from "primeng/menu";
import {SplitButtonModule} from "primeng/splitbutton";
import {DropdownModule} from "primeng/dropdown";
import {AdminSidebarComponent} from "./sideBar/admin.sidebar.component";
import {AdminMenuComponent} from "./menu/admin.menu.component";
import {AdminFooterComponent} from "./footer/admin.footer.component";
import {AdminMenuitemComponent} from "./menuItem/admin.menuitem.component";
import {AdminTopbarComponent} from "./topBar/admin.topbar.component";
import {AppConfigModule} from "../config/config.module";

@NgModule({
    declarations: [
        AdminMenuitemComponent,
        AdminTopbarComponent,
        AdminFooterComponent,
        AdminMenuComponent,
        AdminSidebarComponent,
        AdminLayoutComponent,
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
    exports: [AdminLayoutComponent, AdminTopbarComponent]
})
export class AdminLayoutModule {
}
