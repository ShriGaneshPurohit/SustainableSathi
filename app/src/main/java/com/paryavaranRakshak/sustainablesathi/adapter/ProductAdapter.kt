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
import com.paryavaranRakshak.sustainablesathi.models.ProductsModel

class ProductAdapter(val context: Context, val list: List<ProductsModel>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        var image = view.findViewById<ImageView>(R.id.iv_product)
        var name = view.findViewById<TextView>(R.id.tv_productName)
        var price = view.findViewById<TextView>(R.id.tv_productPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.productdisplaycard,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //loading image
        Glide.with(context)
            .load(list[position].image_link)
            .into(holder.image)

        holder.name.text = list[position].name
        val priceStr = "₹ ${list[position].price} /-"
        holder.price.text = priceStr

        /*holder.image.setOnClickListener{
            categoryWallpapersUtils.CATEGORY_ID = list[position].name
            categoryWallpapersUtils.CATEGORY_SELECTED = list[position].name

            val showWallpapers = Intent(context, CategoryWallpapersActivity::class.java)

            context.startActivity(showWallpapers)
        }*/
    }

}