import {RouterModule} from '@angular/router';
import {NgModule} from '@angular/core';
import {AuthGuard} from 'src/app/zynerator/security/guards/auth.guard';

import {HomePublicComponent} from 'src/app/public/home/home-public.component';

import {LoginAdminComponent} from 'src/app/module/admin/login-admin/login-admin.component';
import {RegisterAdminComponent} from 'src/app/module/admin/register-admin/register-admin.component';
import {ChangePasswordAdminComponent} from 'src/app/module/admin/change-password-admin/change-password-admin.component';
import {LoginDriverComponent} from 'src/app/module/driver/login-driver/login-driver.component';
import {RegisterDriverComponent} from 'src/app/module/driver/register-driver/register-driver.component';
import {
    ChangePasswordDriverComponent
} from 'src/app/module/driver/change-password-driver/change-password-driver.component';
import {LoginPassengerComponent} from 'src/app/module/passenger/login-passenger/login-passenger.component';
import {RegisterPassengerComponent} from 'src/app/module/passenger/register-passenger/register-passenger.component';
import {
    ChangePasswordPassengerComponent
} from 'src/app/module/passenger/change-password-passenger/change-password-passenger.component';
import {AdminLayoutComponent} from "./layout/admin/admin.layout.component";
import {ActivationPassengerComponent} from "./module/passenger/activation-passenger/activation-passenger.component";

@NgModule({
    imports: [
        RouterModule.forRoot(
            [
                {path: '', component: HomePublicComponent},
                {path: 'admin/login', component: LoginAdminComponent },
                {path: 'admin/register', component: RegisterAdminComponent },
                {path: 'admin/changePassword', component: ChangePasswordAdminComponent },
                {path: 'driver/login', component: LoginDriverComponent },
                {path: 'driver/register', component: RegisterDriverComponent },
                {path: 'driver/changePassword', component: ChangePasswordDriverComponent },
                {path: 'passenger/login', component: LoginPassengerComponent },
                {path: 'passenger/register', component: RegisterPassengerComponent },
                {path: 'passenger/changePassword', component: ChangePasswordPassengerComponent },
                {path: 'passenger/activation', component:ActivationPassengerComponent},
                {
                    path: 'app',
                    children: [
                        {
                            path: 'admin',
                            component: AdminLayoutComponent,
                            loadChildren: () => import( './module/admin/admin-routing.module').then(x => x.AdminRoutingModule),
                            canActivate: [AuthGuard],
                        },
                        {
                            path: 'driver',
                            loadChildren: () => import( './module/driver/driver-routing.module').then(x => x.DriverRoutingModule),
                            canActivate: [AuthGuard],
                        },
                        {
                            path: 'passenger',
                            //component: PassengerLayoutModule,
                            loadChildren: () => import( './module/passenger/passenger-routing.module').then(x => x.PassengerRoutingModule),
                            canActivate: [AuthGuard],
                        },
                    ],
                    canActivate: [AuthGuard]
                },
            ],
                { scrollPositionRestoration: 'enabled' }
            ),
        ],
    exports: [RouterModule],
    })
export class AppRoutingModule { }
