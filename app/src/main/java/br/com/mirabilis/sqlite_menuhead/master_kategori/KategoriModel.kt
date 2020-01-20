package br.com.mirabilis.sqlite_menuhead.master_kategori

import java.io.Serializable

data class mKategoriModel(var id: Int=0, var kategori: String): Serializable {

    //to display object as a string in spinner
    override fun toString(): String {
        return kategori
    }

}
