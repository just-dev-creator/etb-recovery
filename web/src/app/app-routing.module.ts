import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RequestComponent } from "./request/request.component";
import {HomeComponent} from "./home/home.component";

const routes: Routes = [
  {
    path: 'edit/:id',
    component: RequestComponent
  },
  {
    path: '**',
    component: HomeComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
