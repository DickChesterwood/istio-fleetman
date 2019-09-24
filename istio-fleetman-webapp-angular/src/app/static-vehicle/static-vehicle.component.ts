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
    this.centerVehicle = this.activatedRoute.snapshot.paramMap.get("vehicleName");
    this.centerVehicle = this.centerVehicle.replace(/_/g, ' ');

    this.vehicleService.centerVehicle.subscribe(vehicle => {
      this.centerVehicle = vehicle.name;

      console.log(this.activatedRoute.routeConfig.component.name);
      if (this.activatedRoute.routeConfig.component == StaticVehicleComponent)
      {
        this.router.navigateByUrl(`/vehicle/${vehicle.name  }`);
      }
    });
  }

}
