package io.jenkins.blueocean.security;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import hudson.Extension;
import hudson.ExtensionList;
import hudson.util.PluginServletFilter;
import io.jenkins.blueocean.config.ApplicationConfig;
import jenkins.model.Jenkins;

import javax.annotation.Nullable;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Extension
public class AbstractAuthenticationFilter extends PluginServletFilter {

    @Inject
    protected ApplicationConfig applicationConfig;

    public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        AuthenticationResult result = doAuthentication(httpReq, httpResp);
        String loginUrl = ensurePath(result.loginUrl);
        String signupUrl = ensurePath(result.loginUrl);
        String logoutUrl = ensurePath(result.loginUrl);
        String path = httpReq.getPathInfo();
        if (result.identity.isAnonymous()
            && (!path.startsWith(loginUrl)
            &&  !path.startsWith(signupUrl)
            &&  !path.startsWith(logoutUrl)
            &&  !path.startsWith("/static")
            &&  !path.startsWith(applicationConfig.getApplicationPath(httpReq)))) {
            httpResp.sendRedirect(applicationConfig.getApplicationPath(httpReq) + loginUrl);
            return;
        }
        chain.doFilter(new BlueOceanHttpServletRequest(result.identity, httpReq), httpResp);
    }

    protected AuthenticationResult doAuthentication(HttpServletRequest httpReq, HttpServletResponse httpResp) {
        throw new IllegalStateException("not implemented");
    }

    protected final class AuthenticationResult {
        public final Identity identity;
        public final String loginUrl;
        public final String logoutUrl;
        public final String signupUrl;

        public AuthenticationResult(Identity identity, String loginUrl, String logoutUrl, String signupUrl) {
            this.identity = identity;
            this.loginUrl = loginUrl;
            this.logoutUrl = logoutUrl;
            this.signupUrl = signupUrl;
        }
    }

    @Nullable
    public static AbstractAuthenticationFilter get() {
        Jenkins j = Jenkins.getInstance();
        if(j == null) {
            throw new IllegalStateException("jenkins instance null");
        }
        ExtensionList<AbstractAuthenticationFilter> extensionList = j.getExtensionList(AbstractAuthenticationFilter.class);
        AbstractAuthenticationFilter filter = Iterables.getFirst(extensionList, null);
        if (filter == null) {
            throw new IllegalStateException("no authentication filter found");
        }
        return filter;
    }

    // TODO: there should be some sort of Util for paths.
    static String ensurePath(String path) {
        return (path == null || path.startsWith("/")) ? path : "/" + path;
    }
}
