import {Component, importProvidersFrom, OnInit} from '@angular/core';
import {Visit} from "../Visit";
import {UserService} from "../services/user.service";
import {Router} from "@angular/router";
import {DoctorService} from "../services/doctor.service";
import {FormsModule} from "@angular/forms";
import {Patient} from "../Patient";
import {BsDatepickerModule} from "ngx-bootstrap/datepicker";
import {CommonModule} from "@angular/common";
import {HttpClientModule} from "@angular/common/http";
import {VisitForm} from "../services/VisitForm";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-doctor-appointments',
  standalone: true,
  imports: [CommonModule, FormsModule, BsDatepickerModule, HttpClientModule, MatButton],
  templateUrl: './doctor-appointments.component.html',
  styleUrl: './doctor-appointments.component.css',
})
export class DoctorAppointmentsComponent implements  OnInit{
  visits: Visit[] = [];
  prevVisits: Visit[] = [];
  upcomingVisits: Visit[] = [];
  errorMessage?: string;
  isLogin?: boolean;
  confirmPopup: number = -1;

  constructor(private patientService: UserService, private doctorService: DoctorService, private router: Router) {
    this.isLogin = true;
  }

  ngOnInit() {
    this.doctorService.getDoctorPage().subscribe({
      next: (data: Visit[]) => {
        this.visits = data;
        this.shuffleVisits();
      },
      error: (error) => {
        console.log('was error');
        this.isLogin = false;
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    });

  }

  shuffleVisits(){
    const now = new Date();
    this.upcomingVisits = this.visits.filter(visit => new Date(visit.appointmentDate) > now);
    this.prevVisits = this.visits.filter(visit => new Date(visit.appointmentDate) <= now);
  }

  cancelVisit(id:number){
    this.hidePopup();
    this.doctorService.deleteAppointment(id).subscribe({
      next: (data) => {
        console.log("deleted")
      },
      error: (error) => {
        console.log('was error on delete');
      }
    });
  }

  showPopup(id:number){
    this.confirmPopup = id;
  }

  hidePopup(){
    this.confirmPopup = -1;
  }


  redirectHome() {
    this.router.navigate(['/home']);
  }

  redirectLogin() {
    this.router.navigate(['/login']);
  }

  redirectAddAppointment() {
    this.router.navigate(['/appointments/add']);
  }



}
