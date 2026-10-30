# PinSoul Backend API

Spring Boot backend for the PinSoul application.

## Deploying on Railway

### 1. Database Configuration
You can connect the Railway MySQL service in either of two ways:

#### Option A: Using individual Railway MySQL variables (Recommended)
Add these variables to the `PinSoul_backend` service:
```text
MYSQLHOST=${{MySQL.MYSQLHOST}}
MYSQLPORT=${{MySQL.MYSQLPORT}}
MYSQLDATABASE=${{MySQL.MYSQLDATABASE}}
MYSQLUSER=${{MySQL.MYSQLUSER}}
MYSQLPASSWORD=${{MySQL.MYSQLPASSWORD}}
```

#### Option B: Using a direct JDBC URL
```text
DB_URL=jdbc:mysql://${{MySQL.MYSQLHOST}}:${{MySQL.MYSQLPORT}}/${{MySQL.MYSQLDATABASE}}?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
DB_USERNAME=${{MySQL.MYSQLUSER}}
DB_PASSWORD=${{MySQL.MYSQLPASSWORD}}
```
*(Note: If you use Railway's default `MYSQL_URL` which starts with `mysql://`, the application automatically prefixes `jdbc:` to ensure driver compatibility).*

### 2. Application Variables
- `JWT_SECRET`: Any random 256-bit string for signing JWT tokens.
- `PORT`: Automatically provided by Railway (the application binds to `0.0.0.0:${PORT:8080}`).

### 3. Exposing Public URL (Fixing Railway Suggestion)
1. In your Railway dashboard, click on the **PinSoul_backend** service.
2. Navigate to **Settings** > **Networking**.
3. Under **Public Networking**, click **Generate Domain**.
4. Railway will generate a public URL (e.g., `https://pinsoul-backend-production.up.railway.app`).

### 4. Healthcheck & Endpoints
- **Healthcheck Path**: `/health` (returns `{"status":"UP"}`)
- **Welcome / Info Endpoint**: `/` (returns application info and status)
- **Table Creation**: Hibernate DDL logging is set to `DEBUG`, so schema updates (`create table user`, `sign`, `location`) are printed directly in Railway Deploy Logs.

