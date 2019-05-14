package com.naldana.ejemplo10

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.naldana.ejemplo10.pojos.Currency
import kotlinx.android.synthetic.main.fragment_currency.view.*

class CurrencyFragment: Fragment() {

    //var currency = ArrayList<Currency>()
    var currency = Currency()

   companion object {
       fun newInstance(currency: Currency /*currency: ArrayList<Currency>*/): CurrencyFragment{
           val newFragment = CurrencyFragment()
           newFragment.currency = currency
           return newFragment
       }
   }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_currency, container, false)

        bindData(view)

        return view
    }

    fun bindData(view: View){
        view.tv_frag_activity_name.text =  currency.name//currency[0].name
        view.tv_frag_activity_country.text = currency.country //currency[0].country
        view.tv_frag_activity_desc.text =  currency.review //currency[0].review
        view.tv_currency_frag_activity_year.text = currency.year //currency[0].year
        view.tv_currency_frag_activity_valor.text = "Valor en USD: $"+currency.value_us //currency[0].value_us
        view.tv_currency_frag_activity_available.text = "En circulacion: "+currency.isAvailable  //currency[0].isAvailable
        Glide.with(view).load(currency.img /*currency[0].img*/)
            .placeholder(R.drawable.ic_launcher_background)
            .into(view.iv_currency_frag)
    }

}