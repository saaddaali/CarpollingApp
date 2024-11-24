import { Component, ElementRef } from '@angular/core';
import {LayoutService} from "../../service/app.layout.service";


@Component({
    selector: 'passenger-sidebar',
    templateUrl: './passenger.sidebar.component.html'
})
export class PassengerSidebarComponent {
    constructor(public layoutService: LayoutService, public el: ElementRef) { }
}

