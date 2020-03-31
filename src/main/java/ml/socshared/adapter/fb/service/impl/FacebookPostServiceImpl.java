package ml.socshared.adapter.fb.service.impl;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.adapter.fb.domain.FacebookPost;
import ml.socshared.adapter.fb.domain.page.Page;
import ml.socshared.adapter.fb.domain.request.FacebookPostRequest;
import ml.socshared.adapter.fb.domain.response.FacebookPostResponse;
import ml.socshared.adapter.fb.exception.impl.HttpBadRequestException;
import ml.socshared.adapter.fb.exception.impl.HttpNotFoundException;
import ml.socshared.adapter.fb.repository.FacebookPostRepository;
import ml.socshared.adapter.fb.service.FacebookAccessGrantService;
import ml.socshared.adapter.fb.service.FacebookAuthorizationService;
import ml.socshared.adapter.fb.service.FacebookPostService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.social.UncategorizedApiException;
import org.springframework.social.facebook.api.FeedOperations;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.PostData;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class FacebookPostServiceImpl implements FacebookPostService {

    @Value("${facebook.adapter.id}")
    private String adapterId;

    @Value("${cache.posts}")
    private final String posts = "posts";

    private FacebookAuthorizationService faService;
    private FacebookAccessGrantService fagService;

    public FacebookPostServiceImpl(FacebookAuthorizationService faService,
                                   FacebookAccessGrantService fagService) {
        this.faService = faService;
        this.fagService = fagService;
    }

    @Override
    public FacebookPostResponse getPostByPostIdOfPage(String systemUserId, String pageId, String postId) {
        AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", accessGrant.getAccessToken());

        try {
            Map page = faService.getConnection(accessGrant).getApi().fetchObject(pageId, Map.class, "access_token");
            accessGrant = new AccessGrant((String) page.get("access_token"));

            try {
                Map post = faService.getConnection(accessGrant).getApi()
                        .fetchObject(pageId + "_" + postId, Map.class, "id,message,from{id},created_time,updated_time," +
                                "insights.metric(post_impressions){name,values},likes.summary(1).limit(0){summary},comments.summary(1).limit(0){summary},shares");

                FacebookPostResponse response = new FacebookPostResponse();
                response.setGroupId(pageId);
                response.setPostId(((String) post.get("id")).split("_")[1]);
                response.setMessage((String) post.get("message"));
                response.setSystemUserId(UUID.fromString(systemUserId));
                response.setUserId((String) ((Map) post.get("from")).get("id"));
                response.setCreatedDate(OffsetDateTime.parse((String) post.get("created_time"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")).toLocalDateTime());
                response.setUpdatedDate(OffsetDateTime.parse((String) post.get("updated_time"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")).toLocalDateTime());
                response.setCommentsCount((Integer) ((Map) ((Map) post.get("comments")).get("summary")).get("total_count"));
                response.setLikesCount((Integer) ((Map) ((Map) post.get("likes")).get("summary")).get("total_count"));
                response.setRepostsCount(post.get("shares") == null ? 0 : (Integer) ((Map) post.get("shares")).get("count"));
                response.setAdapterId(adapterId);

                List insights = (List) ((Map) post.get("insights")).get("data");
                for (Object obj : insights) {
                    Map ins = (Map) obj;
                    if (ins.get("name").equals("post_impressions")) {
                        response.setViewsCount((Integer) ((Map) ((List) ins.get("values")).get(0)).get("value"));
                    }
                }

                return response;
            } catch (UncategorizedApiException exc) {
                throw new HttpNotFoundException("Not found post by id: " + postId);
            }
        } catch (UncategorizedApiException exc) {
            throw new HttpNotFoundException("Not found page by id: " + pageId);
        }
    }

    @Override
    @Cacheable(posts)
    public Page<FacebookPostResponse> getPostsByPageId(String systemUserId, String pageId, Integer page, Integer size) {
        AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", accessGrant.getAccessToken());

        try {
            Map pageUser = faService.getConnection(accessGrant).getApi().fetchObject(pageId, Map.class, "access_token");
            accessGrant = new AccessGrant((String) pageUser.get("access_token"));

            List<FacebookPostResponse> facebookPostServiceList = new LinkedList<>();

            MultiValueMap<String, String> postParamMap = new LinkedMultiValueMap<>();
            postParamMap.put("fields", Collections.singletonList("id,message,from{id},created_time,updated_time," +
                    "insights.metric(post_impressions){name,values},likes.summary(1).limit(0){summary},comments.summary(1).limit(0){summary},shares"));
            postParamMap.put("limit", Collections.singletonList(size + ""));
            postParamMap.put("offset", Collections.singletonList(page * size + ""));

            PagedList<Map> posts = faService.getConnection(accessGrant).getApi().fetchConnections(pageId,
                    "posts", Map.class, postParamMap);

            posts.forEach(s -> {
                FacebookPostResponse response = new FacebookPostResponse();
                response.setGroupId(pageId);
                response.setPostId(((String) s.get("id")).split("_")[1]);
                response.setMessage((String) s.get("message"));
                response.setSystemUserId(UUID.fromString(systemUserId));
                response.setUserId((String) ((Map) s.get("from")).get("id"));
                response.setCreatedDate(OffsetDateTime.parse((String) s.get("created_time"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")).toLocalDateTime());
                response.setUpdatedDate(OffsetDateTime.parse((String) s.get("updated_time"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")).toLocalDateTime());
                response.setCommentsCount((Integer) ((Map) ((Map) s.get("comments")).get("summary")).get("total_count"));
                response.setLikesCount((Integer) ((Map) ((Map) s.get("likes")).get("summary")).get("total_count"));
                response.setRepostsCount(s.get("shares") == null ? 0 : (Integer) ((Map) s.get("shares")).get("count"));
                response.setAdapterId(adapterId);

                List insights = (List) ((Map) s.get("insights")).get("data");
                for (Object obj : insights) {
                    Map ins = (Map) obj;
                    if (ins.get("name").equals("post_impressions")) {
                        response.setViewsCount((Integer) ((Map) ((List) ins.get("values")).get(0)).get("value"));
                    }
                }

                facebookPostServiceList.add(response);
            });

            log.info("Facebook Posts: size: {}: {}", facebookPostServiceList.size(), facebookPostServiceList);

            Page<FacebookPostResponse> postPages = new Page<>();
            postPages.setSize(size);
            postPages.setPage(page);
            postPages.setObjects(facebookPostServiceList);

            return postPages;
        } catch (UncategorizedApiException exc) {
            throw new HttpNotFoundException("Not found page by id: " + pageId);
        }
    }

    @Override
    public FacebookPostResponse addPostToPage(String systemUserId, String pageId, FacebookPostRequest request) {
        AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", accessGrant.getAccessToken());

        try {
            Map page = faService.getConnection(accessGrant).getApi().fetchObject(pageId, Map.class, "access_token");
            accessGrant = new AccessGrant((String) page.get("access_token"));

            PostData postData = new PostData(pageId);
            postData.message(request.getMessage());

            String id = faService.getConnection(accessGrant).getApi().feedOperations().post(postData);

            return getPostByPostIdOfPage(systemUserId, pageId, id);
        } catch (UncategorizedApiException exc) {
            throw new HttpNotFoundException("Not found page by id: " + pageId);
        }
    }

    @Override
    public FacebookPostResponse updatePostOfPage(String systemUserId, String pageId, String postId, FacebookPostRequest request) {
        AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", accessGrant.getAccessToken());

        try {
            Map page = faService.getConnection(accessGrant).getApi().fetchObject(pageId, Map.class, "access_token");
            accessGrant = new AccessGrant((String) page.get("access_token"));

            PostData postData = new PostData(postId);
            postData.message(request.getMessage());

            String id = faService.getConnection(accessGrant).getApi().feedOperations().post(postData);

            return getPostByPostIdOfPage(systemUserId, pageId, id);
        } catch (UncategorizedApiException exc) {
            throw new HttpNotFoundException("Not found page by id: " + pageId);
        }
    }

    @Override
    public void deletePostOfPage(String systemUserId, String pageId, String postId) {
        AccessGrant accessGrant = new AccessGrant(fagService.findBySystemUserId(systemUserId).getAccessToken());
        log.info("Token: {}", accessGrant.getAccessToken());

        try {
            Map page = faService.getConnection(accessGrant).getApi().fetchObject(pageId, Map.class, "access_token");
            accessGrant = new AccessGrant((String) page.get("access_token"));

            faService.getConnection(accessGrant).getApi().feedOperations().deletePost(postId);
        } catch (UncategorizedApiException exc) {
            throw new HttpNotFoundException("Not found page by id: " + pageId);
        }
    }
}
