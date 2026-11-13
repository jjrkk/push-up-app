# Pushups (working title) — Claude Code project guide

A beautiful, minimal push-up tracker. Android first, iOS later. Push-ups only.
The visual design is the product's main competitive advantage — treat it that way.

## Read before working

| File | What it holds | When |
| --- | --- | --- |
| `docs/PRD.md` | What the app does: goal model, features, screens, acceptance criteria | Before any feature work |
| `docs/DESIGN.md` | Visual direction, tokens, glass recipe, motion, haptics, copy voice | Before any UI work |
| `docs/TECH.md` | Architecture, modules, libraries, data model, platform rules | Before any code |
| `docs/SETUP.md` | Toolchain, emulator, phone, GitHub — step by step | Phase 0 |
| `docs/PROMPTS.md` | The build phases, in order, with done criteria | Start of each phase |
| `docs/DECISIONS.md` | Every decision made so far, and open questions | Keep it updated |
| `docs/RESEARCH.md` | Market and review evidence behind the decisions | When a decision needs justifying |

If these docs and the code disagree, stop and ask which is right. Don't silently pick one.

## Who you're working with

JK is a senior product designer (15+ years) and an engineer by training. He has built and shipped
Next.js web apps with Claude Code, but **this is his first Android app**.

- **Explain Android concepts the first time they come up**, in a sentence or two, with a web analogy
  where one fits (e.g. "Gradle is roughly npm + webpack"; "an Activity is the app's window").
- **Give exact clicks** for Android Studio and phone steps ("Settings → System → Developer options").
  One step at a time; wait for him to confirm before moving on during setup.
- **He prefers to be challenged, not validated.** If a request will hurt quality, performance or
  the design, say so plainly and propose the better option.
- **He has a bias to action.** Get something on screen fast, then iterate. Prefer small vertical
  slices he can tap on over big invisible foundations.
- **Aesthetic decisions are his.** Present options (screenshots side by side) rather than silently
  restyling. Once he decides, record it in `docs/DECISIONS.md` and `docs/DESIGN.md`.

## How to work

1. **Plan briefly, then build a thin slice.** State the slice, build it, show it.
2. **Verify every UI change visually.** Build, install on the emulator, take a screenshot
   (`adb exec-out screencap -p > /tmp/screen.png`) and look at it yourself before showing JK.
   Check it against `docs/DESIGN.md`: spacing, type, glass legibility, alignment, nothing clipped.
   Use the debug data seeder so screens show realistic data.
3. **Test the logic.** The goal engine (`GoalEngine`) is pure Kotlin with unit tests in `commonTest`.
   Any change to goal, pace, streak or banking rules needs tests first. Run `./gradlew allTests`
   (or the narrower task you set up) before saying something works.
4. **Judge smoothness on a release build, never debug.** Compose debug builds are much slower.
5. **Commit at checkpoints, with JK's OK.** Conventional Commits:
   `feat`, `fix`, `chore`, `style`, `refactor`, `docs`, `test`. He reviews diffs in GitHub Desktop.
6. **Keep the docs live.** When a decision changes, update `docs/DECISIONS.md` in the same commit.

## Hard rules

- **No Android or iOS imports in common code.** `shared/src/commonMain` must compile for iOS later.
  Platform features go behind interfaces with `androidMain` / `iosMain` implementations.
- **No hard-coded colors, sizes, durations or fonts in screens.** Everything comes from the design
  system tokens (`ui/designsystem`). If a token is missing, add it to the system.
- **No stock Material look.** Material 3 components are allowed only as unstyled building blocks
  under our own components. No default purple, no FAB, no default ripples, no stock top app bar.
- **No new dependencies without asking JK**, except those already listed in `docs/TECH.md`.
- **No network access in v1.** The app does not request the `INTERNET` permission. No analytics,
  no ads, no crash reporting services, no accounts.
- **Never lose user data.** Schema changes need Room migrations with tests. Never wipe the database
  to "fix" a problem on JK's phone.
- **Every rep counts.** No code path may drop, cap or round a logged rep from the totals.
- **Respect accessibility.** Text contrast WCAG AA (4.5:1) even on glass; 48dp touch targets;
  TalkBack labels on all controls; honor the system "Remove animations" setting.
- **Secrets stay out of git.** Keystores and signing passwords live outside the repo
  (see `docs/SETUP.md`).

## Useful commands

```bash
./gradlew :androidApp:installDebug          # debug build → com.justinkirkey.pushups.debug (seeded test data)
./gradlew :androidApp:installRelease        # signed, R8-minified release → com.justinkirkey.pushups (JK's real data)
./gradlew :shared:allTests -x :shared:iosSimulatorArm64Test  # all shared tests (iOS tests need Xcode; skip until the iOS port)
./gradlew :shared:jvmTest                   # fastest loop for commonTest (GoalEngine etc.)
./gradlew :desktopApp:hotRun                # desktop design sandbox with Compose Hot Reload
adb devices                                 # list connected phone/emulators
adb exec-out screencap -p > /tmp/screen.png # screenshot the current screen
adb shell am start -n com.justinkirkey.pushups/.MainActivity                              # open release app
adb shell am start -n com.justinkirkey.pushups.debug/com.justinkirkey.pushups.MainActivity # open debug app
adb logcat -s Pushups                       # app logs (use the "Pushups" log tag)
```

- **Two devices connected?** Gradle installs on all of them; adb refuses. Pick one with
  `ANDROID_SERIAL=<serial>` (Gradle and adb) or `adb -s <serial>`. JK's Pixel 10: `59041FDCR006MG`;
  emulator: `emulator-5554` (AVD `Pixel_10`).
- **Build output is outside the repo** (it's in Google Drive): `~/.pushups-build/<module>/…`, e.g. APKs in
  `~/.pushups-build/androidApp/outputs/apk/`. Configured in root `build.gradle.kts` and `gradle.properties`.
- **Release signing** reads `pushups.keystore.*` / `pushups.key.*` from `~/.gradle/gradle.properties`;
  keystore at `~/keystores/pushups-release.jks`. Never copy either into the repo.
- Non-interactive shells may lack `JAVA_HOME`/`ANDROID_HOME`: `source ~/.zshrc` first.
- **Never `adb uninstall com.justinkirkey.pushups` on JK's phone** — that deletes his real data.
