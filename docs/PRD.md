# PRD — Pushups (working title)

Version 1.0 · 7 Nov 2026 · Owner: JK
Scope of this version: **v1 for personal use** on JK's phone. Play Store work comes later (`docs/LATER.md`).

## 1. One-line pitch

The calm, beautiful way to hit your push-up goal — every day and every month.

It's a ledger and a ritual, not a coach. You set a number; the app makes logging effortless,
makes progress feel good, and never nags, shames or upsells.

## 2. Why this should exist

Evidence from 22,074 store reviews across 45 app listings (see `docs/RESEARCH.md`):

- **The big apps look dated and are built around ads or programs.** The Android leader (10.5M installs)
  is an ad-supported 2010s utility. Nobody on Android owns "beautiful, calm, ad-free tracker".
- **Paywalls are the top complaint:** 47% of 1–2★ push-up app reviews since 2024. Apps that switch
  from a one-time purchase to a subscription get the angriest reviews (1.4★ average).
- **Simplicity is the most-praised quality:** 14% of 4–5★ push-up reviews, 28% for tracker apps,
  35% for the design-led habit trackers (HabitKit, Loop).
- **Users want control of their own numbers.** Top requests after "other exercises": daily/weekly/monthly
  totals, set my own numbers, edit entries, credit partial sets and extra reps.
- **The polished apps (Puuush, PushFit) are iOS-only** and get hammered for paywall creep and
  camera miscounts.

The bet: **design quality + an honest goal model + zero friction** is a real gap, and simplicity is
both what users reward and what JK means by beautiful.

## 3. Who it's for

- **Primary (v1): JK.** He'll use it daily and critique it constantly. If he doesn't love it, it fails.
- **Target (later):** adults doing a daily push-up habit or a challenge (100 a day, 22 for 22,
  3,000 a month, the June Push-Up Challenge) who want *tracking*, not a training program.
- **Not for (v1):** people who want a guided 0→100 program, multiple exercises, or social leaderboards.

## 4. Product principles

These settle disagreements. When in doubt, re-read them.

1. **One number, huge.** Today's count is the hero of the app. Everything else is secondary.
2. **Log in under two seconds.** From the widget, the notification or the open app. One tap is the goal.
3. **Every rep counts.** Partial sets, extra reps, late entries, backfilled days — all count, always editable.
4. **Calm, never guilty.** No shame copy, no red, no "don't break your streak!". Rest is part of the design.
5. **Beauty is the feature.** Light, motion, type and haptics are crafted. Simplicity *is* the elegance:
   every element earns its place or goes.
6. **Yours and private.** No account, no ads, no network, no tracking. Your data, exportable anytime.

## 5. The goal model (core logic)

This is the heart of the app. Implement it in `GoalEngine` (pure Kotlin, fully unit-tested).

### 5.1 Concepts

- **Entry (set):** a count of push-ups logged at a moment. A day can have any number of entries.
- **Day total:** the sum of a day's entries. Days are local calendar days (midnight to midnight).
- **Daily target (T):** the push-ups the user aims for on a scheduled day. Default 20.
- **Rest days:** weekdays the user marks as rest (default: none). Target on a rest day is 0.
- **Monthly goal (M):** either
  - **Auto** (default): the sum of daily targets for every scheduled day in the month, or
  - **Custom:** a number the user types (e.g. 3,000), independent of the daily target.
- **Goal versions:** every goal change is stored with an effective date. Past days keep the target
  they had. Changing the goal today never rewrites history.

### 5.2 Extra reps (setting, default = "Count toward the month")

| Mode | Rule | Day complete when |
| --- | --- | --- |
| **Count toward the month** (default) | Extra reps beyond today's target add to the monthly total and pace. Tomorrow's target is unchanged. | `dayTotal ≥ T` |
| **Bank toward tomorrow** | Extra reps carry forward and reduce the next day's remaining target. Shortfalls never create debt. The bank resets on the 1st of each month. | `dayTotal + bankIn ≥ T` |

Banking arithmetic (scheduled days only; rest days pass the bank through unchanged):

```
credit(d)  = dayTotal(d) + bankIn(d)
complete   = credit(d) >= T(d)
bankOut(d) = if (complete) credit(d) - T(d) else 0
bankIn(d+1) = bankOut(d)          // reset to 0 on the 1st
remainingToday = max(0, T - bankIn - dayTotal)
```

In both modes **all reps always count toward the monthly and lifetime totals.** Banking only changes
what "today's target" asks of you.

### 5.3 Pace (the signature number)

- **Expected by end of today** =
  - Auto monthly goal: the sum of `T(d)` for scheduled days from the 1st to today.
  - Custom monthly goal: `M × (scheduled days elapsed incl. today ÷ scheduled days in month)`.
- **Pace delta** = month total − expected. Shown as "140 ahead of pace" or "60 to catch up".
- **Projection** = month total ÷ elapsed scheduled days × scheduled days in month → "On pace for 3,240".
  Hide the projection until at least 3 scheduled days have passed.

### 5.4 Streaks (deliberately quiet)

- **Current streak:** consecutive scheduled days marked complete, counting back from yesterday
  (today counts once it's complete). Rest days are skipped, not broken.
- **Best streak:** the longest ever.
- Shown on the Month screen only, never as the hero, never with warning copy.

### 5.5 Edge cases (must have tests)

- Goal changed mid-month (both modes, auto and custom monthly goal).
- Rest days added or removed mid-month.
- Month boundaries: 28/29/30/31-day months, bank reset on the 1st.
- Time zone change or DST: an entry belongs to the local date when it was logged; store the instant and
  the zone.
- Backfilled entries on past days recompute completion, bank and streaks for every later day.
- Deleting an entry that made a day complete.
- A month with zero scheduled days (all rest days): no division by zero; pace hidden.
- Entries are positive integers 1–999. Future-dated entries are not allowed.

## 6. v1 features

### Must have (personal v1)

| # | Feature | Acceptance criteria |
| --- | --- | --- |
| F1 | **Today screen** | Hero count + target, progress visual with overflow, "N to go" / "Goal met · +N extra", quick-add, today's sets, compact month pace. |
| F2 | **Quick add** | 3 chips (default sizes from target, e.g. T=20 → 5/10/20; editable in Settings) + a "custom" chip. One tap logs. A glass toast confirms "Logged 10 · Undo" for 5 s. |
| F3 | **Custom amount** | Sheet with a large numeral and a rep dial/stepper; a keypad toggle for typing; date/time defaults to now and can be changed to backfill. |
| F4 | **Count mode** | Full-screen counter: tap anywhere (or touch the screen with your nose/chin) to add one rep. Haptic tick per rep, screen stays awake, big numeral, "Done" saves one entry, "Cancel" discards with confirm. |
| F5 | **Edit history** | Any entry can be edited (count, time) or deleted with undo. Any past day can get new entries. |
| F6 | **Month screen** | Month total vs goal, pace delta, projection, cumulative chart (actual vs pace line), calendar heatmap of completion and volume, streak (current/best). Tap a day → day detail sheet. Swipe or tap to change month. |
| F7 | **Year screen** | 12-month heatmap (GitHub-style tile grid), lifetime total, personal bests (most in a day, biggest set, best month, best streak), monthly totals. |
| F8 | **Goal-hit moment** | When today's target is first met: a crafted light bloom + haptic + one line of copy. Once per day. Never confetti. |
| F9 | **Onboarding** | Max 3 steps: daily target (10/20/30/50/100/custom), monthly goal preview (auto, editable), optional reminder time. No account. Lands on Today. |
| F10 | **Settings** | Daily target, monthly goal (auto/custom), extra-reps mode, rest days, quick-add sizes, reminders, haptics, sound, export/import CSV, about. Theme choice (light / dark / system) arrives with S3. |
| F11 | **Reminders** | Optional daily reminder at a chosen time, only if today isn't complete. Notification has "+N" quick-log actions. Calm copy. No other notifications. |
| F12 | **Home-screen widgets** | Small: today's count/target with progress + one-tap "+N". Medium: today + month pace + quick-add chips. Update instantly after logging anywhere. |
| F13 | **App shortcuts** | Long-press the icon: "Log N", "Count mode". |
| F14 | **Backup & export** | Android Auto Backup includes the database. CSV export/import (`date,time,count,source`) via the system file picker. Import skips duplicates. |

### Should have (v1 if time allows)

| # | Feature | Notes |
| --- | --- | --- |
| S1 | **Month recap** | On the 1st, a beautiful card summarizing last month (total, vs goal, best day, streak). Saved in the Year screen. Share-as-image comes with the Play launch. |
| S2 | **Health Connect** | Write each entry as an exercise session so totals show in Fit / Samsung Health. Off by default. See `docs/TECH.md` §8. |
| S3 | **Dark theme** | Full pass of the design system in dark. |
| S4 | **Day starts at** | Let night owls end their day at e.g. 3 am. |

### Not in v1 (deliberate)

Other exercises · training programs or max tests · accounts or cloud sync · friends, leaderboards,
challenges with others · ads · calories or body-transformation content · camera/AI rep counting ·
proximity-sensor counting · Wear OS · iOS · paid features.

Each of these was considered; most are in `docs/LATER.md` with the reason they wait.

## 7. Screens and navigation

```
Onboarding (first run only)
   └─> Today  ◀──▶  Month  ◀──▶  Year          (floating glass tab bar, 3 items)
         │            │            │
         │            └─> Day detail (sheet)
         ├─> Custom amount (sheet)
         ├─> Count mode (full screen)
         └─> Settings (from a quiet icon on Today; pushed screen)
```

- Default screen: Today. App always opens to Today.
- Back gestures behave as Android users expect (predictive back supported).
- Sheets are glass, dismiss by drag.

### Copy voice (examples)

| Situation | Copy |
| --- | --- |
| Fresh day | "20 today" / "Let's begin" (pick one in design review) |
| In progress | "6 to go" |
| Goal met | "Goal met" · "+12 extra" |
| Rest day | "Rest day" · "Logged reps still count" |
| Ahead | "140 ahead of pace" |
| Behind | "60 to catch up" |
| Reminder | "Still time for today's 20." |
| Undo | "Logged 10 · Undo" |

Never: "You failed", "Don't lose your streak!", exclamation marks in system copy, emoji.

## 8. Success criteria for personal v1

- JK logs every day for 30 days without reaching for another app or a note.
- Logging from the widget takes one tap; from the app, ≤ 2 taps.
- Smooth on JK's Pixel 10 (release build): no visible jank on Today, Month or Year; cold start feels instant.
- JK shows it to people unprompted because it looks great.
- No data lost across updates, ever.

## 9. Open questions

Tracked in `docs/DECISIONS.md` → "Open questions".
