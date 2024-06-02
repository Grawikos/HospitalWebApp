import {Component, OnInit} from '@angular/core';
import {UserService} from "../services/user.service";
import {Visit} from "../Visit";
import {NgForOf, NgIf} from "@angular/common";
import {Router} from "@angular/router";


@Component({
  selector: 'app-patient-visits',
  standalone: true,
  imports: [
    NgForOf,
    NgIf
  ],
  templateUrl: './patient-visits.component.html',
  styleUrl: './patient-visits.component.css'
})
export class PatientVisitsComponent implements OnInit {
  visits?: Visit[];
  errorMessage?: string;
  isLoggin?: boolean;

  constructor(private userService: UserService, private router: Router) {
    this.isLoggin = true;
  }

  ngOnInit() {
    this.userService.getUserPage().subscribe({
      next: (data) => {
        this.visits = data;
      }
      ,
      error: (error) => {
        console.log('was error');
        this.isLoggin = false;
        // this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    });
  }


  redirectHome() {
    this.router.navigate(['/home']);
  }

  redirectLogin() {
    this.router.navigate(['/login']);
  }

}
