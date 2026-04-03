# Nucleus

A minimal reference project demonstrating a **modular monolith** using **Spring Boot** and a **hexagonal (ports & adapters) architecture**.

The goal of this project is not to be feature-rich but to serve as a **clean, pragmatic template** for structuring real-world Spring applications.

---

## 🧠 Core Ideas

* **Modular Monolith** instead of microservices
* **Feature-first (package-by-feature)** structure
* **Hexagonal architecture inside each module**
* **Clear separation of responsibilities**
* **Minimal but realistic setup**

---

## 📦 Project Structure

```text
:app
:modules:entry
:modules:test-support
```

### `:app`

* Spring Boot application (composition root)
* Wires everything together
* Contains no business logic

---

### `:modules:entry`

A single **feature module** representing a bounded context.

```text
entry
 ├─ domain
 ├─ application
 └─ adapter
     ├─ in
     │   └─ web
     └─ out
         └─ memory
```

#### `domain`

* Pure business logic
* Entities, value objects
* No Spring, no infrastructure

#### `application`

* Use cases
* Input and output ports (`port.in`, `port.out`)
* Orchestrates domain logic

#### `adapter`

* Infrastructure and delivery mechanisms
* `inbound` → e.g. REST controllers
* `outbound` → e.g. persistence implementations

---

### `:modules:test-support`

Shared **test infrastructure** used across modules.

Contains:

* custom test annotations (e.g. `@WebMvcSliceTest`)
* Spring test bootstrapping
* reusable test configuration

This module intentionally contains **no business logic**.

---

## 🔁 Dependency Direction

Dependencies always point **inwards**:

```text
adapter → application → domain
```

* `domain` has no dependencies
* `application` depends on `domain`
* `adapter` depends on `application`

---

## 🚫 What This Project Avoids

* No global `domain`, `application`, or `adapter` modules
* No `common`/`utils` dumping ground
* No framework code inside the domain
* No premature over-modularization

---

## 🧩 Why This Structure?

This project follows a pragmatic approach:

* **Feature modules first**, not technical layers
* **Simple by default**, scalable when needed
* **Clear boundaries**, but without excessive Gradle complexity

This aligns with modern practices such as:

* modular monoliths
* domain-driven design (DDD)
* Spring Modulith-style architecture

---

## 🧪 Testing

* Each module can define its own tests
* Shared test utilities live in `:modules:test-support`
* Example: `@WebMvcSliceTest` for controller testing

---

## 🚀 When to Evolve Further

This structure can evolve if needed:

* Split modules further (e.g. `entry:domain`, `entry:application`)
* Introduce stricter boundaries (e.g., ArchUnit, Spring Modulith)
* Extract modules into services if necessary

Start simple, evolve when complexity demands it.

---

## 🧡 Summary

* Modular monolith
* Feature-based structure
* Hexagonal architecture per module
* Minimal, pragmatic, production-ready baseline
