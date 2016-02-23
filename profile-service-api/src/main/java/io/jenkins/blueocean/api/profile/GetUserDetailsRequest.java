package io.jenkins.blueocean.api.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.jenkins.blueocean.security.Identity;

import javax.annotation.Nullable;

/**
 * Request for {@link ProfileService#getUserDetails(Identity, GetUserDetailsRequest)}
 *
 * @author Vivek Pandey
 */
public final class GetUserDetailsRequest{
    @Nullable
    @JsonProperty("byUserId")
    public final String byUserId;

    public GetUserDetailsRequest(@Nullable @JsonProperty("byUserId") String byUserId) {
        this.byUserId = byUserId;
    }

    public static GetUserDetailsRequest byUserId(String userId) {
        return new GetUserDetailsRequest(userId);
    }
}

