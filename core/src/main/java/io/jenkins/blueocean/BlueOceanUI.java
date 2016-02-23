package io.jenkins.blueocean;

import com.google.inject.Inject;
import hudson.Extension;
import io.jenkins.blueocean.api.profile.GetUserDetailsRequest;
import io.jenkins.blueocean.api.profile.ProfileService;
import io.jenkins.blueocean.config.ApplicationConfig;
import io.jenkins.blueocean.security.Identity;
import org.kohsuke.stapler.Stapler;

/**
 * Root of Blue Ocean UI
 *
 * @author Kohsuke Kawaguchi
 */
@Extension
public class BlueOceanUI {

    @Inject
    private ProfileService profiles;
    @Inject
    private ApplicationConfig appConfig;

    public String getCurrentUserFullName() {
        Identity identity = (Identity)Stapler.getCurrentRequest().getUserPrincipal();
        return profiles.getUserDetails(identity, GetUserDetailsRequest.byUserId(identity.user)).userDetails.fullName;
    }

    public String getCurrentUserEmail() {
        Identity identity = (Identity)Stapler.getCurrentRequest().getUserPrincipal();
        return profiles.getUserDetails(identity, GetUserDetailsRequest.byUserId(identity.user)).userDetails.email;
    }
}
