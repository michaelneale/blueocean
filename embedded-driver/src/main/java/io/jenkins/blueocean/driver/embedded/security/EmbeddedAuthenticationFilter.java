package io.jenkins.blueocean.driver.embedded.security;

import hudson.Extension;
import hudson.security.SecurityRealm;
import io.jenkins.blueocean.commons.ServiceException;
import io.jenkins.blueocean.security.AbstractAuthenticationFilter;
import io.jenkins.blueocean.security.Identity;
import jenkins.model.Jenkins;
import org.acegisecurity.Authentication;
import org.acegisecurity.providers.anonymous.AnonymousAuthenticationToken;
import org.acegisecurity.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides a authentication filter that works against security realms
 */
@Extension
final public class EmbeddedAuthenticationFilter extends AbstractAuthenticationFilter {

    @Override
    protected AuthenticationResult doAuthentication(HttpServletRequest httpReq, HttpServletResponse httpResp) {
        Identity identity;
        Authentication authentication = Jenkins.getAuthentication();
        SecurityRealm securityRealm = Jenkins.getInstance().getSecurityRealm();
        // TODO: redirect the user to a nice "not supported configuration" page
        if (securityRealm == null) {
            throw new ServiceException.UnexpectedErrorExpcetion("Jenkins must have security configured");
        }
        if (authentication instanceof AnonymousAuthenticationToken) {
            identity = Identity.ANONYMOUS;
        } else {
            String user = ((UserDetails)authentication.getPrincipal()).getUsername();
            identity = new Identity(user);
        }
        String loginUrl = securityRealm.getLoginUrl();
        String logoutUrl = securityRealm.canLogOut() ? "logout" : null;
        String signupUrl = securityRealm.allowsSignup() ? "signup" : null;
        return new AuthenticationResult(identity, loginUrl, logoutUrl, signupUrl);
    }

}
