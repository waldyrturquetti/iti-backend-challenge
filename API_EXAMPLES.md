# MongoDB Document Entity - API Examples

## Quick Start API Examples

Once your application is running, you can interact with the document endpoints using these examples:

### 1. Create a Text Document

```bash
curl -X POST http://localhost:8080/api/documents/text \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Getting Started with Spring Boot",
    "content": "Spring Boot makes it easy to create stand-alone applications...",
    "author": "John Developer",
    "tags": ["spring", "java", "tutorial"],
    "metadata": {
      "category": "programming",
      "readTime": 5
    }
  }'
```

### 2. Create a JSON Document

```bash
curl -X POST http://localhost:8080/api/documents/json \
  -H "Content-Type: application/json" \
  -d '{
    "name": "API Configuration",
    "data": {
      "apiKey": "abc123",
      "endpoints": {
        "production": "https://api.example.com",
        "staging": "https://staging-api.example.com"
      },
      "timeout": 30
    },
    "schema": "api-config-v1"
  }'
```

### 3. Create a File Document

```bash
curl -X POST http://localhost:8080/api/documents/file \
  -H "Content-Type: application/json" \
  -d '{
    "fileName": "product-catalog.xlsx",
    "fileType": "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
    "fileSize": 524288,
    "storageUrl": "s3://my-bucket/files/product-catalog.xlsx",
    "checksum": "d2d2d2d2d2d2d2d2",
    "metadata": {
      "uploadedBy": "admin",
      "department": "sales"
    }
  }'
```

### 4. Create a Generic Document

```bash
curl -X POST http://localhost:8080/api/documents/generic \
  -H "Content-Type: application/json" \
  -d '{
    "attributes": {
      "eventType": "order_placed",
      "orderId": "ORD-12345",
      "customerId": "CUST-678",
      "amount": 129.99,
      "items": 3,
      "status": "confirmed"
    },
    "metadata": {
      "source": "order-service",
      "version": "2.0"
    }
  }'
```

### 5. Get All Documents

```bash
curl http://localhost:8080/api/documents
```

### 6. Get Document by ID

```bash
curl http://localhost:8080/api/documents/{document-id}
```

### 7. Get Documents by Type

Get all TEXT documents:
```bash
curl http://localhost:8080/api/documents/type/TEXT
```

Get all JSON documents:
```bash
curl http://localhost:8080/api/documents/type/JSON
```

Get all FILE documents:
```bash
curl http://localhost:8080/api/documents/type/FILE
```

Get all GENERIC documents:
```bash
curl http://localhost:8080/api/documents/type/GENERIC
```

### 8. Get Documents Created After a Date

```bash
curl "http://localhost:8080/api/documents/created-after?date=2026-01-01T00:00:00"
```

### 9. Delete a Document

```bash
curl -X DELETE http://localhost:8080/api/documents/{document-id}
```

## PowerShell Examples

If you're using PowerShell, use these commands instead:

### Create a Text Document
```powershell
$body = @{
    title = "My Document"
    content = "Document content here"
    author = "Jane Doe"
    tags = @("tag1", "tag2")
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/documents/text" `
    -Method Post `
    -ContentType "application/json" `
    -Body $body
```

### Get All Documents
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/documents" -Method Get
```

### Get Documents by Type
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/documents/type/TEXT" -Method Get
```

## Response Examples

### Text Document Response
```json
{
  "id": "65c8f4d3e5b2a1b3c4d5e6f7",
  "documentType": "TEXT",
  "createdAt": "2026-02-07T14:30:00",
  "updatedAt": "2026-02-07T14:30:00",
  "metadata": {
    "category": "programming",
    "readTime": 5
  },
  "title": "Getting Started with Spring Boot",
  "content": "Spring Boot makes it easy to create stand-alone applications...",
  "author": "John Developer",
  "tags": ["spring", "java", "tutorial"]
}
```

### JSON Document Response
```json
{
  "id": "65c8f4d3e5b2a1b3c4d5e6f8",
  "documentType": "JSON",
  "createdAt": "2026-02-07T14:31:00",
  "updatedAt": "2026-02-07T14:31:00",
  "metadata": null,
  "name": "API Configuration",
  "data": {
    "apiKey": "abc123",
    "endpoints": {
      "production": "https://api.example.com",
      "staging": "https://staging-api.example.com"
    },
    "timeout": 30
  },
  "schema": "api-config-v1"
}
```

### File Document Response
```json
{
  "id": "65c8f4d3e5b2a1b3c4d5e6f9",
  "documentType": "FILE",
  "createdAt": "2026-02-07T14:32:00",
  "updatedAt": "2026-02-07T14:32:00",
  "metadata": {
    "uploadedBy": "admin",
    "department": "sales"
  },
  "fileName": "product-catalog.xlsx",
  "fileType": "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
  "fileSize": 524288,
  "storageUrl": "s3://my-bucket/files/product-catalog.xlsx",
  "checksum": "d2d2d2d2d2d2d2d2"
}
```

### Generic Document Response
```json
{
  "id": "65c8f4d3e5b2a1b3c4d5e6fa",
  "documentType": "GENERIC",
  "createdAt": "2026-02-07T14:33:00",
  "updatedAt": "2026-02-07T14:33:00",
  "metadata": {
    "source": "order-service",
    "version": "2.0"
  },
  "attributes": {
    "eventType": "order_placed",
    "orderId": "ORD-12345",
    "customerId": "CUST-678",
    "amount": 129.99,
    "items": 3,
    "status": "confirmed"
  }
}
```

## Swagger/OpenAPI Documentation

Once the application is running, you can access the interactive API documentation at:

```
http://localhost:8080/swagger-ui.html
```

This will provide a user-friendly interface to test all the endpoints.

## Use Cases

### 1. Content Management System
Use **TextDocument** for articles, blog posts, and pages.

### 2. Configuration Storage
Use **JsonDocument** for application configurations, feature flags, and settings.

### 3. File Management
Use **FileDocument** to track uploaded files and their metadata.

### 4. Event Logging
Use **GenericDocument** for logging events, activities, and audit trails.

### 5. Mixed Content Repository
Store all types together and query by type when needed, or retrieve all documents for a unified view.

