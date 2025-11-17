# 📱 Praktikum MP #6 — Minda Application (Room + DataStore + Navigation)

Aplikasi **Minda** dikembangkan pada Praktikum MP #6 dengan fokus pada penggunaan **database lokal SQLite melalui Room ORM**, penyimpanan preferensi menggunakan **DataStore**, serta navigasi modern berbasis **Jetpack Compose Navigation**.
Proyek ini mempraktikkan proses **CRUD data jurnal**, onboarding dinamis, penyimpanan nama pengguna, dan UI modern menggunakan **Material 3**, sehingga aplikasi berjalan stabil, modular, dan responsif.

---

## 🧑‍🎓 Informasi Mahasiswa

| Informasi         | Data                                                  |
| ----------------- |-------------------------------------------------------|
| Mata Kuliah       | Pemrograman Mobile                                    |
| Dosen Pengampu    | Muhayat, M.IT                                         |
| Praktikum         | P6 – Menggunakan Database Lokal (SQLite – Room ORM)   |
| Nama Mahasiswa    | Husna Norgina                                         |
| NIM               | 230104040056                                          |
| Kelas             | TI23B                                                 |
| Link Repo Github  | https://github.com/husna-norgina/p6minda_230104040056 |
| Tanggal Praktikum | 12-11-2025                                            |

---

## 🎯 Tujuan Praktikum (Mengacu pada Tujuan Resmi Dosen)

1. Mahasiswa mampu membuat aplikasi Android berbasis Kotlin + Jetpack Compose dengan arsitektur layar modern (tanpa XML legacy).
2. Mahasiswa memahami cara menyimpan data lokal dan offline menggunakan Room (SQLite)untuk kebutuhan CRUD.
3. Mahasiswa dapat menghubungkan UI dengan database melalui Repository agar UI dapat membaca, menambah, mengubah, dan menghapus data jurnal.
4. Mahasiswa memahami penyimpanan preferensi pengguna menggunakan DataStore (menyimpan nama user secara privat).
5. Mahasiswa mampu membuat alur onboarding multi-step (welcome → input nama → konfirmasi → mulai) dan start screen dinamis.
6. Mahasiswa dapat menerapkan Navigation Composemuntuk berpindah antar-screen: Home → Detail → Edit → kembali ke Home.
7. Mahasiswa mampu membuat UI responsif dan personal, termasuk sapaan dinamis (“Hi, <nama>”).
8. Mahasiswa menerapkan konsep privacy-by-design: seluruh data jurnal hanya disimpan di perangkat.
9. Mahasiswa melatih pembuatan UI modern (AppBar, Back/Edit/Delete, list item, detail entry, dll).
10. Mahasiswa mampu melakukan debug build secara nyata (Windows + JDK 17 + KAPT/KSP Room), memahami bahwa konfigurasi build adalah bagian penting dari rekayasa perangkat lunak.

---

## 🧰 Tools & Environment

### 💻 Software

* Android Studio **Narwhal | 2025.1.1**
* **JDK 17 (JetBrains Runtime)**
* Android SDK
    * compileSdk = 34
    * targetSdk = 34
    * minSdk = 24
* GitHub

### 📚 Library

* Jetpack Compose + Material 3
* Room ORM
* DataStore Preferences
* Navigation Compose
* Lifecycle ViewModel
* Coroutines (Flow)

---

## 🗂 Struktur Proyek

```
app/
|--- src/
     |--- main/
          |--- AndroidManifest.xml
          |--- java/
               |--- id/
                    |--- antasari/
                         |--- p6minda_230104040056/
                              |--- MainActivity.kt
                              |--- MindaTheme.kt
                              |--- data/
                                   |--- DiaryDao.kt
                                   |--- DiaryEntry.kt
                                   |--- DiaryRepository.kt
                                   |--- MindaDatabase.kt
                                   |--- UserPrefsRepository.kt
                              |--- ui/
                                   |--- BottomNav.kt
                                   |--- EditEntryScreen.kt
                                   |--- ExtraScreens.kt
                                   |--- HomeScreen.kt
                                   |--- NewEntryScreen.kt
                                   |--- NoteDetailScreen.kt
                                   |--- OnboardingScreens.kt
                                   |--- TestRoomScreen.kt
                                   |--- calendar/
                                        |--- CalendarScreen.kt
                                        |--- CalendarViewModel.kt
                                   |--- navigation/
                                        |--- AppNavHost.kt
                                        |--- Routes.kt
                                   |--- theme/
                                        |--- Color.kt
                                        |--- Theme.kt
                                        |--- Type.kt
                              |--- util/
                                   |--- DateFormatter.kt
          |--- res/
               |--- drawable/
                    |--- banner_diary.jpg
                    |--- ic_launcher_background.xml
                    |--- ic_launcher_foreground.xml
               |--- values/
                    |--- colors.xml
                    |--- ic_launcher_background.xml
                    |--- strings.xml
                    |--- themes.xml
               |--- xml/
                    |--- backup_rules.xml
                    |--- data_extraction_rules.xml
```

---

## ✨ Fitur Utama

* Onboarding multi-step (welcome → input nama → konfirmasi → start)
* Penyimpanan nama user menggunakan DataStore Preferences
* CRUD Jurnal lengkap menggunakan Room (add, read, update, delete)
* UI modern dengan Jetpack Compose Material 3
* Navigation Compose untuk perpindahan screen
* Room Database offline-only
* Calendar View untuk melihat diary berdasarkan tanggal
* State management dengan ViewModel + Flow

---

## 📸 Evidence

Seluruh screenshot dan file laporan terdapat pada folder:
📁 **/evidence**

---

## 📌 Kesimpulan

Dari praktikum ini dapat disimpulkan bahwa:

1. Room ORM mempermudah pengelolaan database lokal dengan cara yang modern dan aman.
2. Arsitektur berlapis (Entity → DAO → Repository → ViewModel) membuat aplikasi lebih rapi, modular, dan mudah dikembangkan.
3. Implementasi CRUD jurnal berjalan responsif berkat kombinasi Flow + ViewModel.
4. DataStore mendukung penyimpanan preferensi secara aman, privat, dan persisten.
5. Navigation Compose memudahkan alur navigasi antar-screen tanpa fragment/XML.
6. UI menggunakan Jetpack Compose Material 3 tampak modern, elegan, dan konsisten.
7. Praktikum ini menguatkan pemahaman tentang Pengembangan aplikasi Android modern, manajemen state, navigasi, dan persistensi data lokal.

---

📅 _Dibuat oleh Husna Norgina – Praktikum Pemrograman Mobile P6 (Minda Application)_

---
