import {User} from "./User";
import {Doctor} from "./Doctor";

export class Patient extends User{
  familyDoctor: Doctor;

  constructor(firstname: string, surname: string, username: string, password: string, familyDoctor: Doctor) {
    super(firstname, surname, username, password)
    this.familyDoctor = familyDoctor
  }

}
