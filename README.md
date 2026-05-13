# Sistema de Gestión de Reservas 

## Descripción del Proyecto
Este sistema es una solución basada en una **arquitectura de microservicios** diseñada para gestionar las operaciones críticas de un restaurante. Cada servicio es independiente, posee su propia base de datos y se comunica para ofrecer una experiencia fluida en la gestión de clientes, menús y pedidos.

El proyecto utiliza **Spring Boot 3** para el desarrollo del backend, **MySQL** para la persistencia y **Docker** para garantizar la portabilidad del entorno en cualquier computador.

---

##  Información del Estudiante
* **Nombre:** Benjamín Montoya

---

## Funcionalidades Implementadas

### 1. Microservicio de Clientes (`Port: 8083`)
* **Gestión de Identidad:** Registro y consulta de clientes.
* **Validaciones Robustas:** * Email con formato válido y único en base de datos.
    * Teléfono con formato obligatorio chileno (`+569XXXXXXXX`).
* **Auditoría:** Registro automático de la fecha de creación.

### 2. Microservicio de Menú (`Port: 8085`)
* **Administración de Carta:** Gestión de platos, descripciones y categorías (Entrada, Fondo, Postre, etc.).
* **Precisión Financiera:** Uso de `BigDecimal` para el manejo de precios, evitando errores de redondeo.
* **Disponibilidad:** Control de stock/disponibilidad de productos en tiempo real.

### 3. Microservicio de Pedidos (`Port: 8082`)
* **Gestión de Órdenes:** Vinculación de pedidos con clientes mediante IDs.
* **Historial:** Consulta y registro de transacciones.

---

## Tecnologías Utilizadas
* **Lenguaje:** Java 17 (JDK 17)
* **Framework:** Spring Boot 
* **Base de Datos:** MySQL 
* **Persistencia:** Spring Data JPA / Hibernate
* **Migraciones:** Flyway
* **Contenedores:** Docker & Docker Compose
* **Utilidades:** Lombok & Jakarta Validation

---

## Pasos para la Ejecución

Para ejecutar este proyecto en cualquier PC, siga estas instrucciones:

### Pre-requisitos
* Tener instalado **Docker Desktop**.
* Tener instalado **Maven**.

### Instrucciones de despliegue

1. **Compilar los microservicios:**
   Ejecute el siguiente comando en la raíz de cada microservicio para generar el archivo ejecutable `.jar`:
   ```bash
   mvn clean package -DskipTests
