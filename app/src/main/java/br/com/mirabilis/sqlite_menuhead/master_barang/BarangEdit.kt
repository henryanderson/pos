package br.com.mirabilis.sqlite_menuhead.master_barang

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.mirabilis.sqlite_menuhead.R
import br.com.mirabilis.sqlite_menuhead.db.UsersDBHelper
import br.com.mirabilis.sqlite_menuhead.master_kategori.mKategoriModel
import kotlinx.android.synthetic.main.act_tambah_barang.*
import java.util.ArrayList

class BarangEdit : AppCompatActivity() {


    private var btnStore: Button? = null
    private var etqrid: EditText? = null
    private var etbarang: EditText? = null
    private var ethargajual: EditText? = null

    private var barangModel: BarangModel? = null
    lateinit var usersDBHelper: UsersDBHelper

    private var idkategori:String=""

    private var vnonstok: String = "0"
    private var vpromo: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_tambah_barang)
//
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
//
        supportActionBar!!.setTitle(Html.fromHtml("<font color='#808080'>" + "Barang Edit" + "</font>"))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//
        val intent = intent
        barangModel = intent.getSerializableExtra("barang") as BarangModel

        var id = barangModel!!.id


        usersDBHelper = UsersDBHelper(this)


        etqrid = findViewById(R.id.qrid) as EditText
        etbarang = findViewById(R.id.barang) as EditText
        ethargajual = findViewById(R.id.harga_jual) as EditText
        btnStore = findViewById(R.id.btnsimpan) as Button

        etqrid!!.setText(barangModel!!.qrid)
        etbarang!!.setText(barangModel!!.barang)
        ethargajual!!.setText(barangModel!!.harga_jual)


        promo!!.setText(barangModel!!.promo)
        harga_grosir!!.setText(barangModel!!.harga_grosir)
        harga_grosir_promo!!.setText(barangModel!!.harga_grosir_promo)
        harga_beli!!.setText(barangModel!!.harga_beli)
        satuan!!.setText(barangModel!!.satuan)
        tax!!.setText(barangModel!!.tax)

        if(barangModel!!.nonstok == "1"){
            nonstok.isChecked = true
            harga_beli!!.isEnabled = true
        }else{
            harga_beli!!.isEnabled = false
        }

        catatan!!.setText(barangModel!!.catatan)


        stok!!.isEnabled = false
        etqrid!!.isEnabled = false
        btnscan!!.isEnabled = false

        var id_kategori : String = barangModel!!.id_kategori
        var nama_kategori : String = usersDBHelper!!.cariKategori(id_kategori.toInt()).toString()


        var arrm = ArrayList<mKategoriModel>()
        arrm = usersDBHelper.readAllKategoris()
        val adapter = ArrayAdapter<mKategoriModel>(this,
            android.R.layout.simple_spinner_item, arrm
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        kategori.setAdapter(adapter)

        val ambilPosisi : Int =  adapter.getPosition(
            mKategoriModel(
                id_kategori.toInt(),
                nama_kategori
            )
        )
        //Toast.makeText(this, (ambilPosisi).toString(), Toast.LENGTH_LONG).show()
        kategori.setSelection(ambilPosisi)

        kategori.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val arritem = parent.selectedItem as mKategoriModel
                idkategori = arritem?.id.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        })


        nonstok.setOnCheckedChangeListener{buttonView, isChecked ->
            if(isChecked == true){
                vnonstok = "1"
                harga_beli.isEnabled = true
            }else{
                vnonstok = ""
                harga_beli.setText("")
                harga_beli.isEnabled = false
            }
        }


        btnStore!!.setOnClickListener {

            usersDBHelper.updateBarang(id,idkategori,
                etqrid!!.text.toString(), etbarang!!.text.toString(), ethargajual!!.text.toString(),
                vpromo,
                harga_grosir!!.text.toString(),
                harga_grosir_promo!!.text.toString(),
                harga_beli!!.text.toString(),
                "0",
                satuan!!.text.toString(),
                tax!!.text.toString(),
                vnonstok,
                catatan!!.text.toString()
            )

            var msg : String = etbarang!!.text.toString()
            Toast.makeText(this@BarangEdit, "Barang "+msg+" berhasil dirubah!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this@BarangEdit, BarangMain::class.java)
            startActivity(intent)

        }


        btninfostok!!.setOnClickListener {
            var msg = "Anda dapat menambah atau mengurangi jumlah stok melalui menu Transaksi " +
                    "Penerimaan atau menu Transaksi Pengeluaran pada layar utama"
            showDialog("Stok Akhir",msg)
        }

        btninfopromo!!.setOnClickListener {
            var msg = "Isi 0 jika tidak ada promosi.\n" +
                    "Jika tidak 0, harga ini akan digunakan sebagai harga jual.\n"
            showDialog("Harga Promo",msg)
        }

        btninfohb!!.setOnClickListener {
            var msg = "Transaksi yang akan merubah nilai harga pokok adalah transaksi Penerimaan Barang.\n" +
                    "Apabila transaksi penerimaan barang dihapus, maka harga pokok akan dihitung kembali."
            showDialog("Harga Beli",msg)
        }



    }



    private fun showDialog(title : String, msg : String){
        val builder = AlertDialog.Builder(this@BarangEdit)

        builder.setTitle(title)
        builder.setMessage(msg)

        builder.setNeutralButton("Tutup"){_,_ ->
            //Toast.makeText(applicationContext,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()

    }


}