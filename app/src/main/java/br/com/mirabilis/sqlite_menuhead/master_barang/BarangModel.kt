package br.com.mirabilis.sqlite_menuhead.master_barang

import java.io.Serializable



data class BarangModel(val id: Int=0, val id_kategori: String,
                       val qrid: String,
                       val barang: String,
                       val harga_jual: String, //step
                       val promo: String,
                       val harga_grosir: String,
                       val harga_grosir_promo: String,
                       val harga_beli: String,
                       val stok: String,
                       val satuan: String,
                       val tax: String,
                       val nonstok: String,
                       val catatan: String
):Serializable





