package br.com.mirabilis.sqlite_menuhead.master_barang

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.mirabilis.sqlite_menuhead.R
import br.com.mirabilis.sqlite_menuhead.db.UsersDBHelper
import br.com.mirabilis.sqlite_menuhead.master_kategori.mKategoriModel
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.act_tambah_barang.*
import java.util.ArrayList

class BarangAdd : AppCompatActivity() {


    private var btnStore: Button? = null
    private var etqrid: EditText? = null
    private var etbarang: EditText? = null
    private var ethargajual: EditText? = null
    private var idkategori:String=""
    private var etstok: EditText? = null
    private var promo: EditText? = null


    lateinit var usersDBHelper: UsersDBHelper

    var scannedResult: String = ""

    private var vnonstok: String = "0"
    private var vpromo: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_tambah_barang)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        //supportActionBar!!.setTitle("Master Barang")
        supportActionBar!!.setTitle(Html.fromHtml("<font color='#808080'>"+"Tambah Barang"+"</font>"))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        usersDBHelper = UsersDBHelper(this)

        btnStore = findViewById(R.id.btnsimpan) as Button
        etqrid = findViewById(R.id.qrid) as EditText
        etbarang = findViewById(R.id.barang) as EditText
        ethargajual = findViewById(R.id.harga_jual) as EditText
        etstok = findViewById(R.id.stok) as EditText
        promo = findViewById(R.id.promo) as EditText

        etstok!!.isEnabled = false
        harga_beli.isEnabled = false


        var arrm = ArrayList<mKategoriModel>()
        arrm = usersDBHelper.readAllKategoris()
        //arrm!!.add(mKategoriModel(1,"kopi"))
        //arrm!!.add(mKategoriModel(2,"gula"))

        val adapter = ArrayAdapter<mKategoriModel>(this,
            android.R.layout.simple_spinner_item, arrm
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        kategori.setAdapter(adapter)

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

        btnscan.setOnClickListener {
            run {
                IntentIntegrator(this@BarangAdd).initiateScan()
            }
        }


        btnStore!!.setOnClickListener {

            if(etqrid!!.text.toString()=="") {
                Toast.makeText(this@BarangAdd, "Kode barang harus diisi", Toast.LENGTH_SHORT).show()
                kategori!!.requestFocus()
            }else if(idkategori=="") {
                Toast.makeText(this@BarangAdd, "Kategori harus diisi", Toast.LENGTH_SHORT).show()
                kategori!!.requestFocus()
            }else if(etbarang!!.text.toString()=="") {
                Toast.makeText(this@BarangAdd, "Nama barang harus diisi", Toast.LENGTH_SHORT).show()
                etbarang!!.requestFocus()
            }else if(ethargajual!!.text.toString()=="") {
                Toast.makeText(this@BarangAdd, "Harga jual harus diisi", Toast.LENGTH_SHORT).show()
                ethargajual!!.requestFocus()
            }else if(promo!!.text.toString()=="") {
                vpromo = "0"
            }else if(satuan!!.text.toString()=="") {
                Toast.makeText(this@BarangAdd, "Satuan harus diisi", Toast.LENGTH_SHORT).show()
                satuan!!.requestFocus()
            }else {

                usersDBHelper.insertUser(
                    idkategori, etqrid!!.text.toString(),
                    etbarang!!.text.toString(),
                    ethargajual!!.text.toString(),
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

                //SQLiteLog

                var msg: String = etbarang!!.text.toString()
                Toast.makeText(
                    this@BarangAdd,
                    "Barang " + msg + " berhasil disimpan!",
                    Toast.LENGTH_SHORT
                ).show()

                etqrid!!.setText("")
                etbarang!!.setText("")
                ethargajual!!.setText("")
                harga_grosir!!.setText("")
                harga_grosir_promo!!.setText("")
                harga_beli!!.setText("")
                stok!!.setText("")
                satuan!!.setText("")
                tax!!.setText("")
                nonstok!!.isChecked = false
                catatan!!.setText("")

            }
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
        val builder = AlertDialog.Builder(this@BarangAdd)

        builder.setTitle(title)
        builder.setMessage(msg)

        builder.setNeutralButton("Tutup"){_,_ ->
            //Toast.makeText(applicationContext,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()

    }



    fun Context.toast(message: String) {

    }


    //scanner-----------------------------------------------------------------------------------------------
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        var result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if(result != null){

            if(result.contents != null){
                scannedResult = result.contents
                qrid.text = scannedResult.toEditable()
            } else {
                qrid.text = "".toEditable()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    override fun onSaveInstanceState(outState: Bundle) {

        outState?.putString("scannedResult", scannedResult)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState?.let {
            scannedResult = it.getString("scannedResult")!!
            qrid.text = scannedResult.toEditable()
        }
    }


}
