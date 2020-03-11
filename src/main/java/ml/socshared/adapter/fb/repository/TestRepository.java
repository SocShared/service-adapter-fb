package ml.socshared.adapter.fb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ml.socshared.adapter.fb.domain.TestObject;

@Repository
public interface TestRepository extends JpaRepository<TestObject, Integer> {
}
