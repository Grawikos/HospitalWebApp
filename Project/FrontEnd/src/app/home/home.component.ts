import {Component, OnInit} from '@angular/core';
import {RouterLink} from "@angular/router";
import {HomeService} from "../services/home.service";
import {Doctor} from "../Doctor";
import {NgForOf, NgIf} from "@angular/common";
import {MatToolbar, MatToolbarRow} from "@angular/material/toolbar";
import {MatList, MatListItem} from "@angular/material/list";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    RouterLink,
    NgForOf,
    NgIf,
    MatToolbar,
    MatToolbarRow,
    MatList,
    MatListItem,
    FormsModule
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  homeService: HomeService;
  doctors: Doctor[] = [];
  searchTerm = '';
  sortOption = 'name';
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

  filteredDoctors() {
    let filtered = this.doctors.filter(doctor =>
      `${doctor.name ?? ''} ${doctor.surname ?? ''} ${doctor.speciality ?? ''}`
        .toLowerCase()
        .includes(this.searchTerm.toLowerCase())
    );

    if (this.sortOption === 'name') {
      filtered.sort((a, b) =>
        (a.name ?? '').localeCompare(b.name ?? '') || (a.surname ?? '').localeCompare(b.surname ?? '')
      );
    } else if (this.sortOption === 'speciality') {
      filtered.sort((a, b) => (a.speciality ?? '').localeCompare(b.speciality ?? ''));
    }

    return filtered;
  }




}
