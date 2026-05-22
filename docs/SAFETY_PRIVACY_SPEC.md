# Security, Safety, and Privacy Specifications

StyleAI is custom-engineered to meet stringent personal security and data minimization specifications. It operates on a **zero-trust, local-first computing model** to shield user images and metadata from structural vulnerabilities.

---

## 🛡️ Guardrails and Architecture

### 1. No INTERNET Permission in MVP
* **Direct Shielding:** The native Android `AndroidManifest.xml` file **MUST NOT** request the `android.permission.INTERNET` permission in the MVP release.
* **Guarantee:** This ensures absolute logical verification that user data, screenshots, facial shapes, and private photographs can never leave the local terminal boundaries.

### 2. No Raw Photo Persistence
* **Rule:** The app must never store raw camera frames or pixel streams in physical SQLite databases or local directories.
* **Implementation:** Photos uploaded for optional Style Profiler checks must be resized and read only with in-memory Bitmap instances or Android Jetpack Graphic utilities. Once the numeric pigment vector or structural aspect-ratio is analyzed, the binary image instance is immediately garbaged and cleared from volatile RAM.
* **Background Check:** `EXIF` data is scrubbed instantly upon selection to wipe GPS coordinates, camera models, and timestamps.

### 3. Optional Profiling Flow
* **Constraint:** Completing a face or body capture must remain strictly optional. 
* **Independence:** The main value proposition—the wardrobe checker (`Should I buy this?`)—is fully active and works deterministically with category, context, and accent sliders even if a user has empty canvas profiles. 
* **No Biometric Pressures:** No prompts or blockades should restrict utility behind a forced photo gate.

---

## 🚫 Safety Guidelines against Abuse

To prevent malicious uploads or improper behaviors, the input validator layers must strictly implement:

### 1. No Free-text Prompts
* **Rule:** Free-text writing boxes and text inputs in prompt fields are strictly prohibited.
* **Relevance:** This eliminates prompt injection attacks, generation of abusive material, bypass attempts of image policies, or standard chatbot-larping.
* **Control:** Users select parameters only from structured dropdown menus and toggles.

### 2. Forbidden Categories
* **Design Blockade:** If the StyleAI reference models are expanded to incorporate local generative assets (e.g., local Stable Diffusion mobile models in the future), the parameter validations must trigger a safe crash or strict block (`SecurityException`) whenever checked categories resemble terms representing:
  * Underwear, lingerie, brassieres
  * Swimwear, bikinis, speedos
  * Transparency filters or nude silhouettes
  * Sexualized stylings or fetishes
  * Minors (under-age styling matches)

---

## 💵 Mock Billing Controls

* **Zero Transaction Policy:** All payment and full-report unlock triggers operate as local-first mock cycles.
* **Balance Reset:** Users can restore credits instantly via the app setting menus at no actual financial cost.
* **No Telemetry Integration:** Google Play billing modules must stay deactivated in the sandbox to verify that transactions are purely offline and simulated.
