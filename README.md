# kogito-banking project

# for kogito

Additional testcases : please see comments in the drl files 

```
curl -H "Content-Type: application/json" -X POST -d '{"transactions":[{"id": "tx_001", "amount":2000 }]}' http://localhost:8080/checktx

curl -H "Content-Type: application/json" -X POST -d '{"transactions":[{"id": "tx_001", "amount":99 }]}' http://localhost:8080/checksmalltx
```
CEP demo work in progress, the following curl command will create 2 events and simulate ATM events being detected over 2 ATM locations that belongs to the same account within 10 secs

```
 curl localhost:8080/rest/hello
```

### POST /hello

Post "hello":

```sh
curl -H "Content-Type: application/json" -X POST -d '{"strings":["hello"]}' http://localhost:8080/hello
```

the service will return `["hello", "world"]`



This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `kogito-banking-1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/kogito-banking-1.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/kogito-banking-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.