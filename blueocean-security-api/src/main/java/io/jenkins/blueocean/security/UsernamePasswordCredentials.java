package io.jenkins.blueocean.security;

import com.google.common.base.Predicate;

import javax.annotation.Nullable;

public class UsernamePasswordCredentials implements Credentials {

    public final String username;
    public final String password;

    public UsernamePasswordCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Predicate<Credentials> identityPredicate() {
        final UsernamePasswordCredentials me = this;
        return new Predicate<Credentials>() {
            @Override
            public boolean apply(@Nullable Credentials input) {
                if (input instanceof UsernamePasswordCredentials) {
                    UsernamePasswordCredentials other = (UsernamePasswordCredentials)input;
                    return me.username.equals(other.username);
                }
                return false;
            }
        };
    }
}
