# Desarrollador-Backend-SR
This is a project to CREATE, UPDATE, ASSIGN order to drivers

Instrucciones para ejecutar el proyecto:
1. Clonar el repositorio:
   ```bash
   git clone <repository_url>
   cd Desarrollador-Backend-SR
   ```
2. Abrir el proyecto en tu IDE favorito (por ejemplo, Visual Studio Code, PyCharm).
3. Instalar el proyecto con Maven:
   ```bash
   mvn clean install
   ```
4. Construir la imagen de Docker:
   ```bash
   docker build -t java_app .
   ```
5. Ejecutar el contenedor de Docker:
   ```bash
   docker-compose up
   ```
6. Acceder a la aplicación en tu navegador web:
   ```http://localhost:8080
   ```
7. Probar los endpoints utilizando herramientas como Postman o curl.
    -/order
    -/driver
    -/assign
8. Probar el API Rest con los ejemplos del punto "Datos de prueba".
9. Detener el contenedor de Docker:
   ```bash
   docker-compose down
   ```
   
## Datos de prueba
### Crear un pedido (POST /order)
```json
{
    "origin":"Naucalpan",
    "destination":"Ecatepec"
}
```

### Crear un conductor (POST /driver)
```json
{
    "name":"Mario ALbert",
    "licenseNumber":"123456",
    "active":true
}
```
### Asignar un pedido a un conductor (POST /assign)
```json
{
    "orderId":1,
    "driverId":1
}
```
### Actualizar el estado de un pedido (POST /order/)
```json
{
    "id": 1,
    "status":"IN_TRANSIT"
}
```
### Actualizar el estado de un conductor (POST /driver/)
```json
{
    "id": 1,
    "active":false
}
```
### Filtrar ordenes por filtro (GET /order?filter=OPTIONS)
- filter = status(CREATED, IN_TRANSIT, DELIVERED, CANCELLED), origin, destination, date(yyyy-MM-dd)
### Obtener todos los pedidos (GET /order)
### Obtener todos los conductores (GET /driver)
