import { Component } from '@angular/core';
import {LayoutService} from "../../service/app.layout.service";


@Component({
    selector: 'passenger-footer',
    templateUrl: './passenger.footer.component.html'
})
export class PassengerFooterComponent {
    constructor(public layoutService: LayoutService) { }
}
