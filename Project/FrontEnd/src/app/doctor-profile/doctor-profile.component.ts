import {Component, OnInit} from '@angular/core';
import {BsDatepickerModule} from "ngx-bootstrap/datepicker";
import {FormsModule} from "@angular/forms";
import {TokenStorageService} from "../auth/token-storage.service";
import {ProfileService} from "../services/profile.service";
import {Doctor} from "../Doctor";
import {Observable} from "rxjs";
import {Patient} from "../Patient";

@Component({
  selector: 'app-doctor-profile',
  standalone: true,
  imports: [
    BsDatepickerModule,
    FormsModule
  ],
  templateUrl: './doctor-profile.component.html',
  styleUrl: './doctor-profile.component.css'
})
export class DoctorProfileComponent implements OnInit{
  name?: string;
  surname?: string;
  speciality?: string;
  constructor(private tokenStorage: TokenStorageService, private profileService: ProfileService) { }

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
      }
    });
  }

  update() {
    console.log("update()");
    let doc: Doctor = new Doctor(this.name, this.surname, "", "", this.speciality);
    this.profileService.setDoctor(doc).subscribe({
      next: (updatedDoctor) => console.log('Doctor updated', updatedDoctor),
      error: (err) => console.error('Error updating doctor', err)
    });
  }
}
