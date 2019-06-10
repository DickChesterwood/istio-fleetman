
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

import { DOCUMENT } from '@angular/platform-browser';

const stompConfig: StompConfig = {
     url: "ws://" + window.location.hostname + ":" + window.location.port + "/api/updates",
     headers: {
     },
     heartbeat_in: 0, // Typical value 0 - disabled
     heartbeat_out: 20000, // Typical value 20000 - every 20 seconds
     reconnect_delay: 5000,
     debug: true
};

@NgModule({
  declarations: [
    AppComponent,
    VehiclesComponent,
    MapComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    LeafletModule.forRoot(),
    NgbModule.forRoot()
  ],
  providers: [VehicleService,
              StompService,
              {
                 provide: StompConfig,
                 useValue: stompConfig
              }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
