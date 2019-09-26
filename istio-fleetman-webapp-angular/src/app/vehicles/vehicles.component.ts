import { Component, OnInit } from '@angular/core';
import { Vehicle } from '../vehicle';
import { VehicleService } from '../vehicle.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-vehicles',
  templateUrl: './vehicles.component.html',
  styleUrls: ['./vehicles.component.css']
})
export class VehiclesComponent implements OnInit {

  vehicles: Vehicle[] = [];
  centeredVehicle: string;

  constructor(private vehicleService: VehicleService, private activatedRoute: Router) { }

  ngOnInit() {
    this.vehicleService.subscription.subscribe(updatedVehicle => {
      if (updatedVehicle==null) return;

      let foundIndex = this.vehicles.findIndex(existingVehicle => existingVehicle.name == updatedVehicle.name);
      if (foundIndex == -1)
      {
        this.vehicles.push(updatedVehicle);
        this.vehicles.sort( (a:Vehicle,b:Vehicle) => {
          return (a.name < b.name) ? -1 : 1;
        });
      }
      else
      {
        this.vehicles[foundIndex] = updatedVehicle;
      }

      // case #14
      if (this.centeredVehicle == null)
      {
        let url = decodeURIComponent(this.activatedRoute.url);
        let requiredCenteredVehicle = url.split("/").slice(-1)[0];
        if (requiredCenteredVehicle == updatedVehicle.name)
        {
          this.centerVehicle(updatedVehicle);
        }
      }

    });
  }

  centerVehicle(vehicle: Vehicle) {
    this.centeredVehicle = vehicle.name;
    this.vehicleService.updateCenterVehicle(vehicle);
  }
}
