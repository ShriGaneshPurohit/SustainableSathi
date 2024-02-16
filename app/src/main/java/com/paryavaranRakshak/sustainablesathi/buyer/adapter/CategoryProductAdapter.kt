package com.paryavaranRakshak.sustainablesathi.buyer.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paryavaranRakshak.sustainablesathi.buyer.ProductViewActivity
import com.paryavaranRakshak.sustainablesathi.R
import com.paryavaranRakshak.sustainablesathi.buyer.models.CategoryProductsModel
import com.paryavaranRakshak.sustainablesathi.buyer.models.ProductsModel
import com.paryavaranRakshak.sustainablesathi.buyer.utils.ProductUtils

class CategoryProductAdapter(val context: Context, val list: List<CategoryProductsModel>) : RecyclerView.Adapter<CategoryProductAdapter.ViewHolder>() {

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

        // Set click listener for the item
        holder.itemView.setOnClickListener {
            // Pass the selected product details to the utils
            ProductUtils.productId = list[position].id
            ProductUtils.productName = list[position].name
            ProductUtils.imageLink = list[position].image_link
            ProductUtils.description = list[position].description
            ProductUtils.quantity = list[position].quantity
            ProductUtils.price = list[position].price

            val intent = Intent(context, ProductViewActivity::class.java)
            context.startActivity(intent)
        }
    }

}