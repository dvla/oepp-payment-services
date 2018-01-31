package uk.gov.dvla.services;

import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;


public class ApplicationIT {

    @ClassRule
    public static final DropwizardAppRule<Configuration> RULE =
            new DropwizardAppRule<>(Application.class, "src/main/resources/config.yaml",
                    ConfigOverride.config("server.applicationConnectors[0].port", "0"),
                    ConfigOverride.config("server.adminConnectors[0].port", "0"));

    @Test
    public void paymentBrokerHealthCheckIsRegistered() {
        assertThat(RULE.getEnvironment().healthChecks().getNames(), hasItem("payment broker"));
    }

    @Test
    public void paymentProviderHealthCheckIsRegistered() {
        assertThat(RULE.getEnvironment().healthChecks().getNames(), hasItem("payment provider (TLG)"));
    }
}