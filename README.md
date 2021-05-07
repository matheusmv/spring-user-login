# spring-user-login

Complete user login and registration + email verification

    Spring Boot
    Spring Security
    MongoDB
    Java Mail
    Email verification with expiry

## Settings

application.yml:

        spring:
          data:
            mongodb:
              uri: mongodb+srv://

        mail:
          host: smtp.gmail.com
          port: 587
          username: email-service
          password: email-service-password

## Example requests

CURL

        curl --location --request POST 'localhost:8080/api/v1/registration' \
        --header 'Content-Type: application/json' \
        --data-raw '{
            "firstName": "firstNameExample",
            "lastName": "lastNameExample",
            "email": "example@email.com",
            "password": "password"
        }'
