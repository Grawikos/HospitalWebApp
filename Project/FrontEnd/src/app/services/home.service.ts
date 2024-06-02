import { Injectable } from '@angular/core';
import {Observable, tap} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Visit} from "../Visit";
import {Doctor} from "../Doctor";

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  private homeUrl = 'http://localhost:8080/home';

  constructor(private http: HttpClient) { }

  getDoctors(): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(this.homeUrl)
      .pipe(
        tap(_ => console.log('fetched doctors'))
      );
  }

}
