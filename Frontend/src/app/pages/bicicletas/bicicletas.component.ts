import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-bicicletas',
  imports: [CommonModule, FormsModule],
  templateUrl: './bicicletas.component.html',
  styleUrls: ['./bicicletas.component.css']
})
export class BicicletasComponent {

  private http = inject(HttpClient);
  private baseUrl = 'https://backend414-production.up.railway.app/bicicletas';
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

  /** GET ALL */
  getBicicletas() {
    this.http.get('https://backend414-production.up.railway.app/bicicletas')
      .subscribe(data => {
        this.items = data as any[];
      });
  }

  /** GET ONE */
  getBicicletaById(id: number) {
    this.http.get(`https://backend414-production.up.railway.app/bicicletas/${id}`)
      .subscribe(data => {
        console.log("DETALLE DE LA BICICLETA:", data);
        alert(JSON.stringify(data, null, 2));
      });
  }

  /** ACTIVAR MODO EDICIÓN */
  startEdit(item: any) {
    this.isEditing = true;
    this.bicicleta = { ...item };
  }

  /** REINICIAR FORM */
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

  /** POST */
  postBicicleta() {

  const biciSinId = {
    marca: this.bicicleta.marca,
    tipo: this.bicicleta.tipo,
    precio: this.bicicleta.precio,
    disponible: this.bicicleta.disponible
  };

  this.http.post(`https://backend414-production.up.railway.app/bicicletas`, biciSinId, { observe: 'response' })
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


  /** PUT */
  putBicicleta() {
    this.http.put(`https://backend414-production.up.railway.app/bicicletas/${this.bicicleta.id}`, this.bicicleta, { observe: 'response' })
      .subscribe({
        next: () => {
          this.getBicicletas();
          this.reset();
        },
        error: err => {
          console.error("Error al actualizar:", err);
          alert("No se pudo actualizar la bicicleta.");
        }
      });
  }

  /** DELETE */
  deleteBicicleta(id: number) {

    if (!confirm("¿Seguro que desea eliminar esta bicicleta?")) return;

    this.http.delete(`https://backend414-production.up.railway.app/bicicletas/${id}`, { observe: 'response' })
      .subscribe({
        next: resp => {
          if (resp.status === 204 || resp.status === 200) {
            this.items = this.items.filter(item => item.id !== id);
          }
        },
        error: err => {
          console.error("Error al eliminar:", err);
          alert("No se puede eliminar. Puede estar relacionada con otra tabla.");
        }
      });
  }

}
