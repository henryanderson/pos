package br.com.mirabilis.sqlite_menuhead.adapter



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import br.com.mirabilis.sqlite_menuhead.R
import br.com.mirabilis.sqlite_menuhead.master_kategori.mKategoriModel

import java.util.ArrayList

/**
 * Created by Parsania Hardik on 26-Apr-17.
 */
@Suppress("NAME_SHADOWING")
class KategoriAdapter(private val context: Context, private val userModelArrayList: ArrayList<mKategoriModel>) :
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
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.kategori_item, null, true)

            holder.tvkategori = convertView!!.findViewById(R.id.kategori) as TextView


            convertView.tag = holder
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = convertView.tag as ViewHolder
        }

        holder.tvkategori!!.text = userModelArrayList[position].kategori

        return convertView
    }

    private inner class ViewHolder {

        var tvkategori: TextView? = null

    }

}