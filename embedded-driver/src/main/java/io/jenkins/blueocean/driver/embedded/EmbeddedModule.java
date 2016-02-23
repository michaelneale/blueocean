package io.jenkins.blueocean.driver.embedded;

import com.google.inject.AbstractModule;
import hudson.Extension;
import io.jenkins.blueocean.api.pipeline.PipelineService;
import io.jenkins.blueocean.api.profile.ProfileService;
import io.jenkins.blueocean.driver.embedded.service.EmbeddedPipelineService;
import io.jenkins.blueocean.driver.embedded.service.EmbeddedProfileService;

/**
 * @author Ivan Meredith
 */
@Extension
public class EmbeddedModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ProfileService.class).to(EmbeddedProfileService.class).asEagerSingleton();
        bind(PipelineService.class).to(EmbeddedPipelineService.class).asEagerSingleton();
    }
}
