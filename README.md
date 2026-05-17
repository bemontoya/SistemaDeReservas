# Sistema de Gestión de Reservas y Operaciones de Restaurante

## Descripción del Proyecto
Este sistema es una solución robusta basada en una **arquitectura de microservicios distribuida** y diseñada para gestionar las operaciones críticas de un restaurante de alta demanda. Sigue el patrón de diseño **Database-per-Service**, donde cada microservicio es completamente independiente, posee su propia base de datos aislada y se integra en una red virtual privada.

El proyecto utiliza **Spring Boot 3** para el desarrollo del backend, **MySQL 8.0** para la persistencia, **Flyway** para la migración automatizada de esquemas y **Docker / Docker Compose** con optimización de imágenes para garantizar un despliegue ultra ligero y portátil en cualquier computador.

---

## Información del Estudiante
* **Nombre:** Benjamín Montoya
  
---

## Arquitectura y Microservicios Implementados

El sistema está compuesto por **10 contenedores en ejecución** intercomunicados a través de la red virtual `restaurante-network`:

### 1. Base de Datos Centralizada (`restaurante-db` | Port: 3306)
* Motor MySQL 8.0 que aloja de forma aislada e independiente las bases de datos de cada servicio: `cliente_db`, `empleado_db`, `inventario_db`, `menu_db`, `mesa_db`, `notificaciones_db`, `pagos_db`, `pedido_db`, `reportes_db` y `reserva_db`.

### 2. Microservicio de Clientes (`clientes-app` | Port: 8084)
* **Gestión de Identidad:** Registro, actualización y consulta de clientes.
* **Validaciones Robustas:** Email con formato válido y único; Teléfono con formato obligatorio chileno (`+569XXXXXXXX`).
* **Auditoría:** Registro automático de la fecha de creación mediante ganchos del ciclo de vida de JPA.

### 3. Microservicio de Menú (`menu-app` | Port: 8085)
* **Administración de Carta:** Gestión de platos, descripciones y categorías (Entrada, Fondo, Postre, Bebestible).
* **Precisión Financiera:** Uso estricto de `BigDecimal` para el manejo de precios, evitando errores de redondeo nativos de tipos flotantes.
* **Disponibilidad:** Control de stock y disponibilidad de productos en tiempo real.

### 4. Microservicio de Pedidos (`pedido-app` | Port: 8082)
* **Gestión de Órdenes:** Vinculación lógica de consumos y comandas asociados al ID de los clientes y mesas.
* **Historial:** Trazabilidad completa de transacciones y estados del pedido.

### 5. Microservicio de Reserva (`reserva-app` | Port: 8081)
* **Control de Agendamiento:** Gestión de reservas vinculando `clienteId` y `mesaId`.
* **Validaciones temporales:** Uso de `@Future` para asegurar que las fechas de reserva pertenezcan estrictamente al futuro.
* **Estados Automatizados:** Lógica `@PrePersist` para inicializar las reservas en estado `"CONFIRMADA"` por defecto si no se especifica otro.

### 6. Microservicio de Mesas (`mesa-app` | Port: 8083)
* Control de distribución física del restaurante, capacidades de asientos y estados de ocupación.

### 7. Microservicio de Empleados (`empleados-app` | Port: 8086)
* Gestión del personal del restaurante (garzones, cocineros, administradores) y asignación de roles.

### 8. Microservicio de Inventario (`inventario-app` | Port: 8087)
* Control físico de materias primas e insumos de cocina para asegurar la cadena de suministro interna.

### 9. Microservicio de Notificaciones (`notificacion-app` | Port: 8088)
* Servicio encargado del flujo de alertas y confirmaciones hacia clientes y personal.

### 10. Microservicio de Pagos (`pagos-app` | Port: 8089)
* Procesamiento del cierre de cuentas, boletas y pasarelas de pago.

### 11. Microservicio de Reportes (`reportes-app` | Port: 8090)
* Generación de informes analíticos de rendimiento con persistencia de texto enriquecido o JSON estructurado en columnas de tipo `TEXT`.

---

## Tecnologías Utilizadas
* **Lenguaje:** Java 17
* **Framework:** Spring Boot 3.x
* **Base de Datos:** MySQL 8.0
* **Persistencia:** Spring Data JPA / Hibernate
* **Evolución de Base de Datos:** Flyway (Migraciones SQL versionadas)
* **Contenedores:** Docker & Docker Compose
* **Optimización de Entorno:** Eclipse Temurin JRE Alpine (Imágenes ultra ligeras de ~114MB de contenido base)
* **Utilidades:** Lombok & Jakarta Validation

---

## Pasos para la Ejecución en cualquier Computador

Gracias a la dockerización avanzada y la automatización de Flyway, el sistema completo se compila e inicializa de manera autónoma sin necesidad de configurar bases de datos manuales.

### Pre-requisitos
1. Tener instalado **Docker Desktop** (y asegurarse de que esté corriendo).
2. Tener instalado **Maven** (o utilizar el envoltorio nativo `mvnw` incluido en la raíz).

### Instrucciones de Despliegue (Flujo Unificado)

Abra una terminal (se recomienda PowerShell en Windows o Bash en Linux/macOS) en la **raíz principal del proyecto** y ejecute la siguiente secuencia de comandos:

```powershell
# 1. Limpiar construcciones anteriores y empaquetar todos los microservicios distribuidos en archivos .jar limpios
.\mvnw clean package -DskipTests

# 2. Construir las imágenes optimizadas de Docker y levantar los 9 contenedores en segundo plano
docker compose up --build -d
   mvn clean package -DskipTests

