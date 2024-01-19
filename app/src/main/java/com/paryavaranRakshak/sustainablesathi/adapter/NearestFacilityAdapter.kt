package com.paryavaranRakshak.sustainablesathi.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paryavaranRakshak.sustainablesathi.R
import com.paryavaranRakshak.sustainablesathi.models.LocatorFacilityModel

class NearestFacilityAdapter(val context: Context, val list: List<LocatorFacilityModel>) : RecyclerView.Adapter<NearestFacilityAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        var name = view.findViewById<TextView>(R.id.tv_facilityName)
        var distance = view.findViewById<TextView>(R.id.tv_distance)
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
    }

}