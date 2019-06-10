export class Vehicle {
  name: string;
  lat: number;
  lng: number;
  dateAndTime: string;
  speed: number;

  constructor(name: string, lat:number, lng:number, dateAndTime:string, speed: number) {
    this.name = name;
    this.lat = lat;
    this.lng = lng;
    this.dateAndTime = dateAndTime;
    this.speed = speed;
  }

}
