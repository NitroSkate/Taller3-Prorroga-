package com.naldana.ejemplo10

import android.content.ContentValues
import android.support.v4.app.Fragment
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import android.os.Bundle
import android.provider.BaseColumns
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.Gson
import com.naldana.ejemplo10.adapters.CurrencyAdapter
import com.naldana.ejemplo10.data.Database
import com.naldana.ejemplo10.data.DatabaseContract
import com.naldana.ejemplo10.pojos.AppConstants
import com.naldana.ejemplo10.pojos.Currency
import com.naldana.ejemplo10.utils.NetworkUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var viewAdapter: CurrencyAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var mainContentFragment: CurrencyFragment
    var dbHelper = Database(this)
    var twoPane = false

    private fun changeFragment(id: Int, frag: Fragment){ supportFragmentManager.beginTransaction().replace(id, frag).commit() }

    override fun onDestroy() {
        //this.deleteDatabase("miprimerabase.db")
        dbHelper.close()
        super.onDestroy()
    }

    /*override fun onRestart() {
        this.deleteDatabase("miprimerabase.db")
        dbHelper.close()
        super.onRestart()
    }*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // TODO (9) Se asigna a la actividad la barra personalizada
        setSupportActionBar(toolbar)


        // TODO (10) Click Listener para el boton flotante
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Lista actualizado", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            var actualizar = leerBasesdedatos()
            initRecycler(actualizar)
        }


        // TODO (11) Permite administrar el DrawerLayout y el ActionBar
        // TODO (11.1) Implementa las caracteristicas recomendas
        // TODO (11.2) Un DrawerLayout (drawer_layout)
        // TODO (11.3) Un lugar donde dibujar el indicador de apertura (la toolbar)
        // TODO (11.4) Una String que describe el estado de apertura
        // TODO (11.5) Una String que describe el estado cierre
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        // TODO (12) Con el Listener Creado se asigna al  DrawerLayout
        drawer_layout.addDrawerListener(toggle)


        // TODO(13) Se sincroniza el estado del menu con el LISTENER

        toggle.syncState()

        // TODO (14) Se configura el listener del menu que aparece en la barra lateral
        // TODO (14.1) Es necesario implementar la inteface {{@NavigationView.OnNavigationItemSelectedListener}}
        nav_view.setNavigationItemSelectedListener(this)

        // TODO (20) Para saber si estamos en modo dos paneles
        if (fragment_content != null) {
            twoPane = true
        }


        /*
         * TODO (Instrucciones)Luego de leer todos los comentarios añada la implementación de RecyclerViewAdapter
         * Y la obtencion de datos para el API de Monedas
         */


        FetchCurrencyTask().execute("")

    }


    // TODO (16) Para poder tener un comportamiento Predecible
    // TODO (16.1) Cuando se presione el boton back y el menu este abierto cerralo
    // TODO (16.2) De lo contrario hacer la accion predeterminada
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    // TODO (17) LLena el menu que esta en la barra. El de tres puntos a la derecha
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    // TODO (18) Atiende el click del menu de la barra
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun searchPokemon(country: String) {

        if (!country.isEmpty()) {
            QueryPokemonTask().execute(country)
        }

    }

    // TODO (14.2) Funcion que recibe el ID del elemento tocado
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var listap : ArrayList<Currency>
        // Handle navigation view item clicks here.
        when (item.itemId) {
            // TODO (14.3) Los Id solo los que estan escritos en el archivo de MENU
            R.id.nav_camera -> {
                Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
                listap = leerBasesdedatosUno(item.title.toString(),2)
                Log.d("sivar", listap[1].name)
                initRecycler(listap)
            }
            R.id.nav_gallery -> {
                Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
                listap = leerBasesdedatosUno(item.title.toString(),2)
                initRecycler(listap)

            }
            R.id.nav_slideshow -> {
                Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
                listap = leerBasesdedatosUno(item.title.toString(),2)
                initRecycler(listap)
            }
            R.id.nav_manage -> {
                Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
                listap = leerBasesdedatosUno(item.title.toString(),2)
                initRecycler(listap)
            }
            R.id.nav_share -> {
                Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
                listap = leerBasesdedatosUno(item.title.toString(),2)
                initRecycler(listap)
            }
            R.id.nav_send -> {
                Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
                listap = leerBasesdedatosUno(item.title.toString(),2)
                initRecycler(listap)
            }
            R.id.guatemala -> {
                Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
                listap = leerBasesdedatosUno(item.title.toString(),2)
                initRecycler(listap)
            }
        }

        // TODO (15) Cuando se da click a un opcion del menu se cierra de manera automatica
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun initRecycler(currency: MutableList<Currency>) {
        viewManager = LinearLayoutManager(this)
        viewAdapter = CurrencyAdapter(currency, { pokemonItem: Currency -> pokemonItemClicked(pokemonItem) })

        recyclerview.apply {
            setHasFixedSize(false)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun pokemonItemClicked(item: Currency) {
        val listab = Bundle()
        val lista = leerBasesdedatosUno(item.name, 1)
        Log.d("olv", lista[0].name)
        listab.putParcelableArrayList("Coins", lista)
        if (twoPane){

            mainContentFragment = CurrencyFragment.newInstance(item)
            changeFragment(R.id.fragment_content, mainContentFragment)

        }else{
            startActivity(Intent(this, CurrecncyActivity::class.java).putExtra("CLAVIER", listab))
        }
        //
    }

    private inner class FetchCurrencyTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg query: String): String {
            Log.i("CURRENCYFAIL", "HERE")

            if (query.isNullOrEmpty()) return ""

            val ID = query[0]
            val currencyAPI = NetworkUtils().buildUrl("", ID)
            Log.i("HTTPRES", currencyAPI.toString())
            try {
                Log.i("RESULTADO", NetworkUtils().getResponseFromHttpUrl(currencyAPI))
            } catch (e: IOException) {

                e.printStackTrace()
                ""
            }
            //

            return try {

                NetworkUtils().getResponseFromHttpUrl(currencyAPI)
            } catch (e: IOException) {
                e.printStackTrace()
                ""
            }

        }

        override fun onPostExecute(currencyInfo: String) {
            listCurrency(currencyInfo, "coins")
        }
    }

    private inner class QueryPokemonTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg query: String): String {

            if (query.isNullOrEmpty()) return ""

            val ID = query[0]
            val pokeAPI = NetworkUtils().buildUrl("country", ID)

            return try {
                NetworkUtils().getResponseFromHttpUrl(pokeAPI)
            } catch (e: IOException) {
                e.printStackTrace()
                ""
            }

        }

        override fun onPostExecute(currencyInfo: String) {
            listCurrency(currencyInfo, "coin")
        }
    }

    fun listCurrency(currencyInfo: String, name: String) {
        val currencyList = if (!currencyInfo.isEmpty()) {
            val root = JSONObject(currencyInfo)
            val results = root.getJSONArray(name)
            results.length()
            Log.i("JSONARRAY", results.toString())
            Log.i("JSONRESULT", results.length().toString().trim())
            MutableList(results.length()) { i ->
              //  val resulty = JSONObject(results[i].toString())
                Gson().fromJson<Currency>(results[i].toString(), Currency::class.java)


            }
        } else {
            MutableList(8) { i ->
                Currency(
                    i.toString(),
                    R.string.n_a_value.toString(),
                    R.string.n_a_value.toString(),
                    R.string.n_a_value.toString(),
                    R.string.n_a_value.toString(),
                    R.string.n_a_value.toString(),
                    R.string.n_a_value.toString(),
                    R.string.n_a_value.toString()
                )
            }
        }
        meterBasesdedatos(currencyList)
        val listaaux = leerBasesdedatos()
        initRecycler(listaaux)
    }

    private fun meterBasesdedatos(lista : MutableList<Currency>){
        Log.d("olv", lista[0].name)
        val db = dbHelper.writableDatabase
        dbHelper. check(db)
        for(i in 0..7){


// TODO(14) Para ingresar nuevos datos, es necesario solicitar una instancia de escritura de la base de datos.

            val values = ContentValues().apply {
                put(DatabaseContract.PersonaEntry.COLUMN_NAME, lista[i].name)
                put(DatabaseContract.PersonaEntry.COLUMN_COUNTRY, lista[i].country)
                put(DatabaseContract.PersonaEntry.COLUMN_VALUE, lista[i].value)
                put(DatabaseContract.PersonaEntry.COLUMN_VALUE_US, lista[i].value_us)
                put(DatabaseContract.PersonaEntry.COLUMN_YEAR, lista[i].year)
                put(DatabaseContract.PersonaEntry.COLUMN_REVIEW, lista[i].review)
                put(DatabaseContract.PersonaEntry.COLUMN_ISAVAILABLE, lista[i].isAvailable)
                put(DatabaseContract.PersonaEntry.COLUMN_IMG, lista[i].img)
            }

            val newRowId = db?.insert(DatabaseContract.PersonaEntry.TABLE_NAME, null, values)

            if (newRowId == -1L) {
                Toast.makeText(this, "No se ha podido guardar", Toast.LENGTH_SHORT).show()
            } else {
                //Toast.makeText(this, "Guardado con exito", Toast.LENGTH_SHORT).show()
                Log.d("Hola", "Guardado con exito")
            }

        }

    }

    private fun leerBasesdedatos() : MutableList<Currency>{
        // TODO(13) Para obtener los datos almacenados, es necesario solicitar una instancia de lectura de la base de datos.
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            BaseColumns._ID,
            DatabaseContract.PersonaEntry.COLUMN_NAME,
            DatabaseContract.PersonaEntry.COLUMN_COUNTRY,
            DatabaseContract.PersonaEntry.COLUMN_VALUE,
            DatabaseContract.PersonaEntry.COLUMN_VALUE_US,
            DatabaseContract.PersonaEntry.COLUMN_YEAR,
            DatabaseContract.PersonaEntry.COLUMN_REVIEW,
            DatabaseContract.PersonaEntry.COLUMN_ISAVAILABLE,
            DatabaseContract.PersonaEntry.COLUMN_IMG
        )

        //val sortOrder = "${DatabaseContract.PersonaEntry.COLUMN_DISPLAYNAME} DESC"

        val cursor = db.query(
            DatabaseContract.PersonaEntry.TABLE_NAME, // nombre de la tabla
            projection, // columnas que se devolverán
            null, // Columns where clausule
            null, // values Where clausule
            null, // Do not group rows
            null, // do not filter by row
            null // sort order
        )

        var lista = mutableListOf<Currency>()

        with(cursor) {
            while (moveToNext()) {
                var moneda = Currency(
                    "N/A",
                    getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_NAME)),
                    getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_COUNTRY)),
                    getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_VALUE)),
                    getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_VALUE_US)),
                    getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_YEAR)),
                    getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_REVIEW)),
                    getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_ISAVAILABLE)),
                    getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_IMG))
                )

                lista.add(moneda)
            }
        }

        return lista
    }

    private fun leerBasesdedatosUno(oof : String, num : Int) : ArrayList<Currency>{
        // TODO(13) Para obtener los datos almacenados, es necesario solicitar una instancia de lectura de la base de datos.
        val db = dbHelper.readableDatabase
        //Log.d("KPAZA", oof)
        val projection = arrayOf(
            BaseColumns._ID,
            DatabaseContract.PersonaEntry.COLUMN_NAME,
            DatabaseContract.PersonaEntry.COLUMN_COUNTRY,
            DatabaseContract.PersonaEntry.COLUMN_VALUE,
            DatabaseContract.PersonaEntry.COLUMN_VALUE_US,
            DatabaseContract.PersonaEntry.COLUMN_YEAR,
            DatabaseContract.PersonaEntry.COLUMN_REVIEW,
            DatabaseContract.PersonaEntry.COLUMN_ISAVAILABLE,
            DatabaseContract.PersonaEntry.COLUMN_IMG
        )
        var lista = ArrayList<Currency>()

        //val sortOrder = "${DatabaseContract.PersonaEntry.COLUMN_DISPLAYNAME} DESC"
        if(num ==1) {
            val where = "${DatabaseContract.PersonaEntry.COLUMN_NAME}=?"

            val wherev = arrayOf(
                oof
            )


            val cursor = db.query(
                DatabaseContract.PersonaEntry.TABLE_NAME, // nombre de la tabla
                projection, // columnas que se devolverán
                where, // Columns where clausule
                wherev, // values Where clausule
                null, // Do not group rows
                null, // do not filter by row
                null // sort order
            )

            //var lista = ArrayList<Currency>()

            with(cursor) {
                while (moveToNext()) {
                    var moneda = Currency(
                        "N/A",
                        getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_NAME)),
                        getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_COUNTRY)),
                        getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_VALUE)),
                        getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_VALUE_US)),
                        getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_YEAR)),
                        getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_REVIEW)),
                        getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_ISAVAILABLE)),
                        getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_IMG))
                    )

                    lista.add(moneda)
                }
            }
            //Log.d("KPASA", lista[0].name)

        }

        if(num == 2){
            val where = "${DatabaseContract.PersonaEntry.COLUMN_COUNTRY}=?"

            val wherev = arrayOf(
                oof
            )


            val cursor = db.query(
                DatabaseContract.PersonaEntry.TABLE_NAME, // nombre de la tabla
                projection, // columnas que se devolverán
                where, // Columns where clausule
                wherev, // values Where clausule
                null, // Do not group rows
                null, // do not filter by row
                null // sort order
            )

            //var lista = ArrayList<Currency>()

            with(cursor) {
                while (moveToNext()) {
                    var moneda = Currency(
                        "N/A",
                        getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_NAME)),
                        getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_COUNTRY)),
                        getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_VALUE)),
                        getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_VALUE_US)),
                        getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_YEAR)),
                        getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_REVIEW)),
                        getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_ISAVAILABLE)),
                        getString(getColumnIndexOrThrow(DatabaseContract.PersonaEntry.COLUMN_IMG))
                    )

                    lista.add(moneda)
                }
            }
        }
        return lista
    }


    }


