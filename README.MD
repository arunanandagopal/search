Implementation of Recursive	File	Structure in Spring Boot app

1) First create the database and a super user manually in the postgres.

CREATE DATABASE filesystem;
CREATE USER postgres WITH ENCRYPTED PASSWORD 'admin';
GRANT ALL PRIVILEGES ON DATABASE filesystem TO postgres;
ALTER USER postgres WITH SUPERUSER;

2) Import Project into your favorite IDE.

3) Execute "mvn spring-boot:run"