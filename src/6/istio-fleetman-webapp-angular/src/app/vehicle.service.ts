import { Injectable } from '@angular/core';
import { Vehicle } from './vehicle';
import { Staff } from './staff';
import { Observable ,  Subscription, BehaviorSubject ,  of , interval} from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

import {  LatLng } from 'leaflet';

import { environment } from '../environments/environment';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable()
export class VehicleService  {

  subscription: BehaviorSubject<any>;
  centerVehicle: BehaviorSubject<Vehicle>;
  centerVehicleHistory: BehaviorSubject<any>;
  currentDriver: BehaviorSubject<any>;
  timedUpdate: Subscription;
  source = interval(1000);

  constructor(private http: HttpClient, private snackBar: MatSnackBar) {
    // Store local reference to Observable
    // for use with template ( | async )
    this.subscription = new BehaviorSubject(null);
    this.centerVehicle = new BehaviorSubject(null);
    this.centerVehicleHistory = new BehaviorSubject(null);
    this.currentDriver = new BehaviorSubject(null);
    this.timedUpdate = this.source.subscribe(val =>   this.http.get("http://" + window.location.hostname + ":" + window.location.port + "/api/vehicles/")
             .subscribe( data => this.updateAllPositions(data)));    
  }

  updateAllPositions(data) {
    data.forEach( (body) => {
          console.log(body);
          let newVehicle = new Vehicle(body.name,
                                 Number(body.lat),
                                 Number(body.lng),
                                 body.timestamp,
                                 body.speed);     
          this.subscription.next(newVehicle);
    });
  }

  updateCenterVehicle(centerVehicle: Vehicle) {
    this.http.get(environment.gatewayUrl +"/history/" + centerVehicle.name)
       .subscribe( data => this.centerVehicleHistory.next(data),
                   error => this.snackBar.open("Error code " + error.status + " occured. " + error.error.message, 'Dismiss', { duration: 5000 } ));
    this.centerVehicle.next(centerVehicle);
  }

  getLastReportFor(vehicle: String) {
    this.http.get(environment.gatewayUrl +"/vehicles/" + vehicle)
       .subscribe( data => this.subscription.next(data),
       error => this.snackBar.open("Error code " + error.status + " occured. " + error.error.message, 'Dismiss', { duration: 5000 } ));
  }

  getStaffDriverFor(vehicle: String) {
    let dummy = new Staff();
    dummy.name="Error";
    this.currentDriver.next(new Staff());
    this.http.get(environment.gatewayUrl +"/vehicles/driver/" + vehicle)
    .subscribe( data => {
                            this.currentDriver.next(data);
                            console.log("Got driver data for " + Object.keys(data));},

                error => this.snackBar.open("Error code " + error.status + " occured. " + error.error.message, 'Dismiss', { duration: 5000 } ));

  }

}
