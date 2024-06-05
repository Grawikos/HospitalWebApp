import {User} from "./User";

export class Doctor extends User{
  speciality: string;

  constructor(firstname: string = '', surname: string = '', username: string = '', password: string = '', speciality: string = '') {
    super(firstname, surname, username, password)
    this.speciality = speciality
  }

}
