# Qovery Boot

## Building
```
$ ./gradlew test assemble
```

## Running
With Gradle:
```
$ ./gradlew bootRun
```

From jar:
```
$ java -jar build/libs/qovery-boot-0.0.1-SNAPSHOT.jar
```


```
$ qovery project env add 'spring.profiles.active' 'prod'
$ qovery project env add 'spring.datasource.url' '$QOVERY_DATABASE_MY_POSTGRESQL_7274989_CONNECTION_URI'
$ qovery project env add 'spring.datasource.username' '$QOVERY_DATABASE_MY_POSTGRESQL_7274989_CONNECTION_URI'
$ qovery project env add 'spring.datasource.password' '$QOVERY_DATABASE_MY_POSTGRESQL_7274989_CONNECTION_URI'
```
