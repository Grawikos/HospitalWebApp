package pl.dmcs.hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dmcs.hello.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findById (long id);
}
