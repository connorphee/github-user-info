package com.connorphee.githubuserinfo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {

    @JsonProperty("login")
    public String login;

    @JsonProperty("name")
    public String name;

    @JsonProperty(value = "avatar_url")
    public String avatar;

    @JsonProperty("location")
    public String location;

    @JsonProperty("email")
    public String email;

    @JsonProperty(value = "html_url")
    public String url;

    @JsonProperty(value = "created_at")
    public String createdAt;

}
