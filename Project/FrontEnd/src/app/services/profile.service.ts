import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, tap} from "rxjs";
import {Patient} from "../Patient";
import {Doctor} from "../Doctor";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  private patientUrl = 'http://localhost:8080/user/profile';
  private doctorUrl = 'http://localhost:8080/doctor/profile';

  constructor(private http: HttpClient) { }

  getPatient(): Observable<Patient> {
    return this.http.get<Patient>(this.patientUrl)
      .pipe(
        tap(_ => console.log('fetched patient'))
      );
  }

  getDoctor(): Observable<Doctor> {
    return this.http.get<Doctor>(this.doctorUrl)
      .pipe(
        tap(_ => console.log('fetched doctor'))
      );
  }

  setDoctor(updates: Doctor){
    return this.http.patch<Doctor>(this.doctorUrl, updates).pipe(
      tap(_ => console.log('update doctor'))
    );
  }

  setPatient(updates: Patient){
    return this.http.patch<Patient>(this.patientUrl, updates).pipe(
      tap(_ => console.log('update doctor'))
    );
  }
}
