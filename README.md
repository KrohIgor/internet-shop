
# Internet Shop

![Header Image](src/main/resources/internet-shop.jpg)

# Table of Contents

[Project purpose](#purpose)

[Project structure](#structure)

[For developer](#developer)

[Author](#author)

## <a name='purpose'></a>Project purpose

This project is a simplified internet shop.

<hr>

This internet shop performs these basic functions:

- Registration, log in forms and logout
- Shopping cart and order services
- Two roles: User and Admin

<hr>

This project has authentication and authorization filters, DAO and Service layers, Servlets and JSP pages.

DAO layer has two implementations: inner storage based on List and outer storage based on MySQL DB.

## <a name='structure'></a>Project structure

- Java 11
- Maven
- MavenCheckstylePlugin 3.1.1
- javax.servlet 3.1.0
- javax.jstl 1.2
- mysql-connector-java 8.0.20
- log4j 1.2.17

## <a name='developer'></a>For developer
To run this project you need to install:

- <a href="https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html">Java 11</a>
- <a href="https://tomcat.apache.org/download-90.cgi">Tomcat</a>
- <a href="https://www.mysql.com/downloads/">MySQL</a> (can use another RDBMS)

<hr>

Add this project to your IDE as Maven project.

Add Java SDK 11 in project structure.

Configure Tomcat:
- Add war
- Add Java SDK 11

Change a path to your Log file in **src/main/resources/log4j.properties** on line 12.

<hr>

To work with MySQL you need to:
- Use file **src/main/resources/init_db.sql** to create schema and all the tables required by this app in MySQL DB
- Change username and password to match with MySQL in **src/main/java/mate/academy/internet/shop/util/ConnectionUtil.java** class on 19 and 20 lines

<hr>

To work with inner Storage you need to:

- Need to remove annotation @Dao from classes :
    1. **src/main/java/mate/academy/internet/shop/dao/jdbc/OrderDaoJdbcImpl.java**
    2. **src/main/java/mate/academy/internet/shop/dao/jdbc/ProductDaoJdbcImpl.java**
    3. **src/main/java/mate/academy/internet/shop/dao/jdbc/ShoppingCartDaoJdbcImpl.java**
    4. **src/main/java/mate/academy/internet/shop/dao/jdbc/UserDaoJdbcImpl.java**
- Need to add annotation @Dao to classes :
    1. **src/main/java/mate/academy/internet/shop/dao/impl/OrderDaoImpl.java**
    2. **src/main/java/mate/academy/internet/shop/dao/impl/ProductDaoImpl.java**
    3. **src/main/java/mate/academy/internet/shop/dao/impl/ShoppingCartDaoImpl.java**
    4. **src/main/java/mate/academy/internet/shop/dao/impl/UserDaoImpl.java**

 The class **src/main/java/mate/academy/internet/shop/lib/Injector.java**
 checks all classes in all packages and checks the annotation
 over the class name and selects the desired classes that implements the interfaces
<hr>

Run the project:

Main page is at URL: .../{context_path}

For MySQL DAO only **on first run** of the project, for inner Storage **on every launch**, to create default users open URL: .../{context_path}/injectData

<p>By default there are two users with an USER role (name = "Bob", login = "bob", password = "1"),
(name = "Alisa", login = "alisa", password = "1")<br>
one with an ADMIN role (login = "admin", password = "3"),<br>

## <a name='author'></a>Author
[Ihor Krokhmal](https://github.com/KrohIgor)

