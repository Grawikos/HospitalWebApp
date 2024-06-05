export class VisitForm {
  patientId: number;
  appointmentDate: string;

  constructor(patient: number, appDate: string) {
    this.patientId = patient;
    this.appointmentDate = appDate;
  }

}
