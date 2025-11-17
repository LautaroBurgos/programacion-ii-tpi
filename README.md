# TP Integrador - Pedido -> Envio

Proyecto Java (consola) que implementa la relación 1→1 unidireccional Pedido → Envio.
Tecnologías: Java 17, JDBC, MySQL, Maven.

## Pasos para levantar
1. Crear la base de datos ejecutando el script SQL `01_esquema_corregido.sql` (adjunto en el repo).
2. Configurar `src/main/resources/db.properties` con tus credenciales MySQL.
3. Compilar con Maven: `mvn clean package`
4. Ejecutar: `java -cp target/pedido-envio-tp-1.0-SNAPSHOT.jar ar.edu.tfi.main.Main`

## Funcionalidades
- CRUD básico para Pedido y Envio (baja lógica).
- Creación transaccional de Pedido + Envio.
- Menu de consola para operar.

