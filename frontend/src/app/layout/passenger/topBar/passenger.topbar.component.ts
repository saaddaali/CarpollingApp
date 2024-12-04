import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { SelectItem } from 'primeng/api';

import {AppComponent} from 'src/app/app.component';
import {TranslateService} from '@ngx-translate/core';
import {UserService} from 'src/app/zynerator/security/shared/service/User.service';
import {UserDto} from 'src/app/zynerator/security/shared/model/User.model';
import {AuthService} from 'src/app/zynerator/security/shared/service/Auth.service';
import {LayoutService} from "../../service/app.layout.service";
import {PassengerLayoutComponent} from "../passenger.layout.component";
import {Router} from "@angular/router";


@Component({
    selector: 'passenger-topbar',
    templateUrl: './passenger.topbar.component.html',
    styleUrls:['./passenger.topbar.component.scss']
})
export class PassengerTopbarComponent implements OnInit{
   rolePassenger = false;
    editDialog = false ;
    languageOptions: SelectItem[];
    selectedLanguage: string;




    @ViewChild('menubutton') menuButton!: ElementRef;

    @ViewChild('topbarmenubutton') topbarMenuButton!: ElementRef;

    @ViewChild('topbarmenu') menu!: ElementRef;
    public async edit(dto: UserDto) {
        this.userService.findByUsername(dto.username).subscribe(res => {
            this.item = res;
            this.editDialog = true;
        });
        this.router.navigate(['/app/passenger/passenger/passenger/view']);

    }


    public editUser(){
        this.userService.edit().subscribe(data => this.authenticatedUser = data);
        this.authService.loadInfos();
        this.editDialog = false;
    }

    public hideEditDialog() {
        this.editDialog = false;
    }



    constructor(public  layoutService:LayoutService , public app: AppComponent, private authService: AuthService, private translateService: TranslateService, private userService: UserService,private router: Router) {
        this.languageOptions = [
            { label: 'English', value: 'en' },
            { label: 'Français', value: 'fr' },
            { label: 'العربية', value: 'ar' }
        ];
    }

    useLanguage(language: string): void {
        this.translateService.use(language);
    }
    ngOnInit(): void {
        this.authService.loadInfos();
        if ( this.authService.authenticatedUser.roleUsers[0].role.authority === 'ROLE_PASSENGER'){
            this.rolePassenger = true;
        }
    }

    logout(){
        this.authService.logout();
    }
    get item(): UserDto {
        return this.userService.item;
    }

    set item(value: UserDto) {
        this.userService.item = value;
    }
    get authenticatedUser(): UserDto{
        return this.authService.authenticatedUser;
    }
    set authenticatedUser(userDto: UserDto){
        this.authService.authenticatedUser = userDto;
    }


}
