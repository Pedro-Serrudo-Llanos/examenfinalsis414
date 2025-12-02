import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { BicicletasComponent } from '../bicicletas/bicicletas.component';
import { RuedasComponent } from '../ruedas/ruedas.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [FormsModule, CommonModule, BicicletasComponent, RuedasComponent],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  private http = inject(HttpClient);
  private baseUrl = 'https://peppy-kheer-4ac26c.netlify.app/bicicletas';
  items: any[] = [];

  bicicleta = {
    id: 0,
    marca: '',
    tipo: '',
    precio: 0,
    disponible: false
  };

  isEditing = false;

  constructor() {
    this.getBicicletas();
  }

  getBicicletas() {
    this.http.get('https://peppy-kheer-4ac26c.netlify.app/bicicletas')
      .subscribe(data => this.items = data as any[]);
  }

  getBicicletaById(id: number) {
    this.http.get(`https://peppy-kheer-4ac26c.netlify.app/bicicletas/${id}`)
      .subscribe(data => console.log(data));
  }

  postBicicleta() {
    this.http.post(`https://peppy-kheer-4ac26c.netlify.app/bicicletas`, this.bicicleta, { observe: 'response' })
      .subscribe({
        next: () => {
          this.getBicicletas();
          this.reset();
        },
        error: err => {
          console.error("Error al crear:", err);
          alert("No se pudo crear la bicicleta.");
        }
      });
  }

  putBicicleta() {
    this.http.put(`https://peppy-kheer-4ac26c.netlify.app/bicicletas/${this.bicicleta.id}`, this.bicicleta, { observe: 'response' })
      .subscribe({
        next: () => {
          this.getBicicletas();
          this.reset();
        },
        error: err => {
          console.error("Error al actualizar:", err);
          alert("No se pudo actualizar.");
        }
      });
  }

  deleteBicicleta(id: number) {
    this.http.delete(`https://peppy-kheer-4ac26c.netlify.app/bicicletas/${id}`, { observe: 'response' })
      .subscribe({
        next: resp => {
          if (resp.status === 200 || resp.status === 204)
            this.items = this.items.filter(item => item.id !== id);
        },
        error: err => {
          console.error("Error al eliminar:", err);
          alert("No se puede eliminar la bicicleta.");
        }
      });
  }

  startEdit(item: any) {
    this.isEditing = true;
    this.bicicleta = { ...item };
  }

  reset() {
    this.isEditing = false;
    this.bicicleta = {
      id: 0,
      marca: '',
      tipo: '',
      precio: 0,
      disponible: false
    };
  }
}
