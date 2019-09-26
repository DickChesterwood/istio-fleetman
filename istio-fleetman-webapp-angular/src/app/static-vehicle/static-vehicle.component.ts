import { Component, OnInit } from '@angular/core';
import {Router, ActivatedRoute, Params} from '@angular/router';
import { VehicleService } from '../vehicle.service';
import { VehiclesComponent } from '../vehicles/vehicles.component';
import { Vehicle } from '../vehicle';

@Component({
  selector: 'app-static-vehicle',
  templateUrl: './static-vehicle.component.html',
  styleUrls: ['./static-vehicle.component.css'],
  providers:[VehiclesComponent ],
})
export class StaticVehicleComponent implements OnInit {

  centerVehicle: Vehicle = new Vehicle("-",0,0,"-","-");

  constructor(private activatedRoute: ActivatedRoute, private vehicleService: VehicleService, private router: Router,
              private vehiclesComponent: VehiclesComponent) { }

  ngOnInit() {
    this.vehicleService.centerVehicle.subscribe(vehicle => {
      if (vehicle == null) return;
      this.centerVehicle = vehicle;
      this.router.navigateByUrl(`/vehicle/${vehicle.name}`);
    });

    // Case #14, start tracking vehicle immediately based on last known position
    let requiredCenteredVehicle = this.activatedRoute.snapshot.paramMap.get("vehicleName");
    this.vehicleService.getLastReportFor(requiredCenteredVehicle);
  }

}
