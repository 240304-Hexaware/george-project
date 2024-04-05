import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { MatGridListModule } from '@angular/material/grid-list';
import { RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [MatGridListModule, RouterLink, MatCardModule, CommonModule],
  templateUrl: './landing.component.html',
  styleUrl: './landing.component.css'
})
export class LandingComponent {

  currentUser: string= ''

  constructor(private userService : UserService) {
    this.userService.loggedIn$.subscribe(data=>{
      this.currentUser = data.username;
    })
    
  }
}
