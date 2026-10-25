# E-commerce Phase 3

[![CI](https://github.com/thillainirmal-tech/E-commerce-phase3/actions/workflows/ci.yml/badge.svg)](https://github.com/thillainirmal-tech/E-commerce-phase3/actions/workflows/ci.yml)
[![License](https://img.shields.io/github/license/thillainirmal-tech/E-commerce-phase3)](LICENSE)

A Java-based E-commerce application with Redis integration.

## Overview

This project represents the third phase of an E-commerce platform, designed and developed in Java, with Redis used for caching and session management. The repository includes SQL scripts for initial database setup, Maven build configuration, and a standard Java project structure.

## Features

- Modular Java backend for E-commerce operations
- Redis integration for:
  - Caching frequently accessed data
  - Managing user sessions efficiently
- SQL scripts to create and populate core business tables:
  - Users
  - Products
  - Categories
  - Companies
  - Orders and Order Items
- Example product CSV for bulk product import
- Maven wrapper for easy build and dependency management

## Project Structure

```
.
├── .gitattributes
├── .gitignore
├── .mvn/
├── category.sql
├── company.sql
├── customer order.sql
├── mvnw
├── mvnw.cmd
├── order item.sql
├── pom.xml
├── product.csv
├── product.sql
├── src/
├── tmp/
├── user.sql
```

- **src/**: Java source code (main application logic)
- **.mvn/**, **mvnw**, **mvnw.cmd**: Maven wrapper for consistent builds
- **pom.xml**: Maven project configuration and dependencies (including Redis)
- **\*.sql**: SQL files to initialize and populate the database
- **product.csv**: Sample product data in CSV format

## Redis Integration

- **Caching:** Frequently accessed data (such as product information) is cached in Redis to improve performance.
- **Session Management:** User sessions are stored in Redis to allow for scalable, distributed session handling across multiple application instances.

## Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/thillainirmal-tech/E-commerce-phase3.git
   cd E-commerce-phase3
   ```

2. **Set up the database**
   - Run the SQL scripts in your preferred SQL database (e.g., MySQL, PostgreSQL)

3. **Set up Redis**
   - Install and start a local Redis server (default port is 6379)
   - Adjust the Redis configuration in your application properties if needed

4. **Build the project**
   ```bash
   ./mvnw clean install
   ```

5. **Run the application**
   - Run from your IDE or using Maven

## Requirements

- Java 8 or above
- Maven 3.x
- A SQL database (MySQL, PostgreSQL, etc.)
- Redis server

## License

This project is for educational and demonstration purposes. 
---

*For more details, see the source code and SQL schema files in this repository.*
