import { Vehiculo } from './../../model/vehiculo-modelo';
import { VehiculoService } from './../../services/vehiculo.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-parqueadero',
  templateUrl: './parqueadero.component.html',
  styleUrls: ['./parqueadero.component.css']
})
export class ParqueaderoComponent implements OnInit {
  
  vehiculos : Vehiculo[];
  constructor(private vehiculoService: VehiculoService) { }

  ngOnInit() {
    this.consultarVehiculos();
  }

  consultarVehiculos() {
    this.vehiculoService.getVehiculos().subscribe(res => {
      this.vehiculos = res;
      console.log(res)
    });
  }

}
