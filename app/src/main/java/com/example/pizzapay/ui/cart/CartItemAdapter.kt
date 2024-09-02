package com.example.pizzapay.ui.cart

import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzapay.R

class CartItemAdapter(
    modelArrayList: ArrayList<CartItemModel>, cartViewModel: CartViewModel
) : RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {

    private var modelArrayList: ArrayList<CartItemModel>
    private val cartViewModel: CartViewModel

    private val formatter = DecimalFormat("#,##0.00")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // to inflate the layout for each item of recycler view.
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem: CartItemModel = modelArrayList[position]

        holder.image.setImageResource(cartItem.product.img)
        holder.title.text = cartItem.product.title
        holder.description.text = cartItem.product.description
        holder.price.text = "€ " + formatter.format(cartItem.product.price.toDouble() / 100)
        holder.qty.text = cartItem.qty.toString()

        holder.removeCartItem.setOnClickListener {
            cartViewModel.clearProduct(cartItem.product)

            if (cartViewModel.cartItems.value?.values != null) {
                modelArrayList = cartViewModel.cartItems.value?.values!!.toCollection(ArrayList())
            }

            notifyItemRemoved(position)
        }

        holder.decreaseCartItem.setOnClickListener {
            cartViewModel.removeProduct(cartItem.product)

            if (cartViewModel.cartItems.value?.values != null) {
                modelArrayList = cartViewModel.cartItems.value?.values!!.toCollection(ArrayList())
            }

            notifyItemChanged(position)
        }

        holder.increaseCartItem.setOnClickListener {
            cartViewModel.addProduct(cartItem.product)

            if (cartViewModel.cartItems.value?.values != null) {
                modelArrayList = cartViewModel.cartItems.value?.values!!.toCollection(ArrayList())
            }

            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        // this method is used for showing number of card items in recycler view.
        return modelArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.cartItemImage)
        val title: TextView = itemView.findViewById(R.id.cartItemTitle)
        val description: TextView = itemView.findViewById(R.id.cartItemDescription)
        val price: TextView = itemView.findViewById(R.id.cartItemPrice)
        val qty: TextView = itemView.findViewById(R.id.cartItemQty)
        val removeCartItem: ImageButton = itemView.findViewById(R.id.removeCartItem)
        val decreaseCartItem: ImageButton = itemView.findViewById(R.id.decreaseCartItem)
        val increaseCartItem: ImageButton = itemView.findViewById(R.id.increaseCartItem)
    }

    init {
        this.modelArrayList = modelArrayList
        this.cartViewModel = cartViewModel
    }
}