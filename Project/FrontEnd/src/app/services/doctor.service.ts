import { Injectable } from '@angular/core';
import {Observable, tap} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Visit} from "../Visit";
import {Patient} from "../Patient";
import {VisitForm} from "./VisitForm";

@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  private doctorsUrl = 'http://localhost:8080/doctor';
  private visitsUrl = 'http://localhost:8080/doctor/visits';
  private patientsUrl = 'http://localhost:8080/doctor/patients';


  constructor(private http: HttpClient) { }

  getPatients(): Observable<Patient[]>{
    return this.http.get<Patient[]>(this.patientsUrl)
      .pipe(
        tap(_ => console.log('fetched patients'))
      );
  }

  getDoctorPage(): Observable<Visit[]> {
    return this.http.get<Visit[]>(this.visitsUrl)
      .pipe(
        tap(_ => console.log('fetched appointments'))
      );
  }

  addAppointment(visitForm: VisitForm): Observable<Visit> {
    return this.http.post<Visit>(this.visitsUrl, visitForm)
      .pipe(
        tap(_ => console.log('added visit'))
      );
  }

  deleteAppointment(id: number){
    const url = `${this.doctorsUrl}/${id}`;
    return this.http.delete<void>(url)
      .pipe(
        tap(_ => {
          console.log('deleted visit');
          window.location.reload();
        })
      );
  }
}
