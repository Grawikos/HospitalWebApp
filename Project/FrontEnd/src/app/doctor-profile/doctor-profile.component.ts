import {Component, OnInit} from '@angular/core';
import {BsDatepickerModule} from "ngx-bootstrap/datepicker";
import {FormsModule} from "@angular/forms";
import {TokenStorageService} from "../auth/token-storage.service";
import {ProfileService} from "../services/profile.service";
import {Doctor} from "../Doctor";
import {Observable} from "rxjs";
import {Patient} from "../Patient";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import {NgIf} from "@angular/common";
import {Router} from "@angular/router";

@Component({
  selector: 'app-doctor-profile',
  standalone: true,
  imports: [
    BsDatepickerModule,
    FormsModule,
    MatFormField,
    MatInput,
    MatLabel,
    MatButton,
    NgIf
  ],
  templateUrl: './doctor-profile.component.html',
  styleUrl: './doctor-profile.component.css'
})
export class DoctorProfileComponent implements OnInit{
  name?: string;
  surname?: string;
  speciality?: string;
  updated: boolean = false;
  isLogin: boolean = true;
  constructor(private tokenStorage: TokenStorageService, private profileService: ProfileService, private router: Router) { }

  ngOnInit() {
    this.profileService.getDoctor().subscribe({
      next: (data: Doctor) => {
        this.name = data.name;
        this.surname = data.surname;
        this.speciality = data.speciality;
        console.log("fetched doctor's profile" + data.name + data.surname + data.speciality);
      },
      error: (err) =>{
        console.log("err" + err);
        this.isLogin = false;
      }
    });
  }

  update() {
    console.log("update()");
    let doc: Doctor = new Doctor(this.name, this.surname, "", "", this.speciality);
    this.profileService.setDoctor(doc).subscribe({
      next: (updatedDoctor) => {
        console.log('Doctor updated', updatedDoctor)
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
