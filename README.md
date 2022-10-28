[![Java CI with Maven](https://github.com/Psimage/spring-class-not-found/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/Psimage/spring-class-not-found/actions/workflows/maven.yml)

# Description

This is a minimal project that replicates an issue in Spring Cloud Loadbalancer.
When trying to invoke a Feign client for the first time from a thread that whose class loader is not Spring's
`LaunchedURLClassLoader`, the attempt to create context for feign client will result in `ClassNotFoundException`.

The issue gets reproduces only when the application starts from Spring's executable jar (uses Spring's class loader).

References:

* https://github.com/spring-cloud/spring-cloud-openfeign/issues/475
* https://stackoverflow.com/questions/72800515/could-not-find-class-org-springframework-boot-autoconfigure-condition-onbeancon

# Prerequisites

* Java 17

# Build & Test

`mvnw verify`

This will package and run tests on the Jars.
