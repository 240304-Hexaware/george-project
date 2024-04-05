import { Routes } from '@angular/router';
import { FileComponent } from './file/file.component';
import { RecordsComponent } from './records/records.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { authGuard } from './auth.guard';
import { HomeWrapperComponent } from './home-wrapper/home-wrapper.component';

export const routes: Routes = [
    {path: '', component: HomeWrapperComponent},
    {path: 'register', component : RegisterComponent},
    {path: 'login', component: LoginComponent},
    {path: 'file', component: FileComponent, canActivate : [authGuard]},
    {path: 'records', component: RecordsComponent, canActivate : [authGuard]},
    {path: '404', component: NotFoundComponent},
    {path: '**', redirectTo: '/404'}

];
