# Native With Spring boot 3 and GraalVM

### Intall GraalVM for JDK 17 with sdkman 

```
sdk install java 22.3.r17-grl 
```

Make sure that $JAVA_HOME is set to point to your GraalVM installation

```bash
 sdk use java 22.3.r17-grl 
```

Check the changements 

```
java --version
```

Output

```shell
openjdk 17.0.5 2022-10-18
OpenJDK Runtime Environment GraalVM CE 22.3.0 (build 17.0.5+8-jvmci-22.3-b08)
OpenJDK 64-Bit Server VM GraalVM CE 22.3.0 (build 17.0.5+8-jvmci-22.3-b08, mixed mode, sharing)
```

### Build and execute the native image

```
./mvnw -Pnative native:compile
```

We can run the executable

```
./target/contact-service  
```

### Test the application

Go to directory `src/main/api-test` and in the `contacts.http` file execute the http requests




