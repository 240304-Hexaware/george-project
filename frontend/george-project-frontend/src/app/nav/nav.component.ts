import { CommonModule, NgOptimizedImage } from '@angular/common';
import { Component } from '@angular/core';
import {MatToolbarModule} from '@angular/material/toolbar';

import { RouterLink } from '@angular/router';
import { UserService } from '../user.service';
import { MatButtonModule } from '@angular/material/button';
import { User } from '../user';
import { Router } from '@angular/router';


@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [MatToolbarModule, RouterLink, NgOptimizedImage, CommonModule, MatButtonModule],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent {
  user: User = {loggedIn: false, username:'', password:''}

  constructor(private userService : UserService, private router : Router) {
    this.userService.loggedIn$.subscribe(user => {
      this.user = user;
    });
  }
  
  logout() {
    this.userService.logout();
    this.router.navigate(["/"]);
  }
}
