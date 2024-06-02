import { Injectable } from '@angular/core';
import {Observable, tap} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Visit} from "../Visit";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userUrl = 'http://localhost:8080/user';
  private adminUrl = 'http://localhost:8080/admin';


  constructor(private http: HttpClient) { }

  getUserPage(): Observable<Visit[]> {
    return this.http.get<Visit[]>(this.userUrl)
      .pipe(
        tap(_ => console.log('fetched visits'))
      );
  }

  getAdminPage(): Observable<string> {
    // return this.http.get(this.adminUrl, { responseType: 'text' });
  let abc =  this.http.get(this.adminUrl, { responseType: 'text' });
  abc.subscribe({
    next: value => console.log("v " + value),
    error: err => console.log("er " + err),
    complete: () => console.log("cmplt")
  })
  return abc;
  }

}
