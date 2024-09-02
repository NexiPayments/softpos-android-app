package com.example.pizzapay.ui.productlist

import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzapay.R
import com.example.pizzapay.ui.cart.CartViewModel

class ProductItemAdapter(
    private val modelArrayList: ArrayList<ProductItemModel>,
    private val cartViewModel: CartViewModel
) : RecyclerView.Adapter<ProductItemAdapter.ViewHolder>() {

    private val formatter = DecimalFormat("#,##0.00")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.pizza_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product: ProductItemModel = modelArrayList[position]

        holder.image.setImageResource(product.img)
        holder.title.text = product.title
        holder.description.text = product.description
        holder.price.text = formatter.format(product.price.toDouble() / 100)

        holder.addToCart.setOnClickListener {
            cartViewModel.addProduct(product)
        }
    }

    override fun getItemCount(): Int {
        return modelArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.pizzaCardImage)
        val title: TextView = itemView.findViewById(R.id.pizzaCardTitle)
        val description: TextView = itemView.findViewById(R.id.pizzaCardDescription)
        val price: TextView = itemView.findViewById(R.id.pizzaCardPrice)
        val addToCart: ImageButton = itemView.findViewById(R.id.addToCart)
    }
}