import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { SelectItem } from 'primeng/api';

import {AppComponent} from 'src/app/app.component';
import {TranslateService} from '@ngx-translate/core';
import {UserService} from 'src/app/zynerator/security/shared/service/User.service';
import {UserDto} from 'src/app/zynerator/security/shared/model/User.model';
import {AuthService} from 'src/app/zynerator/security/shared/service/Auth.service';
import {LayoutService} from "../../../../layout/service/app.layout.service";
import {PassengerLayoutComponent} from "../../../../layout/passenger/passenger.layout.component";


@Component({
    selector: 'topbar',
    templateUrl: './topBar.component.html'
})
export class TopBarComponent implements OnInit{
   rolePassenger = false;
    editDialog = false ;
    languageOptions: SelectItem[];
    selectedLanguage: string;




    @ViewChild('topbarmenu') menu!: ElementRef;
    public async edit(dto: UserDto) {
        this.userService.findByUsername(dto.username).subscribe(res => {
            this.item = res;
            this.editDialog = true;
        });

    }
    public editUser(){
        this.userService.edit().subscribe(data => this.authenticatedUser = data);
        this.authService.loadInfos();
        this.editDialog = false;
    }

    public hideEditDialog() {
        this.editDialog = false;
    }



    constructor(public  layoutService:LayoutService , public app: AppComponent, private authService: AuthService, private translateService: TranslateService, private userService: UserService) {
    }

    useLanguage(language: string): void {
        this.translateService.use(language);
    }
    ngOnInit(): void {

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
