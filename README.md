# Clean bootstrap

### Environment vars

* `LOG_DIR` - log files destination directory

### Running locally

First up you need to set up the Postgres DB but running:

`docker-compose --profile app up`

### Developing locally

For development, you might want to run just the background services and run the rest though Gradle
or IDE:

`docker-compose --profile service up`

At this point you are ready to either start the app with dev profile:

`./gradlew run --args="-config=src/main/resources/application-dev.conf"`

### Building image

To create a Docker image you can run:

`docker build -t clean-service .`

### App

You can access the GraphQL playground by navigating to:
`http://localhost:8080/graphql`
