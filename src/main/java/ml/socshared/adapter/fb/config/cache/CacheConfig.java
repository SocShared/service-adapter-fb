package ml.socshared.adapter.fb.config.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class CacheConfig {

    @Value("${cache.pages}")
    private final String pages = "pages";

    @Value("${cache.groups}")
    private final String groups = "groups";

    @Value("${cache.posts}")
    private final String posts = "posts";

    @Value("${cache.comments}")
    private final String comments = "comments";

    @Value("${cache.super_comments}")
    private final String superComments = "super_comments";

    @CacheEvict(allEntries = true, cacheNames = {pages, groups, posts, comments, superComments})
    @Scheduled(fixedDelay = 10000)
    public void cacheEvict () {
    }
}