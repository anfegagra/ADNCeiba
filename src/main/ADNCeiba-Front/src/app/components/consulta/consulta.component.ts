import { Vehiculo } from './../../model/vehiculo-modelo';
import { Component, OnInit } from '@angular/core';
import { VehiculoService } from '../../services/vehiculo.service';

@Component({
  selector: 'app-consulta',
  templateUrl: './consulta.component.html',
  styleUrls: ['./consulta.component.css']
})
export class ConsultaComponent implements OnInit {
  vehiculo : Vehiculo;

  constructor(private vehiculoService: VehiculoService) { }

  ngOnInit() {
  }

  consultarVehiculo(placa: string) {
    this.vehiculoService.getVehiculosPorPlaca(placa).subscribe(res => {
      this.vehiculo = res;
      console.log(res);
    });
  }

}
