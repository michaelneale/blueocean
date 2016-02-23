package io.jenkins.blueocean.driver.embedded.service;

import com.google.common.collect.ImmutableMap;
import io.jenkins.blueocean.api.profile.CreateOrganizationRequest;
import io.jenkins.blueocean.api.profile.CreateOrganizationResponse;
import io.jenkins.blueocean.api.profile.FindUsersRequest;
import io.jenkins.blueocean.api.profile.FindUsersResponse;
import io.jenkins.blueocean.api.profile.GetOrganizationRequest;
import io.jenkins.blueocean.api.profile.GetOrganizationResponse;
import io.jenkins.blueocean.api.profile.GetUserDetailsRequest;
import io.jenkins.blueocean.api.profile.GetUserDetailsResponse;
import io.jenkins.blueocean.api.profile.GetUserRequest;
import io.jenkins.blueocean.api.profile.GetUserResponse;
import io.jenkins.blueocean.api.profile.ProfileService;
import io.jenkins.blueocean.api.profile.model.Organization;
import io.jenkins.blueocean.api.profile.model.User;
import io.jenkins.blueocean.commons.ServiceException;
import io.jenkins.blueocean.security.Identity;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link ProfileService} implementation to be used embedded as plugin
 *
 * @author Vivek Pandey
 */
public class EmbeddedProfileService extends AbstractEmbeddedService implements ProfileService {

    @Nonnull
    @Override
    public GetUserResponse getUser(@Nonnull Identity identity, @Nonnull GetUserRequest request) {
        hudson.model.User user = getJenkinsUser(request.id);
        return new GetUserResponse(Mapper.mapUser(user));
    }

    @Nonnull
    @Override
    public GetUserDetailsResponse getUserDetails(@Nonnull Identity identity, @Nonnull GetUserDetailsRequest request) {
        hudson.model.User user;
        if (request.byUserId != null) {
            user = getJenkinsUser(request.byUserId);
        } else {
            throw new ServiceException.UnprocessableEntityException("did not specify userId or credentials");
        }
        return new GetUserDetailsResponse(Mapper.mapUserDetails(user));
    }

    @Nonnull
    @Override
    public GetOrganizationResponse getOrganization(@Nonnull Identity identity, @Nonnull GetOrganizationRequest request) {
        validateOrganization(request.name);
        return new GetOrganizationResponse(new Organization(jenkins.getDisplayName()));
    }

    @Nonnull
    @Override
    public CreateOrganizationResponse createOrganization(@Nonnull Identity identity, @Nonnull CreateOrganizationRequest request) {
        throw new ServiceException.NotImplementedException("Not implemented yet");
    }

    @Nonnull
    @Override
    public FindUsersResponse findUsers(@Nonnull Identity identity, @Nonnull FindUsersRequest request) {
        validateOrganization(request.organization);
        List<User> users = new ArrayList<User>();
        for(hudson.model.User u:hudson.model.User.getAll()){
            users.add(new User(u.getId(), u.getDisplayName()));
        }
        return new FindUsersResponse(users, null, null);
    }

    /** Safe way to query a user without creating it at the same time */
    hudson.model.User getJenkinsUser(String email) {
        hudson.model.User user = hudson.model.User.get(email, false, ImmutableMap.of());
        if (user == null) {
            throw new ServiceException.NotFoundException("could not find user");
        }
        return user;
    }
}
