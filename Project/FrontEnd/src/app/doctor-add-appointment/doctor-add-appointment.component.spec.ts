import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorAddAppointmentComponent } from './doctor-add-appointment.component';

describe('DoctorAddAppointmentComponent', () => {
  let component: DoctorAddAppointmentComponent;
  let fixture: ComponentFixture<DoctorAddAppointmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DoctorAddAppointmentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DoctorAddAppointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
