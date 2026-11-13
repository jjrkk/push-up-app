# RESEARCH — evidence summary

From two research passes in Nov 2026: a market scan of push-up apps, and a coded analysis of
22,074 store reviews (17,575 Google Play, 4,499 App Store) across 45 listings.
Full write-up: "Push-Up Apps: Review Deep Dive (v2)" (claude.ai doc) and `pushup_reviews_coded.csv`.

## Market

- **Android leaders are dated and ad- or program-led.** Push Ups Workout (Simple Design) ~10.5M installs,
  4.7★, ads; 100 Pushups (BeStronger) ~4.6M, ads + IAP; Zen Labs 0-100 ~1.1M; Just 6 Weeks ~1.4M.
- **Design-led push-up apps are iOS-only:** Puuush (4.8★, 3.5K ratings, $3.99/mo) and PushFit (4.7★).
- **Closest Android analogue:** Push Up Streak (~29K installs, 4.7★, ad-free) — reviewers wish it "could be more modern".
- **Fast-growing adjacent category:** "push-ups to unlock your phone" apps (~2.1M installs across five), all subscription-led.
- **Proof of demand for period totals:** Australia's Push-Up Challenge — 232K+ people, 330M+ push-ups in 2025;
  its app is criticized for running only in June and for rigid targets.
- **Design benchmark:** HabitKit (767K installs) and Loop (8M) — reviewers praise simplicity (35% of 4–5★)
  and design (11%), and care a lot about widgets.

## What users hate (share of 1–2★ push-up app reviews)

| Theme | Share |
| --- | --- |
| Paywall / subscription | 38% (47% since 2024) |
| Ads | 9.5% (falling) |
| Bugs / crashes | 8.5% |
| Login / account issues | 4.9% |
| Reps not counted correctly | 4.0% (15% in auto-count apps) |
| Paid once, then asked to subscribe | 1.7% — but the lowest-rated theme (1.4★) |

## What users love (share of 4–5★)

Simplicity (14%; 28% for trackers), motivation/consistency (8.5%), visible results (8.2%).

## What users ask for (share of 1,362 feature requests)

Other exercises 9.2% · daily/weekly/monthly goals and totals 8.6% · set my own numbers / edit 7.8% ·
reminder control 6.7% · fewer ads 5.7% · better history with exact numbers 5.4% · counting accuracy 5.2% ·
health sync 3.1% · backup/export 3.0% · credit extra or partial reps 3.0% · rest days / streak protection 2.6%.

## Implications baked into the PRD

Free core forever · every rep counts · user sets the goal · exact numbers over charts · no account ·
widget as a core screen · manual logging first · say no to other exercises in v1.

## Gaps

No Reddit data (blocked during research); no hands-on teardowns; English-language reviews only.
