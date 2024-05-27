package pl.dmcs.hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dmcs.hello.model.Visit;

import java.util.Optional;

@Repository
public interface VisitsRepository extends JpaRepository<Visit, Long> {
    Optional<Visit> findByPatientId(Long patient_id);

    Optional<Visit> findByDoctorId(Long visit_id);
}
