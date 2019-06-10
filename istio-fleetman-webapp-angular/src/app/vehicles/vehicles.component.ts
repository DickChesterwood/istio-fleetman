import { Component, OnInit } from '@angular/core';
import { Vehicle } from '../vehicle';
import { VehicleService } from '../vehicle.service';

@Component({
  selector: 'app-vehicles',
  templateUrl: './vehicles.component.html',
  styleUrls: ['./vehicles.component.css']
})
export class VehiclesComponent implements OnInit {

  vehicles: Vehicle[] = [];
  centeredVehicle: string;

  constructor(private vehicleService: VehicleService) { }

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
    });
  }

  centerVehicle(vehicle: Vehicle) {
    // allow to "deselect"
    if (this.centeredVehicle == vehicle.name)
    {
      this.centeredVehicle = null
      this.vehicleService.updateCenterVehicle(null);
    }
    else
    {
      this.centeredVehicle = vehicle.name;
      this.vehicleService.updateCenterVehicle(vehicle);
    }
  }

}
