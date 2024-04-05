import { Component, Input } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card'
import { CommonModule, NgOptimizedImage } from '@angular/common';



@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, NgOptimizedImage, MatFormFieldModule, MatInputModule, MatIconModule, MatButtonModule, FormsModule, MatCardModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  hide = true;
  wrongCredentials = false;
  
  usernameControl = new FormControl('', [Validators.required]);
  passwordControl = new FormControl('', [Validators.required]);

  username : string = "";
  password : string = "";

  constructor(private userService : UserService, private router : Router) {}

  login() {
    if (this.usernameControl.valid && this.passwordControl.valid && this.usernameControl.value != null && this.passwordControl.value != null) {
      this.username = this.usernameControl.value;
      this.password = this.passwordControl.value;
      this.userService.login(this.username, this.password)
      this.userService.loggedIn$.subscribe( value => {
        if (value.loggedIn) {
          this.wrongCredentials = false;
          setTimeout(()=> {
            this.router.navigate(['/']);
          }, 500)
        } else if (value.loggedIn == false){
          console.log("try again");
          this.wrongCredentials = true;
        }
      })
    }
  }

}
