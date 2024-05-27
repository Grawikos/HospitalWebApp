package pl.dmcs.hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dmcs.hello.model.Doctor;
import pl.dmcs.hello.model.Role;
import pl.dmcs.hello.model.RoleName;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
//    Optional<Doctor> findById(Long id);
}
