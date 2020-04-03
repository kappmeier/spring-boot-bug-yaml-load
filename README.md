# spring-boot-bug-yaml-load

Simple project showing a bug when loading YAML configurations with explicit
type tag using Spring Boot.

The issue arises when the type is not clear, e.g. if it is an interface and the
exact type has to be specified.

The problem is not present in old versions, e.g. Spring Boot 1.5.1. It is
present in the current version Spring Boot 2.2.6.

## Usage

Run the main class `YamlExampleApplication` to load an example data type
`ExampleConfiguration` from a .yml file.

The program tries to load the same object using three methods

1. SnakeYAML using `load()`. The data expects an explicit type tag and is
   stored in the resource file `manually.yml`.
2. SnakeYAML using `loadAs()` with explicit type `ExampleConfiguration.class`
   passed as parameter. The data is stored in resource file the
   `manuallyLoadAs.yml`.
3. Spring Boot using auto configure of `Configuration`. The example
   configuration class is annotated with prefix `exampleConfig`. The
   corresponding configuration is stored in the resource file `application.yml`.

## Expected Behaviour

```

16:27:57.235 [main] INFO example.YamlExampleApplication - Loaded of type class class example.ExampleConfiguration: ExampleConfiguration{example=DefaultMatcher{property=test}}
16:27:57.238 [main] INFO example.YamlExampleApplication - Using manual load: ExampleConfiguration{example=DefaultMatcher{property=test}}
16:27:57.241 [main] INFO example.YamlExampleApplication - Loaded config: ExampleConfiguration{example=DefaultMatcher{property=test}}
```

## Actual Behaviour

```

15:15:19.089 [main] INFO example.YamlExampleApplication - Loaded of type class example.ExampleConfiguration: ExampleConfiguration{example=DefaultMatcher{property=test}}
15:15:19.091 [main] INFO example.YamlExampleApplication - Using manual load: ExampleConfiguration{example=DefaultMatcher{property=test}}
15:15:19.093 [main] INFO example.YamlExampleApplication - "Loaded config: ExampleConfiguration{example=DefaultMatcher{property=test}}
15:15:19.224 [main] DEBUG org.springframework.boot.context.logging.ClasspathLoggingApplicationListener - Application failed to start with classpath: unknown
15:15:19.226 [main] ERROR org.springframework.boot.SpringApplication - Application run failed
java.lang.IllegalStateException: Failed to load property source from location 'classpath:/application.yml'
	at org.springframework.boot.context.config.ConfigFileApplicationListener$Loader.load(ConfigFileApplicationListener.java:545)
	at org.springframework.boot.context.config.ConfigFileApplicationListener$Loader.loadForFileExtension(ConfigFileApplicationListener.java:494)
	at org.springframework.boot.context.config.ConfigFileApplicationListener$Loader.load(ConfigFileApplicationListener.java:464)
	at org.springframework.boot.context.config.ConfigFileApplicationListener$Loader.lambda$null$7(ConfigFileApplicationListener.java:443)
	at java.base/java.lang.Iterable.forEach(Iterable.java:75)
	at org.springframework.boot.context.config.ConfigFileApplicationListener$Loader.lambda$load$8(ConfigFileApplicationListener.java:443)
	at java.base/java.lang.Iterable.forEach(Iterable.java:75)
	at org.springframework.boot.context.config.ConfigFileApplicationListener$Loader.load(ConfigFileApplicationListener.java:440)
	at org.springframework.boot.context.config.ConfigFileApplicationListener$Loader.lambda$load$0(ConfigFileApplicationListener.java:335)
	at org.springframework.boot.context.config.FilteredPropertySource.apply(FilteredPropertySource.java:54)
	at org.springframework.boot.context.config.ConfigFileApplicationListener$Loader.load(ConfigFileApplicationListener.java:323)
	at org.springframework.boot.context.config.ConfigFileApplicationListener.addPropertySources(ConfigFileApplicationListener.java:214)
	at org.springframework.boot.context.config.ConfigFileApplicationListener.postProcessEnvironment(ConfigFileApplicationListener.java:198)
	at org.springframework.boot.context.config.ConfigFileApplicationListener.onApplicationEnvironmentPreparedEvent(ConfigFileApplicationListener.java:188)
	at org.springframework.boot.context.config.ConfigFileApplicationListener.onApplicationEvent(ConfigFileApplicationListener.java:176)
	at org.springframework.context.event.SimpleApplicationEventMulticaster.doInvokeListener(SimpleApplicationEventMulticaster.java:172)
	at org.springframework.context.event.SimpleApplicationEventMulticaster.invokeListener(SimpleApplicationEventMulticaster.java:165)
	at org.springframework.context.event.SimpleApplicationEventMulticaster.multicastEvent(SimpleApplicationEventMulticaster.java:139)
	at org.springframework.context.event.SimpleApplicationEventMulticaster.multicastEvent(SimpleApplicationEventMulticaster.java:127)
	at org.springframework.boot.context.event.EventPublishingRunListener.environmentPrepared(EventPublishingRunListener.java:76)
	at org.springframework.boot.SpringApplicationRunListeners.environmentPrepared(SpringApplicationRunListeners.java:53)
	at org.springframework.boot.SpringApplication.prepareEnvironment(SpringApplication.java:345)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:308)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1226)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1215)
	at example.YamlExampleApplication.main(YamlExampleApplication.java:46)
Caused by: org.yaml.snakeyaml.constructor.ConstructorException: Cannot create property=property for JavaBean=DefaultMatcher{property=null}
 in 'reader', line 3, column 14:
        example: !!example.DefaultExampleType
                 ^
argument type mismatch
 in 'reader', line 4, column 19:
            property: 'test'
                      ^

	at org.yaml.snakeyaml.constructor.Constructor$ConstructMapping.constructJavaBean2ndStep(Constructor.java:270)
	at org.yaml.snakeyaml.constructor.Constructor$ConstructMapping.construct(Constructor.java:149)
	at org.yaml.snakeyaml.constructor.Constructor$ConstructYamlObject.construct(Constructor.java:309)
	at org.yaml.snakeyaml.constructor.BaseConstructor.constructObjectNoCheck(BaseConstructor.java:216)
	at org.yaml.snakeyaml.constructor.BaseConstructor.constructObject(BaseConstructor.java:205)
	at org.springframework.boot.env.OriginTrackedYamlLoader$OriginTrackingConstructor.constructObject(OriginTrackedYamlLoader.java:94)
	at org.yaml.snakeyaml.constructor.BaseConstructor.constructMapping2ndStep(BaseConstructor.java:465)
	at org.yaml.snakeyaml.constructor.SafeConstructor.constructMapping2ndStep(SafeConstructor.java:184)
	at org.yaml.snakeyaml.constructor.BaseConstructor.constructMapping(BaseConstructor.java:446)
	at org.yaml.snakeyaml.constructor.SafeConstructor$ConstructYamlMap.construct(SafeConstructor.java:521)
	at org.yaml.snakeyaml.constructor.BaseConstructor.constructObjectNoCheck(BaseConstructor.java:216)
	at org.yaml.snakeyaml.constructor.BaseConstructor.constructObject(BaseConstructor.java:205)
	at org.springframework.boot.env.OriginTrackedYamlLoader$OriginTrackingConstructor.constructObject(OriginTrackedYamlLoader.java:94)
	at org.yaml.snakeyaml.constructor.BaseConstructor.constructDocument(BaseConstructor.java:164)
	at org.yaml.snakeyaml.constructor.BaseConstructor.getData(BaseConstructor.java:129)
	at org.yaml.snakeyaml.Yaml$1.next(Yaml.java:548)
	at org.springframework.beans.factory.config.YamlProcessor.process(YamlProcessor.java:160)
	at org.springframework.beans.factory.config.YamlProcessor.process(YamlProcessor.java:134)
	at org.springframework.boot.env.OriginTrackedYamlLoader.load(OriginTrackedYamlLoader.java:75)
	at org.springframework.boot.env.YamlPropertySourceLoader.load(YamlPropertySourceLoader.java:50)
	at org.springframework.boot.context.config.ConfigFileApplicationListener$Loader.loadDocuments(ConfigFileApplicationListener.java:562)
	at org.springframework.boot.context.config.ConfigFileApplicationListener$Loader.load(ConfigFileApplicationListener.java:518)
	... 25 common frames omitted
Caused by: org.yaml.snakeyaml.constructor.ConstructorException: Cannot create property=property for JavaBean=DefaultMatcher{property=null}
 in 'reader', line 3, column 14:
        example: !!example.ExampleType
                 ^
argument type mismatch
 in 'reader', line 4, column 19:
            property: 'test'
                      ^

	at org.yaml.snakeyaml.constructor.Constructor$ConstructMapping.constructJavaBean2ndStep(Constructor.java:270)
	at org.yaml.snakeyaml.constructor.Constructor$ConstructMapping.construct(Constructor.java:149)
	at org.yaml.snakeyaml.constructor.Constructor$ConstructYamlObject.construct(Constructor.java:309)
	at org.yaml.snakeyaml.constructor.BaseConstructor.constructObjectNoCheck(BaseConstructor.java:216)
	at org.yaml.snakeyaml.constructor.BaseConstructor.constructObject(BaseConstructor.java:205)
	at org.springframework.boot.env.OriginTrackedYamlLoader$OriginTrackingConstructor.constructObject(OriginTrackedYamlLoader.java:94)
	at org.yaml.snakeyaml.constructor.Constructor$ConstructMapping.constructJavaBean2ndStep(Constructor.java:247)
	... 46 common frames omitted
Caused by: java.lang.IllegalArgumentException: argument type mismatch
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at org.yaml.snakeyaml.introspector.MethodProperty.set(MethodProperty.java:77)
	at org.yaml.snakeyaml.constructor.Constructor$ConstructMapping.constructJavaBean2ndStep(Constructor.java:263)
	... 52 common frames omitted
```

## Issue

In method `Constructor$ConstructMapping.constructJavaBean2ndStep`, the passed
value is not of type `String`, but of type
`OriginTrackedValue$OriginTrackedCharSequence`. This causes an exception when
the property should be set in `ExampleType.setProperty`, as it expects a
`String` parameter.

## License

This project is [licensed](LICENSE.md) under the terms of the Unlicense.
