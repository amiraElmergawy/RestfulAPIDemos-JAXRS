# RestfulAPIDemos-JAXRS

Rest API Demos using JAX-RS

## Technologies Used

- Java - JDK 17
- Jakarta ee 10
- JAX-RS (impl used: jersey)
- JSON-B
- JAX-B
- Maven (archetype used: "jersey-quickstart-webapp", version: 3.1.1)
- Lombok
- Tomcat 10



## Run Locally

Clone the project

```bash
  git clone https://github.com/amiraElmergawy/RestfulAPIDemos-JAXRS
```

Go to the project directory

```bash
  cd RestfulAPIDemos-JAXRS
```

- Run your tomcat apache server and then change the configuration of tomcat in pom.xml.
- Finally, deploy the application using the following maven command.

```bash
    mvn clean package tomcat7:deploy
```
