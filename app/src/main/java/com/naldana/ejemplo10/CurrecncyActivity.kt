package com.naldana.ejemplo10


import android.os.AsyncTask
import android.os.Bundle
import android.provider.BaseColumns
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.naldana.ejemplo10.data.DatabaseContract


import com.naldana.ejemplo10.pojos.Currency
import com.naldana.ejemplo10.utils.NetworkUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_currecncy.*
import org.json.JSONObject

import java.io.IOException

class CurrecncyActivity : AppCompatActivity() {
    private var mName: TextView? = null
    private var mDesc: TextView? = null
    private var mCountry: TextView? = null
    private var mImg: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currecncy)
        val uri:Bundle = this.intent.extras.getBundle("CLAVIER")
        val lista = uri.getParcelableArrayList<Currency>("Coins")
        Log.d("oof", lista[0].name)
        init(lista)
        //FetchCurrencyTask().execute(uri)




    }


    fun init(currency: ArrayList<Currency>){
        Picasso.with(this)
            .load(currency[0].img)
            .resize((this.resources.displayMetrics.widthPixels/this.resources.displayMetrics.density).toInt(),256)
            .centerCrop()
            .error(R.drawable.ic_menu_camera)
            .into(iv_currency_activity)
        tv_currency_activity_name.text = currency[0].name
        tv_currency_activity_country.text = currency[0].country
        tv_currency_activity_desc.text = currency[0].review
        tv_currency_activity_year.text = currency[0].year
        tv_currency_activity_valor.text = "valor en USD: $"+currency[0].value_us
        tv_currency_activity_available.text = "en circulacion: "+currency[0].isAvailable


    }

    /*private inner class FetchCurrencyTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg query: String): String {

            if (query.isNullOrEmpty()) return ""

            val ID = query[0]
            val pokeAPI = NetworkUtils().buildUrl("name", ID)

            return try {
                NetworkUtils().getResponseFromHttpUrl(pokeAPI)
            } catch (e: IOException) {
                e.printStackTrace()
                ""
            }


        }

        override fun onPostExecute(currencyInfo: String) {
            //Toast.makeText(this@CurrecncyActivity, currencyInfo, Toast.LENGTH_LONG).show()
            val currency = if (!currencyInfo.isEmpty()) {
                val movieJson = JSONObject(currencyInfo)
               // Toast.makeText(this@CurrecncyActivity, movieJson.getString("coin"), Toast.LENGTH_LONG).show()
                Toast.makeText(this@CurrecncyActivity, Gson().fromJson<Currency>(movieJson.getString("coin"), Currency::class.java).name, Toast.LENGTH_LONG).show()
                    Gson().fromJson<Currency>(movieJson.getString("coin"), Currency::class.java)




            } else {
                Currency(0.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString(),R.string.n_a_value.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString())
            }
           init(currency)
        }
    }*/

}