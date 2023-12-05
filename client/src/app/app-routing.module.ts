import { NgModule } from '@angular/core';
import {Router, RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./pages/home/home.component";
import {LoginComponent} from "./pages/login/login.component";
import {RegisterComponent} from "./pages/register/register.component";
import {AuthenticationGuard} from "./pages/guards/authentication.guard";
import {Role} from "../model/Role";
import {NotFoundComponent} from "./pages/not-found/not-found.component";
import {ProfileComponent} from "./pages/profile/profile.component";
import {AdminComponent} from "./pages/admin/admin.component";

const routes: Routes = [

  {path: "", redirectTo: "home", pathMatch: "full"},

  {
    path: "home",
    component: HomeComponent,
    canActivate: [AuthenticationGuard] ,
    data: {roles: [Role.USER, Role.ADMIN]}
  },

  {
    path: "profile",
    component: ProfileComponent,
    canActivate: [AuthenticationGuard] ,
    data: {roles: [Role.USER, Role.ADMIN]}
  },

  {
    path: "admin",
    component: AdminComponent,
    canActivate: [AuthenticationGuard] ,
    data: {roles: [Role.ADMIN]}
  },

  {path: "login", component: LoginComponent},
  {path: "register", component: RegisterComponent},

  {path: "404", component: NotFoundComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
  constructor(private router: Router)
  {
    this.router.errorHandler = (error: any) =>
    {
      this.router.navigate(["/404"])
    }
  }
}
