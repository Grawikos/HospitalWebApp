import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userUrl = 'http://localhost:8080/exampleSecurity/user';
  private adminUrl = 'http://localhost:8080/exampleSecurity/admin';

  constructor(private http: HttpClient) { }

  getUserPage(): Observable<string> {
    return this.http.get(this.userUrl, { responseType: 'text' });
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
