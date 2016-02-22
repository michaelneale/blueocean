package io.jenkins.blueocean.api.profile;

import org.junit.Test;

//import io.jenkins.blueocean.security.GithubCredentials;

/**
 * @author Vivek Pandey
 */
public class GetUserDetailsResponseTest {
    @Test
    public void serializeDeserialize(){
/*
        String accessToken = "1212121212";
        GetUserDetailsResponse response = new GetUserDetailsResponse(new UserDetails("123", "John", "john@example.com",
                ImmutableSet.<Credentials>of(new GithubCredentials("alice", accessToken))));
        String json = JsonConverter.toJson(response);

        System.out.println("Converted from Java:\n"+json);

        GetUserDetailsResponse responseFromJson = JsonConverter.toJava(json, GetUserDetailsResponse.class);


        Assert.assertEquals(response.userDetails.id, responseFromJson.userDetails.id);
        Assert.assertEquals(response.userDetails.name, responseFromJson.userDetails.name);
        Assert.assertEquals(response.userDetails.email, responseFromJson.userDetails.email);
        GithubCredentials ld = (GithubCredentials) response.userDetails.getCredentials(GithubCredentials.class);
        Assert.assertNotNull(ld);
        Assert.assertEquals(ld.accessToken, accessToken);
        Assert.assertEquals(ld.login, "alice");

        System.out.println("Converted back from Json:\n"+JsonConverter.toJson(responseFromJson));*/
    }

}
