# DECISIONS — log and open questions

Claude Code: add a row whenever a decision is made or changed. Newest first.

## Decision log

| Date | Decision | Why | Status |
| --- | --- | --- | --- |
| 2026-11-10 | Project stays in the Google Drive folder; build output and Gradle/Kotlin caches go to `~/.pushups-build/` | JK prefers it and has used Drive for other projects; redirecting builds avoids syncing thousands of temp files. If builds misbehave, move the repo to a local folder first. Never open it on a second Mac while syncing. | Decided |
| 2026-11-10 | Test phone = Pixel 10 (was Pixel 6); performance budget at 120 Hz | JK changed phones | Decided |
| 2026-11-10 | Template: KMP wizard with Android + iOS (shared UI) + Desktop (hot-reload sandbox); minSdk 31, compile/target 37; Kotlin 2.4.21, CMP 1.12.2, AGP 9.1.1 | SETUP §2; Desktop included now because it is cheaper than adding later | Decided |
| 2026-11-07 | Scope = push-ups only; no other exercises, programs, social or ads in v1 | Focus; competitors sprawl; "other exercises" is the top request but dilutes the product | Decided |
| 2026-11-07 | Personal-use v1 first; Play Store later | JK wants to iterate on look-and-feel before launch | Decided |
| 2026-11-07 | Stack = Kotlin Multiplatform + Compose Multiplatform; Android first, iOS later | Native Android performance and APIs; shared logic and custom UI port to iOS | Decided |
| 2026-11-07 | Visual direction = light, airy glass (provisional) | JK's intuition; to be tested in the Phase 2 A/B/C study | Provisional |
| 2026-11-07 | Extra reps count toward the month by default; "bank toward tomorrow" is a setting | Simplest mental model; banking is loved by some, confusing to others | Decided |
| 2026-11-07 | Monthly goal auto-derived from daily target by default; custom total optional | Matches both "N a day" and "3,000 a month" challenges | Decided |
| 2026-11-07 | No account, no network permission, local-first with Auto Backup + CSV | Privacy promise; forced login is the top tracker complaint | Decided |
| 2026-11-07 | Manual + quick-add + tap/nose count mode in v1; no camera or proximity counting | Miscounts drag every auto-count app down; manual is reliable | Decided |
| 2026-11-07 | Streaks are quiet (Month screen only), rest days don't break them | Calm, never guilty | Decided |
| 2026-11-07 | Monetization (later): free core forever; optional cosmetic Supporter unlock + tip jar; never subscriptions, never move features behind a paywall | Paywalls are the #1 complaint; bait-and-switch is the worst-rated pattern | Decided (for later) |

## Open questions

| Question | Options | Decide by |
| --- | --- | --- |
| Final app name | Working title "Pushups". Ideas to test: Floor, Daily Press, Hundred, Rise, Reps | Before Play launch (affects package id only if decided before first upload) |
| Home direction | Halo / Vessel / Horizon / hybrid | End of Phase 2 |
| Accent palette | Dawn / Glacier / Ion | End of Phase 2 |
| Glass library | Kyant Backdrop / Haze / both | Phase 2 |
| Typeface | Google Sans Flex / Geist / Inter | Phase 2 |
| Fresh-day copy | "20 today" vs "Let's begin" | Phase 3 |
| Desktop hot-reload sandbox | Add / skip | Phase 2 |
| Default daily target in onboarding | 20 vs ask with no default | Phase 6 |
