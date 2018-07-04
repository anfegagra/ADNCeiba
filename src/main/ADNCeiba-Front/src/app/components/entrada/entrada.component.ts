import { VehiculoService } from './../../services/vehiculo.service';
import { Vehiculo } from './../../model/vehiculo-modelo';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-entrada',
  templateUrl: './entrada.component.html',
  styleUrls: ['./entrada.component.css']
})
export class EntradaComponent implements OnInit {

  placa = '';
  cilindraje;
  mostrarMensaje: boolean = false;
  mensajeRespuesta: String = "";
  error: boolean= false;
  @Output() vehiculoGuardado = new EventEmitter<boolean>(); 

  vehiculos: Vehiculo[];

  constructor(private vehiculoService: VehiculoService) { }

  ngOnInit() {
  }

  registrarEntrada() {
    const vehiculo = this.crearVehiculo(this.cilindraje);
    this.vehiculoService.postRegistrarIngreso(vehiculo).subscribe(res => {
      this.mostrarMensaje =true;
      if(res != null){
        this.error=false;
        this.vehiculoService.getVehiculos().subscribe(res => {
        this.vehiculoGuardado.emit();
        this.mensajeRespuesta = "Registro exitoso!";
        setTimeout(()=>{
          this.mostrarMensaje = false;
        }, 3000);
          console.log(res);
        });
      } else {
        this.error=true;
        this.mensajeRespuesta = "Registro fallido!";
        setTimeout(()=>{          
          this.mostrarMensaje = false;
        }, 3000);
      }     
    });
  }

  crearVehiculo(cilindraje) {
    const tipo = this.validarTipo(cilindraje);
    const vehiculo: Vehiculo = {
      placa: this.placa,
      tipo: tipo,
      cilindraje: this.cilindraje
    }
    return vehiculo;
  }

  validarTipo(cilindraje) {
    return cilindraje ? 'M' : 'C';
  }

}
