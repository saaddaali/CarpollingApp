import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import {AuthGuard} from 'src/app/zynerator/security/guards/auth.guard';

import { ActivationAdminComponent } from './activation-admin/activation-admin.component';
import { LoginAdminComponent } from './login-admin/login-admin.component';
import { RegisterAdminComponent } from './register-admin/register-admin.component';
import { ForgetPasswordAdminComponent } from './forget-password-admin/forget-password-admin.component';
import { ChangePasswordAdminComponent } from './change-password-admin/change-password-admin.component';
import {DashboardComponent} from "./view/dashboard/dashboard.component";

@NgModule({
    imports: [
        RouterModule.forChild(
            [
                {
                    path: '',
                    children: [
                        {
                            path: 'login',
                            children: [
                                {
                                    path: '',
                                    component: LoginAdminComponent ,
                                    canActivate: [AuthGuard]
                                }
                              ]
                        },
                        {
                            path: 'register',
                            children: [
                                {
                                    path: '',
                                    component: RegisterAdminComponent ,
                                    canActivate: [AuthGuard]
                                }
                              ]
                        },{
                            path: 'forget-password',
                            children: [
                                {
                                    path: '',
                                    component: ForgetPasswordAdminComponent ,
                                    canActivate: [AuthGuard]
                                }
                              ]
                        },{
                            path: 'change-password',
                            children: [
                                {
                                    path: '',
                                    component: ChangePasswordAdminComponent ,
                                    canActivate: [AuthGuard]
                                }
                              ]
                        },
                        {
                            path: 'activation',
                            children: [
                                {
                                    path: '',
                                    component: ActivationAdminComponent ,
                                    canActivate: [AuthGuard]
                                }
                              ]
                        },
                        {
                            path: '',
                            component: DashboardComponent,
                            canActivate: [AuthGuard],
                        },
                        {
                            path: 'trajet',
                            loadChildren: () => import('./view/trajet/trajet-admin-routing.module').then(x => x.TrajetAdminRoutingModule),
                            canActivate: [AuthGuard],
                        },
                        {
                            path: 'vehicule',
                            loadChildren: () => import('./view/vehicule/vehicule-admin-routing.module').then(x => x.VehiculeAdminRoutingModule),
                            canActivate: [AuthGuard],
                        },
                        {
                            path: 'paiement',
                            loadChildren: () => import('./view/paiement/paiement-admin-routing.module').then(x => x.PaiementAdminRoutingModule),
                            canActivate: [AuthGuard],
                        },
                        {
                            path: 'passenger',
                            loadChildren: () => import('./view/passenger/passenger-admin-routing.module').then(x => x.PassengerAdminRoutingModule),
                            canActivate: [AuthGuard],
                        },
                        {
                            path: 'driver',
                            loadChildren: () => import('./view/driver/driver-admin-routing.module').then(x => x.DriverAdminRoutingModule),
                            canActivate: [AuthGuard],
                        },
                        {
                            path: 'message',
                            loadChildren: () => import('./view/message/message-admin-routing.module').then(x => x.MessageAdminRoutingModule),
                            canActivate: [AuthGuard],
                        },
                        {
                            path: 'security',
                            loadChildren: () => import('../security/security-routing.module').then(x => x.SecurityRoutingModule),
                            canActivate: [AuthGuard],
                        }
                    ]
                },
            ]
        ),
    ],
    exports: [RouterModule],
})
export class AdminRoutingModule { }
