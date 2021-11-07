# VPR-Backend
This project includes the backend for the VPR-project.

# Installation and Getting Started
- To run the server you first have to install a MySQL database. 
> For example MariaDB (https://mariadb.com/downloads/)

- Then you have to create a the database with the "create VPR-DB.sql"-file.

- Create a new user for the db e.g. named "springuser"
> The database should only be reachable from localhost to improve the security!

- Next create a "application.properties"-file in "vpr/src/main/resources". It should contain following content:
``` 
# "update" will automatically get the db-structure
# It can be "none", "update", "create", or "create-drop", but you have to start with "create" or "update" and should use "none" in production(for security).
# see https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#configurations-hbmddl for more information.
spring.jpa.hibernate.ddl-auto=update

# Specify the database address, port and name
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/vpr

# Add the login-data
spring.datasource.username=<USERNAME>
spring.datasource.password=<PASSWORD>

spring.datasource.driver-class-name =com.mysql.jdbc.Driver
#spring.jpa.show-sql: true
``` 

You should now be able to start the application :D