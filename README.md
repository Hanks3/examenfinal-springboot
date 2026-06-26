# ExamenFinal — Gestión de Preguntas Tipo Test

Aplicación Spring Boot 3.1.0 para la gestión de preguntas de exámenes tipo test con
soporte de tres tipos de preguntas (Verdadero/Falso, Selección Única, Selección Múltiple),
seguridad JWT, y despliegue en Docker.

---

## Requisitos

- **Java 17** (JDK)
- **Maven 3.8+**
- **Docker Desktop** (solo para despliegue con Docker)

---

## 1. Ejecución en local (perfil `dev` — H2 en memoria)

Por defecto la aplicación arranca con el perfil `dev` y una base de datos H2 en memoria,
sin necesidad de instalar MySQL.

```bash
# Compilar
mvn clean package -DskipTests

# Ejecutar
mvn spring-boot:run

# O bien
java -jar target/examenfinal-1.0.0.jar
```

La aplicación estará disponible en: **http://localhost:8080**

### Datos de prueba precargados (seed automático via `data.sql`)

Se cargan 5 temáticas, 10 preguntas de ejemplo y 2 usuarios:

| Usuario | Contraseña | Roles               |
|---------|------------|---------------------|
| admin   | 123456     | ROLE_ADMIN, ROLE_USER |
| user    | 123456     | ROLE_USER           |

> Las contraseñas están almacenadas como hash BCrypt en `data.sql`.

### H2 Console

Accesible en: **http://localhost:8080/h2-console**
- JDBC URL: `jdbc:h2:mem:examenfinal`
- User: `sa`
- Password: *(vacío)*

---

## 2. Ejecutar los tests

Los tests usan un perfil `test` con H2 en memoria aislado, sin afectar a la base de datos
de desarrollo ni de producción.

```bash
# Todos los tests
mvn test

# Tests unitarios de servicio
mvn test -Dtest="PreguntaServiceTest"

# Test de integración REST
mvn test -Dtest="PreguntaRestControllerTest"

# Ambos
mvn test -Dtest="PreguntaServiceTest,PreguntaRestControllerTest"
```

---

## 3. Ejecución con Docker (perfil `prod` — MySQL)

### Requisitos adicionales

- **Docker Engine** 20.10+
- **Docker Compose** v2

### Arrancar

```bash
# Construir imágenes e iniciar los contenedores
docker compose up --build -d

# Ver logs
docker compose logs -f app
```

Esto levanta dos servicios:
- **db** (`examenfinal-db`): MySQL 8.0 con健康 check
- **app** (`examenfinal-app`): aplicación Spring Boot (perfil `prod`)

La aplicación estará disponible en: **http://localhost:8080**

### Detener

```bash
docker compose down

# Eliminar también el volumen de datos
docker compose down -v
```

### Variables de entorno configurables

Se definen en `docker-compose.yml` o mediante un fichero `.env`:

| Variable       | Default        | Descripción                    |
|----------------|----------------|--------------------------------|
| `DB_HOST`      | `db`           | Host de MySQL                  |
| `DB_PORT`      | `3306`         | Puerto de MySQL                |
| `DB_NAME`      | `examenfinal`  | Nombre de la base de datos     |
| `DB_USERNAME`  | `root`         | Usuario de MySQL               |
| `DB_PASSWORD`  | `root`         | Contraseña de MySQL            |

---

## 4. API REST (endpoints)

### Autenticación — `POST /api/auth`

```bash
# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"123456"}'

# Registro
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"nuevo","email":"nuevo@email.com","password":"secreta"}'
```

### Preguntas — `GET/POST/PUT/DELETE /api/preguntas`

```bash
# Listar todas (paginado)
curl -X GET http://localhost:8080/api/preguntas?page=0&size=10 \
  -H "Authorization: Bearer <token>"

# Obtener por ID
curl -X GET http://localhost:8080/api/preguntas/1 \
  -H "Authorization: Bearer <token>"

# Crear
curl -X POST http://localhost:8080/api/preguntas \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "enunciado": "2+2=4?",
    "tipo": "V_F",
    "tematicaId": 1,
    "opcionCorrecta": true
  }'

# Actualizar
curl -X PUT http://localhost:8080/api/preguntas/1 \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{ "enunciado": "2+2=5?", "tipo": "V_F", "tematicaId": 1, "opcionCorrecta": false }'

# Eliminar
curl -X DELETE http://localhost:8080/api/preguntas/1 \
  -H "Authorization: Bearer <token>"
```

### Filtros en listado

```
GET /api/preguntas?page=0&size=10
GET /api/preguntas?page=0&size=10&tematicaId=1
GET /api/preguntas?page=0&size=10&tipo=V_F
GET /api/preguntas?page=0&size=10&tematicaId=1&tipo=V_F
```

### Navegación web

- **http://localhost:8080/** — Página de bienvenida
- **http://localhost:8080/preguntas** — Listado de preguntas (requiere login)
- **http://localhost:8080/preguntas/nueva** — Crear pregunta (solo ADMIN)

---

## 5. Estructura del proyecto

```
src/main/java/com/examenfinal/
├── config/          → SecurityConfig, AppConfig
├── controller/      → AuthController, HomeController, PreguntaMvcController, PreguntaRestController
├── converter/       → StringListConverter (JPA)
├── dto/             → PreguntaDTO, AuthResponse, LoginRequest, RegisterRequest
├── entity/          → Pregunta, PreguntaVerdaderoFalso, PreguntaSeleccionUnica,
│                      PreguntaSeleccionMultiple, Tematica, Usuario, Rol
├── exception/       → PreguntaNoEncontradaException, GlobalExceptionHandler
├── repository/      → PreguntaRepository, TematicaRepository, UsuarioRepository
├── security/        → JwtUtils, JwtAuthenticationFilter, CustomUserDetailsService
└── service/         → PreguntaService (interface + impl)

src/test/java/com/examenfinal/
├── controller/      → PreguntaRestControllerTest
└── service/         → PreguntaServiceTest
```

---

## 6. Perfiles de Spring

| Perfil | Base de datos | Uso                          |
|--------|---------------|------------------------------|
| `dev`  | H2 (memoria)  | Desarrollo local (default)   |
| `prod` | MySQL         | Producción / Docker           |
| `test` | H2 (memoria)  | Tests automatizados           |

Para cambiar de perfil manualmente:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```
