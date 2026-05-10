package com.example.belanjaonline

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi View
        val etNamaPembeli = findViewById<EditText>(R.id.etNamaPembeli)
        val etNamaProduk = findViewById<EditText>(R.id.etNamaProduk)
        val etHarga = findViewById<EditText>(R.id.etHarga)
        val etJumlah = findViewById<EditText>(R.id.etJumlah)
        val spKota = findViewById<Spinner>(R.id.spKota)
        val rgMember = findViewById<RadioGroup>(R.id.rgMember)
        val btnHitung = findViewById<Button>(R.id.btnHitung)
        val btnReset = findViewById<Button>(R.id.btnReset)
        val tvHasil = findViewById<TextView>(R.id.tvHasil)

        btnHitung.setOnClickListener {
            val nama = etNamaPembeli.text.toString()
            val produk = etNamaProduk.text.toString()
            val hargaStr = etHarga.text.toString()
            val jumlahStr = etJumlah.text.toString()

            if (nama.isEmpty() || produk.isEmpty() || hargaStr.isEmpty() || jumlahStr.isEmpty()) {
                Toast.makeText(this, "Harap lengkapi semua data!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val harga = hargaStr.toDouble()
            val jumlah = jumlahStr.toInt()
            val subtotal = harga * jumlah

            // Logika Ongkos Kirim
            val kota = spKota.selectedItem.toString()

            val ongkir = when (kota) {
                "Sukabumi (Gratis)" -> 0.0
                "Jakarta (Rp 15.000)" -> 15000.0
                "Bandung (Rp 10.000)" -> 10000.0
                else -> 50000.0
            }

            // Logika Diskon & Promo
            val selectedMemberId = rgMember.checkedRadioButtonId
            var diskonPersen = 0.0
            var statusPromo = "Bukan Member (Tidak ada promo)"

            when (selectedMemberId) {
                R.id.rbGold -> {
                    diskonPersen = 0.15 // 15%
                    statusPromo = "Promo Member Gold (15%)"
                }
                R.id.rbSilver -> {
                    diskonPersen = 0.10 // 10%
                    statusPromo = "Promo Member Silver (10%)"
                }
            }

            val nilaiDiskon = subtotal * diskonPersen
            val totalBayar = (subtotal - nilaiDiskon) + ongkir

            // Output Hasil
            val ringkasan = """
                ==== STRUK BELANJA ====
                Nama Pembeli   : $nama
                Nama Produk    : $produk
                Harga Satuan   : Rp ${String.format("%,.0f", harga)}
                Jumlah Barang  : $jumlah
                -----------------------
                Subtotal       : Rp ${String.format("%,.0f", subtotal)}
                Diskon         : Rp ${String.format("%,.0f", nilaiDiskon)}
                Ongkos Kirim   : Rp ${String.format("%,.0f", ongkir)}
                -----------------------
                TOTAL BAYAR    : Rp ${String.format("%,.0f", totalBayar)}
                
                Keterangan: $statusPromo
            """.trimIndent()

            tvHasil.text = ringkasan
        }

        btnReset.setOnClickListener {
            etNamaPembeli.text.clear()
            etNamaProduk.text.clear()
            etHarga.text.clear()
            etJumlah.text.clear()
            spKota.setSelection(0)
            rgMember.check(R.id.rbBiasa)
            tvHasil.text = "Ringkasan Transaksi..."
        }
    }
}