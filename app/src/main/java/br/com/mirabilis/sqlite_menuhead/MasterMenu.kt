package br.com.mirabilis.sqlite_menuhead

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.mirabilis.sqlite_menuhead.adapter.MenuAdapter
import br.com.mirabilis.sqlite_menuhead.master_barang.BarangMain
import br.com.mirabilis.sqlite_menuhead.master_kategori.KategoriMain


class MasterMenu : AppCompatActivity() {

    private var listView: ListView? = null

    val language = arrayOf<String>("Data Kategori Barang","Data Barang")
    val imageId = arrayOf<Int>(
        R.drawable.ic_kategori_72dp, R.drawable.ic_barang_72dp
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.master_menu)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        @Suppress("DEPRECATION")
        supportActionBar!!.setTitle(Html.fromHtml("<font color='#808080'>"+"Menu Master"+"</font>"))

        listView = findViewById(R.id.lv) as ListView


        val myListAdapter = MenuAdapter(this,language,imageId)
        listView!!.adapter = myListAdapter



        listView!!.setOnItemClickListener(){ adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos:Int = adapterView.getItemIdAtPosition(position).toInt()

            when (itemIdAtPos) {
                0 -> startActivity(Intent(this@MasterMenu, KategoriMain::class.java))
                1 -> startActivity(Intent(this@MasterMenu, BarangMain::class.java))
                else -> {
                    print("x is neither 1 nor 2")
                }
            }
        }


    }

}

