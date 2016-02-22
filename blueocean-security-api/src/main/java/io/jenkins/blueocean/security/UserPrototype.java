package io.jenkins.blueocean.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import javax.annotation.Nullable;

/** Represents the minimal amount of information needed to create a user */
public final class UserPrototype {
    @JsonProperty("username")
    @Nullable
    public final String username;

    @JsonProperty("fullName")
    @Nullable
    public final String fullName;
    @JsonProperty("email")
    @Nullable
    public final String email;

    public UserPrototype(String username, String fullName, String email) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
    }

    @JsonIgnore
    public String getPreferredUsernameSeed() {
        return Objects.firstNonNull(Objects.firstNonNull(username, email), fullName);
    }
}
