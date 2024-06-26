import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user.model';
import { UserService } from '../../services/user.service';
import { Department } from '../../models/department.model';
import { DepartmentService } from '../../services/department.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css'],
})
export class AddUserComponent implements OnInit {
  departments?: Department[];
  user: User = {
    name: '',
    surname: '',
    email: '',
    department: null
  };
  submitted = false;
  message = '';

  constructor(
    private UserService: UserService,
    private departmentService: DepartmentService
  ) {}

  ngOnInit(): void {
    this.retrieveDepartments();
  }

  saveUser(): void {
    if (this.isUserComplete(this.user)) {
      const data = {
        name: this.user.name,
        surname: this.user.surname,
        email: this.user.email,
        department: this.user.department
      };

      this.UserService.create(data).subscribe({
        next: (res) => {
          console.log(res);
          this.message = '';
          this.submitted = true;
        },
        error: (e) => console.error(e)
      });
    } else {
      this.message =  'Todos os campos são obrigatórios, favor preenchê-los!';
    }
  }

  isUserComplete(User: User): boolean {
    for (let key in User) {
      if (User.hasOwnProperty(key)) {
        // @ts-ignore
        if (User[key] === null || User[key] === '' || User[key] === undefined) {
          return false;
        }
      }
    }
    return true;
  }

  retrieveDepartments(): void {
    this.departmentService.getAll().subscribe({
      next: (data) => {
        this.departments = data;
        console.log(data);
      },
      error: (e) => console.error(e)
    });
  }

  newUser(): void {
    this.message = '';
    this.submitted = false;
    this.user = {
      name: '',
      surname: '',
      email: '',
      department: null
    };
  }
}
