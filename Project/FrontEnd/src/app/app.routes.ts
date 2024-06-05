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

export const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'signup', component: RegisterComponent},
  {path: 'visits', component: PatientVisitsComponent},
  {path: 'appointments/add', component: DoctorAddAppointmentsComponent},
  {path: 'appointments', component: DoctorAppointmentsComponent},
  {path: 'admin', component: AdminComponent},
  {path: 'profile/patient', component: PatientProfileComponent},
  {path: 'profile/doctor', component: DoctorProfileComponent},
  {path: 'home', component: HomeComponent},
  {path: '', redirectTo: '/home', pathMatch:"full"}
];
