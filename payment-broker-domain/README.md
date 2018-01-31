Payment Broker Domain
================

Module provides domain classes used by Payment Broker Service

Domain classes use Jackson library (https://github.com/FasterXML/jackson) to handle JSON serialization / deserialization.

NOTE: Module was created because Payment Broker Service does not provide any domain library but this might change in the future. It is intention to use external payment broker domain library.

## Requirements

 * Java JDK 1.8+
 * Maven 3

## How to build it

To build this project execute the following command:

```bash
  mvn clean install
```