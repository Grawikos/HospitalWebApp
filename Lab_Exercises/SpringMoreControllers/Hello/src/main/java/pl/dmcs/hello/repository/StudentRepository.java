package pl.dmcs.hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dmcs.hello.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findById(long Id);
}
