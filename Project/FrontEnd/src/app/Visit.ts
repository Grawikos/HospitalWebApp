import {Patient} from "./Patient";
import {Doctor} from "./Doctor";

export class Visit {
  id: number = -1;
  patient: Patient;
  doctor: Doctor;
  appointmentDate: Date;

  constructor(patient: Patient, doctor: Doctor, appDate: Date) {
    this.patient = patient;
    this.doctor = doctor;
    this.appointmentDate = appDate;
  }

}
