# Smart Traffic Management System

## 🚗 Project Overview

The **Smart Traffic Management System** is a comprehensive, full-stack web application designed to efficiently monitor, manage, and optimize traffic flow across multiple junctions. Built with Spring Boot on the backend and modern frontend technologies, this system provides real-time traffic monitoring, signal management, and alert notifications.

## ✨ Features

### Core Features
- **Vehicle Tracking**: Monitor active vehicles in real-time with location and speed data
- **Traffic Signal Management**: Control traffic signals (RED, YELLOW, GREEN) across junctions
- **Junction Monitoring**: Track traffic density and congestion levels at each junction
- **Alert System**: Create and manage traffic alerts (accidents, congestion, signal failures, etc.)
- **Dashboard**: Interactive dashboard with real-time statistics
- **Responsive Design**: Works seamlessly on desktop and mobile devices

### Advanced Features
- RESTful API endpoints for all operations
- Database persistence with MySQL
- Comprehensive exception handling
- Secure authentication ready (extensible)
- Pagination and filtering capabilities
- Real-time data updates

## 🛠 Technologies Used

### Backend
- **Framework**: Spring Boot 3.1.5
- **Language**: Java 17
- **Database**: MySQL 8.0
- **ORM**: Hibernate / Spring Data JPA
- **Build Tool**: Maven
- **Logging**: SLF4J with Logback
- **Additional Libraries**: Lombok, Jackson

### Frontend
- **HTML5**: Semantic markup
- **CSS3**: Modern styling with gradients and flexbox
- **JavaScript (ES6+)**: Async/await, Fetch API
- **Responsive Design**: Mobile-first approach

### DevOps
- **Containerization**: Docker & Docker Compose
- **Port**: 8080 (Backend), 3306 (MySQL)

## 📋 Project Structure

```
Smart-Traffic-Management-Systems/
├── src/
│   ├── main/
│   │   ├── java/com/traffic/
│   │   │   ├── SmartTrafficManagementApplication.java
│   │   │   ├── controller/
│   │   │   │   ├── VehicleController.java
│   │   │   │   ├── TrafficSignalController.java
│   │   │   │   ├── TrafficJunctionController.java
│   │   │   │   └── TrafficAlertController.java
│   │   │   ├── service/
│   │   │   │   ├── VehicleService.java
│   │   │   │   ├── TrafficSignalService.java
│   │   │   │   ├── TrafficJunctionService.java
│   │   │   │   └── TrafficAlertService.java
│   │   │   ├── repository/
│   │   │   │   ├── VehicleRepository.java
│   │   │   │   ├── TrafficSignalRepository.java
│   │   │   │   ├── TrafficJunctionRepository.java
│   │   │   │   └── TrafficAlertRepository.java
│   │   │   ├── entity/
│   │   │   │   ├── Vehicle.java
│   │   │   │   ├── TrafficSignal.java
│   │   │   │   ├── TrafficJunction.java
│   │   │   │   └── TrafficAlert.java
│   │   │   └── exception/
│   │   │       └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   │           ├── index.html
│   │           ├── css/style.css
│   │           └── js/app.js
│   └── test/
├── pom.xml
├── Dockerfile
├── docker-compose.yml
└── README.md
```

## 🚀 Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.8+
- MySQL 8.0+
- Git
- Docker (optional)

### Step 1: Clone the Repository
```bash
git clone https://github.com/Rajammal2811/Smart-Traffic-Management-Systems.git
cd Smart-Traffic-Management-Systems
```

### Step 2: Create Database
```sql
CREATE DATABASE smart_traffic;
```

### Step 3: Configure Database
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smart_traffic
spring.datasource.username=root
spring.datasource.password=your_password
```

### Step 4: Build the Project
```bash
mvn clean install
```

### Step 5: Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Alternative: Using Docker
```bash
docker-compose up -d
```

## 🔌 API Documentation

### Vehicle Endpoints
```
GET    /api/vehicles              - Get all vehicles
GET    /api/vehicles/{id}         - Get vehicle by ID
GET    /api/vehicles/active/all   - Get active vehicles
GET    /api/vehicles/count/active - Get active vehicle count
POST   /api/vehicles              - Create new vehicle
PUT    /api/vehicles/{id}         - Update vehicle
PUT    /api/vehicles/{id}/exit    - Mark vehicle as exited
DELETE /api/vehicles/{id}         - Delete vehicle
```

### Traffic Signal Endpoints
```
GET    /api/signals       - Get all signals
GET    /api/signals/{id}  - Get signal by ID
POST   /api/signals       - Create new signal
PUT    /api/signals/{id}  - Update signal
DELETE /api/signals/{id}  - Delete signal
```

### Traffic Junction Endpoints
```
GET    /api/junctions           - Get all junctions
GET    /api/junctions/{id}      - Get junction by ID
GET    /api/junctions/congested/list - Get congested junctions
POST   /api/junctions           - Create new junction
PUT    /api/junctions/{id}      - Update junction
DELETE /api/junctions/{id}      - Delete junction
```

### Traffic Alert Endpoints
```
GET    /api/alerts            - Get all alerts
GET    /api/alerts/{id}       - Get alert by ID
GET    /api/alerts/active/list - Get active alerts
POST   /api/alerts            - Create new alert
PUT    /api/alerts/{id}       - Update alert
DELETE /api/alerts/{id}       - Delete alert
```

## 📊 Database Schema

### vehicles Table
```sql
CREATE TABLE vehicles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    vehicle_number VARCHAR(20) UNIQUE NOT NULL,
    vehicle_type VARCHAR(50),
    owner_name VARCHAR(100),
    entry_time DATETIME,
    exit_time DATETIME,
    current_location VARCHAR(150),
    speed DOUBLE DEFAULT 0.0,
    latitude DOUBLE DEFAULT 0.0,
    longitude DOUBLE DEFAULT 0.0,
    created_at DATETIME,
    updated_at DATETIME
);
```

### traffic_signals Table
```sql
CREATE TABLE traffic_signals (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    signal_name VARCHAR(50) UNIQUE,
    location VARCHAR(150),
    status VARCHAR(20),
    timer INT DEFAULT 30,
    latitude DOUBLE DEFAULT 0.0,
    longitude DOUBLE DEFAULT 0.0,
    green_duration INT DEFAULT 30,
    yellow_duration INT DEFAULT 5,
    red_duration INT DEFAULT 30,
    created_at DATETIME,
    updated_at DATETIME
);
```

### traffic_junctions Table
```sql
CREATE TABLE traffic_junctions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    junction_name VARCHAR(100) UNIQUE,
    location VARCHAR(150),
    number_of_signals INT,
    traffic_density INT,
    latitude DOUBLE DEFAULT 0.0,
    longitude DOUBLE DEFAULT 0.0,
    status VARCHAR(20),
    vehicle_count INT DEFAULT 0,
    capacity INT DEFAULT 100,
    created_at DATETIME,
    updated_at DATETIME
);
```

### traffic_alerts Table
```sql
CREATE TABLE traffic_alerts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    alert_type VARCHAR(50),
    message VARCHAR(255),
    location VARCHAR(150),
    created_time DATETIME,
    severity VARCHAR(20),
    latitude DOUBLE DEFAULT 0.0,
    longitude DOUBLE DEFAULT 0.0,
    status VARCHAR(20),
    created_at DATETIME,
    updated_at DATETIME
);
```

## 📱 Frontend Features

### Dashboard
- Real-time statistics cards showing:
  - Active vehicles count
  - Total traffic signals
  - Total junctions
  - Active alerts
- Latest alerts display

### Vehicle Management
- View all vehicles with details
- Add new vehicles
- Update vehicle information
- Mark vehicles as exited
- Delete vehicle records

### Signal Management
- Monitor all traffic signals
- Create new signals
- Update signal status and timing
- Delete signals

### Junction Management
- View junction details and status
- Create new junctions
- Monitor traffic density
- Update junction information
- Identify congested junctions

### Alert Management
- Create traffic alerts
- View active alerts
- Update alert status
- Delete alerts
- Filter by severity and type

## 🔒 Security Features

- Input validation on all endpoints
- Exception handling with meaningful error messages
- CORS enabled for frontend integration
- SQL injection prevention through JPA
- Proper HTTP status codes

## 📈 Performance Considerations

- Connection pooling with HikariCP
- Indexed database queries
- Efficient pagination
- Lazy loading for relationships
- Caching ready (extensible)

## 🧪 Testing

Test the API using:
- Postman
- Curl
- Frontend UI
- Browser DevTools

### Example CURL Request
```bash
curl -X GET http://localhost:8080/api/vehicles
```

## 🐛 Troubleshooting

### Port Already in Use
```bash
# Kill process on port 8080
lsof -ti:8080 | xargs kill -9
```

### Database Connection Failed
- Ensure MySQL is running
- Verify credentials in application.properties
- Check if database 'smart_traffic' exists

### CORS Errors
- Ensure frontend URL is in CORS allowed origins
- Check browser console for specific error messages

## 📝 Logging

Logs are configured in `application.properties`:
```properties
logging.level.com.traffic=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %logger{36} - %msg%n
```

## 🎯 Future Enhancements

- [ ] User authentication and authorization
- [ ] Real-time WebSocket updates
- [ ] Google Maps integration
- [ ] Advanced analytics and reporting
- [ ] Mobile application
- [ ] AI-based traffic prediction
- [ ] Vehicle count detection using computer vision
- [ ] Number plate recognition
- [ ] Emergency vehicle priority system

## 📄 License

This project is open source and available under the MIT License.

## 👥 Contributors

- **Rajammal S** - B.Tech CSBS, VSB Engineering College

## 📞 Support

For issues and questions:
- Open an issue on GitHub
- Check existing documentation
- Review API documentation

## 🙏 Acknowledgments

- Spring Boot Team for the excellent framework
- MySQL community for reliable database
- All contributors and users

---

**Happy Traffic Management! 🚦**
