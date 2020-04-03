package example;

/**
 * A simple example bean containing a single {@code String} property.
 *
 * @author Jan-Philipp Kappmeier
 */
public class DefaultExampleType implements ExampleType {
    
    String property;

    public String getProperty() {
        return property;
    }

    public void setProperty(String value) {
        this.property = value;
    }

    @Override
    public String toString() {
        return "DefaultMatcher{" + "property=" + property + '}';
    }
}
