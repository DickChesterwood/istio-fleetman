import { Component, OnInit } from '@angular/core';
import {Router, ActivatedRoute, Params} from '@angular/router';
import { VehicleService } from '../vehicle.service';
import { VehiclesComponent } from '../vehicles/vehicles.component';
@Component({
  selector: 'app-static-vehicle',
  templateUrl: './static-vehicle.component.html',
  styleUrls: ['./static-vehicle.component.css'],
  providers:[VehiclesComponent ],
})
export class StaticVehicleComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute, private vehicleService: VehicleService, private router: Router,
              private vehiclesComponent: VehiclesComponent) { }

  ngOnInit() {
    this.vehicleService.centerVehicle.subscribe(vehicle => {
      if (vehicle == null) return;
      this.router.navigateByUrl(`/vehicle/${vehicle.name}`);
    });

    // Case #14, start tracking vehicle immediately based on last known position
    let requiredCenteredVehicle = this.activatedRoute.snapshot.paramMap.get("vehicleName");
    console.log("getting lkp of " + requiredCenteredVehicle);

    this.vehicleService.getLastReportFor(requiredCenteredVehicle);
  }

}
