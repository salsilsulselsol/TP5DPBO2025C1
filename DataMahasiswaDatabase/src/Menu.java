import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.sql.*;

public class Menu extends JFrame {
    public static void main(String[] args) {
        // buat objek window
        Menu window = new Menu();

        // atur ukuran window
        window.setSize(480, 560);
        // letakkan window di tengah layar
        window.setLocationRelativeTo(null);
        // isi window
        window.setContentPane(window.mainPanel);
        // ubah warna background
        window.getContentPane().setBackground(Color.white);
        // tampilkan window
        window.setVisible(true);
        // agar program ikut berhenti saat window ditutup
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua mahasiswa
    private ArrayList<Mahasiswa> listMahasiswa;
    // objek database
    private Database database;

    // Deklarasi atribut form
    private JPanel mainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JComboBox<String> jenisKelaminComboBox;
    private JButton addUpdateButton;
    private JButton deleteButton;
    private JButton cancelButton;
    private JTable mahasiswaTable;

    // Deklarasi label yang ada di form
    private JLabel titleLabel;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;
    private JLabel nilaiLabel;

    // Radio button untuk nilai
    private JRadioButton nilaiARadio;
    private JRadioButton nilaiBRadio;
    private JRadioButton nilaiCRadio;
    private JRadioButton nilaiDRadio;
    private JRadioButton nilaiERadio;
    private ButtonGroup nilaiButtonGroup;

    public Menu() {
        // inisialisasi listMahasiswa
        listMahasiswa = new ArrayList<>();

        // buat objek database
        database = new Database();

        // Inisialisasi ButtonGroup untuk nilai radio buttons
        nilaiButtonGroup = new ButtonGroup();
        nilaiButtonGroup.add(nilaiARadio);
        nilaiButtonGroup.add(nilaiBRadio);
        nilaiButtonGroup.add(nilaiCRadio);
        nilaiButtonGroup.add(nilaiDRadio);
        nilaiButtonGroup.add(nilaiERadio);

        // isi tabel mahasiswa
        mahasiswaTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] jenisKelaminData = {"", "Laki-laki", "Perempuan"};
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel<>(jenisKelaminData));

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex >= 0) {
                    // Tambah confirmation prompt sebelum delete
                    int confirm = JOptionPane.showConfirmDialog(null,
                            "Apakah Anda yakin ingin menghapus data ini?",
                            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        deleteData();
                    }
                }
            }
        });

        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // saat salah satu baris tabel ditekan
        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris yang diklik
                selectedIndex = mahasiswaTable.getSelectedRow();

                // simpan value textfield dan combo box
                String selectedNim = mahasiswaTable.getModel().getValueAt(selectedIndex, 1).toString();
                String selectedNama = mahasiswaTable.getModel().getValueAt(selectedIndex, 2).toString();
                String selectedJenisKelamin = mahasiswaTable.getModel().getValueAt(selectedIndex, 3).toString();
                String selectedNilai = mahasiswaTable.getModel().getValueAt(selectedIndex, 4).toString();

                // ubah isi textfield dan combo box
                nimField.setText(selectedNim);
                namaField.setText(selectedNama);
                jenisKelaminComboBox.setSelectedItem(selectedJenisKelamin);

                // Set radio button yang sesuai
                setSelectedNilaiRadioButton(selectedNilai);

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");
                // tampilkan button delete
                deleteButton.setVisible(true);
            }
        });
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] column = {"No", "NIM", "Nama", "Jenis Kelamin", "Nilai"};

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel temp = new DefaultTableModel(null, column);

        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa");

            int i = 0;
            while (resultSet.next()) {
                Object[] row = new Object[5]; // ubah menjadi 5 kolom untuk menampung nilai

                row[0] = i + 1;
                row[1] = resultSet.getString("nim");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("jenis_kelamin");
                row[4] = resultSet.getString("nilai"); // tambahkan nilai

                temp.addRow(row);
                i++;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }

        return temp;
    }

    public void insertData() {
        // ambil value dari textfield dan combobox
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String nilai = getSelectedNilai();

        // Validasi isian form
        if (nim.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || nilai.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cek duplikat NIM
        try {
            ResultSet rs = database.selectQuery("SELECT * FROM mahasiswa WHERE nim = '" + nim + "'");
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "NIM sudah terdaftar!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tambahkan data ke database
        String sql = "INSERT INTO mahasiswa VALUES (null, '" + nim + "', '" + nama + "', '" + jenisKelamin + "', '" + nilai + "')";
        database.insertUpdateDeleteQuery(sql);

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Insert berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
    }

    public void updateData() {
        // ambil data dari form
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String nilai = getSelectedNilai();

        // Validasi sederhana
        if (nim.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || nilai.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Ambil NIM lama dari tabel
        String oldNim = mahasiswaTable.getValueAt(selectedIndex, 1).toString();

        // Cek duplikat NIM jika NIM diubah
        if (!oldNim.equals(nim)) {
            try {
                ResultSet rs = database.selectQuery("SELECT * FROM mahasiswa WHERE nim = '" + nim + "'");
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "NIM sudah terdaftar!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Update data di database
        String sql = "UPDATE mahasiswa SET nim = '" + nim + "', nama = '" + nama +
                "', jenis_kelamin = '" + jenisKelamin + "', nilai = '" + nilai +
                "' WHERE nim = '" + oldNim + "'";
        database.insertUpdateDeleteQuery(sql);

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Update Berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil diubah");
    }

    public void deleteData() {
        // Ambil NIM dari data yang dipilih
        String nim = mahasiswaTable.getValueAt(selectedIndex, 1).toString();

        // Hapus data dari database
        String sql = "DELETE FROM mahasiswa WHERE nim = '" + nim + "'";
        database.insertUpdateDeleteQuery(sql);

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Delete berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
    }

    public void clearForm() {
        // kosongkan semua textfield dan combo box
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedItem("");
        nilaiButtonGroup.clearSelection(); // Clear radio button selection

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");
        // sembunyikan button delete
        deleteButton.setVisible(false);
        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }

    // Method untuk mendapatkan nilai dari radio button yang dipilih
    private String getSelectedNilai() {
        for (Enumeration<AbstractButton> buttons = nilaiButtonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return ""; // Default jika tidak ada yang dipilih
    }

    // Method untuk mengatur radio button sesuai nilai yang dipilih
    private void setSelectedNilaiRadioButton(String nilai) {
        switch (nilai) {
            case "A":
                nilaiARadio.setSelected(true);
                break;
            case "B":
                nilaiBRadio.setSelected(true);
                break;
            case "C":
                nilaiCRadio.setSelected(true);
                break;
            case "D":
                nilaiDRadio.setSelected(true);
                break;
            case "E":
                nilaiERadio.setSelected(true);
                break;
            default:
                nilaiButtonGroup.clearSelection(); // Clear selection jika tidak ada yang cocok
                break;
        }
    }
}