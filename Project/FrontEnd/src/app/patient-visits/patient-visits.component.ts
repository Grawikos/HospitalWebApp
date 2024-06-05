import {Component, numberAttribute, OnInit} from '@angular/core';
import {UserService} from "../services/user.service";
import {Visit} from "../Visit";
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {Router} from "@angular/router";


@Component({
  selector: 'app-patient-visits',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    DatePipe
  ],
  templateUrl: './patient-visits.component.html',
  styleUrl: './patient-visits.component.css'
})
export class PatientVisitsComponent implements OnInit {
  visits: Visit[] = [];
  prevVisits: Visit[] = [];
  upcomingVisits: Visit[] = [];
  errorMessage?: string;
  isLoggin?: boolean;

  constructor(private userService: UserService, private router: Router) {
    this.isLoggin = true;
  }

  ngOnInit() {
    this.userService.getUserPage().subscribe({
      next: (data: Visit[]) => {
        this.visits = data;
        this.shuffleVisits();
      },
      error: (error) => {
        console.log('was error');
        this.isLoggin = false;
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    });
  }

  shuffleVisits(){
    const now = new Date();
    this.upcomingVisits = this.visits.filter(visit => new Date(visit.appointmentDate) > now);
    this.prevVisits = this.visits.filter(visit => new Date(visit.appointmentDate) <= now);
  }



  redirectHome() {
    this.router.navigate(['/home']);
  }

  redirectLogin() {
    this.router.navigate(['/login']);
  }

}
