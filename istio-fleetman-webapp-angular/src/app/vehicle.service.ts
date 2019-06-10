import { Injectable } from '@angular/core';
import { Vehicle } from './vehicle';
import { Observable ,  Subscription, BehaviorSubject ,  of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

import {Message} from '@stomp/stompjs';
import {StompService} from '@stomp/ng2-stompjs';

import {  LatLng } from 'leaflet';

@Injectable()
export class VehicleService  {

  subscription: BehaviorSubject<Vehicle>;
  centerVehicle: BehaviorSubject<Vehicle>;
  centerVehicleHistory: BehaviorSubject<any>;

  constructor(private _stompService: StompService, private http: HttpClient) {
    // Store local reference to Observable
    // for use with template ( | async )
    this.subscribe();
    this.subscription = new BehaviorSubject(null);
    this.centerVehicle = new BehaviorSubject(null);
    this.centerVehicleHistory = new BehaviorSubject(null);
  }

  subscribe() {
    // Stream of messages
    var messages = this._stompService.subscribe('/vehiclepositions/messages');
    // Subscribe a function to be run on_next message
    messages.subscribe(this.onMessage);
  }

  /** Consume a message from the _stompService */
  onMessage = (message: Message) => {

    let body = JSON.parse(message.body);

    // update vehicle and notify
    let newVehicle = new Vehicle(body.name,
                                 Number(body.lat),
                                 Number(body.longitude),
                                 body.timestamp,
                                Number(body.speed));
    this.subscription.next(newVehicle);
  }

  updateCenterVehicle(centerVehicle: Vehicle) {
    this.centerVehicle.next(centerVehicle);

    if (centerVehicle == null)
    {
      this.centerVehicleHistory.next(null);
    }
    else
    {
      // call API gateway, get the history for this vehicle.
      this.http.get("http://" + window.location.hostname + ":" + window.location.port + "/api/history/" + centerVehicle.name);
    }
  }
}
