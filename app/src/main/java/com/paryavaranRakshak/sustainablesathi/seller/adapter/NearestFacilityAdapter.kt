package com.paryavaranRakshak.sustainablesathi.seller.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paryavaranRakshak.sustainablesathi.R
import com.paryavaranRakshak.sustainablesathi.seller.models.LocatorFacilityModel

class NearestFacilityAdapter(val context: Context, val list: List<LocatorFacilityModel>) : RecyclerView.Adapter<NearestFacilityAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val name = view.findViewById<TextView>(R.id.tv_facilityName)
        val distance = view.findViewById<TextView>(R.id.tv_distance)
        val direction = view.findViewById<Button>(R.id.btn_direction)
        val contact = view.findViewById<Button>(R.id.btn_contact)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.facility_cardview,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list[position].name
        val distanceStr = "${list[position].distance} km"
        holder.distance.text = distanceStr
        holder.direction.setOnClickListener { openWebView("https://www.mappls.com/@${list[position].latitude},${list[position].longitude}") }
        holder.contact.setOnClickListener { dial(list[position].contactNumber) }
    }

    private fun openWebView(url: String) {
        val chromePackageName = "com.android.chrome"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.setPackage(chromePackageName)

        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            // If Chrome is not installed, fall back to a generic browser intent
            val genericIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(genericIntent)
        }
    }

    private fun dial(number: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))

        // Check if there's an activity that can handle the intent
        if (dialIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(dialIntent)
        } else {
            // Handle the case where the device doesn't have a dialer app installed
            // You can provide a message or an alternative action here
            // For example, you might display a message to the user or open a messaging app.
        }
    }

}