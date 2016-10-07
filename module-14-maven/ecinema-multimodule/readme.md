# Multimodule Spring MVC app

This project is deployed by the following command:
```
    mvn clean install tomcat7:deploy
```
It is necessary to configure user rights in **tomcat-users.xml** file to be able to deploy this app:
```
    <role rolename="tomcat"/>
    <role rolename="manager-gui"/>
    <role rolename="manager-script"/>
    <role rolename="manager-jmx"/>
    <user username="user" password="user" roles="tomcat,manager-gui,manager-script,manager-jmx"/>
```
After deployment you can access this app by the url http://localhost:8080/ecinema/