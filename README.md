# kogito-banking project

Demo on Quarkus Kogito 

## for kogito CEP

#### Simulate multiple ATM withdrawals in multiple locations in a time window

##### Running the app

`mvn clean compile quarkus:dev`

##### Sending a `Transaction` event to a REST endpoint, e.g.:

```
curl -H "Content-Type: application/json" -X POST -d @event{1...5}.json  http://localhost:8080/rest/event
```

event1, event3 causes the rule to be invoked. (the events must be sent within 10 secs)


Sample output:
```
EntryPoint::ATM Stream

Detected potential fraud for the following activities: 
ATM tx1 : Transaction(customer=com.redhat.app.kogito.models.Customer@4c63d6fd, status=NEW, timestamp=Wed May 27 13:50:16 SGT 2020, transactionType=WITHDRAWAL, amount=3000.0, account=com.redhat.app.kogito.models.Account@306aa073, location=EAST, id=TX_0003)
ATM tx2 : Transaction(customer=com.redhat.app.kogito.models.Customer@ce73942, status=NEW, timestamp=Wed May 27 13:50:45 SGT 2020, transactionType=WITHDRAWAL, amount=3000.0, account=com.redhat.app.kogito.models.Account@22bda7bb, location=WEST, id=TX_0001)
org.drools.modelcompiler.consequence.DroolsEntryPointImpl@320b8b

!!!! Registered fraud event!!!!

ATM activity at 2 locations detected for account:  acct_1
```


Sample event data:
```
{
    "id": "TX_0003",
    "account": {
        "accountId": "acct_1"
    },
    "timestamp": 15000000,
    "location": 2,
    "amount": 3000,
    "customer": {
        "name": "john",
        "accounts": []
    },
    "status": "NEW",
    "transactionType": 1

    
}
```



## for kogito hello world

Additional hello world testcases to play around with RuleUnits : please see comments in the drl files 

```
curl -H "Content-Type: application/json" -X POST -d '{"transactions":[{"id": "tx_001", "amount":2000 }]}' http://localhost:8080/checktx

curl -H "Content-Type: application/json" -X POST -d '{"transactions":[{"id": "tx_001", "amount":99 }]}' http://localhost:8080/checksmalltx
```
Test CEP , generates 3 events to simulate ATM withdrawal in multiple location within a timeframe

```
 curl localhost:8080/rest/hello
```

### POST /hello

Post "hello":

```sh
curl -H "Content-Type: application/json" -X POST -d '{"strings":["hello"]}' http://localhost:8080/hello
```

the service will return `["hello", "world"]`


## Generic Quarkus project README below:

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