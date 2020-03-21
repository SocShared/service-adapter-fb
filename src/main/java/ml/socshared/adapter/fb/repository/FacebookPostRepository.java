package ml.socshared.adapter.fb.repository;

import ml.socshared.adapter.fb.domain.FacebookPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacebookPostRepository extends JpaRepository<FacebookPost, String> {

}
