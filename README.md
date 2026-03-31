# Nucleus

A clean, modular Spring Boot example focused on architecture, maintainability, and modern Gradle build design.

## Highlights

- Hexagonal Architecture
- Modular Gradle setup with convention plugins in `build-logic`
- Version Catalog-based dependency management
- Value Object-driven domain model
- Clear separation of concerns
- Spring Web MVC
- In-memory persistence adapter

## Module Structure

```text
app
modules
├── domain
├── application
├── adapter-in-web
└── adapter-out-memory
build-logic
