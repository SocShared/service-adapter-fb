package ml.socshared.adapter.fb.repository;

import ml.socshared.adapter.fb.domain.FacebookPost;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FacebookPostRepository extends JpaRepository<FacebookPost, String> {

    Page<FacebookPost> findByGroupId(String groupId);
    Page<FacebookPost> findByUserId(UUID userId);
    Page<FacebookPost> findByUserIdAndGroupId(UUID userId, String groupId);
    FacebookPost findByUserIdAndFacebookPostId(UUID userId, String facebookPostId);

}
