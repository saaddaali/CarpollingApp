import { Component, ElementRef } from '@angular/core';
import {LayoutService} from "../../service/app.layout.service";


@Component({
    selector: 'app-sidebar',
    templateUrl: './admin.sidebar.component.html'
})
export class AdminSidebarComponent {
    constructor(public layoutService: LayoutService, public el: ElementRef) { }
}

