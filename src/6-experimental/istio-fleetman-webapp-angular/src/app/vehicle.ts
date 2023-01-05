export class Vehicle {
  name: string;
  lat: number;
  lng: number;
  dateAndTime: string;
  speed: string;

  constructor(name: string, lat:number, lng:number, dateAndTime:string, speed: string) {
    this.name = name;
    this.lat = lat;
    this.lng = lng;
    this.dateAndTime = dateAndTime;
    this.speed = speed;
  }

}
