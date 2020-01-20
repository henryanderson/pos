package br.com.mirabilis.sqlite_menuhead.master_kategori

import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.mirabilis.sqlite_menuhead.R
import br.com.mirabilis.sqlite_menuhead.db.UsersDBHelper

class KategoriAdd : AppCompatActivity() {

    private var btnStore: Button? = null
    private var etkategori: EditText? = null

    lateinit var usersDBHelper: UsersDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_tambah_kategori)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        //supportActionBar!!.setTitle("Master Barang")
        supportActionBar!!.setTitle(Html.fromHtml("<font color='#808080'>"+"Tambah Kategori"+"</font>"))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        usersDBHelper = UsersDBHelper(this)

        btnStore = findViewById(R.id.btnsimpan) as Button
        etkategori = findViewById(R.id.nama_kategori) as EditText

        btnStore!!.setOnClickListener {

           if(etkategori!!.text.toString()=="") {

               Toast.makeText(
                   this@KategoriAdd,
                   "Nama kategori harus diisi",
                   Toast.LENGTH_SHORT
               ).show()

           }else{

               usersDBHelper.insertKategori(etkategori!!.text.toString())

               var msg: String = etkategori!!.text.toString()
               Toast.makeText(
                   this@KategoriAdd,
                   "Kategori " + msg + " berhasil disimpan!",
                   Toast.LENGTH_SHORT
               ).show()

               etkategori!!.setText("")
           }
        }


    }

}
