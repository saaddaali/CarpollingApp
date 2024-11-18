import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import {AuthGuard} from 'src/app/zynerator/security/guards/auth.guard';

import { ActivationDriverComponent } from './activation-driver/activation-driver.component';
import { LoginDriverComponent } from './login-driver/login-driver.component';
import { RegisterDriverComponent } from './register-driver/register-driver.component';
import { ForgetPasswordDriverComponent } from './forget-password-driver/forget-password-driver.component';
import { ChangePasswordDriverComponent } from './change-password-driver/change-password-driver.component';

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
                                    component: LoginDriverComponent ,
                                    canActivate: [AuthGuard]
                                }
                              ]
                        },
                        {
                            path: 'register',
                            children: [
                                {
                                    path: '',
                                    component: RegisterDriverComponent ,
                                    canActivate: [AuthGuard]
                                }
                              ]
                        },{
                            path: 'forget-password',
                            children: [
                                {
                                    path: '',
                                    component: ForgetPasswordDriverComponent ,
                                    canActivate: [AuthGuard]
                                }
                              ]
                        },{
                            path: 'change-password',
                            children: [
                                {
                                    path: '',
                                    component: ChangePasswordDriverComponent ,
                                    canActivate: [AuthGuard]
                                }
                              ]
                        },
                        {
                            path: 'activation',
                            children: [
                                {
                                    path: '',
                                    component: ActivationDriverComponent ,
                                    canActivate: [AuthGuard]
                                }
                              ]
                        },
                        {
                            path: 'trajet',
                            loadChildren: () => import('./view/trajet/trajet-driver-routing.module').then(x => x.TrajetDriverRoutingModule),
                            canActivate: [AuthGuard],
                        },
                        {
                            path: 'vehicule',
                            loadChildren: () => import('./view/vehicule/vehicule-driver-routing.module').then(x => x.VehiculeDriverRoutingModule),
                            canActivate: [AuthGuard],
                        },
                        {
                            path: 'paiement',
                            loadChildren: () => import('./view/paiement/paiement-driver-routing.module').then(x => x.PaiementDriverRoutingModule),
                            canActivate: [AuthGuard],
                        },
                        {
                            path: 'passenger',
                            loadChildren: () => import('./view/passenger/passenger-driver-routing.module').then(x => x.PassengerDriverRoutingModule),
                            canActivate: [AuthGuard],
                        },
                        {
                            path: 'driver',
                            loadChildren: () => import('./view/driver/driver-driver-routing.module').then(x => x.DriverDriverRoutingModule),
                            canActivate: [AuthGuard],
                        },
                        {
                            path: 'message',
                            loadChildren: () => import('./view/message/message-driver-routing.module').then(x => x.MessageDriverRoutingModule),
                            canActivate: [AuthGuard],
                        },
                        {
                            path: 'reservation',
                            loadChildren: () => import('./view/reservation/reservation-driver-routing.module').then(x => x.ReservationDriverRoutingModule),
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
export class DriverRoutingModule { }
