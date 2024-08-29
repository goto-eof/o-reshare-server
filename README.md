```
   ___        __      __ _
  /___\      /__\ ___/ _\ |__   __ _ _ __ ___
 //  //____ / \/// _ \ \| '_ \ / _` | '__/ _ \
/ \_//_____/ _  \  __/\ \ | | | (_| | | |  __/
\___/      \/ \_/\___\__/_| |_|\__,_|_|  \___|
                                    SERVER
------------------------------------------------
```

# O-ReShare Server

As is written in the description, O-ReShare is an open source file sharing micro platform enough secure. This project consists of a back-end side and a front-end side. The back-end is solely responsible for the storage of the files. While the front-end has as 
objective to encrypt the files selected by a user.

The front-end side is available [here](https://github.com/goto-eof/o-reshare-client)

# Technologies
- Java (JDK 21)
- Spring Boot
- Apache FileUpload2 (Jakarta)
- Hibernate
- Liquibase
- Postgres
- Docker
- Lombok
- MapStruct
- Gradle

<img src="https://andre-i.eu/api/v1/ipResource/custom.png?host=https://github.com/goto-eof/o-reshare-server" onerror="this.parentNode.removeChild(this)" />

## Configure and run the application
- copy the `application.yml` and create `application-dev.yml`
- set `spring.profiles.active=dev` as Environment Variable of your IDE
- edit the the `application-dev.yml` and set the `o-reshare.files.path` property to an existing path, like the Desktop path
- move to the `docker-dbms` directory and run `sudo docker-compose up -d` in order to run the DBMS
- run the project as Spring Boot project
