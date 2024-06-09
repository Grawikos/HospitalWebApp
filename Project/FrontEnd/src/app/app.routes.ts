import { Routes } from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {PatientVisitsComponent} from "./patient-visits/patient-visits.component";
import {DoctorAppointmentsComponent} from "./doctor-appointments/doctor-appointments.component";
import {AdminComponent} from "./admin/admin.component";
import {DoctorAddAppointmentsComponent} from "./doctor-add-appointment/doctor-add-appointments.component";
import {PatientProfileComponent} from "./patient-profile/patient-profile.component";
import {DoctorProfileComponent} from "./doctor-profile/doctor-profile.component";
import {authGuard} from "./guards/auth.guard";

export const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'signup', component: RegisterComponent},
  {path: 'visits', component: PatientVisitsComponent, canActivate: [authGuard], data: { roles: ['ROLE_USER'] },},
  {path: 'appointments/add', component: DoctorAddAppointmentsComponent, canActivate: [authGuard], data: { roles: ['ROLE_DOCTOR'] },},
  {path: 'appointments', component: DoctorAppointmentsComponent, canActivate: [authGuard], data: { roles: ['ROLE_DOCTOR'] },},
  {path: 'admin', component: AdminComponent, canActivate: [authGuard], data: { roles: ['ROLE_ADMIN'] },},
  {path: 'profile/patient', component: PatientProfileComponent , canActivate: [authGuard], data: { roles: ['ROLE_USER'] },},
  {path: 'profile/doctor', component: DoctorProfileComponent, canActivate: [authGuard], data: { roles: ['ROLE_DOCTOR'] },},
  {path: 'home', component: HomeComponent},
  {path: '', redirectTo: '/home', pathMatch:"full"}
];
