import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-ruedas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './ruedas.component.html',

  styleUrls: ['./ruedas.component.css']
})
export class RuedasComponent {

  private baseUrl = 'http://backend414-production.up.railway.app/ruedas';

  items: any[] = [];

  isEditing: boolean = false;

  rueda: any = {
    material: '',
    color: '',
    price: null,
    width: null,
    bicicleta: { id: null }
  };

  constructor(private http: HttpClient) {
    this.getRuedas();
  }

  getRuedas() {
    this.http.get<any[]>('http://backend414-production.up.railway.app/ruedas').subscribe({
      next: (data) => (this.items = data),
      error: (err) => console.error('Error al cargar ruedas', err)
    });
  }

  postRueda() {
    this.http.post('http://backend414-production.up.railway.app/ruedas', this.rueda).subscribe({
      next: () => {
        this.reset();
        this.getRuedas();
      },
      error: (err) => alert('Error al crear rueda: ' + err.error)
    });
  }

  startEdit(item: any) {
    this.isEditing = true;
    this.rueda = JSON.parse(JSON.stringify(item));
  }

  putRueda() {
    const id = this.rueda.ruedaId;

    this.http.put(`http://backend414-production.up.railway.app/ruedas/${id}`, this.rueda).subscribe({
      next: () => {
        this.reset();
        this.getRuedas();
      },
      error: (err) => alert('Error al actualizar: ' + err.error)
    });
  }

  deleteRueda(id: number) {
    if (!confirm('¿Eliminar rueda?')) return;

    this.http.delete(`http://backend414-production.up.railway.app/ruedas/${id}`).subscribe({
      next: () => this.getRuedas(),
      error: (err) => alert('Error al eliminar')
    });
  }

  reset() {
    this.isEditing = false;
    this.rueda = {
      material: '',
      color: '',
      price: null,
      width: null,
      bicicleta: { id: null }
    };
  }
}
