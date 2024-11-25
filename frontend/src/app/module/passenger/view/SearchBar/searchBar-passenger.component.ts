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
    selector: 'passenger-search-bar',
    templateUrl: './searchBar-passenger.component.html',
    styleUrls: ['./searchBar-passenger.component.scss']
})
export class SearchBarPassengerComponent implements OnInit {

    villeDeparts: VilleDto[] = [];
    villeDestinations: VilleDto[] = [];
    filteredDepartures: VilleDto[] = [];
    filteredDestinations: VilleDto[] = [];
    today: Date;

    constructor(public layoutService: LayoutService,private service: TrajetPassengerService ,private villeService: VillePassengerService, public app: AppComponent ,private router: Router) {
    this.item.placesDisponibles = 1;
    this.item.horaireDepart= new Date();
    }


    filterCountry(event: any, type: 'departure' | 'destination'): void {
        const query = event.query.toLowerCase();
        if (type === 'departure') {
            this.filteredDepartures = this.villeDeparts.filter(ville =>
                ville.libelle.toLowerCase().includes(query)
            );
        } else {
            this.filteredDestinations = this.villeDestinations.filter(ville =>
                ville.libelle.toLowerCase().includes(query)
            );
        }
    }



    loading = true;
    bool: boolean = true;

    toSearch() {
        console.log('Item before navigating:', this.item);



        if (this.item && this.item.villeDepart && this.item.villeDestination) {
            this.router.navigate(['/app/passenger/trajet']);
        } else {
            console.log('Item or required properties are null or not properly initialized');
        }
    }



    ngOnInit(): void {

        this.loadVilleDepart();
        this.loadVilleDestination();
        this.today = new Date();



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
