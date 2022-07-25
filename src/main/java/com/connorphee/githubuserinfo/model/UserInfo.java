package com.connorphee.githubuserinfo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    @JsonProperty("user_name")
    private String username;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("geo_location")
    private String geolocation;

    @JsonProperty("email")
    private String email;

    @JsonProperty("url")
    private String url;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("repos")
    private Repository[] repositories;
}
