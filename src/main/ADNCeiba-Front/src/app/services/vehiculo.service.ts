import { Vehiculo } from './../model/vehiculo-modelo';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class VehiculoService {

  constructor(private http: HttpClient) { }

  private url = 'http://localhost:8080/ceiba/vehiculos/';
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
    })
  };

  getVehiculos() {
    return this.http.get<Vehiculo[]>(this.url);
  }

  getVehiculosPorPlaca(placa: string) {
    return this.http.get<Vehiculo>(this.url + placa);
  }

  postRegistrarIngreso(vehiculo){
    return this.http.post<Vehiculo>(this.url, JSON.stringify(vehiculo), this.httpOptions);
  }

  postRegistrarSalida(placa){
    return this.http.post(this.url + 'salida/' + placa, placa);
  }
}
