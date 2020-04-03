package example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yaml.snakeyaml.Yaml;

/**
 * Example application that loads {@link ExampleConfiguration} from yml files with various methods.
 *
 * Three variants should load the same object,
 * <pre>{@code
 *   ExampleConfiguration{example=DefaultMatcher{property=test}}
 * }</pre> once by {@link Yaml#load(java.io.InputStream) SnakeYAML load}, once by
 * {@link Yaml#loadAs(java.io.InputStream, java.lang.Class) SnakeYAML explicit load}, and once by
 * {@link org.springframework.boot.env.YamlPropertySourceLoader Spring Boot configuration loader}.
 *
 * @author Jan-Philipp Kappmeier
 */
@SpringBootApplication
public class YamlExampleApplication implements CommandLineRunner {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(YamlExampleApplication.class);

    /**
     * The configuration to load by Spring Boot.
     */
    @Autowired
    private ExampleConfiguration myConfig;

    public static void main(String[] args) throws IOException {
        Object configuration = load();
        LOG.info("Loaded of type class " + configuration.getClass() + ": " + configuration);

        LOG.info("Using manual load: " + loadAs());

        // Finally using Spring Boot, will fail
        loadSpringBoot();
    }

    /**
     * Try to load using snake yaml without type specification from resource file {@code manually.yml}
     *
     * @see Yaml#loadAs(java.io.InputStream, java.lang.Class)
     * @return the loaded object (which should be of type {@link ExampleConfiguration}
     */
    private static Object load() throws IOException {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("manually.yml")) {
            Map<String, Object> obj = yaml.load(inputStream);
            return obj.entrySet().iterator().next().getValue();
        }
    }

    /**
     * Try to load using SnakeYAL with a specific type from resource file {@code manuallyLoadAs.yml}.
     *
     * @return the loaded {@code ExampleConfiguration}
     */
    private static ExampleConfiguration loadAs() throws IOException {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("manuallyLoadAs.yml")) {
            return yaml.loadAs(inputStream, ExampleConfiguration.class);
        }

    }

    /**
     * Initialize Spring Boot application and try to load configuration using spring boot (will fail).
     */
    private static void loadSpringBoot() {
        SpringApplication.run(YamlExampleApplication.class, new String[] {});
    }

    @Override
    public void run(String... args) {
        LOG.info("Loaded config: " + myConfig);
    }
}
