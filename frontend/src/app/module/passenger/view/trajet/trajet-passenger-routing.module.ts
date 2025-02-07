// const root = environment.rootAppUrl;


import {UserListComponent} from 'src/app/module/security/user/list/user-list.component';
import {
    ModelPermissionListComponent
} from 'src/app/module/security/model-permission/list/model-permission-list.component';
import {
    ActionPermissionListComponent
} from 'src/app/module/security/action-permission/list/action-permission-list.component';
import {
    ModelPermissionUserListComponent
} from 'src/app/module/security/model-permission-utilisateur/list/model-permission-user-list.component';
import {RoleListComponent} from 'src/app/module/security/role/list/role-list.component';


import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {AuthGuard} from 'src/app/zynerator/security/guards/auth.guard';


import {VilleListPassengerComponent} from './ville/list/ville-list-passenger.component';
import {TrajetListPassengerComponent} from './trajet/list/trajet-list-passenger.component';
import {TrajetViewPassengerComponent} from "./trajet/view/trajet-view-passenger.component";
import {TrajetCreatePassengerComponent} from "./trajet/create/trajet-create-passenger.component";
import {TrajetsPassengerComponent} from "./trajet/trajets/trajets-passenger.component";

@NgModule({
    imports: [
        RouterModule.forChild(
            [
                {
                    path: '',
                    children: [
                        {

                            path: 'action-permission',
                            children: [
                                {
                                    path: 'list',
                                    component: ActionPermissionListComponent,
                                    canActivate: [AuthGuard]
                                }
                            ]
                        },

                        {

                            path: 'model-permission-user',
                            children: [
                                {
                                    path: 'list',
                                    component: ModelPermissionUserListComponent,
                                    canActivate: [AuthGuard]
                                }
                            ]
                        },
                        {

                            path: 'role',
                            children: [
                                {
                                    path: 'list',
                                    component: RoleListComponent,
                                    canActivate: [AuthGuard]
                                }
                            ]
                        },
                        {

                            path: 'user',
                            children: [
                                {
                                    path: 'list',
                                    component: UserListComponent,
                                    canActivate: [AuthGuard]
                                }
                            ]
                        },

                        {

                            path: 'model-permission',
                            children: [
                                {
                                    path: 'list',
                                    component: ModelPermissionListComponent,
                                    canActivate: [AuthGuard]
                                }
                            ]
                        },


                        {

                            path: 'ville',
                            children: [
                                {
                                    path: 'list',
                                    component: VilleListPassengerComponent,
                                    canActivate: [AuthGuard]
                                }
                            ]
                        },

                        {

                            path: '',
                            children: [
                                {
                                    path: 'create',
                                    component: TrajetCreatePassengerComponent,
                                    canActivate: [AuthGuard]
                                },
                                {
                                    path: 'list',
                                    component: TrajetListPassengerComponent,
                                    canActivate: [AuthGuard]
                                },
                                {
                                    path: 'details',
                                    component: TrajetViewPassengerComponent,
                                    canActivate: [AuthGuard]
                                }, {
                                    path: 'trajets',
                                    component: TrajetsPassengerComponent,
                                    canActivate: [AuthGuard]
                                }

                            ]
                        },

                    ]
                },
            ]
        ),
    ],
    exports: [RouterModule],
})
export class TrajetPassengerRoutingModule {
}
