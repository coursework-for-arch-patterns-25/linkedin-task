# LinkedIn OAuth Login for ConSOLIDate.org

This project implements LinkedIn OAuth2 login functionality for the ConSOLIDate application using Spring Boot.

## Features
- Login via LinkedIn (accessible from home page)
- Home page displays a welcome message with the user's name after login

## Usage
```bash
export LINKEDIN_CLIENT_ID=your_client_id
export LINKEDIN_CLIENT_SECRET=your_client_secret
./mvnw clean spring-boot:run
```

Access the app at [http://localhost:8080](http://localhost:8080)

## Sequence Diagrams
Sequence diagrams for the LinkedIn OAuth flow (both technical and non-technical) can be found in the `/docs` directory:
- `docs/linkedin-non-technical-seq-diagram.puml`
- `docs/linkedin-technical-seq-diagram.puml`
- `docs/linkedin-non-technical-seq-diagram.png`
- `docs/linkedin-technical-seq-diagram.png`





