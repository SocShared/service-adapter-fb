package ml.socshared.adapter.fb.domain.group;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TypeGroup {
    @JsonProperty("PAGE")
    FB_PAGE,
    @JsonProperty("GROUP")
    FB_GROUP
}