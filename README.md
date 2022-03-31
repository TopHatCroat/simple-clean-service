# Clean bootstrap

### Environment vars

* `LOG_DIR` - log files destination directory

## Building image

To create a Docker image you can run:

`docker build -t clean-service .`

## Docker compose

Running the app and/or services with docker-compose

### Running locally

First up you need to set up the Postgres DB but running:

`docker-compose --profile app up`

### Developing locally

For development, you might want to run just the background services and run the rest though Gradle
or IDE:

`docker-compose --profile service up`

At this point you are ready to either start the app with dev profile:

`./gradlew run --args="-config=src/main/resources/application-dev.conf"`

## Kubernetes

First of, you need to have `minikube` installed.

* On Mac, this can be done with `brew install minikube`.

* Then make sure your minikube cluster is started with `minikube start`

* Run `eval $(minikube docker-env)` to allow minikube to see your local docker env including images

* Build the local image with `docker build -t clean-service .`

* Run `kubectl create k8s/database.yml` to start the databse

* Finally, run `kubectl create k8s/app.yml` to start the app

### App

You can access the GraphQL playground by navigating to:

http://localhost:8080/graphql
