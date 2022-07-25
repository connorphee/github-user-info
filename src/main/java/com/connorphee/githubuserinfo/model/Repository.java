package com.connorphee.githubuserinfo.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repository {

    @JsonProperty("name")
    public String name;

    @JsonAlias({"html_url", "url"})
    public String url;
}
