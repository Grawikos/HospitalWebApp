import {Component, OnInit} from '@angular/core';
import {Visit} from "../Visit";
import {UserService} from "../services/user.service";
import {Router} from "@angular/router";
import {DoctorService} from "../services/doctor.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {Patient} from "../Patient";
import {BsDatepickerModule} from "ngx-bootstrap/datepicker";
import {CommonModule} from "@angular/common";
import {HttpClientModule} from "@angular/common/http";
import {VisitForm} from "../services/VisitForm";
import {MatFormField, MatFormFieldModule, MatHint, MatLabel} from "@angular/material/form-field";
import {MatOption, MatSelect} from "@angular/material/select";
import {
  MatDatepicker,
  MatDatepickerInput,
  MatDatepickerModule,
  MatDatepickerToggle
} from "@angular/material/datepicker";
import {MatInput, MatInputModule} from "@angular/material/input";
import {MatNativeDateModule, provideNativeDateAdapter} from "@angular/material/core";
import {MatIconModule} from "@angular/material/icon";
import {NgxMatTimepickerComponent, NgxMatTimepickerModule} from "ngx-mat-timepicker";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-doctor-appointments',
  standalone: true,
  providers: [provideNativeDateAdapter()],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatIconModule,
    MatSelect,
    MatOption,
    NgxMatTimepickerModule,
    NgxMatTimepickerComponent,
    MatButton
  ],
  templateUrl: './doctor-add-appointments.component.html',
  styleUrl: './doctor-add-appointments.component.css',
})
export class DoctorAddAppointmentsComponent implements  OnInit{
  patients?: Patient[];
  choosePatient?: Patient;
  errorMessage?: string;
  isLogin: boolean = true;
  newDate?: Date;
  newTime ?: string;
  minDate : Date;
  maxDate : Date;
  added: boolean = false;

  constructor(private patientService: UserService, private doctorService: DoctorService, private router: Router) {
    const currentYear = new Date().getFullYear();
    this.minDate = new Date(currentYear, 0, 1);
    this.maxDate = new Date(currentYear + 9, 11, 31);
  }

  ngOnInit() {
    this.getPatients();
  }

  getPatients(){
    this.doctorService.getPatients().subscribe({
      next: (data: Patient[]) => {
        this.patients = data;
        console.log(data);
      },
      error: (err) => {
        this.isLogin = false;
        console.log("error" + err);
    }
    });
  }


  addAppointment(){
    let patientId: number = -1;
    if (this.choosePatient){
      if (this.choosePatient.id)
        patientId = this.choosePatient.id;
    }
    else{
      console.log("this patient does not exist");
      return;
    }
    if (!this.newDate){
      console.log("no date");
      return;
    }
    // "yyyy-MM-dd'T'HH:mm:ss"
    if(!this.newTime){return;}
    const hourParts = this.newTime.split(':');
    let hour = parseInt(hourParts[0], 10);
    if (this.newTime.endsWith('PM') && hour !== 12) {
      hour += 12;
    } else if (this.newTime.endsWith('AM') && hour === 12) {
      hour = 0;
    }
    let hourS = String(hour);
    let minutesS = String(parseInt(hourParts[1], 10));
    let month: string = String(this.newDate.getMonth() + 1);
    let day: string = String(this.newDate.getDate());
    if (hourS.length == 1){
      hourS = '0' + hourS;
    }
    if (minutesS.length == 1){
      minutesS = '0' + minutesS;
    }
    if (day.length == 1){
      day = '0' + day;
    }
    if (month.length == 1){
      month = '0' + month;
    }
    let dateTime: string = this.newDate.getFullYear() + "-" + month + "-" + day + 'T' + hourS + ":" + minutesS + ":00";
    this.doctorService.addAppointment(new VisitForm(patientId, dateTime)).subscribe({
      next: (data: Visit) => {
        console.log('added appointment')
        this.added = true;
      },
      error: (error) => {
        console.log('was error');
      }
    });
  }
  redirectHome() {
    this.router.navigate(['/home']);
  }

  redirectLogin() {
    this.router.navigate(['/login']);
  }

}
