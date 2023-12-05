import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UnauthorizedComponent } from './pages/unauthorized/unauthorized.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { LoginComponent } from './pages/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { HttpClientModule } from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { RegisterComponent } from './pages/register/register.component';
import { FileDeleteComponent } from './components/file-delete/file-delete.component';
import { FileEditComponent } from './components/file-edit/file-edit.component';
import {authInterceptorProviders} from "./interceptors/auth.interceptor";
import { ChangeNameComponent } from './components/change-name/change-name.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { AdminComponent } from './pages/admin/admin.component';

@NgModule({
  declarations: [
    AppComponent,
    UnauthorizedComponent,
    NotFoundComponent,
    ProfileComponent,
    LoginComponent,
    HomeComponent,
    RegisterComponent,
    FileDeleteComponent,
    FileEditComponent,
    ChangeNameComponent,
    ChangePasswordComponent,
    AdminComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
