# Clean bootstrap

### Environment vars

* `LOG_DIR` - log files destination directory

### Running locally

First up you need to set up the Postgres DB but running:

`docker-compose up -d`

### Developing locally

For development, you might want to run just the background services and run the rest though Gradle
or IDE:

`docker-compose up -d postgres`

At this point you are ready to either start the app through your IDE or through Gradle:

`./gradlew run`

### Building image

To create a Docker image you can run:

`docker build -t clean-service .`
