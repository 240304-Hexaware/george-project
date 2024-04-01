import { Routes } from '@angular/router';
import { FileComponent } from './file/file.component';
import { RecordsComponent } from './records/records.component';

export const routes: Routes = [
    {path: 'file', component: FileComponent},
    {path: 'records', component: RecordsComponent}
];
