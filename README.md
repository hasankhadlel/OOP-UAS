# 🏥 Sistem Manajemen Klinik

Aplikasi web manajemen klinik berbasis **Spring Boot** yang dibangun sebagai tugas **Ujian Akhir Semester (UAS)** mata kuliah Pemrograman Berorientasi Objek (OOP). Sistem ini memungkinkan pengelolaan data dokter, pasien, jadwal janji temu, dan rekam medis secara terintegrasi.

---

## 📋 Fitur Utama

- **Autentikasi & Otorisasi** — Login, register, dan manajemen sesi menggunakan Spring Security
- **Manajemen Dokter** — Tambah, edit, hapus, dan lihat data dokter beserta spesialisasi
- **Manajemen Pasien** — Pengelolaan data pasien termasuk informasi pribadi dan kontak
- **Jadwal Janji Temu (Appointment)** — Pembuatan dan pemantauan status janji temu antara pasien dan dokter
- **Rekam Medis** — Pencatatan keluhan, diagnosis, dan penanganan tiap pasien
- **Dashboard** — Tampilan ringkasan data klinik secara keseluruhan

---

## 🛠️ Teknologi yang Digunakan

| Teknologi | Versi | Keterangan |
|---|---|---|
| Java | 17 | Bahasa pemrograman utama |
| Spring Boot | 3.3.0 | Framework backend |
| Spring Security | - | Autentikasi & otorisasi |
| Spring Data JPA | - | ORM dan akses database |
| Thymeleaf | - | Template engine untuk tampilan HTML |
| MySQL | - | Database relasional |
| Maven | - | Build tool & dependency management |

---

## 📁 Struktur Proyek

```
OOP-UAS/
├── src/
│   └── main/
│       ├── java/com/example/clinic/
│       │   ├── ClinicApplication.java       # Entry point aplikasi
│       │   ├── config/
│       │   │   ├── SecurityConfig.java      # Konfigurasi Spring Security
│       │   │   └── CustomUserDetailsService.java
│       │   ├── controller/
│       │   │   ├── AuthController.java      # Login & Register
│       │   │   ├── DashboardController.java
│       │   │   ├── DoctorController.java
│       │   │   ├── PatientController.java
│       │   │   ├── AppointmentController.java
│       │   │   └── MedicalRecordController.java
│       │   ├── entity/
│       │   │   ├── User.java
│       │   │   ├── Doctor.java
│       │   │   ├── Patient.java
│       │   │   ├── Appointment.java
│       │   │   └── MedicalRecord.java
│       │   └── repository/
│       │       ├── UserRepository.java
│       │       ├── DoctorRepository.java
│       │       ├── PatientRepository.java
│       │       ├── AppointmentRepository.java
│       │       └── MedicalRecordRepository.java
│       └── resources/
│           ├── templates/                   # Halaman Thymeleaf
│           │   ├── login.html
│           │   ├── register.html
│           │   ├── dashboard.html
│           │   ├── doctors.html
│           │   ├── patients.html
│           │   ├── appointments.html
│           │   └── medical-records.html
│           └── application.properties
├── pom.xml
```

---

## ⚙️ Cara Menjalankan

### Prasyarat

Pastikan sudah terinstal:
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/)
- [MySQL](https://www.mysql.com/)

### Langkah Instalasi

**1. Clone repositori**
```bash
git clone https://github.com/hasankhadlel/OOP-UAS.git
cd OOP-UAS
```

**2. Buat database MySQL**
```sql
CREATE DATABASE clinic_db;
```

**3. Konfigurasi environment variable**

Aplikasi ini menggunakan environment variable untuk koneksi database. Set variabel berikut sesuai konfigurasi MySQL kamu:

```bash
export MYSQLHOST=localhost
export MYSQLPORT=3306
export MYSQLDATABASE=clinic_db
export MYSQLUSER=root
export MYSQLPASSWORD=password_kamu
```

> Atau bisa langsung edit file `src/main/resources/application.properties` dengan nilai yang sesuai.

**4. Build dan jalankan aplikasi**
```bash
mvn spring-boot:run
```

**5. Buka di browser**
```
http://localhost:8080
```

---

## 🗃️ Model Data

### Doctor
| Field | Tipe | Keterangan |
|---|---|---|
| id | Long | Primary key |
| user | User | Relasi ke akun pengguna |
| fullName | String | Nama lengkap dokter |
| specialization | String | Spesialisasi dokter |
| phone | String | Nomor telepon |

### Patient
| Field | Tipe | Keterangan |
|---|---|---|
| id | Long | Primary key |
| user | User | Relasi ke akun pengguna |
| fullName | String | Nama lengkap pasien |
| gender | String | Jenis kelamin |
| phone | String | Nomor telepon |
| address | String | Alamat |

### Appointment
| Field | Tipe | Keterangan |
|---|---|---|
| id | Long | Primary key |
| patient | Patient | Pasien yang membuat janji |
| doctor | Doctor | Dokter yang dituju |
| appointmentDate | LocalDateTime | Tanggal & waktu janji |
| status | String | Status janji temu |
| complaint | String | Keluhan pasien |

### MedicalRecord
| Field | Tipe | Keterangan |
|---|---|---|
| id | Long | Primary key |
| appointment | Appointment | Janji temu terkait |
| complaint | Text | Keluhan pasien |
| diagnosis | Text | Diagnosis dokter |
| treatment | Text | Penanganan/resep |

---

## 🔐 Keamanan

Aplikasi menggunakan **Spring Security** dengan fitur:
- Enkripsi password menggunakan BCrypt
- Sesi berbasis form login
- Proteksi endpoint berdasarkan role pengguna
- Integrasi dengan Thymeleaf Security untuk tampilan berbasis hak akses

---

## 👨‍💻 Konsep OOP yang Diterapkan

- **Encapsulation** — Semua field entity bersifat private dengan getter/setter
- **Inheritance** — Penggunaan hierarki kelas dan interface Spring
- **Polymorphism** — Controller dan Repository menggunakan abstraksi Spring
- **Abstraction** — Interface JpaRepository sebagai kontrak akses data

---

## 📄 Lisensi

Proyek ini dibuat untuk keperluan akademik (Tugas UAS). Silakan gunakan dan kembangkan sesuai kebutuhan.

---

> Dibuat dengan ☕ Java & Spring Boot
