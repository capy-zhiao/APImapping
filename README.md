# APImapping

A dynamic Xposed-based runtime tracing tool for analyzing API invocations in Android apps, especially Mini Programs in WeChat (`com.tencent.mm`) and QQ (`com.tencent.mobileqq`).

---

## 🎯 Features

- 🔍 Hook **MiniApp JSBridge APIs**, such as `connectWifi`, `getWifiList`, etc.
- 📡 Hook system Android APIs like `WifiManager`, `LocationManager`, `TelephonyManager`, and more
- 🧵 Trace **thread context**, stack traces, and runtime arguments
- 🧠 Monitor calls to WeChat internals: `MMHandler.post`, `Handler.dispatchMessage`, `jsapi.h.Q`
- 📦 Modular design via `Method.methodListPartX` definitions

---

## 📁 Project Structure

```bash
.
├── Method.java                # API method definitions to hook
├── HookTest.java             # Main Xposed entry and hook logic
├── build.gradle              # Gradle build configuration
├── settings.gradle
├── replay_*.log              # Crash/trace logs
└── README.md
````

---

## 🚀 How to Use

### ✅ Requirements

* Android (rooted)
* [Xposed Framework](https://repo.xposed.info/)
* Android Studio
* minSdkVersion: 21+

### 🧩 Steps

1. Clone the repo:

   ```bash
   git clone https://github.com/capy-zhiao/APImapping.git
   cd APImapping
   ```

2. Build:

   ```bash
   ./gradlew assembleDebug
   ```

3. Push module to device, install, and enable via **Xposed Installer**

4. Reboot device and monitor log output:

   ```bash
   adb logcat | grep "MiniAppApi"
   ```

---

## 🧪 Example Log Output

```
[MiniAppApi] appId=wx123456789*******invokeHandler: getWifiList
[AndroidAPI] Thread ID: 317 | Class: android.net.wifi.WifiManager | Method: getScanResults | Args: ...
[hookMMHandlerPost] com.tencent.mm.sdk.platformtools.MMHandler.post triggered from MiniApp context
```

---

## 📌 Tracked APIs

* ✅ JSBridge APIs: `connectWifi`, `getWifiList`, etc.
* ✅ Android: `WifiManager.getScanResults`, `LocationManager.getLastKnownLocation`, etc.
* ✅ Internals: `com.tencent.mm.plugin.appbrand.jsapi.h.Q`, `MMHandler.post`, `Handler.dispatchMessage`

> To extend tracking, just update `Method.methodListPartX`.

---

## ⚠️ Disclaimer

This project is **for research/educational use only**. Hooking commercial apps (e.g., WeChat, QQ) may violate terms of service. Use responsibly and respect local laws.


