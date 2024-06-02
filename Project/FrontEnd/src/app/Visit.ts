import {Patient} from "./Patient";
import {Doctor} from "./Doctor";

export class Visit {
  id?: number;
  patient: Patient;
  doctor: Doctor;
  appointmentDate: Date;
  day: number
  // upcomming: boolean;

  constructor(patient: Patient, doctor: Doctor, appDate: Date) {
    this.patient = patient;
    this.doctor = doctor;
    this.appointmentDate = appDate;
    this.day = appDate.getDay();
  }

}
