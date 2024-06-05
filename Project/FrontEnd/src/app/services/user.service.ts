import { Injectable } from '@angular/core';
import {Observable, tap} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Visit} from "../Visit";
import {Patient} from "../Patient";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userUrl = 'http://localhost:8080/user';


  constructor(private http: HttpClient) { }

  getUserPage(): Observable<Visit[]> {
    return this.http.get<Visit[]>(this.userUrl)
      .pipe(
        tap(_ => console.log('fetched visits'))
      );
  }



}
