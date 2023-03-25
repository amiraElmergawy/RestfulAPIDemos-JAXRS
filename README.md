# RestfulAPIDemos-JAXRS

Rest API Demos using JAX-RS

## Technologies Used

- Java - JDK 17
- Jakarta ee 10
- JAX-RS (impl used: jersey)
- JSON-B (for JSON binding)
- JAX-B (for XML binding)
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
- Deploy the application using the following maven command.

```bash
    mvn clean package tomcat7:deploy
```

- Finally, open your browser and type the following url to test <a href="http://localhost:9090/rest-jersey/webapi/v1/users">Users </a> resource from simple example

  
     http://localhost:9090/rest-jersey/webapi/v1/users 

- Note that my tomcat server port is '9090', So you need to change this to your own port
