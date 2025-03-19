# demo Bank RESTful API

- Spring Boot;
- Spring Web;
- Spring Security;
  - JWT for security;
- Spring Data JPA;
- OpenAPI (Swagger) for endpoints documentation;
- H2 database in development;
- PostgresSQL in production;
- Lombok for clean code;
- Logback (Sl4j) for logging;
- Deployment with Railway;

## Usage
Access: `/swagger-ui/index.html` for endpoint JSON templates and more information on endpoints.
- `POST` `/login` for authentication, retuns JWT toekn `Bearer`.<br/>
- `POST` `/register` user creation, retuns user with created accounts `SAVINGS` and `CHECKING` with balance 0.0 and `CHECKING` with `maxLimit` and `availableLimit` of `1000.00`.<br/>
  - `WITHDRAWAL` is only available for the account owner.
  - `DEPOSIT` can be made to any valid account.
- `POST` `/accounts` user must provider token for authentication obtained in the /login endpoint, and a account to DEPOSIT or WITHDRAWAL.<br/>
- `GET` `/users/{username}` retrieve user information.
<br/>

## Diagram
```mermaid
erDiagram
    USER {
        long id
        string username
        string password
        string salt
        integer version
    }
    USER ||--o{ ACCOUNT : "accounts"

    ACCOUNT {
        long id
        string accountNumber
        BigDecimal balance
        AccountType accountType
        BigDecimal availableLimit
        BigDecimal maxLimit
    }

```
