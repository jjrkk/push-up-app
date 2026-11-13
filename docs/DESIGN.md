# DESIGN — Pushups (working title)

Version 1.0 · 7 Nov 2026 · Direction: **Light, airy glass** (provisional — confirmed after the A/B/C study in Phase 2)

Beauty is the product's competitive advantage. This doc sets the direction firmly enough that the
build doesn't drift to a generic look, and leaves room for JK to tune it by eye.

## 1. North star

> **Light you can feel filling up.**
> A calm, luminous space. One huge number. Progress shown as light gathering, not bars filling.
> Glass for the things you touch; crisp type for the things you read.

Keywords: **air · light · glass · breath · precision · calm.**

### What we borrow, and from whom

| Reference | Take | Leave |
| --- | --- | --- |
| Apple Liquid Glass (iOS 26) | Translucent, refractive material for floating controls; specular edges; content-first layering | Glass on everything; low-contrast text on busy backgrounds |
| Gemini | Soft, luminous gradients; light as a signal of activity | Rainbow sparkle overload |
| OpenAI apps | Restraint; typographic confidence; neutral palette; generous space | Sterility — we need warmth and delight |
| Peloton | Ceremony for achievement; big live metrics | Dark aggression, loud color |
| Nike Run Club | Huge numerals as heroes; earned celebration moments | Heavy photography |
| StrongLifts 5x5 | One primary action per screen; big targets; zero thinking mid-workout | Dated stock components |
| HabitKit | One strong visual idea (the tile grid) carried everywhere, including widgets | — |

### What we never do

Stock Material look (default purple, FAB, stock top app bar, heavy ripples) · red/green status colors ·
badges and counters with red dots · confetti · emoji · stock fitness photography · sweaty "beast mode"
energy · more than one accent gradient on a screen · glass stacked more than two layers deep ·
text placed directly on the moving background without a legibility layer.

## 2. Layers (how a screen is built)

```
4  Moments      transient light bloom, toasts, celebration (rare)
3  Glass        floating controls: tab bar, quick-add dock, sheets, chips
2  Content      hero numeral, labels, charts — crisp, opaque type, never blurred
1  Aurora       slow-moving luminous gradient field that reacts to progress
0  Canvas       pearl base color
```

**Glass only means something if there's light behind it.** That's why layer 1 exists: the aurora gives
the glass something to refract. Over flat white, glass looks like a grey card — avoid that.

## 3. Color

Light theme ships first. All values are starting points to tune by eye in Phase 2; keep them as tokens.

| Token | Value (start) | Use |
| --- | --- | --- |
| `canvas` | `#F6F5F2` (warm pearl) | App background |
| `ink.primary` | `#0E0E12` at 92% | Hero numeral, titles |
| `ink.secondary` | `#0E0E12` at 60% | Labels, secondary text |
| `ink.tertiary` | `#0E0E12` at 38% | Hints, axis labels (check contrast) |
| `glass.tint` | `#FFFFFF` at 45–60% | Glass fill over blur |
| `glass.stroke` | `#FFFFFF` at 70% → 15% (top→bottom gradient), 1 dp | Glass edge highlight |
| `glass.shadow` | `#1A1A2E` at 6%, blur 24 dp, y 8 dp | Lift |
| `aurora.*` | 3 soft color fields from the accent palette, 25–45% saturation | Background light |
| `progress.gradient` | Accent palette, 3 stops | Progress, the one accent |

**One accent gradient per app, chosen in Phase 2.** Candidates to test:

| Palette | Stops | Feel |
| --- | --- | --- |
| **Dawn** | `#FF9A76` peach → `#FF6FA5` rose → `#A78BFA` lilac | Warm, optimistic, morning |
| **Glacier** | `#7DD3FC` ice → `#5EEAD4` aqua → `#A7F3D0` mint | Cool, fresh, clinical-calm |
| **Ion** | `#4F7CFF` cobalt → `#8B5CF6` violet → `#EC4899` magenta | Energetic, Gemini-adjacent |

Semantics: there is no "success green" or "error red". Completion = the accent gradient in full bloom.
Behind pace = neutral ink, calm copy. Destructive actions (delete) use ink + confirmation, not red.

Dark theme (later): canvas `#08080B`, glass tint white at 8–12%, aurora at lower brightness, same accent.

## 4. Typography

**Primary family: Google Sans Flex** (open source, SIL OFL, variable; released Nov 2025). Bundle it.
Fallback candidates to compare in Phase 2: **Geist**, **Inter**. One family for everything.

| Style | Size / line height | Weight | Notes |
| --- | --- | --- | --- |
| `display.hero` | 132 sp / 1.0 | 300–400 (tune) | Today's count. Tabular figures, tracking −3% |
| `display.large` | 64 / 1.05 | 400 | Month total, count mode |
| `headline` | 28 / 1.15 | 500 | Screen titles (rarely needed) |
| `title` | 20 / 1.25 | 500 | Section and card titles |
| `body` | 16 / 1.45 | 400 | Body text |
| `label` | 14 / 1.3 | 500 | Chips, buttons, tab labels |
| `caption` | 12 / 1.35 | 400 | Metadata, axis labels; tracking +1% |

- **Numerals are the stars.** Use tabular figures everywhere numbers change, so they don't jitter.
- Counting numbers **roll** (odometer style) with a spring; never cross-fade.
- Respect system font scaling up to 200%. The hero scales down to fit rather than truncating.

## 5. Shape, space, elevation

- **Grid:** 4 dp. Screen margins 24 dp. Section spacing 32–40 dp.
- **Corners:** cards and sheets 28 dp; chips and buttons fully rounded (pill); the tab bar a floating pill.
  Use continuous ("squircle") corners if the shape library supports them.
- **Touch targets:** ≥ 48 dp. Primary quick-add chips ≥ 56 dp tall.
- **Elevation is light, not shadow:** prefer edge highlights and blur over drop shadows.

## 6. Glass material (recipe)

A `GlassSurface` component owns all of this; screens never re-implement it.

1. **Backdrop blur** of what's behind (aurora + content), radius 24–40 dp.
2. **Tint** `glass.tint` over the blur.
3. **Refraction/lens edge** (API 33+): a slight bulge distortion at the edges plus a specular highlight,
   via a runtime shader. This is what makes it "liquid" rather than "frosted".
4. **Edge stroke** `glass.stroke`, 1 dp, gradient top-bright.
5. **Soft shadow** `glass.shadow`.
6. **Optional motion response:** highlight shifts slightly with device tilt (accelerometer, low-pass
   filtered, ≤ 4 dp travel). Off when "Remove animations" is on. Try it; keep it only if it feels good.

Libraries to evaluate in Phase 2 (both Compose Multiplatform, so iOS-ready):
**Kyant Backdrop** (`io.github.kyant0:backdrop`, Apache 2.0 — refraction, highlights) and **Haze**
(blur). Pick by look and frame time on the Pixel 10.

Fallbacks: API 31–32 → blur + tint, no refraction. Below 31 (not supported in v1) → solid tint.
**Legibility rule:** text on glass must hit 4.5:1 contrast against the *worst-case* backdrop. If it
doesn't, raise the tint, not the text weight.

## 7. The aurora (background light)

- 2–3 large, very soft color blobs from the accent palette drifting slowly (one full cycle ≈ 20–40 s).
- **It reacts to progress:** at 0% it's faint and cool; it gathers and brightens toward the hero as
  today's progress rises; at goal it blooms fully once, then settles at a warm "complete" state.
- Rendered cheaply (shader or a few blurred gradient shapes drawn once and moved with transforms).
  It must never cost frames. Pause it when the app is in the background.
- On Month and Year screens it's calmer and dimmer, so data stays legible.

## 8. Motion

- **Springs everywhere,** no linear tweens. Default spring: damping ≈ 0.8, stiffness medium-low.
  Interactive drags: higher stiffness. Define named specs as tokens (`motion.snappy`, `motion.gentle`,
  `motion.bloom`).
- **Log a set:** chip compresses (scale 0.96) → numeral rolls up → progress sweeps → aurora brightens a step
  → light haptic. Total ≈ 400–600 ms, interruptible.
- **Goal met:** one light bloom from the progress visual (≈ 900 ms) + aurora swell + "confirm" haptic +
  copy fades in. Once per day.
- **Overflow:** beyond 100%, progress continues as a second, brighter layer (a halo or a rising glow) —
  extra reps should look like a bonus, never like a capped bar.
- **Navigation:** shared-element transitions (the hero numeral morphs into the month's day cell).
  Predictive back previews.
- **Material 3 Expressive** motion physics and shape morphing are welcome where they fit (e.g. a chip
  morphing on press). Its default colors and components are not.
- **Reduce motion:** when system animations are off, replace motion with instant state changes and
  short fades; the aurora becomes static.

## 9. Haptics and sound

| Moment | Haptic (Android `VibrationEffect` primitives) |
| --- | --- |
| Log a set | `PRIMITIVE_CLICK`, low scale |
| Count mode rep | `PRIMITIVE_TICK` per rep |
| Goal met | A short rising composition (e.g. `SLOW_RISE` → `CLICK`) |
| Undo / delete | `PRIMITIVE_TICK`, low |

Restrained, distinct, and toggleable. Sound is **off by default**; optional soft tick in count mode.
Haptics sit behind a `Haptics` interface so iOS can use Core Haptics later.

## 10. Components (the design system)

Build these in `shared/.../ui/designsystem`, each with previews and a place in the debug **Gallery** screen:

`Canvas` · `Aurora` · `GlassSurface` · `GlassPillTabBar` · `HeroCount` (rolling numerals) ·
`ProgressVisual` (one per direction, see §11) · `QuickAddChip` · `QuickAddDock` · `GlassToast` ·
`GlassSheet` · `RepDial` / `Stepper` · `SetRow` · `StatTile` · `PaceChart` (cumulative actual vs pace line) ·
`MonthHeatmap` · `YearHeatmap` · `DayCell` · `SegmentedControl` · `Toggle` · `TimePickerSheet` ·
`EmptyState` · `Celebration` (bloom).

Charts are drawn by hand on a Compose `Canvas` — no chart library — so they match the system exactly.

## 11. Home-screen direction study (Phase 2)

Build three Today-screen directions behind a debug switch, all using the same tokens, data and copy.

| | **A · Halo** | **B · Vessel** | **C · Horizon** |
| --- | --- | --- | --- |
| Idea | Giant centered numeral inside a thin luminous gradient ring | A tall glass capsule that fills with soft liquid light as reps add up | Type-led editorial: huge numeral top-left; the aurora rises like a sunrise with progress |
| Progress | Ring sweep; overflow = a second, brighter orbit | Fill level with a gentle meniscus wobble on each set; overflow = glow rising above the vessel | Light horizon height across the whole screen; overflow = the sky fully lit plus a bright edge |
| Glass | Quick-add dock, tab bar | The vessel itself + dock | Dock and tab bar only — the most restrained |
| Risk | Familiar (rings are everywhere) | Could tip into kitsch; performance of liquid | Less legible progress at a glance |

**Render each in five states:** 0/20 (fresh day) · 14/20 · 20/20 (goal moment) · 32/20 (overflow) · rest day.
Capture all 15 as screenshots on the same device, laid out as a grid, plus a short screen recording of
logging a set in each. JK picks a direction (or a hybrid), and an accent palette. Record the decision in
`docs/DECISIONS.md` and update this doc. Delete the losing directions from the code afterwards.

## 12. Widgets

Android widgets (Glance) can't do live blur or shaders. Don't fake glass badly — design them as a
**calm sibling**: pearl surface, crisp numeral, and the progress visual rendered to a bitmap in the
chosen direction's style. They must still look like they belong to the app. Tap-to-log feedback should
update within a second.

## 13. Accessibility

- Contrast AA (4.5:1 for text, 3:1 for large numerals and meaningful graphics) — test on glass over the
  brightest aurora state.
- TalkBack: the hero reads as "14 of 20 push-ups today, 6 to go"; charts have text summaries.
- Font scaling to 200% without truncation; layouts reflow.
- Everything works without haptics, sound or motion.

## 14. Design review checklist (use on every screenshot)

1. Is the most important thing the biggest and clearest thing?
2. Could anything be removed without loss? Remove it.
3. Are all values tokens? Spacing on the 4 dp grid?
4. Is every piece of text legible on its worst-case background?
5. Does motion feel physical and finish fast? Is anything moving that doesn't need to?
6. Does it feel calm? Any guilt, nagging or noise?
7. Would this look at home next to Apple, OpenAI and Gemini's best screens?
