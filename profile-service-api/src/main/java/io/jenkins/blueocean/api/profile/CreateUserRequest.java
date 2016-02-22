package io.jenkins.blueocean.api.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.jenkins.blueocean.security.Credentials;
import io.jenkins.blueocean.security.UserPrototype;

public final class CreateUserRequest {
    @JsonProperty("userPrototype")
    public final UserPrototype userPrototype;
    @JsonProperty("authType")
    public final String authType;
    @JsonProperty("credentials")
    public final Credentials credentials;

    public CreateUserRequest(
        @JsonProperty("userPrototype") UserPrototype userPrototype,
        @JsonProperty("authType") String authType,
        @JsonProperty("credentials") Credentials credentials) {
        this.userPrototype = userPrototype;
        this.authType = authType;
        this.credentials = credentials;
    }

    /** Create a new user from a UserPrototype */
    public static CreateUserRequest fromUserPrototype(UserPrototype userPrototype, String authType, Credentials credentials) {
        return new CreateUserRequest(userPrototype, authType, credentials);
    }
}
