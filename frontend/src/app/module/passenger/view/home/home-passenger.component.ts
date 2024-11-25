import {VilleDto} from "../../../../shared/model/trajet/Ville.model";
import {Component, OnInit} from "@angular/core";
import {LayoutService} from "../../../../layout/service/app.layout.service";
import {TrajetPassengerService} from "../../../../shared/service/passenger/trajet/TrajetPassenger.service";
import {VillePassengerService} from "../../../../shared/service/passenger/trajet/VillePassenger.service";
import {AppComponent} from "../../../../app.component";
import {Router} from "@angular/router";
import {TrajetDto} from "../../../../shared/model/trajet/Trajet.model";
import {TrajetCriteria} from "../../../../shared/criteria/trajet/TrajetCriteria.model";


@Component({
    selector: 'passenger-home',
    templateUrl: './home-passenger.component.html',
    styleUrls: ['./home-passenger.component.scss']
})
export class HomePassengerComponent implements OnInit {

    villeDeparts: VilleDto[] = [];
    villeDestinations: VilleDto[] = [];
    filteredDepartures: VilleDto[] = [];
    filteredDestinations: VilleDto[] = [];
    today: Date;
    passengerCount = 1;

    constructor(public layoutService: LayoutService,private service: TrajetPassengerService ,private villeService: VillePassengerService, public app: AppComponent ,private router: Router) {
    }


    loading = true;


    ngOnInit(): void {

        this.loadVilleDepart();
        this.loadVilleDestination();
        this.today = new Date();
        this.item.placesDisponibles=1;


    }

    public async loadVilleDepart(){
        this.villeService.findAllOptimized().subscribe(villeDeparts => this.villeDeparts = villeDeparts, error => console.log(error))
    }
    public async loadVilleDestination(){
        this.villeService.findAllOptimized().subscribe(villeDestinations => this.villeDestinations = villeDestinations, error => console.log(error))
    }

    get item(): TrajetDto {
        return this.service.item;
    }

    set item(value: TrajetDto) {
        this.service.item = value;
    }

    get criteria(): TrajetCriteria {
        return this.service.criteria;
    }

    set criteria(value: TrajetCriteria) {
        this.service.criteria = value;
    }

}
