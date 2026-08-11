# Kasir Digital Android - POS System

**Sistem Kasir Digital (Point of Sale) berbasis Android menggunakan Java dan XML**

## 📋 Fitur Lengkap

### ✅ Dashboard
- Total penjualan hari ini
- Jumlah transaksi hari ini
- Produk terlaris (extensible)
- Navigation menu

### ✅ Penjualan
- Browse produk dalam format horizontal scroll
- Cari produk
- Tambah ke keranjang dengan harga real-time
- Adjust quantity (tambah/kurang)
- Hapus item dari keranjang
- Hitung total otomatis
- Proses pembayaran dengan kembalian otomatis
- Generate nomor transaksi unik (TRX-yyyyMMddHHmmss)
- Simpan transaksi ke database
- Update stok produk otomatis
- Display struk transaksi

### ✅ Inventori
- Daftar semua produk
- Tambah produk baru
  - Nama
  - Harga
  - Stok
  - Kategori
  - Barcode (optional)
- Edit produk (update nama, harga, stok)
- Hapus produk dengan konfirmasi
- View lengkap produk details

### ✅ Laporan
- Laporan penjualan harian
- Total penjualan hari ini
- Total jumlah transaksi
- Rata-rata per transaksi
- Daftar detail transaksi dengan filter tanggal
- Detail setiap transaksi (nomor, tanggal, kasir, items, total, diskon, bayar, kembalian)

### ✅ User Management
- Login dengan username & password
- Default users:
  - Username: `admin`, Password: `admin` (Role: Admin)
  - Username: `kasir`, Password: `kasir123` (Role: Kasir)
- Session management
- Logout

### ✅ Database
- SQLite lokal
- Tabel: Produk, Transaksi, Pengguna
- Auto-increment ID
- Unique constraints untuk username & barcode

---

## 📱 Teknologi Stack

| Aspek | Teknologi |
|-------|----------|
| **Platform** | Android |
| **Bahasa** | Java |
| **Database** | SQLite |
| **UI** | XML Layouts |
| **API Level** | Target 26, Min 21 |
| **IDE** | Aide atau Android Studio |

---

## 🚀 Cara Setup

### 1. Clone Repository
```bash
git clone https://github.com/defouwfabiano-ops/kasir-digital-android.git
cd kasir-digital-android
```

### 2. Buka di Aide
- Buka Aide
- Pilih "Open Project"
- Navigate ke folder project
- Tunggu Gradle sync

### 3. Build & Run
- Klik **Run** atau tekan `Ctrl+R`
- Pilih emulator atau device
- Tunggu compile & install

---

## 🔐 Default Login

| Username | Password | Role |
|----------|----------|------|
| admin | admin | Admin |
| kasir | kasir123 | Kasir |

---

## 📁 Struktur Project

```
kasir-digital-android/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/kasirdigital/
│   │   │   │   ├── activities/
│   │   │   │   │   ├── LoginActivity.java
│   │   │   │   │   ├── MainActivity.java
│   │   │   │   │   ├── PenjualanActivity.java
│   │   │   │   │   ├── InventoriActivity.java
│   │   │   │   │   ├── LaporanActivity.java
│   │   │   │   │   └── DetailTransaksiActivity.java
│   │   │   │   ├── models/
│   │   │   │   │   ├── Produk.java
│   │   │   │   │   ├── Transaksi.java
│   │   │   │   │   ├── KeranjangItem.java
│   │   │   │   │   └── Pengguna.java
│   │   │   │   ├── database/
│   │   │   │   │   └── DatabaseHelper.java
│   │   │   │   ├── adapters/
│   │   │   │   │   ├── ProdukAdapter.java
│   │   │   │   │   ├── KeranjangAdapter.java
│   │   │   │   │   └── TransaksiAdapter.java
│   │   │   │   └── helpers/
│   │   │   │       └── SessionManager.java
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_login.xml
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   ├── activity_penjualan.xml
│   │   │   │   │   ├── activity_inventori.xml
│   │   │   │   │   ├── activity_laporan.xml
│   │   │   │   │   ├── activity_detail_transaksi.xml
│   │   │   │   │   ├── item_produk.xml
│   │   │   │   │   ├── item_keranjang.xml
│   │   │   │   │   └── item_transaksi.xml
│   │   │   │   ├── values/
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   ├── dimens.xml
│   │   │   │   │   └── styles.xml
│   │   │   │   └── drawable/
│   │   │   │       ├── btn_bg_primary.xml
│   │   │   │       ├── btn_bg_secondary.xml
│   │   │   │       ├── btn_bg_info.xml
│   │   │   │       ├── btn_bg_danger.xml
│   │   │   │       ├── edit_text_bg.xml
│   │   │   │       └── card_bg.xml
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   ├── build.gradle
│   └── proguard-rules.pro
├── build.gradle
├── settings.gradle
├── .gitignore
└── README.md
```

---

## 🎯 Fitur yang Bisa Dikembangkan

- [ ] Scan barcode dengan camera
- [ ] Export laporan ke PDF/Excel
- [ ] Multi-outlet support
- [ ] Cloud sync
- [ ] Analytics dashboard
- [ ] Receipt printer integration
- [ ] Payment gateway integration (Midtrans, etc)
- [ ] QR code payment
- [ ] Inventory notifications
- [ ] Advanced reporting (monthly, yearly)

---

## 📞 Support

Jika ada pertanyaan atau issue, silakan buka issue di repository ini.

---

**Dibuat dengan ❤️ untuk sistem kasir yang lebih baik**

*Version: 1.0.0*
*Last Updated: 2024*
