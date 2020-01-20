package br.com.mirabilis.sqlite_menuhead.db

import android.provider.BaseColumns

object DBContract {

    /* Inner class that defines the table contents */
    class UserEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "barangs"
            val COLUMN_ID = "id"
            val COLUMN_IDKATEGORI = "id_kategori"
            val COLUMN_QRID = "qrid"
            val COLUMN_BARANG = "barang"
            val COLUMN_HARGA_JUAL = "harga_jual"
            val COLUMN_PROMO = "promo"
            val COLUMN_HARGA_GROSIR = "harga_grosir"
            val COLUMN_HARGA_GROSIR_PROMO = "harga_grosir_promo"
            val COLUMN_HARGA_BELI = "harga_beli"
            val COLUMN_STOK = "stok"
            val COLUMN_SATUAN = "satuan"
            val COLUMN_TAX = "tax"
            val COLUMN_NONSTOK = "nonstok"
            val COLUMN_CATATAN = "catatan"
        }
    }

    class KategoriEntry : BaseColumns {
        companion object {

            val TABLE_KATEGORI = "kategoris"
            val COLUMN_ID_KATEGORI = "id"
            val COLUMN_KATEGORI = "kategori"

        }
    }

}