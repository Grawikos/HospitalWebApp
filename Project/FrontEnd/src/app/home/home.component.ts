import {Component, OnInit} from '@angular/core';
import {RouterLink} from "@angular/router";
import {HomeService} from "../services/home.service";
import {Doctor} from "../Doctor";
import {NgForOf, NgIf} from "@angular/common";
import {MatToolbar, MatToolbarRow} from "@angular/material/toolbar";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    RouterLink,
    NgForOf,
    NgIf,
    MatToolbar,
    MatToolbarRow
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  homeService: HomeService;
  doctors?: Doctor[];
  constructor(homeService: HomeService) {
    this.homeService = homeService;
  }

  ngOnInit(): void {
        this.homeService.getDoctors().subscribe({
          next: (data) => {
            this.doctors = data;
          }
          ,
          error: (error) => {
            console.log('cannot fetch doctors');
          }
        });
    }



}
