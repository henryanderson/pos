package br.com.mirabilis.sqlite_menuhead.master_kategori

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.mirabilis.sqlite_menuhead.R
import br.com.mirabilis.sqlite_menuhead.db.UsersDBHelper
import br.com.mirabilis.sqlite_menuhead.master_kategori.mKategoriModel

class KategoriEdit : AppCompatActivity() {

    private var kategoriModel: mKategoriModel? = null

    private var btnStore: Button? = null
    private var etkategori: EditText? = null

    lateinit var usersDBHelper: UsersDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_tambah_kategori)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        supportActionBar!!.setTitle(Html.fromHtml("<font color='#808080'>" + "Kategori Edit" + "</font>"))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        kategoriModel = intent.getSerializableExtra("kategori") as mKategoriModel

        var id = kategoriModel!!.id
        //Toast.makeText(this@KategoriEdit,kategoriModel!!.kategori.toString(),Toast.LENGTH_SHORT).show()

        usersDBHelper = UsersDBHelper(this)

        btnStore = findViewById(R.id.btnsimpan) as Button
        etkategori = findViewById(R.id.nama_kategori) as EditText

        etkategori!!.setText(kategoriModel!!.kategori)

        btnStore!!.setOnClickListener {

            usersDBHelper.updateKategori(id, etkategori!!.text.toString())


            var msg : String = etkategori!!.text.toString()
            Toast.makeText(this@KategoriEdit, "Kategori "+msg+" berhasil dirubah!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this@KategoriEdit, KategoriMain::class.java)
            startActivity(intent)
        }

    }


}