# Auth básico + Roles (Spring Boot 3)

Proyecto basico para practica de autenticación y autorizacion por Roles

## Stack

- Java 17+
- Spring Boot 3
- Spring Security + JWT
- Spring Data JPA + H2 (demo)
- Springdoc OpenAPI (Swagger UI)

>Para levantar proyecto `mvn spring-boot:run`.

## Documentación API

- **OpenAPI JSON**: `http://localhost:8080/api/docs`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`

## Endpoints principales
-Se pueden ver en la documentacion de Swagger

### Registro

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"test@mail.com","password":"Password123"}'
```

### Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@mail.com","password":"Password123"}'
```

### Perfil (protegido)

```bash
curl http://localhost:8080/api/me \
  -H "Authorization: Bearer <TOKEN>"
```

### Admin (requiere ROLE_ADMIN)

```bash
curl http://localhost:8080/api/admin/dashboard \
  -H "Authorization: Bearer <TOKEN>"
```

## Notas de seguridad

- El secreto JWT está en `application.yml` el cual es leido del .env.
- Por defecto se crea el rol `ROLE_USER` al registrar.
- Para crear un ADMIN debe realizarse por BD. Feature posible: Modulo de creacion de admin con contraseña precargada
