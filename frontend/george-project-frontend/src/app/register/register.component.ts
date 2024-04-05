import { Component, Input } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card'
import { CommonModule, NgOptimizedImage } from '@angular/common';


@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, NgOptimizedImage, MatFormFieldModule, MatInputModule, MatIconModule, MatButtonModule, FormsModule, MatCardModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  usernameControl = new FormControl('', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(20), Validators.pattern('^[a-zA-Z0-9]*$')]));
  passwordControl = new FormControl('', [Validators.required]);


  hide = true;
  wrongCredentials = false;

  username : string = "";
  password : string = "";

  constructor(private userService : UserService, private router : Router) {
  }

  register() {
    if (this.usernameControl.valid && this.passwordControl.valid && this.usernameControl.value != null && this.passwordControl.value != null) {
      this.username = this.usernameControl.value;
      this.password = this.passwordControl.value;
      this.userService.register(this.username, this.password)
      this.userService.loggedIn$.subscribe( value => {
        if (value.loggedIn) {
          this.wrongCredentials = false;
          setTimeout(()=> {
            this.router.navigate(['/']);
          }, 500)
        } else {
          console.log("try again");
          this.wrongCredentials = true;
        }
      })
    } else {
      console.log("form is invalid");
    }
  }
}
