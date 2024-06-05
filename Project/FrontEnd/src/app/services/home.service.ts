import { Injectable } from '@angular/core';
import {Observable, tap} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Visit} from "../Visit";
import {Doctor} from "../Doctor";
import {Patient} from "../Patient";
import {User} from "../User";

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  private doctorUrl = 'http://localhost:8080/home/doctors';
  private patientUrl = 'http://localhost:8080/home/patients';
  private userUrl = 'http://localhost:8080/home/users';


  constructor(private http: HttpClient) { }

  getDoctors(): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(this.doctorUrl)
      .pipe(
        tap(_ => console.log('fetched doctors'))
      );
  }

  getPatients(): Observable<Patient[]> {
    return this.http.get<Patient[]>(this.patientUrl)
      .pipe(
        tap(_ => console.log('fetched doctors'))
      );
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.userUrl)
      .pipe(
        tap(_ => console.log('fetched doctors'))
      );
  }

  deleteUser(id: number){
    const url = `${this.userUrl}/${id}`;
    return this.http.delete(url)
      .pipe(
        tap(_ => console.log('deleted user'))
      )
  }

}
