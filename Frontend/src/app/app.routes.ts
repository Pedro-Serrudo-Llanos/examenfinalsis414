import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { BicicletasComponent } from './pages/bicicletas/bicicletas.component';
import { ContactComponent } from './pages/contact/contact.component';

export const routes: Routes = [
    { path: '', redirectTo: 'home', pathMatch: 'full' },
    { path: 'home', component: HomeComponent },
    {path: 'bicicletas', component: BicicletasComponent},
    {path: 'contact', component: ContactComponent}

];
