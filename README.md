# TP Integrador - Pedido -> Envio

Proyecto Java (consola) que implementa la relación 1→1 unidireccional Pedido → Envio.
Tecnologías: Java 17, JDBC, MySQL, Maven.

## Pasos para levantar el proyecto

1. No es necesario crear manualmente las tablas.  
   El archivo `src/main/resources/schema.sql` se ejecuta automáticamente al iniciar la aplicación
   y genera el esquema completo en la base H2.

2. Verificar que `src/main/resources/db.properties`
   contiene la URL correcta para H2.

3. Ejecutar con Maven:
   mvn clean package
   java -jar target/pedido-envio-tp-1.0-SNAPSHOT.jar
## Funcionalidades
- CRUD básico para Pedido y Envio (baja lógica).
- Creación transaccional de Pedido + Envio.
- Menu de consola para operar.
