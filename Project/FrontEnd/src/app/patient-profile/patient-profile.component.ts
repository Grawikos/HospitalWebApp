import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from "../auth/token-storage.service";
import {ProfileService} from "../services/profile.service";
import {Doctor} from "../Doctor";
import {Patient} from "../Patient";
import {FormsModule} from "@angular/forms";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatOption} from "@angular/material/autocomplete";
import {MatSelect} from "@angular/material/select";
import {NgForOf, NgIf} from "@angular/common";
import {HomeService} from "../services/home.service";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import {Router} from "@angular/router";

@Component({
  selector: 'app-patient-profile',
  standalone: true,
  imports: [
    FormsModule,
    MatFormField,
    MatLabel,
    MatOption,
    MatSelect,
    NgForOf,
    NgIf,
    MatInput,
    MatButton
  ],
  templateUrl: './patient-profile.component.html',
  styleUrl: './patient-profile.component.css'
})
export class PatientProfileComponent implements OnInit{
  doctors?: Doctor[];
  rdyDoctors: Doctor[] = [];
  chooseFamilyDoctor?: Doctor;
  name?: string;
  surname?: string;
  familyDoctor?: Doctor;
  updated: boolean = false;
  isLogin: boolean = true;
  constructor(private homeService: HomeService, private profileService: ProfileService, private router: Router) { }

  ngOnInit() {
    this.getDoctors();
    this.profileService.getPatient().subscribe({
      next: (data: Patient) => {
        this.name = data.name;
        this.surname = data.surname;
        this.familyDoctor = data.familyDoctor;
        console.log("fetched patients profile" + data.name + data.surname);
      },
      error: (err) =>{
        console.log("err" + err);
        this.isLogin = false;
      }
    });
  }

  getDoctors(){
    this.homeService.getDoctors().subscribe({
      next: (data : Doctor[]) => {
        this.doctors = data;
        for (const doctor of this.doctors) {
          if (doctor.name && doctor.surname && doctor.speciality){
            this.rdyDoctors.push(doctor);
          }
        }
      }
      ,
      error: (error) => {
        console.log('cannot fetch doctors');
      }
    });
  }

  update() {
    console.log("update()");
    let patient: Patient = new Patient(this.name, this.surname, "", "", this.chooseFamilyDoctor);
    this.profileService.setPatient(patient).subscribe({
      next: (updatedPatient: Patient) => {
        console.log('Patient updated', updatedPatient)
        this.updated = true;
      },
      error: (err) => console.error('Error updating doctor', err)
    });
  }

  redirectHome() {
    this.router.navigate(['/home']);
  }

  redirectLogin() {
    this.router.navigate(['/login']);
  }
}
