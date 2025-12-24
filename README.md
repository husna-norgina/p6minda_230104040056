# 📱 Praktikum 6 — Menggunakan Database Lokal (SQLite + Room ORM)

Praktikum ini membahas **penggunaan database lokal pada aplikasi Android** menggunakan **SQLite melalui Room ORM** dengan pendekatan arsitektur modern **Jetpack Compose**. Fokus utama praktikum adalah memahami **persistensi data lokal**, **penerapan CRUD (Create, Read, Update, Delete)** pada data jurnal, **penyimpanan preferensi pengguna menggunakan DataStore**, serta **navigasi multi-screen** menggunakan **Navigation Compose**.
Aplikasi **Minda** dirancang sebagai aplikasi jurnal pribadi yang sepenuhnya **offline**, mengedepankan prinsip **privacy-by-design**, dan menggunakan **Material Design 3 (MD3)** untuk tampilan yang modern dan responsif.

**Topik:** SQLite, Room ORM, CRUD, DataStore Preferences, Navigation Compose, Jetpack Compose, Material Design 3

---

## 🧑‍🎓 Informasi Mahasiswa

| Informasi          | Data                                                  |
| ------------------ |-------------------------------------------------------|
| Mata Kuliah        | Pemrograman Mobile                                    |
| Dosen Pengampu     | Muhayat, M.IT                                         |
| Praktikum / Proyek | P6 – Menggunakan Database Lokal (SQLite + Room)       |
| Nama Mahasiswa     | Husna Norgina                                         |
| NIM                | 230104040056                                          |
| Kelas              | TI23B                                                 |
| Repo GitHub        | https://github.com/husna-norgina/p6minda_230104040056 |
| Tanggal Praktikum  | 12-11-2025                                            |

---

## 🎯 Tujuan Praktikum

1. Memahami konsep **penyimpanan data lokal** menggunakan SQLite dengan **Room ORM**.
2. Mengimplementasikan **CRUD (Create, Read, Update, Delete)** pada data jurnal.
3. Menghubungkan UI dengan database melalui **Repository** dan **ViewModel**.
4. Menyimpan preferensi pengguna menggunakan **DataStore Preferences**.
5. Menerapkan **onboarding multi-step** yang hanya muncul pada penggunaan pertama.
6. Mengatur **start destination dinamis** (onboarding → home).
7. Mengimplementasikan **Navigation Compose** antar layar.
8. Menampilkan UI yang personal dengan sapaan **nama user**.
9. Menerapkan prinsip **privacy-by-design** (data hanya tersimpan di device).
10. Membangun UI modern dan responsif menggunakan **Jetpack Compose & MD3**.

---

## 🛠 Tools & Environment

* Android Studio (Narwhal | 2025.1.1)
* Android SDK (compileSdk 34, targetSdk 34, minSdk 24)
* Bahasa pemrograman Kotlin
* Jetpack Compose & Material 3
* Room ORM (SQLite)
* DataStore Preferences
* Navigation Compose
* Android Emulator / Real Device
* Git & GitHub

---

## 🧱 Struktur Aplikasi

Aplikasi dikembangkan menggunakan **Jetpack Compose** dengan arsitektur terstruktur sebagai berikut:

* **OnboardingScreens.kt** — Alur onboarding multi-step
* **HomeScreen.kt** — Daftar jurnal utama
* **NewEntryScreen.kt** — Menambahkan jurnal baru
* **NoteDetailScreen.kt** — Detail jurnal (view & delete)
* **EditEntryScreen.kt** — Edit jurnal
* **CalendarScreen.kt** — Kalender & filter tanggal
* **InsightsScreen.kt** — Statistik & insight jurnal
* **SettingsScreen.kt** — Pengaturan aplikasi
* **DiaryEntry.kt** — Entity Room
* **DiaryDao.kt** — DAO Room
* **MindaDatabase.kt** — Database Room
* **DiaryRepository.kt** — Repository
* **UserPrefRepository.kt** — DataStore Preferences
* **Navigation (NavHost & Routes)** — Navigasi aplikasi
* **Theme (MD3)** — Light & Dark Theme

---

## 🧩 Implementasi Praktikum

### 🔹 Onboarding & DataStore

Onboarding multi-step digunakan untuk menyimpan **nama pengguna** dan status onboarding menggunakan **DataStore**, sehingga hanya muncul pada penggunaan pertama.

### 🔹 Room Database (SQLite)

Room digunakan untuk menyimpan data jurnal secara lokal dengan dukungan operasi **CRUD** dan arsitektur **Repository + ViewModel**.

### 🔹 Navigation Compose

Navigasi antar layar diimplementasikan menggunakan **NavHost** dan **NavController**, termasuk alur Home → Detail → Edit → kembali ke Home.

---

## 📸 Screenshot Praktikum

Berikut merupakan hasil implementasi aplikasi **Minda** pada **Praktikum 6 – Database Lokal (Room ORM)**:

**Gambar 1.** Onboarding 1 – Welcome to My Minda!
![Onboarding 1](evidence/1.%20Onboarding%201%20(Welcome%20to%20My%20Minda!).jpg)

**Gambar 2.** Onboarding 2 – What’s your Name?
![Onboarding 2](evidence/2.%20Onboarding%202%20(What’s%20your%20Name).jpg)

**Gambar 3.** Onboarding 3 – HelloScreen (Name)  
![Onboarding 3](evidence/3.%20Onboarding%203%20%28HelloScreen%20%28Name%29%21%29.jpg)

**Gambar 4.** Onboarding 4 – Start Journaling
![Onboarding 4](evidence/4.%20Onboarding%204%20(Start%20Journaling).jpg)

**Gambar 5.** HomeScreen – HomeScreen Minda
![HomeScreen](evidence/5.%20HomeScreen%20(HomeScreen%20Minda!).jpg)

**Gambar 6.** HomeScreen – Search Bar
![Search Bar](evidence/6.%20HomeScreen%20(Search%20Bar).jpg)

**Gambar 7.** NewEntryScreen – New Entry
![New Entry](evidence/7.%20NewEntryScreen%20(NewEntryScreen%20Minda!).jpg)

**Gambar 8.** NoteDetailScreen – Your Entry
![Detail Entry](evidence/8.%20NoteDetailScreen%20(Your%20entry).jpg)

**Gambar 9.** EditEntryScreen – Edit Entry
![Edit Entry](evidence/9.%20EditEntryScreen%20(EditEntryScreen%20Minda!).jpg)

**Gambar 10.** NoteDetailScreen – Delete Entry
![Delete Entry](evidence/10.%20NoteDetailScreen%20(Delete%20entry).jpg)

**Gambar 11.** CalendarScreen – Calendar
![Calendar](evidence/11.%20CalendarScreen%20(CalendarScreen%20Minda!).jpg)

**Gambar 12.** CalendarScreen – Select Date
![Select Date](evidence/12.%20CalendarScreen%20(Select%20date).jpg)

**Gambar 13.** InsightsScreen – Insights
![Insights](evidence/13.%20InsightsScreen%20(InsightsScreen%20Minda!).jpg)

**Gambar 14.** SettingsScreen – Settings
![Settings](evidence/14.%20SettingsScreen%20(SettingsScreen%20Minda!).jpg)

---

## 📄 Laporan Praktikum 6

[230104040056_Husna Norgina_P6.pdf](evidence/230104040056_Husna%20Norgina_P6.pdf)

> Semua screenshot dan laporan praktikum disimpan pada folder:  
> 📂 `./evidence/`

---

## 📊 Analisis Praktikum

* Room ORM berhasil mengelola database lokal dengan aman dan efisien.
* Operasi CRUD berjalan dengan baik dan responsif.
* Data jurnal tersimpan secara offline dan persisten.
* Onboarding hanya muncul pada penggunaan pertama aplikasi.
* DataStore berhasil menyimpan preferensi pengguna.
* Navigasi antar layar berjalan lancar.
* UI konsisten dengan Material Design 3.
* Aplikasi stabil pada emulator maupun perangkat fisik.

---

## ✅ Kesimpulan

Berdasarkan hasil **Praktikum 6 – Menggunakan Database Lokal (SQLite + Room ORM)**, dapat disimpulkan bahwa **Room Database** merupakan solusi yang efektif untuk pengelolaan data lokal pada aplikasi Android. Dengan integrasi **Jetpack Compose, Navigation Compose, Room, dan DataStore**, aplikasi **Minda** mampu menyajikan sistem jurnal yang **modern, aman, modular, dan stabil** sesuai praktik pengembangan Android terkini.

---

## 📌 Catatan

* Praktikum dikerjakan sesuai modul pembelajaran.
* Seluruh fitur utama berhasil diimplementasikan.
* Dokumentasi dilakukan secara sistematis melalui screenshot.
* Proyek ini dibuat untuk keperluan pembelajaran.

---

📝 *Disusun oleh Husna Norgina (230104040056) — Praktikum 6 Pemrograman Mobile*

