# SETUP — from zero to the app on your phone

For Claude Code: walk JK through this **one step at a time**, explaining each concept briefly the first
time it appears. Confirm each step worked before moving on. Assume a MacBook Pro with Apple Silicon;
check with `uname -m` (`arm64`) and adjust if not.

Expect 1.5–3 hours the first time, mostly downloads.

## 0. Where the project lives

- Decided 2026-11-10: the repo stays in the Google Drive folder (`~/My Drive/jk-ai-projects/push-up-app`).
  Build output and Gradle/Kotlin caches are redirected to `~/.pushups-build/` (root `build.gradle.kts` +
  `gradle.properties`) so Drive never syncs them.
- Why: Android builds write thousands of temporary files (`build/`, `.gradle/`, `.kotlin/`), which would make
  sync slow and can corrupt builds. If builds misbehave, move the repo to a local folder first. GitHub is the backup.

## 1. Install the toolchain

1. **Android Studio** (latest stable) from developer.android.com/studio. It bundles:
   - the **JDK** (Java runtime Gradle needs),
   - the **Android SDK** (platform libraries and tools),
   - the **emulator**.
   Run the setup wizard with "Standard" settings.
2. **Kotlin Multiplatform plugin** for Android Studio: Settings → Plugins → Marketplace → "Kotlin Multiplatform".
3. **Command-line access** so Claude Code can build and use `adb` from the terminal. Add to `~/.zshrc`:
   ```bash
   export ANDROID_HOME="$HOME/Library/Android/sdk"
   export PATH="$PATH:$ANDROID_HOME/platform-tools:$ANDROID_HOME/emulator"
   export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
   ```
   Then `source ~/.zshrc` and check: `adb version`, `java -version`.
4. **Xcode** is *not* needed yet (only for the iOS port).

Concepts to explain: **Gradle** (build tool + dependency manager, like npm + a bundler), **SDK levels**
(each Android version has an API number; minSdk is the oldest we support), **adb** (command-line bridge to
phones and emulators).

## 2. Create the project

1. Generate with the Kotlin Multiplatform wizard — in Android Studio (New Project → Kotlin Multiplatform)
   or at kmp.new:
   - Name: `Pushups` · Package: `com.justinkirkey.pushups` (confirm)
   - Targets: **Android** and **iOS** (shared UI with Compose Multiplatform). Optional: **Desktop** for the
     design sandbox (`docs/TECH.md` §10).
2. Generate it into a temporary folder (e.g. `~/Developer/pushups-template`), then move its contents into
   `~/Developer/pushups`, where this pack already sits. Claude Code can do the move and merge the
   `.gitignore`; keep the pack's `CLAUDE.md`, `.claude/` and `docs/`.
3. Open the folder in Android Studio; let Gradle sync finish (first sync downloads a lot).
4. Claude Code: verify the generated structure matches `docs/TECH.md` §2 (`androidApp`, `shared`, `iosApp`),
   update the "Useful commands" in `CLAUDE.md` with the real Gradle task names, and set minSdk 31.

## 3. Emulator

1. Android Studio → Device Manager → Create virtual device → **Pixel 10** profile (matches JK's phone)
   → latest stable system image **with Google Play**, arm64.
2. Start it, then run the app (green ▶ with `androidApp` selected) or `./gradlew :androidApp:installDebug`.
3. Success = the template app on the emulator. Claude Code takes a screenshot to confirm it can see the screen.

## 4. JK's phone

1. On the phone: Settings → About phone → tap **Build number** 7 times → "You are now a developer".
2. Settings → System → Developer options → turn on **USB debugging** (and optionally **Wireless debugging**).
3. Connect by USB-C, accept the "Allow USB debugging?" prompt (tick "Always allow").
   Or wireless: Android Studio → Device Manager → Pair devices using Wi-Fi → scan the QR code from the phone.
4. `adb devices` should list the phone. Install with `./gradlew :androidApp:installDebug`.
5. Note: installing over adb is exempt from Google's 2026 developer-verification rules — no registration
   needed for personal development.
6. **Never uninstall the app on the phone once you're using it daily** — that deletes your data. Updates
   install over the top.

## 5. A fast personal build (release)

Debug builds feel sluggish; the app you use daily should be a signed release build.

1. Create a release keystore **outside the repo**, e.g. `~/keystores/pushups-release.jks`
   (Android Studio → Build → Generate Signed App Bundle/APK → Create new). Store the passwords in a
   password manager.
2. Put signing config values in `~/.gradle/gradle.properties` (not the project's), and read them in
   `androidApp/build.gradle.kts`.
3. `./gradlew :androidApp:installRelease`.
4. Back up the keystore. This key will become the upload key for the Play Store later.

Note: a debug build and a release build can't be installed over each other if they share an application
id and use different signatures. Add `applicationIdSuffix = ".debug"` to the debug build so both can
live on the phone side by side (debug with seeded test data, release with real data).

## 6. GitHub

1. Use JK's **personal** GitHub account (not the work account) for this repo.
2. In GitHub Desktop: File → Add local repository → `~/Developer/pushups` → create repository → Publish,
   **private**.
3. Check `.gitignore` covers: `build/`, `.gradle/`, `local.properties`, `*.jks`, `*.keystore`, `.idea/`
   (mostly), `*.apk`, `*.aab`, `.DS_Store`.
4. First commit: `chore: scaffold KMP project with design pack`.

## 7. Done when

- [ ] Template app runs on the emulator and on JK's phone.
- [ ] Release build installs on the phone.
- [ ] Claude Code can build, install and screenshot from the terminal.
- [ ] Repo is on GitHub (private), first commit pushed.
- [ ] `CLAUDE.md` commands updated with real task names.
