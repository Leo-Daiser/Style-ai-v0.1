# StyleAI Design System

This design system establishes the visual standards for StyleAI. It uses an editorial, fashion-focused aesthetic that feels premium, structured, and calm rather than playful or tech-larping.

---

## 🎨 Color Palette Specs

We avoid standard tech-blue gradients and neon highlights. The palette draws from organic earth tones, soft linen, and rich clay.

### 🏛️ Dominant Canvas Colors

| Element Theme | Light Slate (Active) | Slate Dark (Simulator Theme) | Semantic Role |
| :--- | :--- | :--- | :--- |
| **App Canvas Background** | `#F3EFE9` | `#262625` | Earth-neutral canvas mimicking premium cardstock |
| **Card Fill Container** | `#FFFFFF` | `#2D2D2C` | High contrast raised components |
| **Brand Accent Clay** | `#8D5B4C` | `#A87464` | Primary brand tone representing warm clay |
| **Brand Accent Olive** | `#3A4D39` | `#546E52` | Secondary brand tone representing wild olive |
| **Supporting Soft Gray** | `#64748B` | `#94A3B8` | Subtitle descriptions & neutral descriptions |

### 🚦 Semantic Match Colors

These states categorize buying and style checks:
* **`GOOD MATCH` (Positive Verdict):** Emerald-tinted background with deep emerald typography. Represents high-versatility alignment.
  * *Hex values:* `#10B981` with light transparencies (e.g., `rgba(16, 185, 129, 0.1)`).
* **`MAYBE` (Cautionary Verdict):** Gold-tinted amber container with charcoal typography. Represents a balanced, contextual buying risk.
  * *Hex values:* `#F59E0B` with light transparencies.
* **`SKIP` (Avoidance Verdict):** Muted rose-rust container with deep brick-red typography. Represents poor capsule integration, seasonal clash, or redundancy.
  * *Hex values:* `#EF4444` with light transparencies.

---

## ✒️ Typography System

StyleAI pairs premium display sans/serif headings or clean grotesque widths with functional technical monospace labels.

```
+-------------------------------------------------------+
|  StyleAI (Heading Displays)                           |
|  "Outfit Details" (Sans-Grotesque)                    |
|  [CATEGORY: OUTERWEAR] (Monospace utility labels)    |
|  Score 86 (Utility Code / Stats)                      |
+-------------------------------------------------------+
```

* **Display (Headers):** Space Grotesk or Outfit (Sans-Serif). Clean, geometric, tracking set slightly snug (`-0.02em`) to convey fashion seriousness.
* **Body Text (Reads):** Inter (Sans-Serif). High legibility at `11sp` to `13sp` sizes with proportional line heights.
* **Utility Code (Data):** JetBrains Mono or Fira Code. Used for stats counters, priority badges, wardrobe scores (e.g., `Score 92`), and technical descriptions (e.g. `[PRO - 3 CREDITS]`).

---

## 🎛️ Component Blueprints

### 1. Elevated Material Cards
* **Border Radii:** Rounded corners set exactly to `16dp` for large cards/overlays and `12dp` for inner item listings.
* **Elevation & Shadows:** Ultra-soft, high-diffusion shadows (`shadow-sm` or `shadow-md` equivalents). Never use dark, stark black outlines or thick boundaries.
* **Internal Margins:** High-density balanced padding (16dp-20dp padding) to ensure focus on content with generous negative space boundaries.

### 2. Actionable Buttons
* **Primary Clay Btn:** Fully rounded or `12dp` corner fills with background `#8D5B4C` and bold text tracking. Elevated hover states with subtle size scales in the browser web simulator.
* **Secondary Utility Btn:** Clean light grey borders (`border-slate-205`) with a transparent or white background. Fully desaturated text to preserve visual hierarchy.

### 3. Integrated Navigation
* **Placement:** Desktop-anchored bottom menu utilizing exactly five key tab segments:
  1. **Home:** Instant command center featuring quick analysis gates.
  2. **Wardrobe:** Visual physical closet overview showing items and custom passport evaluation sheets.
  3. **Decisions:** Chronological history matching log with skip/buy counters.
  4. **Looks:** Seamless visual Reference Board with simulated credit metrics.
  5. **Profile:** Rule engines showcasing seasonal palettes, silhouette measurements, and advanced privacy settings.
