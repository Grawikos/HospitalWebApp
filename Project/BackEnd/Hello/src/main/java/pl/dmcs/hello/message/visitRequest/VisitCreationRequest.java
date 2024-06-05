package pl.dmcs.hello.message.visitRequest;

import java.time.LocalDateTime;

public class VisitCreationRequest {
    private Long patientId;
    private LocalDateTime appointmentDate;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
