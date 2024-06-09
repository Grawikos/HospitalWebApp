import { Component, OnInit } from '@angular/core';
import {AuthService} from '../auth/auth.service';
import {SignupInfo} from '../auth/signup-info';
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    FormsModule,
    MatFormField,
    MatInput,
    MatButton,
    MatLabel
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  form: any = {};
  signupInfo?: SignupInfo;
  isSignedUp = false;
  isSignUpFailed = false;
  errorMessage = '';

  constructor(private authService: AuthService) { }

  ngOnInit() { }

  onSubmit() {
    console.log(this.form);

    this.signupInfo = new SignupInfo(
      this.form.username,
      this.form.password);

    this.authService.signUp(this.signupInfo).subscribe({
      next: (data) =>
    {
      console.log(data);
      this.isSignedUp = true;
      this.isSignUpFailed = false;
    }
  ,
    error: (error) => {
      console.log(error);
      this.errorMessage = error.error.message;
      this.isSignUpFailed = true;
    }
  });
  }
}
