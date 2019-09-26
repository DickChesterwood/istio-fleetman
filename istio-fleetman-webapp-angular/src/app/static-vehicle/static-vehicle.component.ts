import { Component, OnInit } from '@angular/core';
import {Router, ActivatedRoute, Params} from '@angular/router';
import { VehicleService } from '../vehicle.service';

@Component({
  selector: 'app-static-vehicle',
  templateUrl: './static-vehicle.component.html',
  styleUrls: ['./static-vehicle.component.css']
})
export class StaticVehicleComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute, private vehicleService: VehicleService, private router: Router) { }

  centerVehicle: string;

  ngOnInit() {
    // TODO review this.centerVehicle = this.activatedRoute.snapshot.paramMap.get("vehicleName");
    // TODO review this.centerVehicle = this.centerVehicle.replace(/_/g, ' ');

    this.vehicleService.centerVehicle.subscribe(vehicle => {
      if (vehicle == null) return;
      this.centerVehicle = vehicle.name;

      console.log("Received an UPDATE!");
      this.router.navigateByUrl(`/vehicle/${vehicle.name  }`);
    });
  }

}
