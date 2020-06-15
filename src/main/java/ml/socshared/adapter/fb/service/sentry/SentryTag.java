package ml.socshared.adapter.fb.service.sentry;

public enum SentryTag {
    GET_ALL_FACEBOOK_ACCOUNT("type", "get_all_facebook_account"),
    SAVE_ACCOUNT("type", "save_account"),
    REMOVE_ACCOUNT("type",  "remove_account"),
    URL_FORMING("type", "url_forming"),
    GET_USER_INFO("type", "get_user_info"),
    COMMENTS_OF_POST("type", "get_comments_of_post"),
    COMMENT_OF_POST("type", "get_comment_of_post"),
    GET_SUB_COMMENTS("type", "get_sub_comments"),
    GET_SUB_COMMENT_BY_ID("type", "get_sub_comment_by_id"),
    GET_USER_GROUPS("type", "get_user_groups"),
    GET_USER_GROUP("type", "get_user_group"),
    GET_POSTS("type", "get_posts"),
    GET_POST("type", "get_post_by_id"),
    ADD_POST("type", "get_post_by_id"),
    UPDATE_POST("type", "update_post"),
    DELETE_POST("type", "delete_post");

    SentryTag(String t, String tag) {
        type = t;
        sentryTag = tag;
    }

    public String type() {
        return type;
    }
    public String value() {
        return sentryTag;
    }

    private final String sentryTag;
    private final String type;

}
