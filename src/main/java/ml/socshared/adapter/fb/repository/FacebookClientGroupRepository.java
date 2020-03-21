package ml.socshared.adapter.fb.repository;

import ml.socshared.adapter.fb.domain.SelectedFacebookClientGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FacebookClientGroupRepository extends JpaRepository<SelectedFacebookClientGroup, UUID> {
}
