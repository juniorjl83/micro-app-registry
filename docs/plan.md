---
name: mvp-implementation-plan
description: Detailed MVP implementation plan for Micro‑App Registry (UI + backend)
metadata:
  type: reference
---

## Overview & Scope
Build a **Nuxt 4 + Vue 3 + TS** frontend and a **Kotlin + Spring Boot** backend delivering the MVP features listed in the plan (auth, org, catalog, trial, checkout via Wompi, subscriptions, entitlements, admin dashboard, CI/CD, observability). The backend is a modular monolith for ease of early development.

**Key phases** (person‑days):
- Prep (3), Analysis (5), Core backend (12), Frontend base (10), Checkout (8), Admin (7), Testing (6), Observability (4), CI/CD (5), Docs (4) – **≈ 64 pd**.

## Critical Files (to be created)
- `backend/src/main/kotlin/com/example/identity/AuthController.kt` – registration & login.
- `backend/src/main/kotlin/com/example/billing/PaymentProvider.kt` – payment abstraction.
- `frontend/pages/catalog.vue` – public catalog UI.
- `frontend/components/CheckoutButton.vue` – checkout flow.
- `docs/plan.md` – this file (the plan itself).
- `README.md` – project overview.
- `.github/workflows/ci.yml` – CI pipeline.

## Next Steps
1. Push this scaffold to the repo (already added). 
2. Create the initial folder hierarchy (`backend/`, `frontend/`, `docs/`). 
3. Add a basic `docker-compose.yml` wiring Postgres, Redis, and stub Wompi. 
4. Begin Phase 1 – finalize OpenAPI spec and UI component inventory.

## Acceptance Criteria (MVP)
1. User can self‑register and log in.
2. User can create an organization and invite members.
3. Public catalog is browseable unauthenticated.
4. Trial can be started and temporary entitlement enforced.
5. Checkout via Wompi creates a subscription and entitlement.
6. Admin can CRUD micro‑apps & plans, view audit log.
7. All critical actions are auditable.
8. CI runs lint → unit → integration → Docker build.
9. Logging, metrics, and tracing are enabled.

## Risk Mitigations
- **Payment bugs:** use Wompi sandbox, idempotent webhook handling.
- **Entitlement races:** DB transaction + unique constraint.
- **RBAC complexity:** central `EntitlementService` with exhaustive tests.
- **CI slowdown:** parallel jobs + caching.

---

*End of plan.*
