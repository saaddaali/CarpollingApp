import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {HomePublicComponent} from 'src/app/public/home/home-public.component';
import {ContactUsComponent} from 'src/app/public/contact-us/contact-us.component';
import {TrajetsPublicComponent} from "./trajets/trajets-public.component";

@NgModule({
    imports: [
        RouterModule.forChild(
            [
                {
                    path: '',
                    children: [
                        {
                            path: 'home',
                            children: [
                                {
                                    path: '',
                                    component: HomePublicComponent,
                                }
                            ]
                        },
                        {
                            path: 'contact',
                            children: [
                                {
                                    path: '',
                                    component: ContactUsComponent,
                                }
                            ]
                        },
                        {
                            path: 'search',
                            children: [
                                {
                                    path: '',
                                    component: TrajetsPublicComponent,
                                }
                            ]
                        }
                    ]
                },
            ]
        ),
    ],
    exports: [RouterModule],
})
export class PublicRoutingModule {
}
