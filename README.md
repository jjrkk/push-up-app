# Pushups — PRD and Claude Code prompt pack

Everything a new Claude Code session needs to set up, design and build the app with you.

## What's in here

```
CLAUDE.md            Claude Code reads this automatically: who you are, how to work, hard rules
.claude/settings.json  Pre-approved safe commands (gradle, adb screenshots, git status/diff);
                     blocks commands that would wipe app data on your phone
docs/PRD.md          Product: principles, goal model, features, screens, success criteria
docs/DESIGN.md       Visual direction (light, airy glass), tokens, glass recipe, motion, A/B/C study
docs/TECH.md         Kotlin Multiplatform + Compose architecture, libraries, data model, platform features
docs/SETUP.md        Toolchain → emulator → your phone → GitHub, step by step
docs/PROMPTS.md      Ten phases with copy-paste prompts and "done when" checklists
docs/DECISIONS.md    Decisions so far + open questions (Claude Code keeps it updated)
docs/RESEARCH.md     The evidence behind the decisions
docs/LATER.md        Play Store launch, 12-tester rule, marketing ideas, parking lot
```

## How to start

1. Make an empty folder at `~/Developer/pushups` (a local folder, not Google Drive — see SETUP §0).
2. Copy this pack's contents into it.
3. Open Terminal there and run `claude`.
4. Paste the **Phase 0** prompt from `docs/PROMPTS.md`.

Claude Code will walk you through installing Android Studio, generating the project into this folder,
running it on the emulator and your phone, and pushing to GitHub — then Phase 1 gets real logging onto your
phone the same day.

## The shape of the build

| Phase | You get | Rough effort |
| --- | --- | --- |
| 0 Setup | Template app on emulator + phone; repo on GitHub | 2 h |
| 1 Walking skeleton | Log real push-ups on your phone (plain UI); goal engine tested | 1 session |
| 2 Design system + A/B/C study | Glass, aurora, type, motion; three home directions to critique | 2–3 sessions |
| 3 Today | The real home screen in your chosen direction | 1–2 sessions |
| 4 Count mode | Tap/nose counter for sets | 1 session |
| 5 Month + Year | Pace, heatmaps, bests | 2 sessions |
| 6 Onboarding, settings, reminders, export | Complete personal app | 1–2 sessions |
| 7 Widgets + shortcuts | One-tap logging from the home screen | 1–2 sessions |
| 8 Should-haves | Month recap, dark theme, Health Connect | as you like |
| 9 Polish + performance | Personal v1.0 | 1–2 sessions |

Phase 2 is where the app is won or lost. Spend your critique energy there.
