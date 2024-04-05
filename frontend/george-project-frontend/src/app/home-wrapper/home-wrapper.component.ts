import { Component } from '@angular/core';
import { HomeComponent } from "../home/home.component";
import { LandingComponent } from "../landing/landing.component";
import { UserService } from '../user.service';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-home-wrapper',
    standalone: true,
    templateUrl: './home-wrapper.component.html',
    styleUrl: './home-wrapper.component.css',
    imports: [CommonModule, HomeComponent, LandingComponent]
})
export class HomeWrapperComponent {
  loggedIn : boolean = false;

  constructor(private userService : UserService) {
    this.userService.loggedIn$.subscribe(data=> {
      this.loggedIn = data.loggedIn;
    })
  }


}
