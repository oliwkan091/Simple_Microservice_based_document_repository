import {Role} from "./Role";

export class User {
  id: number = -1;
  password: string = "";
  name: string = "";
  token: string = "";
  role: Role = Role.USER;


  construct(user: User)
  {
    this.id = user?.id;
    this.password = user.password;
    this.name = user.name;
    this.token = user.token;
    this.role = user.role;
  }
}
