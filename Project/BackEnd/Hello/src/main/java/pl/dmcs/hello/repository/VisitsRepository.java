package pl.dmcs.hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dmcs.hello.model.Doctor;
import pl.dmcs.hello.model.Visit;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitsRepository extends JpaRepository<Visit, Long> {

    List<Visit> findByPatientId(Long patient_id);

    List<Visit> findByDoctorId(Long doctorId);

    Optional<Visit> findById(long id);
    void deleteAllById(long id);

    boolean existsById(long id);
}
