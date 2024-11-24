import { OnInit } from '@angular/core';
import { Component } from '@angular/core';
import {LayoutService} from "../../service/app.layout.service";
import {AppComponent} from "../../../app.component";
import {RoleService} from "../../../zynerator/security/shared/service/Role.service";
import {AuthService} from "../../../zynerator/security/shared/service/Auth.service";
import {Router} from "@angular/router";
import {PassengerLayoutComponent} from "../passenger.layout.component";


@Component({
  selector: 'passenger-menu',
  templateUrl: './passenger.menu.component.html'
})
export class PassengerMenuComponent implements OnInit {
  model: any[];
  modelPassenger: any[];
constructor(public layoutService: LayoutService, public app: AppComponent, public appMain: PassengerLayoutComponent, private roleService: RoleService, private authService: AuthService, private router: Router) { }
  ngOnInit() {
    this.modelPassenger =
      [
				{
                    label: 'Pages',
                    icon: 'pi pi-fw pi-briefcase',
                    items: [
					  {
						label: 'Vehicle Management',
						icon: 'pi pi-wallet',
						items: [
								  {
									label: 'Liste vehicule',
									icon: 'pi pi-fw pi-plus-circle',
									routerLink: ['/app/passenger/vehicule/vehicule/list']
								  },
						]
					  },
					  {
						label: 'Trajet Management',
						icon: 'pi pi-wallet',
						items: [
								  {
									label: 'Liste ville',
									icon: 'pi pi-fw pi-plus-circle',
									routerLink: ['/app/passenger/trajet/ville/list']
								  },
								  {
									label: 'Liste trajet',
									icon: 'pi pi-fw pi-plus-circle',
									routerLink: ['/app/passenger/trajet/trajet/list']
								  },
						]
					  },
					  {
						label: 'Payment Method Management',
						icon: 'pi pi-wallet',
						items: [
								  {
									label: 'Liste carte bancaire',
									icon: 'pi pi-fw pi-plus-circle',
									routerLink: ['/app/passenger/paiement/carte-bancaire/list']
								  },
						]
					  },
					  {
						label: 'Passenger Management',
						icon: 'pi pi-wallet',
						items: [
								  {
									label: 'Liste passenger',
									icon: 'pi pi-fw pi-plus-circle',
									routerLink: ['/app/passenger/passenger/passenger/list']
								  },
						]
					  },
					  {
						label: 'Reservation Management',
						icon: 'pi pi-wallet',
						items: [
								  {
									label: 'Liste reservation',
									icon: 'pi pi-fw pi-plus-circle',
									routerLink: ['/app/passenger/reservation/reservation/list']
								  },
						]
					  },
					  {
						label: 'Message Management',
						icon: 'pi pi-wallet',
						items: [
								  {
									label: 'Liste conversation',
									icon: 'pi pi-fw pi-plus-circle',
									routerLink: ['/app/passenger/message/conversation/list']
								  },
								  {
									label: 'Liste message',
									icon: 'pi pi-fw pi-plus-circle',
									routerLink: ['/app/passenger/message/message/list']
								  },
						]
					  },
					  {
						label: 'Driver Management',
						icon: 'pi pi-wallet',
						items: [
								  {
									label: 'Liste driver',
									icon: 'pi pi-fw pi-plus-circle',
									routerLink: ['/app/passenger/driver/driver/list']
								  },
						]
					  },

				   {
					  label: 'Security Management',
					  icon: 'pi pi-wallet',
					  items: [
						  {
							  label: 'List User',
							  icon: 'pi pi-fw pi-plus-circle',
							  routerLink: ['/app/admin/security/user/list']
						  },
						  {
							  label: 'List Model',
							  icon: 'pi pi-fw pi-plus-circle',
							  routerLink: ['/app/admin/security/model-permission/list']
						  },
						  {
							  label: 'List Action Permission',
							  icon: 'pi pi-fw pi-plus-circle',
							  routerLink: ['/app/admin/security/action-permission/list']
						  },
					  ]
				  }
			]
	    }
    ];

        if (this.authService.authenticated) {
            if (this.authService.authenticatedUser.roleUsers) {
              this.authService.authenticatedUser.roleUsers.forEach(role => {
                  const roleName: string = this.getRole(role.role.authority);
                  this.roleService._role.next(roleName.toUpperCase());
                  eval('this.model = this.model' + this.getRole(role.role.authority));
              })
            }
        }
  }

    getRole(text){
        const [role, ...rest] = text.split('_');
        return this.upperCaseFirstLetter(rest.join(''));
    }

    upperCaseFirstLetter(word: string) {
      if (!word) { return word; }
      return word[0].toUpperCase() + word.substr(1).toLowerCase();
    }

    onMenuClick(event) {
        this.appMain.onMenuClick(event);
    }
}
