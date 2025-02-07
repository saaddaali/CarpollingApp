import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ToastModule} from 'primeng/toast';
import {ToolbarModule} from 'primeng/toolbar';
import {TableModule} from 'primeng/table';
import {DropdownModule} from 'primeng/dropdown';
import {InputSwitchModule} from 'primeng/inputswitch';
import {InputTextareaModule} from 'primeng/inputtextarea';

import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {DialogModule} from 'primeng/dialog';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {CalendarModule} from 'primeng/calendar';
import {PanelModule} from 'primeng/panel';
import {InputNumberModule} from 'primeng/inputnumber';
import {BadgeModule} from 'primeng/badge';
import {MultiSelectModule} from 'primeng/multiselect';

import {PasswordModule} from 'primeng/password';
import {InputTextModule} from 'primeng/inputtext';
import {ButtonModule} from 'primeng/button';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {TabViewModule} from 'primeng/tabview';
import {SplitButtonModule} from 'primeng/splitbutton';
import {MessageModule} from 'primeng/message';
import {MessagesModule} from 'primeng/messages';
import {PreloadpageComponent} from './preloadpage/preloadpage.component';
import {MainComponent} from './main/main.component';
import {StepsComponent} from './main/steps/steps.component';
import {FrameworksComponent} from './main/frameworks/frameworks.component';
import {FooterComponent} from './footer/footer.component';
import {HomePublicComponent} from './home/home-public.component';
import {ContactUsComponent} from './contact-us/contact-us.component';
import {TopComponent} from './top/top.component';
import {TrajetsPublicComponent} from "./trajets/trajets-public.component";
import {AutoCompleteModule} from "primeng/autocomplete";
import {PassengerLayoutModule} from "../layout/passenger/passenger.layout.module";
import {PassengerModule} from "../module/passenger/passenger.module";
import {SearchBarComponent} from "./SearchBar/searchBar.component";


@NgModule({
    declarations: [
        PreloadpageComponent,
        MainComponent,
        StepsComponent,
        FrameworksComponent,
        FooterComponent,
        HomePublicComponent,
        ContactUsComponent,
        TrajetsPublicComponent,
        TopComponent,
        SearchBarComponent
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
        AutoCompleteModule,
        PassengerLayoutModule,
        PassengerModule,
    ],
    exports: [
        PreloadpageComponent,
        MainComponent

    ],

})
export class PublicModule {
}
