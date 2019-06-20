import { Component, OnInit } from '@angular/core';
import {Router, ActivatedRoute, Params} from '@angular/router';

@Component({
  selector: 'app-static-vehicle',
  templateUrl: './static-vehicle.component.html',
  styleUrls: ['./static-vehicle.component.css']
})
export class StaticVehicleComponent implements OnInit {

  vehicleName: string;

  constructor(private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.vehicleName = this.activatedRoute.snapshot.paramMap.get("vehicleName");
  }

}
