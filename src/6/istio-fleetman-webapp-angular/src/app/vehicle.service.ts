import { Injectable } from '@angular/core';
import { Vehicle } from './vehicle';
import { Staff } from './staff';
import { Observable ,  Subscription, BehaviorSubject ,  of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

import {Message} from '@stomp/stompjs';
import {StompService} from '@stomp/ng2-stompjs';

import {  LatLng } from 'leaflet';

import { environment } from '../environments/environment';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable()
export class VehicleService  {

  subscription: BehaviorSubject<any>;
  centerVehicle: BehaviorSubject<Vehicle>;
  centerVehicleHistory: BehaviorSubject<any>;
  currentDriver: BehaviorSubject<any>;

  constructor(private _stompService: StompService, private http: HttpClient, private snackBar: MatSnackBar) {
    // Store local reference to Observable
    // for use with template ( | async )
    this.subscribe();
    this.subscription = new BehaviorSubject(null);
    this.centerVehicle = new BehaviorSubject(null);
    this.centerVehicleHistory = new BehaviorSubject(null);
    this.currentDriver = new BehaviorSubject(null);
  }

  subscribe() {
    // Stream of messages
    var messages = this._stompService.subscribe('/vehiclepositions/messages');
    // Subscribe a function to be run on_next message
    messages.subscribe(this.onMessage);
  }

  /** This is responding to an incoming websocket message, an updated vehicle report */
  onMessage = (message: Message) => {
    let body = JSON.parse(message.body);

    // update vehicle and notify
    let newVehicle = new Vehicle(body.name,
                                 Number(body.lat),
                                 Number(body.lng),
                                 body.timestamp,
                                 body.speed);
    this.subscription.next(newVehicle);
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
    // TODO set the icon to spinning disks?
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
