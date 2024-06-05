package pl.dmcs.hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dmcs.hello.model.Patient;
import pl.dmcs.hello.model.Role;
import pl.dmcs.hello.model.RoleName;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findById(Long id);

    List<Patient> findAllByDeleted(boolean del);
}
