package com.blueocean.authentication;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import hudson.security.HudsonPrivateSecurityRealm;
import io.jenkins.blueocean.api.profile.GetUserDetailsRequest;
import io.jenkins.blueocean.api.profile.ProfileService;
import io.jenkins.blueocean.api.profile.model.UserDetails;
import io.jenkins.blueocean.commons.ServiceException;
import io.jenkins.blueocean.security.AuthenticationProvider;
import io.jenkins.blueocean.security.Identity;
import io.jenkins.blueocean.security.UserPrototype;
import io.jenkins.blueocean.security.UsernamePasswordCredentials;

import javax.servlet.http.HttpServletRequest;

public class UsernamePasswordAuthenticationProvider extends AuthenticationProvider<UsernamePasswordCredentials> {

    private final ProfileService profiles;

    @Inject
    public UsernamePasswordAuthenticationProvider(ProfileService profiles) {
        this.profiles = profiles;
    }

    @Override
    public UsernamePasswordCredentials getCredentials(HttpServletRequest req) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password)) {
            throw new ServiceException.UnprocessableEntityException("did not specify username or password");
        }
        return new UsernamePasswordCredentials(username, password);
    }

    @Override
    public UserPrototype validate(UsernamePasswordCredentials loginDetails) {
        UserDetails userDetails = profiles.getUserDetails(Identity.ROOT, GetUserDetailsRequest.byUserId(loginDetails.username)).userDetails;
        UsernamePasswordCredentials storedCredentials = (UsernamePasswordCredentials) userDetails.getCredentials(UsernamePasswordCredentials.class);
        if (storedCredentials == null) {
            throw new ServiceException.NotFoundException("cant find stored password credentials");
        }
        if (!HudsonPrivateSecurityRealm.PASSWORD_ENCODER.encodePassword(loginDetails.password, null).equals(storedCredentials.password)) {
            throw new ServiceException.ForbiddenException("bad password");
        }
        return null;
    }

    @Override
    public String getType() {
        return "basic";
    }

    @Override
    public boolean allowSignup() {
        return false;
    }

    @Override
    public String getLoginUrl() {
        return null;
    }
}
