# Document Entity - MongoDB Polymorphic Collection

## Overview

This project includes a flexible MongoDB entity structure that can handle different types of documents in a single collection. The implementation uses Kotlin sealed classes to provide type safety while allowing polymorphism.

## Architecture

### Base Document Class

The `Document` sealed class serves as the base for all document types. It includes common fields:
- `id`: MongoDB document ID
- `documentType`: Discriminator field to identify the document type
- `createdAt`: Timestamp of document creation
- `updatedAt`: Timestamp of last update
- `metadata`: Optional map for additional metadata

### Document Types

The implementation includes four predefined document types:

#### 1. TextDocument
For storing text-based content such as articles, notes, or blog posts.

**Fields:**
- `title`: Document title
- `content`: Text content
- `author`: Optional author name
- `tags`: List of tags for categorization

**Example:**
```kotlin
val textDoc = TextDocument(
    title = "My Article",
    content = "This is the content of my article...",
    author = "John Doe",
    tags = listOf("technology", "kotlin")
)
```

#### 2. JsonDocument
For storing structured JSON data.

**Fields:**
- `name`: Document name
- `data`: Map containing the JSON data
- `schema`: Optional schema identifier

**Example:**
```kotlin
val jsonDoc = JsonDocument(
    name = "User Profile",
    data = mapOf(
        "userId" to "12345",
        "email" to "user@example.com",
        "preferences" to mapOf("theme" to "dark")
    ),
    schema = "user-profile-v1"
)
```

#### 3. FileDocument
For storing file metadata and references.

**Fields:**
- `fileName`: Name of the file
- `fileType`: MIME type or file extension
- `fileSize`: Size in bytes
- `storageUrl`: URL or path to the stored file
- `checksum`: Optional file checksum for integrity verification

**Example:**
```kotlin
val fileDoc = FileDocument(
    fileName = "report.pdf",
    fileType = "application/pdf",
    fileSize = 1024000,
    storageUrl = "s3://bucket/reports/report.pdf",
    checksum = "abc123def456"
)
```

#### 4. GenericDocument
For storing any arbitrary data when the structure doesn't fit other types.

**Fields:**
- `attributes`: Map containing any key-value pairs

**Example:**
```kotlin
val genericDoc = GenericDocument(
    attributes = mapOf(
        "type" to "custom",
        "value" to 42,
        "nested" to mapOf("key" to "value")
    )
)
```

## Usage

### Using the Service

The `DocumentService` provides methods to interact with documents:

```kotlin
@RestController
@RequestMapping("/api/documents")
class DocumentController(private val documentService: DocumentService) {
    
    @PostMapping("/text")
    fun createTextDocument(@RequestBody request: TextDocumentRequest): TextDocument {
        val doc = TextDocument(
            title = request.title,
            content = request.content,
            author = request.author,
            tags = request.tags
        )
        return documentService.saveDocument(doc) as TextDocument
    }
    
    @GetMapping
    fun getAllDocuments(): List<Document> {
        return documentService.findAllDocuments()
    }
    
    @GetMapping("/{id}")
    fun getDocumentById(@PathVariable id: String): Document? {
        return documentService.findDocumentById(id)
    }
    
    @GetMapping("/type/{type}")
    fun getDocumentsByType(@PathVariable type: String): List<Document> {
        return documentService.findDocumentsByType(type)
    }
}
```

### Direct Repository Usage

You can also use the repository directly:

```kotlin
@Autowired
private lateinit var documentRepository: DocumentRepository

// Save a document
val document = TextDocument(title = "Test", content = "Content")
documentRepository.save(document)

// Find by type
val textDocuments = documentRepository.findByDocumentType("TEXT")

// Find all
val allDocuments = documentRepository.findAll()
```

## Extending with New Document Types

To add a new document type:

1. Create a new data class extending `Document`:

```kotlin
@TypeAlias("email_document")
@MongoDocument(collection = "documents")
data class EmailDocument(
    @field:Id
    override val id: String? = null,
    override val documentType: String = "EMAIL",
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime = LocalDateTime.now(),
    override val metadata: Map<String, Any>? = null,
    val subject: String,
    val from: String,
    val to: List<String>,
    val body: String
) : Document(id, documentType, createdAt, updatedAt, metadata)
```

2. Use it like any other document type:

```kotlin
val email = EmailDocument(
    subject = "Hello",
    from = "sender@example.com",
    to = listOf("recipient@example.com"),
    body = "Email content"
)
documentService.saveDocument(email)
```

## Benefits

1. **Type Safety**: Sealed classes provide compile-time type safety
2. **Single Collection**: All documents stored in one MongoDB collection
3. **Flexible Querying**: Can query by document type or treat all as documents
4. **Easy Extension**: Simple to add new document types
5. **Metadata Support**: Common metadata field for additional properties
6. **Timestamps**: Automatic tracking of creation and update times

## MongoDB Collection Structure

All documents are stored in the `documents` collection with the `documentType` field acting as a discriminator:

```json
{
  "_id": "507f1f77bcf86cd799439011",
  "documentType": "TEXT",
  "createdAt": "2026-02-07T10:30:00",
  "updatedAt": "2026-02-07T10:30:00",
  "title": "My Article",
  "content": "Article content...",
  "author": "John Doe",
  "tags": ["kotlin", "mongodb"],
  "metadata": null,
  "_class": "com.iti.backend_challenge.entities.TextDocument"
}
```

