package example;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * As simple configuration bean that should be loaded from a yml configuration file.
 *
 * @author Jan-Philipp Kappmeier
 */
@Configuration
@ConfigurationProperties(prefix = "exampleConfig")
@EnableConfigurationProperties
public class ExampleConfiguration {

    /**
     * Some example data.
     */
    ExampleType example;

    public ExampleType getExample() {
        return example;
    }

    public void setExample(ExampleType example) {
        this.example = example;
    }

    @Override
    public String toString() {
        return "ExampleConfiguration{" + "example=" + example + '}';
    }

}
