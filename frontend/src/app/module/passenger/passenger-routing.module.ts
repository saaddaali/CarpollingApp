import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import {AuthGuard} from 'src/app/zynerator/security/guards/auth.guard';

import { ActivationPassengerComponent } from './activation-passenger/activation-passenger.component';
import { LoginPassengerComponent } from './login-passenger/login-passenger.component';
import { RegisterPassengerComponent } from './register-passenger/register-passenger.component';
import { ForgetPasswordPassengerComponent } from './forget-password-passenger/forget-password-passenger.component';
import { ChangePasswordPassengerComponent } from './change-password-passenger/change-password-passenger.component';

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
                                    component: LoginPassengerComponent ,
                                    canActivate: [AuthGuard]
                                }
                              ]
                        },
                        {
                            path: 'register',
                            children: [
                                {
                                    path: '',
                                    component: RegisterPassengerComponent ,
                                    canActivate: [AuthGuard]
                                }
                              ]
                        },{
                            path: 'forget-password',
                            children: [
                                {
                                    path: '',
                                    component: ForgetPasswordPassengerComponent ,
                                    canActivate: [AuthGuard]
                                }
                              ]
                        },{
                            path: 'change-password',
                            children: [
                                {
                                    path: '',
                                    component: ChangePasswordPassengerComponent ,
                                    canActivate: [AuthGuard]
                                }
                              ]
                        },
                        {
                            path: 'activation',
                            children: [
                                {
                                    path: '',
                                    component: ActivationPassengerComponent ,
                                    canActivate: [AuthGuard]
                                }
                              ]
                        },
                        {
                            path: 'trajet',
                            loadChildren: () => import('./view/trajet/trajet-passenger-routing.module').then(x => x.TrajetPassengerRoutingModule),
                            canActivate: [AuthGuard],
                        },
                        {
                            path: 'vehicule',
                            loadChildren: () => import('./view/vehicule/vehicule-passenger-routing.module').then(x => x.VehiculePassengerRoutingModule),
                            canActivate: [AuthGuard],
                        },
                        {
                            path: 'paiement',
                            loadChildren: () => import('./view/paiement/paiement-passenger-routing.module').then(x => x.PaiementPassengerRoutingModule),
                            canActivate: [AuthGuard],
                        },
                        {
                            path: 'passenger',
                            loadChildren: () => import('./view/passenger/passenger-passenger-routing.module').then(x => x.PassengerPassengerRoutingModule),
                            canActivate: [AuthGuard],
                        },
                        {
                            path: 'driver',
                            loadChildren: () => import('./view/driver/driver-passenger-routing.module').then(x => x.DriverPassengerRoutingModule),
                            canActivate: [AuthGuard],
                        },
                        {
                            path: 'message',
                            loadChildren: () => import('./view/message/message-passenger-routing.module').then(x => x.MessagePassengerRoutingModule),
                            canActivate: [AuthGuard],
                        },
                        {
                            path: 'reservation',
                            loadChildren: () => import('./view/reservation/reservation-passenger-routing.module').then(x => x.ReservationPassengerRoutingModule),
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
export class PassengerRoutingModule { }
