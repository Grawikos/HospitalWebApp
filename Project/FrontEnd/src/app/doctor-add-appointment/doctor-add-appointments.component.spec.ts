import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorAddAppointmentsComponent } from './doctor-add-appointments.component';

describe('DoctorAddAppointmentsComponent', () => {
  let component: DoctorAddAppointmentsComponent;
  let fixture: ComponentFixture<DoctorAddAppointmentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DoctorAddAppointmentsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DoctorAddAppointmentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
