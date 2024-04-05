import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { FileComponent } from "./file/file.component";
import { RecordsComponent } from "./records/records.component";
import { NavComponent } from "./nav/nav.component";

@Component({
    selector: 'app-root',
    standalone: true,
    templateUrl: './app.component.html',
    styleUrl: './app.component.css',
    imports: [RouterOutlet, FormsModule, FileComponent, RecordsComponent, NavComponent]
})
export class AppComponent {
  title = 'Gahan';
}
