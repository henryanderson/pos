package br.com.mirabilis.sqlite_menuhead.master_kategori

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import br.com.mirabilis.sqlite_menuhead.R
import br.com.mirabilis.sqlite_menuhead.adapter.KategoriAdapter
import br.com.mirabilis.sqlite_menuhead.db.UsersDBHelper
import br.com.mirabilis.sqlite_menuhead.master_kategori.mKategoriModel

class KategoriMain : AppCompatActivity() {

    private var listView: ListView? = null
    private var customAdapter: KategoriAdapter? = null
    lateinit var usersDBHelper: UsersDBHelper
    var datas: ArrayList<mKategoriModel>? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_all)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        //supportActionBar!!.setTitle("Master Barang")
        supportActionBar!!.setTitle(Html.fromHtml("<font color='#808080'>"+"Master Kategori"+"</font>"))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        listView = findViewById(R.id.lv) as ListView

        usersDBHelper = UsersDBHelper(this)
        datas = usersDBHelper.readAllKategoris()

        customAdapter = KategoriAdapter(this, datas!!)
        listView!!.adapter = customAdapter


        listView!!.onItemClickListener = OnItemClickListener {
                parent, view, position, id ->
            val intent = Intent(this@KategoriMain, KategoriEdit::class.java)
            intent.putExtra("kategori",datas!![position])
            startActivity(intent)
            //Toast.makeText(this@BarangMain,"yooo", Toast.LENGTH_SHORT).show()
        }

        listView!!.setOnItemLongClickListener(AdapterView.OnItemLongClickListener { arg0, v, index, arg3 ->
            showDialog(index.toString())
            //Toast.makeText(this@BarangMain,index.toString(), Toast.LENGTH_SHORT).show()
            true
        })


    }



    private fun showDialog(index : String){
        lateinit var dialog: AlertDialog
        val builder = AlertDialog.Builder(this)

        var m: mKategoriModel? =  datas!![index.toInt()]

        builder.setTitle(m!!.kategori)
        builder.setMessage("Delete this item?")

        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> toast("yes", index)
                DialogInterface.BUTTON_NEUTRAL -> toast("cancel", index)
            }
        }

        // Set the alert dialog positive/yes button
        builder.setPositiveButton("OK",dialogClickListener)
        //builder.setNegativeButton("NO",dialogClickListener)
        builder.setNeutralButton("Cancel",dialogClickListener)

        dialog = builder.create()
        dialog.show()
    }



    fun Context.toast(message: String, index: String) {

        if(message=="yes") {

            var userModel: mKategoriModel? =  datas!![index.toInt()]
            var msg : String = userModel!!.kategori

            usersDBHelper.deleteKategori(userModel!!.id.toString())

            datas!!.removeAt(index.toInt())
            customAdapter!!.notifyDataSetChanged()

            Toast.makeText(this, "Kategori "+msg+" berhasil dihapus", Toast.LENGTH_SHORT).show()
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Membaca file menu dan menambahkan isinya ke action bar jika ada.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        when (item.getItemId()) {
            R.id.add -> {
                //Toast.makeText(this@GetAllUsersActivity, "ok", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, KategoriAdd::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}