package br.com.mirabilis.sqlite_menuhead.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import br.com.mirabilis.sqlite_menuhead.master_barang.BarangModel
import br.com.mirabilis.sqlite_menuhead.R

import java.util.ArrayList

/**
 * Created by Parsania Hardik on 26-Apr-17.
 */
@Suppress("NAME_SHADOWING")
class BarangAdapter(private val context: Context, private val userModelArrayList: ArrayList<BarangModel>) :
    BaseAdapter() {


    override fun getCount(): Int {
        return userModelArrayList.size
    }

    override fun getItem(position: Int): Any {
        return userModelArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {


        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {
            holder = ViewHolder()
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.barang_item, null, true)

            holder.tvqrid = convertView!!.findViewById(R.id.qrid) as TextView
            holder.tvbarang = convertView.findViewById(R.id.barang) as TextView
            holder.tvharga = convertView.findViewById(R.id.harga) as TextView
            holder.tvstok = convertView.findViewById(R.id.stok) as TextView

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        if(position %2 == 1) {
            convertView.setBackgroundColor(Color.WHITE)
        }else{
            convertView.setBackgroundColor(Color.parseColor("#F0EBEA"))
        }

        holder.tvqrid!!.text = userModelArrayList[position].qrid
        holder.tvbarang!!.text = userModelArrayList[position].barang
        holder.tvharga!!.text = "IDR " + userModelArrayList[position].harga_jual
        holder.tvstok!!.text = "(0)"

        return convertView
    }

    private inner class ViewHolder {

        var tvqrid: TextView? = null
        var tvbarang: TextView? = null
        var tvharga: TextView? = null
        var tvstok: TextView? = null

    }

}