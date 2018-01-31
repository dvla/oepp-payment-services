Payment Service
========

Module provides a server component which exposes Payment Service RESTful API endpoint.

Service uses Dropwizard stack (http://www.dropwizard.io) to provide RESTful endpoint and Swagger library (http://swagger.io) to provide API documentation.

## Requirements

 * Java JDK 1.8+
 * Maven 3

## How to build it

To build this project execute the following command:

```bash
  mvn clean package
```

To build RPM package execute the following command:

```bash
  mvn clean package -P release
```

## How to run it

To run the REST endpoint execute the following command (you may have to change the version in this):

```bash
  java -jar target/payment-service-1.0-SNAPSHOT.jar server target/classes/config.yaml
```

Application starts HTTP server which listens on ports configured in configuration YAML file. See `server.applicationConnectors.port` and `server.adminConnectors.port` properties.

## How to use it

You can call this service from your favorite REST client or from a command line. Below is example request to initiate payment:

```bash
curl -i -X POST -H "Content-Type: application/json" -d "{...}" "http://localhost:8080/payment/initiate"
```

You can also use the Dropwizard healthcheck functionality to check the health of the service:

```bash
curl -i "http://localhost:8081/healthcheck"
```

## API documentation

API documentation for service interface has been generated using Swagger. To see this information open `http://localhost:8080/swagger` in the browser.

You can also access raw Swagger API metadata opening following URL in the browser:

```bash
http://localhost:8080/swagger.yaml
```

or

```bash
http://localhost:8080/swagger.json
```

Alternatively enter the above URL's in a terminal window, prefixed by `curl -i` command.
