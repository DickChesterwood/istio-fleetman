import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StaticVehicleComponent } from './static-vehicle.component';

describe('StaticVehicleComponent', () => {
  let component: StaticVehicleComponent;
  let fixture: ComponentFixture<StaticVehicleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StaticVehicleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StaticVehicleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
