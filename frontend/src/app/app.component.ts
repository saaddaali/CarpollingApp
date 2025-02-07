import {Component, OnInit} from '@angular/core';
import {PrimeNGConfig} from 'primeng/api';
import {RoleService} from "./zynerator/security/shared/service/Role.service";
import {TranslateService} from "@ngx-translate/core";
import {Observable} from "rxjs";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
    layoutMode = 'static';
    lightMenu = true;
    topbarColor = 'layout-topbar-blue';
    inlineUser = false;
    isRTL = false;
    inputStyle = 'outlined';
    ripple = true;
    private role$: Observable<string>;

    constructor(private primengConfig: PrimeNGConfig, private roleService: RoleService, private translateService: TranslateService) {
        translateService.addLangs(['ar','en', 'fr']);
        translateService.setDefaultLang('en');
        const browserLang = translateService.getBrowserLang();
        console.log('browser lang ==>  ' +browserLang);
        translateService.use('en');
        //translateService.use(browserLang.match(/ar|en|fr/) ? browserLang : 'eng');
    }

    ngOnInit() {
        this.primengConfig.ripple = true;
        this.role$ = this.roleService.role$;
        this.role$.subscribe(role => {

        });
    }
}
