# Native With Spring boot 3.2 and GraalVM

### Intall GraalVM for JDK 21 with sdkman 

```
sdk install java  21-graalce
```

Make sure that $JAVA_HOME is set to point to your GraalVM installation

```bash
 sdk use java 21-graalce 
```

Check the changements 

```
java --version
```

Output

```shell
openjdk 21 2023-09-19
OpenJDK Runtime Environment GraalVM CE 21+35.1 (build 21+35-jvmci-23.1-b15)
OpenJDK 64-Bit Server VM GraalVM CE 21+35.1 (build 21+35-jvmci-23.1-b15, mixed mode, sharing)
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




