# E-Wallet

## Features Implemented

- Implement REST API
- Complete database interaction with the domain model
- Create, Deposit, Withdraw, Wallet to Wallet transactions and list all wallets
- Run with Dockerfile or Docker-Compose
- Basic endpoint authorization with JWT token mechanism 

## Technologies Used

- Java 
- Spring Boot
- Maven
- MySQL
- Spring Secuirty with JWT 
- Docker

## How to Run the application

* By using Dockerfile
    > docker network create wallet-mysql

    > docker run -p3306:3306 --name mysqldb --network wallet-mysql -e MYSQL_ROOT_PASSWORD=Stl@123456 -e MYSQL_DATABASE=ewalletdb -d mysql:8

    > docker build -t app .

    > docker run -p8080:8080 --name app --net wallet-mysql -e MYSQL_HOST=mysqldb -e MYSQL_USER=root -e MYSQL_PASSWORD=Stl@123456 -e MYSQL_PORT=3306 app
  
* By using docker compose
    >docker compose up .

## URLs

* Demonstrate 3 types of users (admin, user & any)
    >http://localhost:8080/api/access/all
   
    >http://localhost:8080/api/access/user

    >http://localhost:8080/api/access/admin

* SignUp & SignIn
   >http://localhost:8080/api/auth/signup
   
  >http://localhost:8080/api/auth/signin
  
* Wallet APIs
  >http://localhost:8080/api/wallet/create

  > http://localhost:8080/api/wallet/all/{id}

  > http://localhost:8080/api/wallet/deposit

  > http://localhost:8080/api/wallet/withdraw

  > http://localhost:8080/api/wallet/wallet-to-wallet