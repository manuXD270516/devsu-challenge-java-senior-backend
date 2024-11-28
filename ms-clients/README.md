# Microservicio de Clientes (ms-clients)

## Descripción
El microservicio **ms-clients** gestiona la información de los clientes y publica eventos relacionados con las operaciones de clientes (crear, actualizar, eliminar) en un sistema de mensajería RabbitMQ.

## Funcionalidad principal
- **Gestión de Clientes:**
    - Crear, actualizar, eliminar y consultar clientes.
- **Publicación de eventos:**
    - Cada operación realizada genera un evento que se publica en RabbitMQ para ser consumido por otros microservicios.

## Tecnologías utilizadas
- **Java 17**
- **Spring Boot 3.x**
    - Spring Web
    - Spring Data JPA
    - Spring AMQP (RabbitMQ)
- **PostgreSQL** como base de datos relacional.
- **RabbitMQ** como sistema de mensajería.

## Endpoints principales
| Método | Endpoint           | Descripción                        |
|--------|--------------------|------------------------------------|
| GET    | `/clientes`        | Listar todos los clientes.        |
| POST   | `/clientes`        | Crear un nuevo cliente.           |
| PUT    | `/clientes/{id}`   | Actualizar un cliente existente.  |
| DELETE | `/clientes/{id}`   | Eliminar un cliente.              |

## Configuración necesaria

### Base de datos
- Crea una base de datos en PostgreSQL con las credenciales:
    - Nombre: `ms_clients_db`
    - Usuario: `postgres`
    - Contraseña: `password`

### RabbitMQ
- Asegúrate de que RabbitMQ esté configurado y ejecutándose en `localhost:5672`.

### Archivo `application.properties`
Configura las propiedades de conexión:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ms_clients_db
spring.datasource.username=postgres
spring.datasource.password=password

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

## Pasos para ejecutar el proyecto

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/manuXD270516/devsu-challenge-ms-clients.git
   cd ms-clients
   ```

2. **Compilar y ejecutar:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. **Probar los endpoints:**
    - Utiliza herramientas como Postman para probar los endpoints mencionados anteriormente.

---


## Documentación OpenAPI
- El microservicio expone una documentación de API interactiva utilizando OpenAPI.
- Puedes acceder a la interfaz de OpenAPI en la siguiente URL cuando el servicio esté en ejecución:
  ```http://localhost:8081/swagger-ui/index.html```

## Alternativa con Docker Compose
- También puedes utilizar el archivo `docker-compose.yml` proporcionado para iniciar rápidamente el microservicio junto con la base de datos y RabbitMQ.
- Ejecuta el siguiente comando para iniciar todos los servicios:
  ```bash
  cd ..
  docker-compose up -d --build
  ```
- Asegurate de tener ambos microservicios en el mismo directorio al mismo nivel del archivo **docker-compose.yml**
- En el mismo directorio raiz que contiene los dos proyectos se encuentra el archivo **devsu_challenge.postman_collection.json** que contiene la coleccion de peticiones HTTP de ambos microservicios lista a importar en la herramienta POSTMAN 