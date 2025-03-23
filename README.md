# TP5DPBO2025C1
Tugas Praktikum 5 Dasar Pemrograman Berbasis Objek (DPBO)

# Janji
Saya Faisal Nur Qolbi dengan NIM 2311399 mengerjakan Tugas Praktikum 5 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

# Desain Program
Berdasarkan program TP4DPBO2025C1, program ini telah dikembangkan dengan menambahkan integrasi database MySQL melalui Java Database Connectivity (JDBC). Perubahan utama adalah penggantian penyimpanan data dari ArrayList statis menjadi database relasional, yang memungkinkan data disimpan secara permanen dan dapat diakses kembali setiap kali program dijalankan.

## Class
1. **Mahasiswa** - Model data yang berisi informasi mahasiswa
2. **Menu** - GUI untuk mengelola data mahasiswa (extends JFrame dari Form Java Swing GUI)
3. **Database** - Kelas yang menangani koneksi dan operasi ke database MySQL

## Atribut
### Class Mahasiswa
- nim (String)
- nama (String)
- jenisKelamin (String)
- nilai (String) - A, B, C, D, atau E

### Class Menu
- Komponen GUI (JTextField, JRadioButton, JComboBox, JTable, dll)
- ArrayList<Mahasiswa> untuk menyimpan data sementara
- Database database - objek untuk mengelola koneksi database
- selectedIndex (int) - untuk melacak baris yang dipilih di tabel

### Class Database
- Connection connection - koneksi ke database MySQL
- Statement statement - objek untuk eksekusi query SQL

## Classes's Methods
### Class Database
- Database() - konstruktor yang membuat koneksi ke database MySQL
- selectQuery(String sql) - menjalankan query SELECT dan mengembalikan ResultSet
- insertUpdateDeleteQuery(String sql) - menjalankan query INSERT, UPDATE, DELETE dan mengembalikan jumlah baris yang terpengaruh
- getStatement() - getter untuk objek Statement

### Class Menu
- DefaultTableModel setTable() - mengatur model tabel dengan data dari database
- insertData() - menambah data mahasiswa ke database dengan validasi input dan NIM duplikat
- updateData() - mengubah data mahasiswa di database dengan validasi input dan NIM duplikat
- deleteData() - menghapus data mahasiswa dari database
- clearForm() - mengosongkan form input
- getSelectedNilai() - mendapatkan nilai dari radio button yang dipilih
- setSelectedNilaiRadioButton() - mengatur radio button berdasarkan nilai

# Alur Program
1. Program dimulai dengan membuat koneksi ke database MySQL melalui class Database
2. Method setTable() dipanggil untuk mengambil data dari database dan menampilkannya di tabel
3. User dapat:
   - **Menambah data**: isi form → klik `Add` → validasi input kosong → validasi NIM duplikat → insertData() → tambah ke database → refresh tabel
   - **Mengubah data**: pilih/klik data dari tabel → data muncul di form → ubah → klik `Update` → validasi input kosong → validasi NIM duplikat (jika NIM diubah) → updateData() → update di database → refresh tabel
   - **Menghapus data**: pilih/klik data dari tabel → klik `Delete` → konfirmasi → deleteData() → hapus dari database → refresh tabel
   - **Membatalkan input**: klik `Cancel` → clearForm()
4. Setiap operasi CRUD terhubung langsung ke database:
   - Operasi READ menggunakan database.selectQuery()
   - Operasi CREATE, UPDATE, DELETE menggunakan database.insertUpdateDeleteQuery()
5. Validasi ditambahkan:
   - Semua field harus diisi (tidak boleh kosong)
   - NIM tidak boleh duplikat saat insert atau update
6. Dialog/prompt error akan ditampilkan jika terjadi kesalahan validasi

# Dokumentasi
<Soon>
