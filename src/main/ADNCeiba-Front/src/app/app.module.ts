import { ConsultaComponent } from './components/consulta/consulta.component';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http'
import { FormsModule } from "@angular/forms";

import { AppComponent } from './app.component';
import { EntradaComponent } from './components/entrada/entrada.component';
import { SalidaComponent } from './components/salida/salida.component';
import { ParqueaderoComponent } from './components/parqueadero/parqueadero.component';

@NgModule({
  declarations: [
    AppComponent,
    EntradaComponent,
    SalidaComponent,
    ConsultaComponent,
    ParqueaderoComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
