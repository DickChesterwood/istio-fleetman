
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Inject } from '@angular/core';

import { AppComponent } from './app.component';
import { VehiclesComponent } from './vehicles/vehicles.component';
import { VehicleService } from './vehicle.service';

import { HttpClientModule }    from '@angular/common/http';
import { MapComponent } from './map/map.component';

import { LeafletModule } from '@asymmetrik/ngx-leaflet';

import {StompConfig, StompService} from '@stomp/ng2-stompjs';
import { HeaderComponent } from './header/header.component';

import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import { environment } from '../environments/environment';
import { RouterModule, Routes } from '@angular/router';
import { StaticVehicleComponent } from './static-vehicle/static-vehicle.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSnackBar } from '@angular/material/snack-bar';
import { OverlayModule } from "@angular/cdk/overlay";
import { MatSnackBarModule } from '@angular/material';


const stompConfig: StompConfig = {
     url: environment.gatewayUrl.replace('http','ws') + "/updates",
     headers: {
     },
     heartbeat_in: 0, // Typical value 0 - disabled
     heartbeat_out: 20000, // Typical value 20000 - every 20 seconds
     reconnect_delay: 5000,
     debug: false
};

const appRoutes: Routes = [
  { path: '', redirectTo: '/vehicle/City%20Truck', pathMatch: 'full' },
  { path: 'vehicle/:vehicleName', component: StaticVehicleComponent },
  { path: '**', redirectTo: '/vehicle/City%20Truck', pathMatch: 'full' }
];

@NgModule({
  declarations: [
    AppComponent,
    VehiclesComponent,
    MapComponent,
    HeaderComponent,
    StaticVehicleComponent
  ],
  entryComponents: [],

  imports: [
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: false }
    ),
    BrowserModule,
    HttpClientModule,
    LeafletModule.forRoot(),
    BrowserAnimationsModule,
    OverlayModule,
    MatSnackBarModule  
  ],
  providers: [VehicleService,
              MatSnackBar,
              StompService,
              {
                 provide: StompConfig,
                 useValue: stompConfig
              }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
