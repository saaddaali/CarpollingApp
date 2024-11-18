import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import {ToastModule} from 'primeng/toast';
import {ToolbarModule} from 'primeng/toolbar';
import {TableModule} from 'primeng/table';
import {DropdownModule} from 'primeng/dropdown';
import {InputSwitchModule} from 'primeng/inputswitch';
import {InputTextareaModule} from 'primeng/inputtextarea';
import {EditorModule} from "primeng/editor";

import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {CalendarModule} from 'primeng/calendar';
import {PanelModule} from 'primeng/panel';
import {InputNumberModule} from 'primeng/inputnumber';
import {BadgeModule} from 'primeng/badge';
import { MultiSelectModule } from 'primeng/multiselect';
import {TranslateModule} from '@ngx-translate/core';
import {FileUploadModule} from 'primeng/fileupload';
import {FullCalendarModule} from '@fullcalendar/angular';
import {CardModule} from "primeng/card";
import {TagModule} from "primeng/tag";

import { ConversationCreatePassengerComponent } from './conversation/create/conversation-create-passenger.component';
import { ConversationEditPassengerComponent } from './conversation/edit/conversation-edit-passenger.component';
import { ConversationViewPassengerComponent } from './conversation/view/conversation-view-passenger.component';
import { ConversationListPassengerComponent } from './conversation/list/conversation-list-passenger.component';
import { MessageCreatePassengerComponent } from './message/create/message-create-passenger.component';
import { MessageEditPassengerComponent } from './message/edit/message-edit-passenger.component';
import { MessageViewPassengerComponent } from './message/view/message-view-passenger.component';
import { MessageListPassengerComponent } from './message/list/message-list-passenger.component';

import { PasswordModule } from 'primeng/password';
import { InputTextModule } from 'primeng/inputtext';
import {ButtonModule} from 'primeng/button';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {TabViewModule} from 'primeng/tabview';
import { SplitButtonModule } from 'primeng/splitbutton';
import { MessageModule } from 'primeng/message';
import {MessagesModule} from 'primeng/messages';
import {PaginatorModule} from 'primeng/paginator';



@NgModule({
  declarations: [

    ConversationCreatePassengerComponent,
    ConversationListPassengerComponent,
    ConversationViewPassengerComponent,
    ConversationEditPassengerComponent,
    MessageCreatePassengerComponent,
    MessageListPassengerComponent,
    MessageViewPassengerComponent,
    MessageEditPassengerComponent,
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
    PaginatorModule,
    TranslateModule,
    FileUploadModule,
    FullCalendarModule,
    CardModule,
    EditorModule,
    TagModule,


  ],
  exports: [
  ConversationCreatePassengerComponent,
  ConversationListPassengerComponent,
  ConversationViewPassengerComponent,
  ConversationEditPassengerComponent,
  MessageCreatePassengerComponent,
  MessageListPassengerComponent,
  MessageViewPassengerComponent,
  MessageEditPassengerComponent,
  ],
})
export class MessagePassengerModule { }
