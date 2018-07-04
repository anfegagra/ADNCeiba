import { Vehiculo } from './../../model/vehiculo-modelo';
import { VehiculoService } from './../../services/vehiculo.service';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-salida',
  templateUrl: './salida.component.html',
  styleUrls: ['./salida.component.css']
})
export class SalidaComponent implements OnInit {

  @Input() vehiculos: Vehiculo[];
  placa = ''
  mostrarMensaje: boolean = false;
  mostrarMensajeValor: boolean = false;
  mensajeRespuesta: String = "";
  valorParqueo: String = "";

  constructor(private vehiculoService: VehiculoService) { }

  ngOnInit() {
  }

  registrarSalida(placa){
    this.vehiculoService.postRegistrarSalida(placa).subscribe(res => {
      console.log(res);
      //alert("El total a pagar es: " + res);
      this.mostrarMensajeValor = true;
      this.mostrarMensaje = true;

      this.valorParqueo = "El total a pagar es: " + res;
      setTimeout(()=>{
        this.mostrarMensajeValor = false;
      }, 5000);

      this.mensajeRespuesta = "Retiro exitoso!";
      setTimeout(()=>{
        this.mostrarMensaje = false;
      }, 8000);
      
      this.vehiculos.splice(this.vehiculos.indexOf(placa),1);
    });
  }

  prueba(event: any) {
    alert(event);
  }

}
