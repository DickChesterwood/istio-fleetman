import { Component, OnInit } from '@angular/core';
import {Router, ActivatedRoute, Params} from '@angular/router';
import { VehicleService } from '../vehicle.service';
import { VehiclesComponent } from '../vehicles/vehicles.component';
import { Vehicle } from '../vehicle';
import { Staff } from '../staff';

@Component({
  selector: 'app-static-vehicle',
  templateUrl: './static-vehicle.component.html',
  styleUrls: ['./static-vehicle.component.css'],
  providers:[VehiclesComponent ],
})
export class StaticVehicleComponent implements OnInit {

  centerVehicle: Vehicle = new Vehicle("-",0,0,"-","-");
  currentDriver: Staff = new Staff();

  constructor(private activatedRoute: ActivatedRoute, private vehicleService: VehicleService, private router: Router,
              private vehiclesComponent: VehiclesComponent) { }

  ngOnInit() {
    this.vehicleService.currentDriver.subscribe(staff => {
      this.currentDriver = staff;
    });

    this.vehicleService.centerVehicle.subscribe(vehicle => {
      if (vehicle == null) return;
      this.centerVehicle = vehicle;
      this.router.navigateByUrl(`/vehicle/${vehicle.name}`);

      // get staff details for this vehicle
      this.vehicleService.getStaffDriverFor(vehicle.name);
    });

    this.vehicleService.subscription.subscribe(vehicle => {
      if (vehicle == null) return;
      this.centerVehicle.lat = vehicle.lat;
      this.centerVehicle.lng = vehicle.lng;
    });

    // Case #14, start tracking vehicle immediately based on last known position
    let requiredCenteredVehicle = this.activatedRoute.snapshot.paramMap.get("vehicleName");
    this.vehicleService.getLastReportFor(requiredCenteredVehicle);
  }

}
