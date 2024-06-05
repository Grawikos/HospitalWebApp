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
    NgIf
  ],
  templateUrl: './patient-profile.component.html',
  styleUrl: './patient-profile.component.css'
})
export class PatientProfileComponent implements OnInit{
  doctors?: Doctor[];
  chooseFamilyDoctor?: Doctor;
  name?: string;
  surname?: string;
  familyDoctor?: Doctor;
  updated: boolean = false;
  constructor(private homeService: HomeService, private profileService: ProfileService) { }

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
      }
    });
  }

  getDoctors(){
    this.homeService.getDoctors().subscribe({
      next: (data) => {
        this.doctors = data;
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
}
