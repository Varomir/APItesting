package cdp.bonial.qa;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedBuilderParametersImpl;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;

public class BaseTest {

    protected static PropertiesConfiguration config = null;

    @BeforeAll
    public static void setup() throws ConfigurationException {
        final File configFile = new File("./src/test/resources/stage.properties");
        final FileBasedConfigurationBuilder<PropertiesConfiguration> builder =
                new FileBasedConfigurationBuilder<>(
                        PropertiesConfiguration.class)
                        .configure(new FileBasedBuilderParametersImpl()
                                .setFile(configFile));

        builder.getConfiguration();
        config = builder.getConfiguration();
    }
}
