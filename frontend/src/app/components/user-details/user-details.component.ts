import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../../models/user.model';
import { UserService } from '../../services/user.service';
import { Department } from '../../models/department.model';
import { DepartmentService } from '../../services/department.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css'],
})
export class UserDetailsComponent implements OnInit {
  @Input() viewMode = false;

  @Input() currentUser: User = {
    name: '',
    surname: '',
    email: '',
    department: ''
  };
  message = '';

  departments?: Department[];

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private departmentService: DepartmentService
  ) {}

  ngOnInit(): void {
    this.retrieveDepartments()
    if (!this.viewMode) {
      this.message = '';
      this.getUser(this.route.snapshot.params['id']);
    }
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

  getUser(id: string): void {
    this.userService.get(id).subscribe({
      next: (data) => {
        this.currentUser = data;
        console.log(data);
      },
      error: (e) => console.error(e)
    });
  }

  updateUser(): void {
    this.message = '';

    this.userService
      .update(this.currentUser.id, this.currentUser)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.message = res.message
            ? res.message
            : 'Usuário atualizado com sucesso!';
        },
        error: (e) => console.error(e)
      });
  }

  deleteUser(): void {
    this.userService.delete(this.currentUser.id).subscribe({
      next: (res) => {
        console.log(res);
        this.router.navigate(['/user']);
      },
      error: (e) => console.error(e)
    });
  }

}
