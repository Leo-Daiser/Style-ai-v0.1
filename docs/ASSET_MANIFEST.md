# StyleAI Asset Manifest

Central reference for all visual assets used across the web prototype and Android app.

---

## Wardrobe Assets

| Asset ID | Web Path | Android Drawable | Category | Aspect Ratio |
|---|---|---|---|---|
| cream-structured-blazer | /assets/wardrobe/cream-structured-blazer.webp | R.drawable.cream_structured_blazer | Outerwear | 1:1 |
| white-cotton-shirt | /assets/wardrobe/white-cotton-shirt.webp | R.drawable.white_cotton_shirt | Tops | 1:1 |
| dark-blue-straight-jeans | /assets/wardrobe/dark-blue-straight-jeans.webp | R.drawable.dark_blue_straight_jeans | Bottoms | 1:1 |
| beige-tailored-trousers | /assets/wardrobe/beige-tailored-trousers.webp | R.drawable.beige_tailored_trousers | Bottoms | 1:1 |
| olive-midi-dress | /assets/wardrobe/olive-midi-dress.webp | R.drawable.olive_midi_dress | Dresses | 1:1 |
| oatmeal-merino-knit | /assets/wardrobe/oatmeal-merino-knit.webp | R.drawable.oatmeal_merino_knit | Tops | 1:1 |
| camel-trench-coat | /assets/wardrobe/camel-trench-coat.webp | R.drawable.camel_trench_coat | Outerwear | 1:1 |
| white-leather-sneakers | /assets/wardrobe/white-leather-sneakers.webp | R.drawable.white_leather_sneakers | Shoes | 1:1 |
| black-leather-loafers | /assets/wardrobe/black-leather-loafers.webp | R.drawable.black_leather_loafers | Shoes | 1:1 |
| tan-ankle-boots | /assets/wardrobe/tan-ankle-boots.webp | R.drawable.tan_ankle_boots | Shoes | 1:1 |
| brown-leather-handbag | /assets/wardrobe/brown-leather-handbag.webp | R.drawable.brown_leather_handbag | Accessories | 1:1 |
| brown-leather-belt | /assets/wardrobe/brown-leather-belt.webp | R.drawable.brown_leather_belt | Accessories | 1:1 |

## Outfit Board Assets

| Asset ID | Web Path | Android Drawable | Category | Aspect Ratio |
|---|---|---|---|---|
| soft-office-capsule | /assets/outfits/soft-office-capsule.webp | R.drawable.soft_office_capsule | Office | 4:5 |
| weekend-minimal-casual | /assets/outfits/weekend-minimal-casual.webp | R.drawable.weekend_minimal_casual | Weekend | 4:5 |
| autumn-layered-look | /assets/outfits/autumn-layered-look.webp | R.drawable.autumn_layered_look | City Walk | 4:5 |
| date-night-classic | /assets/outfits/date-night-classic.webp | R.drawable.date_night_classic | Dinner | 4:5 |
| travel-capsule-outfit | /assets/outfits/travel-capsule-outfit.webp | R.drawable.travel_capsule_outfit | Travel | 4:5 |
| smart-casual-everyday | /assets/outfits/smart-casual-everyday.webp | R.drawable.smart_casual_everyday | Everyday | 4:5 |

## Empty State Assets

| Asset ID | Web Path | Android Drawable | Category | Aspect Ratio |
|---|---|---|---|---|
| empty-wardrobe | /assets/empty/empty-wardrobe.webp | R.drawable.empty_wardrobe | Wardrobe empty state | 1:1 |
| empty-looks | /assets/empty/empty-looks.webp | R.drawable.empty_looks | Looks empty state | 1:1 |

## Missing Assets

> [!WARNING]
> The following assets are not yet available and currently use fallback images:
>
> - `creative-workspace-blend.webp` → uses `weekend-minimal-casual.webp` as fallback
> - `gallery-evening-look.webp` → uses `date-night-classic.webp` as fallback
> - `empty-decisions.webp` → uses `empty-looks.webp` as fallback

## Naming Conventions

- **Web**: lowercase kebab-case (e.g., `cream-structured-blazer.webp`)
- **Android**: lowercase snake_case (e.g., `cream_structured_blazer`), stored in `drawable-nodpi`

## Centralized Mapping Files

- **Android**: `android/app/src/main/java/com/example/styleai/data/mock/VisualAssets.kt`
- **Web**: `web-prototype/src/data/assets.ts`
