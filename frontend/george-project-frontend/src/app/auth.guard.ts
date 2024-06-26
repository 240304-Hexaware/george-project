import { CanActivateFn } from '@angular/router';
import { UserService } from './user.service';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = (route, state) => {
  return inject(UserService).isLoggedIn();
};
