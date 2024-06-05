import {Component, OnInit} from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {TokenStorageService} from "./auth/token-storage.service";
import {NgIf} from "@angular/common";
import {MatToolbar, MatToolbarRow} from "@angular/material/toolbar";
import {MatAnchor} from "@angular/material/button";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HomeComponent, RouterLink, NgIf, MatToolbarRow, MatToolbar, MatAnchor],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  title = 'Clinic thingy';
  private roles?: string[];
  authority?: string;
  constructor(private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      console.log(this.tokenStorage.getToken());
      this.roles = this.tokenStorage.getAuthorities();
      if(this.roles.length == 0){
        console.log("0");
        this.authority = 'user';
        return true;
      }
      this.roles.every(role => {
        if (role === 'ROLE_ADMIN') {
          this.authority = 'admin';
          return false;
        } else if (role === 'ROLE_USER'){
          this.authority = 'patient';
          return false;
        } else if (role === 'ROLE_DOCTOR'){
          this.authority = 'doctor';
          return false;
        }
        console.log("user");
        this.authority = 'user';
        return true;
      });
    }
    return true;
  }
}
