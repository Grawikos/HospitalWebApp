export class User {
  id?: number;
  name: string;
  surname: string;
  username: string;
  password: string;

  constructor(firstname: string, surname: string, username: string, password: string) {
    this.name = firstname;
    this.surname = surname;
    this.username = username;
    this.password = password;
  }

}
