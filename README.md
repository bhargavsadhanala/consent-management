Project Overview
This project is a Spring Boot application designed to manage user consents with authentication using JWT and rate limiting via IP-based In-memory RateLimiter.

Prerequisites
Java 21
MySQL
Spring Boot 3.4

Features & Technologies Used
Spring Security for JWT Authentication
IP-based In-memory RateLimiter
Redis can be used as a distributed store for Rate Limiter if the system scales.
MySQL for Database Operations
Tables designed with AutoGenerated IDs. Can be upgraded to distributed IDs/UUIDs for distributed scaling.

Database Schema

User Master Table:
Stores user details for registration and authentication.
Table Name: user
Fields: id (Generated ID), user_name, password, gender,status etc.

Purpose Master Table:
(Stores purposes for which consent is required.)
Table Name: purpose
Fields: id (Generated ID), description,status etc

Consent Table:
(Links users with specific purposes for consent management.)
Table Name: consent
Fields:  user_id, purpose_id,status etc

Consent Audit Log Table:
(Logs all modifications (create, update, delete) on the consent table.)

Table Name: consent_audit_log
Fields: id (Generated ID), user_id,purpose_id, modification_date etc

Swagger Documentation
Access the Swagger documentation at:
http://localhost:8080/swagger-ui

Postman Collection
The Postman collection has been updated for Consent Management, covering the following:

Add User/Users
Get User/Users to verify
Authenticate User to get the TOKEN
Use the Token in headers with Authorization key for Purpose and Consent APIs.
