public class Mahasiswa {
    private String nim;
    private String nama;
    private String jenisKelamin;
    private String nilai; // Atribut tambahan

    // Constructor
    public Mahasiswa(String nim, String nama, String jenisKelamin, String nilai) {
        this.nim = nim;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.nilai = nilai;
    }

    // Getter dan Setter (udah ga perlu sebenernya karena pake JDBC jadi udah ada dari MySQL tapi ini buat formalitas aja)
    public String getNim() {
        return this.nim;
    }

    public String getNama() {
        return this.nama;
    }

    public String getJenisKelamin() {
        return this.jenisKelamin;
    }

    public String getNilai() {
        return this.nilai;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }
}
