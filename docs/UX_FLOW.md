# StyleAI UX Flow Specification

This blueprint details the user interaction journey, the screen states, and the navigation graph of the StyleAI mobile application. It serves as the official screen-by-screen navigation resource for native Kotlin implementation in Jetpack Compose.

---

## 🗺️ Navigation Graph & Information Architecture

```
Splash Screen (3 seconds)
     │
     ▼
Onboarding Carousels (Optional / Skippable)
     │
     ▼
Privacy & Legal Consent Gate (Strict Check)
     │
     ▼
Main Tab Controller (5 Core Navigation Tabs)
     ├──► 1. Home (Command Center: Instant "Should I buy this?" CTAs & Style Profile Status)
     │
     ├──► 2. Wardrobe (Physical Closet Mirror)
     │         └──► Item Passport (Detailed evaluation cards with wardrobe role tags, worn score, and wear log)
     │
     ├──► 3. Decisions (Smart Impulsive-Buying Inhibitor & Shopping Report Archives)
     │
     ├──► 4. Looks (Aesthetic Reference Engine: Saved combinations & credit-based reference generations)
     │
     └──► 5. Profile (Style Rule Engine: Personal seasonal color palettes, silhouette instructions, and GDPR privacy cache controls)
```

---

## 🎬 Core Screen Details

### 1. Splash Screen
* **Purpose:** App branding, initialization, and trust building.
* **Duration:** 3000ms timer with visual fade-in.
* **Visuals:** Minimalist, editorial brand logo with translated subtitles ("Your Private AI Style Assistant" / "Ваш приватный AI-стилист").

### 2. Onboarding Screens (3-stage Carousel)
* **Slide 1: Style Discovery** — Explains automated local palette analysis without biometrics.
* **Slide 2: Wardrobe Improvement** — Focuses on filling active closet gaps and identifying low-utilization clothes.
* **Slide 3: Privacy Framework** — Reassures users of strict local-first, in-memory data minimizations.
* **Navigation:** Clear "Skip" option in the top right to land directly on the Privacy Consent Gate.

### 3. Privacy & Legal Consent Gate
* **Requirements:** Strict three-checkbox gate. The "Confirm and Continue" CTA remains disabled until all elements are active:
  1. *Permission:* Confirmation that uploaded images are owned by the subject.
  2. *Disclaimer:* Agreement that recommendations are style-based, not therapeutic, medical, or body-morphing advice.
  3. *In-Memory Policy:* Agreement that raw files are immediately processed, metadata-scrubbed, and discarded.
* **Compliance Banner:** Prominently highlights GDPR-aligned raw image stripping and EXIF scrubbing.

### 4. Home Screen (Post-Consent Landing Screen)
* **Post-Consent Behavior:** Users **MUST** land directly on the Home Screen main hub, not on photo uploads, allowing immediate access to shopping tools without forcing face/body capture.
* **Top Header:** Clean brand header displaying active language settings and a simulated credit meter (Default: 3 credits).
* **Main CTA Element:** "Should I buy this?" (dominating screen weight) triggers the direct purchase checker flow.
* **Secondary CTA Element:** "What should I wear?" triggers Looks tab navigation.
* **Accessory Element:** "Style Profile Activation Indicator" - explicitly states whether style rules are active.

### 5. "Should I Buy This?" (Shopping Check Flow)
* **Step A: Clothing Category** — Explicit buttons selecting Top, Bottom, Dress, Outerwear, Shoes, Bag, or Accessory.
* **Step B: Context Type** — Single-choice selecting Basic wardrobe, Seasonal Trend, Rare occasion, Similar to an existing item, or Low-versatility.
* **Step C: Color Accent** — Choice selectors for Neutral, Warm, Cool, Soft, or Contrast tones.
* **Step D: Loading Animation (1200ms)** - Simulates safety compliance, EXIF scrubbing, and color palette alignment checks.
* **Deterministic Results:** Instantly transitions to a detailed report card with three customized outcomes (Good Match, Maybe, or Skip), a compatibility color indicator, and specific architectural justifications. Includes direct options to "Save to local history" or "Check another garment".

### 6. Wardrobe Screen (Physical closet mirror with "Item Passports")
* **Close Integration:** Displays a clean grid of items with category tags (Outerwear, Tops, Bottoms, Dresses, Shoes, Bags, Accessories), versatility scores, colors, and specific warnings for elements flagged with low utilization ("Unused").
* **Filter Bar:** Smooth inline pill filters including a dedicated "Rarely Worn" filter (to clean up low-utilization clothing) and a "Wishlist" filter (to keep track of planned wardrobe gaps / shopping guides).
* **Modal Add Item:** Beautiful, clean form allowing users to append custom clothes with custom name, category, color tone, color direction, and wishlist flag safely into local cache.

### 7. Item Detail (Passport Screen)
* **Close-up Evaluation:** Transitions cleanly from the Wardrobe grid to reveal the item's Passport:
  - **Style Evaluation Matrix:** Aesthetic compatibility with other clothes, and personalized Wardrobe Role (Base Core, Accent Detail, Occasion, Unused, or Wishlist Gap).
  - **Outfit Formulas:** Automated, simple structural style combinations.
  - **Decision Status:** Recommended lifecycle action (Keep active, Replace over time, Sell/Archive, or Wishlist Similar).
  - **Wear Log Metrics:** Keeps track of worn counts and last worn dates without storing raw photos.
  - **Garment Discard Action:** Localized "Delete Item" option to safely discard items from physical cache.

### 8. Decisions Screen (Smart buying inhibitor)
* **impulse Purchase Tracker:** Displays a statistics overview counter showing how many bad clothes the user successfully skipped versus saved, celebrating money and environmental savings.
* **History List:** Compact card items representing current shopping logs, verdicts, dates, and full rationale justifications.
* **Saved Archive Lists:** Direct links to generated style reports and saved outfit collections.

### 9. Looks Screen (Aesthetic Reference Engine)
* **Pre-composed Reference Outfits:** Beautiful cards presenting balanced garments, occasions, style directives, seasons, and formality metrics.
* **Reference Visual Render Action:** Simulates generating visual layout outlines using credit spending counters (max 3 daily credits), prompting a premium Subscription Paywall once reached.
* **Mandatory Reference Warning:** Prominently details that representations are symbolic style coordinates, not virtual face/body try-on simulations.

### 10. Profile Screen (Style Rule System & Controls)
* **Rule Engine:** Displays the primary personal style configurations if unlocked (via reports compilation):
  - **Recommended Season Palette:** Includes recommended coloring spectrums and colors to avoid or handle carefully.
  - **Silhouette & Cut Rules:** Compact, bulleted rules guiding shoulder shapes and lengths.
  - **Smart Shopping Rules:** Wardrobe-specific guidelines detailing how to select high-versatility items and fill visual gaps.
* **Unified System Controls:** Integrated with strict privacy switches, interface language select bars (EN/RU), and full database clean up options (wipe saved fits, reset app onboarding, or full cache wipe).

---

## 🚨 Error & Handling States

| State | Context | Fallback UI |
| :--- | :--- | :--- |
| **Blurry Capture** | Low contrast or lighting in camera frame | Soft warning toast banner requesting user to stand closer to natural lighting vectors. |
| **Invalid Image** | Visual scanning detects multiples or non-human silhouettes | High-contrast security alert blocking completion: *"Safety filter trigger: Blocked"*. |
| **No Store Notice** | Temporary memory limit / session timeout | Alert assuring users that transient files were discarded safely. |
