import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LoginInfo} from './login-info';
import {Observable} from 'rxjs';
import {JwtResponse} from './jwt-response';
import {SignupInfo} from './signup-info';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loginUrl = 'http://localhost:8080/auth/signin';
  private signupUrl = 'http://localhost:8080/auth/signup';
  private doctorUrl = 'http://localhost:8080/auth/admin/register-doctor';
  private adminUrl = 'http://localhost:8080/auth/admin/register-admin';

  constructor(private http: HttpClient) { }

  attemptAuth(credentials: LoginInfo): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(this.loginUrl, credentials, httpOptions);
  }

  signUp(info: SignupInfo): Observable<string> {
    return this.http.post<string>(this.signupUrl, info, httpOptions);
  }

  createDoctor(info: SignupInfo): Observable<string> {
    return this.http.post<string>(this.doctorUrl, info, httpOptions);
  }
  createAdmin(info: SignupInfo): Observable<string> {
    return this.http.post<string>(this.adminUrl, info, httpOptions);
  }
}
