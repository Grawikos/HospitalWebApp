import { Component, OnInit } from '@angular/core';
import {UserService} from '../services/user.service';
import {NgForOf, NgIf} from "@angular/common";
import {Patient} from "../Patient";
import {Doctor} from "../Doctor";
import {User} from "../User";
import {HomeService} from "../services/home.service";
import {SignupInfo} from "../auth/signup-info";
import {AuthService} from "../auth/auth.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatButtonToggle, MatButtonToggleGroup} from "@angular/material/button-toggle";
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
  MatTable
} from "@angular/material/table";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [
    NgForOf,
    FormsModule,
    NgIf,
    ReactiveFormsModule,
    MatButtonToggleGroup,
    MatButtonToggle,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatCell,
    MatCellDef,
    MatHeaderCellDef,
    MatHeaderRow,
    MatRow,
    MatHeaderRowDef,
    MatRowDef,
    MatFormField,
    MatInput,
    MatLabel,
    MatButton
  ],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  patients: Patient[] = [];
  doctors: Doctor[] = [];
  users: User[] = [];
  form: any = {};
  signupInfo?: SignupInfo;
  errorMessage?: string;
  isSignUpFailed = false;
  added: boolean = false;
  deleteChoice:number = -1;
  display: User[] = [];
  displayedColumns: string[] = ["username", "name", "surname", "delete"]
  access: boolean = true;

  constructor(private userService: UserService, private homeService: HomeService, private authService: AuthService) {
    this.added = false;
  }

  ngOnInit() {
    this.homeService.getPatients().subscribe({
      next:(data) => {
        this.patients = data;
      },
      error: (error) => {
        this.access = false;
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
  });
    this.homeService.getDoctors().subscribe({
      next:(data) => {
        this.doctors = data;
      },
      error: (error) => {
        this.access = false;
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    });
    this.homeService.getUsers().subscribe({
      next:(data) => {
        this.users = data;
      },
      error: (error) => {
        this.access = false;
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    });


  }

  createDoctor() {
    console.log(this.form);

    this.signupInfo = new SignupInfo(
      this.form.username,
      this.form.password);

    this.authService.createDoctor(this.signupInfo).subscribe({
      next: (data) =>
      {
        console.log(data);
      }
      ,
      error: (error) => {
        console.log(error);
        this.errorMessage = error.error.message;
      }
    });
    this.added = true;
  }

  createAdmin() {
    console.log(this.form);

    this.signupInfo = new SignupInfo(
      this.form.username,
      this.form.password);

    this.authService.createAdmin(this.signupInfo).subscribe({
      next: (data) =>
      {
        console.log(data);
      }
      ,
      error: (error) => {
        console.log(error);
        this.errorMessage = error.error.message;
      }
    });
    this.added = true;
  }

  deleteUser(){
    this.homeService.deleteUser(this.deleteChoice).subscribe({
      error: (error) => {
        console.log(error);
        this.errorMessage = error.error.message;
      }
    })
    this.deleteChoice = -1;
  }


}
