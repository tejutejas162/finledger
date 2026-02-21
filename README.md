💳 FinLedger – Transaction-Safe Banking Backend
📌 Overview

FinLedger is a Spring Boot-based backend system that simulates a digital banking core platform. It supports secure fund transfers using a double-entry ledger model, ACID-compliant transactions, idempotent APIs, and Kafka-based event publishing.

This project demonstrates backend engineering concepts required in FinTech and BFSI product companies.

🚀 Key Features

✅ Account creation & retrieval

✅ Double-entry ledger system

✅ ACID-compliant fund transfers

✅ Pessimistic locking for concurrency safety

✅ Idempotent transfer API using Idempotency-Key

✅ Kafka-based transaction event publishing

✅ Swagger API documentation

✅ Dockerized PostgreSQL & Kafka setup

✅ Clean layered architecture

🏗 Architecture
Client (Swagger / Postman)
        ↓
Controller Layer
        ↓
Service Layer (@Transactional)
        ↓
Repository Layer (JPA)
        ↓
PostgreSQL Database
        ↓
Kafka Event Publishing
        ↓
Consumer (Audit / Fraud Simulation)
🧠 Core Engineering Concepts Implemented
1️⃣ Double-Entry Ledger Model

Every transfer generates two ledger entries:

Debit from sender

Credit to receiver

Ensures:

Total Debit = Total Credit

This mimics real banking accounting systems.

2️⃣ ACID-Compliant Transfers

The transfer flow runs inside a single @Transactional boundary:

Account locking

Balance update

Ledger entry creation

Idempotency storage

Event publishing

If any step fails → entire transaction rolls back.

3️⃣ Concurrency Safety

Uses pessimistic locking:

@Lock(LockModeType.PESSIMISTIC_WRITE)

Prevents:

Double spending

Race conditions

Dirty writes

4️⃣ Idempotent API Handling

Transfer API requires:

Idempotency-Key: <unique-value>

If the same key is reused:

Transfer is rejected

Duplicate financial operation prevented

This mirrors real payment gateways.

5️⃣ Event-Driven Architecture (Kafka)

After successful transfer:

Transaction event published to Kafka topic

Consumer simulates audit/fraud detection

Decouples transaction processing from downstream systems.

📂 Project Structure
finledger/
 ├── controller/
 │     ├── AccountController
 │     ├── TransactionController
 │
 ├── service/
 │     ├── AccountService
 │     ├── TransactionService
 │     ├── KafkaProducerService
 │
 ├── repository/
 │     ├── AccountRepository
 │     ├── LedgerEntryRepository
 │     ├── IdempotencyRepository
 │
 ├── model/
 │     ├── Account
 │     ├── LedgerEntry
 │     ├── IdempotencyKey
 │
 ├── config/
 │     ├── KafkaConfig
 │
 ├── dto/
 │     ├── AccountRequest
 │     ├── TransferRequest


🛠 Tech Stack

Java 21

Spring Boot

Spring Data JPA

PostgreSQL

Apache Kafka

Swagger (SpringDoc OpenAPI)

Docker

Maven

🗄 Database Schema
Accounts
Column	Type
id	UUID
customer_name	String
balance	BigDecimal
status	String
version	Long
Ledger Entries
Column	Type
id	UUID
transaction_id	UUID
account_id	UUID
debit	BigDecimal
credit	BigDecimal
Idempotency Keys
Column	Type
id	UUID
idempotency_key	String
created_at	Timestamp
▶️ How to Run
1️⃣ Start Infrastructure
docker-compose up -d

This starts:

PostgreSQL

Kafka

Zookeeper

2️⃣ Build Application
mvn clean install
3️⃣ Run Application
java -jar target/finledger.jar
📘 Swagger UI

Access:

http://localhost:8080/swagger-ui/index.html
🧪 Sample API Usage
Create Account

POST /api/accounts

{
  "customerName": "Tejas",
  "initialBalance": 10000
}
Transfer Money

POST /api/transactions/transfer

Header:

Idempotency-Key: transfer-001

Body:

{
  "fromAccountId": "UUID1",
  "toAccountId": "UUID2",
  "amount": 2000
}
🔍 Transaction Flow

Validate idempotency key

Lock sender and receiver rows

Check sufficient balance

Update balances

Insert double-entry ledger records

Publish Kafka event

Commit transaction

🧩 Future Enhancements

Retry mechanism with Dead Letter Queue

Fraud detection microservice

Reconciliation batch job

Distributed tracing

Rate limiting

Circuit breaker integration
