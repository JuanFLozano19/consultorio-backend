# Consultorio Dr. Jeison Patiño · Backend

API REST del consultorio: servicios, solicitudes de cita desde la web y panel administrativo.
**Spring Boot 3.5 · Java 17 · PostgreSQL · Flyway · JWT · Swagger**, con **arquitectura hexagonal** por módulos.

## Requisitos

- Java 17 o superior
- PostgreSQL (local con Docker o una cuenta en Supabase)
- Archivo `.env` con las variables (copiar de `.env.example`)

## Desarrollo local

```bash
cp .env.example .env                 # y completar JWT_SECRET, ADMIN_CORREO, ADMIN_PASSWORD
docker compose up -d db              # PostgreSQL en localhost:5432
./mvnw spring-boot:run               # API en http://localhost:8080
./mvnw test                          # pruebas unitarias
```

- Swagger UI: http://localhost:8080/swagger-ui.html
- Salud: http://localhost:8080/actuator/health
- Flyway crea las tablas y los servicios iniciales al arrancar.
- El administrador se crea solo la primera vez, con `ADMIN_CORREO` y `ADMIN_PASSWORD` (10+ caracteres).

## Arquitectura

```
src/main/java/co/com/drjeisonpatino/consultorio/
├── core/                  ← transversal
│   ├── config/            ← reloj (zona America/Bogota), @EnableAsync
│   ├── exception/         ← GlobalExceptionHandler, ErrorResponse
│   └── security/          ← JWT, CORS, rate limit, Swagger
├── cita/                  ← solicitudes de cita
│   ├── domain/            ← Cita (reglas de negocio), EstadoCita, Franja, excepciones
│   ├── ports/input        ← casos de uso: Solicitar, Listar, CambiarEstado
│   ├── ports/output       ← CitaRepositoryPort, NotificacionCitaPort
│   ├── application/       ← servicios que implementan los casos de uso + DTO
│   └── adapters/          ← REST (entrada), JPA y correo (salida)
├── servicio/              ← servicios que muestra la web (misma estructura)
└── usuario/               ← login del panel y administrador inicial
```

Reglas del dominio (en `Cita`):

- La fecha preferida no puede ser pasada, ni más de 90 días adelante, ni domingo.
- Hay que aceptar el tratamiento de datos (Ley 1581 de 2012).
- Estados: `PENDIENTE → CONFIRMADA → ATENDIDA`, y `PENDIENTE` o `CONFIRMADA → CANCELADA`.

## Endpoints

| Método | Ruta                                  | Acceso  | Descripción                         |
| ------ | ------------------------------------- | ------- | ----------------------------------- |
| GET    | `/api/servicios`                      | Público | Servicios activos                   |
| POST   | `/api/citas`                          | Público | Solicitar cita (máx. 5 por IP/hora) |
| POST   | `/api/auth/login`                     | Público | Login, devuelve JWT                 |
| GET    | `/api/auth/perfil`                    | Admin   | Usuario autenticado                 |
| GET    | `/api/citas?estado=&pagina=&tamano=`  | Admin   | Listar citas                        |
| GET    | `/api/citas/{id}`                     | Admin   | Ver una cita                        |
| PATCH  | `/api/citas/{id}/estado`              | Admin   | Confirmar, cancelar o atender       |
| GET    | `/api/servicios/todos`                | Admin   | Todos los servicios                 |
| POST   | `/api/servicios`                      | Admin   | Crear servicio                      |
| PUT    | `/api/servicios/{id}`                 | Admin   | Editar o desactivar servicio        |

## Variables de entorno

Ver `.env.example`. Las obligatorias son `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` y `JWT_SECRET` (32+ caracteres).
Para el aviso por correo de nuevas citas: `NOTIFICACION_CORREO`, `MAIL_USERNAME` y `MAIL_PASSWORD`
(con Gmail, una contraseña de aplicación). Por privacidad, el correo no incluye documento ni motivo.

## Despliegue

- **Base de datos:** Supabase (PostgreSQL). Usar la URL JDBC con `?sslmode=require`.
- **API:** Render o Railway con el `Dockerfile` incluido. Health check en `/actuator/health`.
- Configurar `CORS_ALLOWED_ORIGINS` con el dominio del frontend.

## Ramas y flujo de trabajo

| Rama            | Para qué                                                     |
| --------------- | ------------------------------------------------------------ |
| `main`          | Producción: lo que ve el público. Solo recibe desde `preproduccion`. |
| `preproduccion` | Pruebas finales y aprobación del doctor antes de publicar.   |
| `desarrollo`    | Integración del trabajo diario.                              |

1. Crear una rama desde `desarrollo`: `git switch desarrollo && git pull && git switch -c feature/mi-cambio`
2. Pull Request de `feature/...` → `desarrollo`.
3. Cuando un grupo de cambios está listo: Pull Request `desarrollo` → `preproduccion` y se revisa.
4. Aprobado: Pull Request `preproduccion` → `main` (publica).

GitHub Actions (`.github/workflows/ci.yml`) compila y corre las pruebas en cada push y Pull Request de las tres ramas.
