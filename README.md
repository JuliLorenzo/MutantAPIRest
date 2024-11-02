# ADN Mutante

## Descripción

ADN Mutante es una aplicación Java basada en Spring Boot que permite verificar si un conjunto de cadenas de ADN corresponde a un mutante. Utiliza una base de datos H2 en memoria para almacenar los registros de ADN analizados.

## Características

- Verificación de ADN mutante a través de un conjunto de reglas.
- Almacenamiento de registros de ADN en una base de datos H2.
- API RESTful para interactuar con la aplicación.
- Consola H2 habilitada para visualizar los datos almacenados.

## Requisitos

- Java 17 o superior
- Maven o Gradle
- IDE (opcional, pero recomendado)

## Instalación

1. Clona el repositorio:

   ```bash
   git clone <URL_DEL_REPOSITORIO>
   cd adnMutante
   ```

2. Compila el proyecto:

   Si usas Gradle:

   ```bash
   ./gradlew build
   ```

3. Ejecuta la aplicación:

   Si usas Gradle:

   ```bash
   ./gradlew bootRun
   ```

4. Accede a la consola H2 en:

   ```
   http://localhost:8080/h2-console/
   ```

   - JDBC URL: `jdbc:h2:mem:testdb`
   - Usuario: `sa`
   - Contraseña: (dejar vacío)

## Uso

La API proporciona un endpoint para verificar si un ADN es mutante:

- **POST** `/api/mutante/`

### Ejemplo de solicitud
```json
{
    "adnArray": "AGTC,AGTC,AGTC,AGTC"
}
```

### Respuestas

- **200 OK**: Si el ADN es mutante.
- **403 Forbidden**: Si el ADN no es mutante.
- **400 Bad Request**: Si el cuerpo de la solicitud es inválido.

## Pruebas

Para ejecutar las pruebas, utiliza el siguiente comando:

```bash
./gradlew test
```