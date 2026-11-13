# PROMPTS — the build, phase by phase

Paste one phase prompt at a time into Claude Code. Each phase ends with something JK can tap on, and a
checkpoint commit. Don't start the next phase until the "Done when" list is ticked.

Model tip: use **Opus** for Phases 1–2 (architecture, goal engine, design system) and whenever a problem
survives 2–3 attempts; **Sonnet** is fine for the rest.

---

## Session starter (paste at the start of any new session)

```
Read CLAUDE.md, then skim docs/DECISIONS.md and the git log for the last few commits.
Tell me in 3–5 lines: where we are in docs/PROMPTS.md, what's done, and what you suggest next.
Don't change anything yet.
```

---

## Phase 0 — Setup (≈2 hours)

```
I'm starting a new Android app. Read CLAUDE.md and all of docs/ first.
I've built web apps before but never an Android app.

Walk me through docs/SETUP.md one step at a time. For each step: tell me what to do (exact clicks or
commands), briefly explain the concept if it's new, then wait for me to confirm it worked before moving on.
If something I report looks wrong, help me debug before continuing.

At the end, update CLAUDE.md "Useful commands" with the real Gradle task names and confirm every item in
SETUP.md §7 is done.
```

**Done when:** SETUP.md §7 is all ticked.

---

## Phase 1 — Walking skeleton: log real push-ups on day one (≈1 session)

```
Phase 1 from docs/PROMPTS.md. Goal: I can log push-ups on my phone today, even if it's ugly.

1. Set up the shared package layout from docs/TECH.md §2 and the libraries in §3 that this phase needs
   (Room KMP, DataStore, kotlinx-datetime, coroutines, DI). Explain each one in a sentence.
2. Implement the data model (TECH §5) and GoalEngine (PRD §5) with unit tests for every edge case in
   PRD §5.5. Write the tests first and show me the list of test names before implementing.
3. A deliberately plain Today screen: today's total / target, "N to go", buttons +5 / +10 / +20, today's
   sets with delete. No styling effort.
4. A debug-only data seeder: 120 days of realistic history (target 30, sets of 8–25, some missed days,
   rest Sundays, some overflow days), one tap from a debug menu, debug build only.
5. Install the release build on my phone so I can start logging real reps.

Stop after each numbered step for a quick check-in.
```

**Done when:** tests pass; JK has logged real push-ups on his phone; data survives an app update.

---

## Phase 2 — Design system and the direction study (≈2–3 sessions; the most important phase)

```
Phase 2 from docs/PROMPTS.md. Read docs/DESIGN.md closely — the visual design is this app's main advantage.

1. Build the design system foundations in ui/designsystem: tokens (color, type, spacing, shape, motion,
   haptics), Google Sans Flex bundled, Canvas, Aurora, GlassSurface, HeroCount with rolling numerals,
   QuickAddChip/Dock, GlassPillTabBar, GlassToast. Add a debug Gallery screen showing every component and state.
2. Glass: try Kyant Backdrop and Haze on the Pixel 10. Show me side-by-side screenshots and frame-time
   numbers from a release build, and recommend one.
3. (Optional, ask me first) Add the Desktop target with Compose Hot Reload as a design sandbox (TECH §10).
4. Build the three Today directions from DESIGN §11 (Halo, Vessel, Horizon) behind a debug switch, using
   seeded data. Render each in the five states. Also render the three accent palettes (Dawn, Glacier, Ion)
   on your favourite direction.
5. Make a contact sheet: put all screenshots in docs/design-study/ and generate a single HTML page that
   lays them out as a labelled grid I can open in a browser. Also record a short screen recording of
   logging a set in each direction (adb shell screenrecord).

Then stop. I'll critique and choose. After I choose, update DESIGN.md and DECISIONS.md, delete the
losing directions, and commit.
```

**Done when:** JK has picked a direction and palette; DESIGN.md and DECISIONS.md record the choice;
the Gallery shows the final components.

---

## Design critique loop (reuse any time)

```
Design critique round. Here's my feedback on <screen>:
<paste notes or attach a screenshot with annotations>

For each point: say what you'll change and which token/component it touches. Make the changes, then show
me before/after screenshots side by side (same state, same seeded data). If you disagree with a point,
say why and offer an alternative instead of just complying. Update DESIGN.md if a rule changed.
```

---

## Phase 3 — Today, for real (≈1–2 sessions)

```
Phase 3 from docs/PROMPTS.md. Rebuild Today in the chosen direction (PRD F1, F2, F3, F5, F8).

- Quick-add dock with sizes from settings; one-tap logging; GlassToast with Undo (5 s).
- Custom amount sheet: large numeral, RepDial/stepper, keypad toggle, date/time for backfilling.
- Today's sets list: edit and delete with undo.
- Overflow visual beyond 100%; rest-day state; bank-mode copy when banking is on.
- The goal-met moment (DESIGN §8): bloom + haptic + copy, once per day.
- Haptics per DESIGN §9 behind the Haptics interface.
- TalkBack labels; contrast check on glass.

Show me screenshots of every state, then a release build on my phone.
```

**Done when:** logging is ≤ 2 taps from open, it feels smooth on the Pixel 10 release build, and JK likes it.

---

## Phase 4 — Count mode (≈1 session)

```
Phase 4 from docs/PROMPTS.md. Build Count mode (PRD F4, TECH §7).
Full screen, tap anywhere or nose/chin-tap to count, ~250 ms debounce, PRIMITIVE_TICK haptic per rep,
keep screen on, huge numeral, aurora responding to the count. "Done" saves one entry (source COUNT_MODE);
"Cancel" asks before discarding. Make it feel great to use face-down on the floor.
```

**Done when:** JK has used it for real sets and the count is reliable.

---

## Phase 5 — Month and Year (≈2 sessions)

```
Phase 5 from docs/PROMPTS.md. Build Month (PRD F6) and Year (F7) using seeded data.
- Month: total vs goal, pace delta ("140 ahead of pace"), projection, a hand-drawn cumulative PaceChart
  (actual vs pace line), MonthHeatmap, current/best streak, day detail sheet with edit/add.
- Year: YearHeatmap tile grid, lifetime total, personal bests, monthly totals.
- Shared-element transition from Today's numeral to today's day cell.
- Scrolling and month switching must be jank-free on a release build.
Show screenshots with the seeded 120-day history and with a brand-new user (empty states).
```

**Done when:** numbers match GoalEngine tests; empty states look intentional; smooth on device.

---

## Phase 6 — Onboarding, Settings, Reminders, Export (≈1–2 sessions)

```
Phase 6 from docs/PROMPTS.md. Build PRD F9 (onboarding, max 3 steps), F10 (settings, including the
extra-reps mode and rest days, which must create a new GoalVersion rather than rewrite history),
F11 (reminders with quick-log notification actions; request notification permission only when
turned on), and F14 (CSV export/import via the system file picker, Auto Backup rules).
Test the reminder by scheduling one 1 minute out. Test export → wipe a debug install → import.
```

**Done when:** first-run flow takes < 30 s; reminders fire only on incomplete days; CSV round-trips.

---

## Phase 7 — Widgets and shortcuts (≈1–2 sessions)

```
Phase 7 from docs/PROMPTS.md. Build the small and medium Glance widgets (PRD F12, DESIGN §12) and the
app shortcuts (F13). Tap-to-log from the widget must update the widget, the app and the notification state
within a second. Render the progress visual to a bitmap so it matches the chosen direction.
Show me screenshots of both widgets on a home screen with a light and a dark wallpaper.
```

**Done when:** JK logs from the widget daily and it's one tap.

---

## Phase 8 — Should-haves (pick with JK)

```
Phase 8 from docs/PROMPTS.md. Let's do the should-haves in this order unless I say otherwise:
S1 month recap card, S3 dark theme, S2 Health Connect (TECH §8), S4 "day starts at".
Before each, give me a short plan and any risks.
```

---

## Phase 9 — Polish, performance, personal v1 sign-off

```
Phase 9 from docs/PROMPTS.md. Get personal v1 to "done":
- App icon (adaptive + themed/monochrome) and splash, in the chosen direction — show me 3 icon options first.
- Accessibility pass: TalkBack walkthrough, 200% font, contrast on glass, reduce-motion behaviour.
- Performance: Baseline Profile, R8 check, frame timing on Today/Month/Year (release build, Pixel 10).
- Run the DESIGN §14 checklist on every screen and fix what fails.
- Update docs/DECISIONS.md and write a short CHANGELOG.md for v1.0.
```

**Done when:** PRD §8 success criteria are met (the 30-day one keeps running).

---

## After v1

See `docs/LATER.md` for the Play Store launch, the 12-tester closed test, and marketing.
