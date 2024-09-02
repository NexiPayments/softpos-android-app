package com.example.pizzapay.ui.cart

import android.icu.text.DecimalFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzapay.R
import com.example.pizzapay.databinding.FragmentCartBinding
import com.example.pizzapay.ui.MarginItemDecoration

class CartFragment : Fragment() {

    private lateinit var cartViewModel: CartViewModel

    private var _binding: FragmentCartBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartViewModel =
            ViewModelProvider(
                activity as FragmentActivity,
                CartViewModelFactory()
            )[CartViewModel::class.java]

        // we are initializing our adapter class and passing our arraylist to it.
        val adapter = cartViewModel.cartItems.value?.values?.let {
            CartItemAdapter(
                it.toCollection(ArrayList()), cartViewModel
            )
        }

        cartViewModel.cartItems.observe(activity as FragmentActivity, Observer { cartItems ->
            var totalAmount = 0
            var totalQty = 0

            cartItems.forEach {
                totalAmount += (it.value.qty * it.value.product.price)

                totalQty += it.value.qty
            }

            val formatter = DecimalFormat("#,##0.00")

            binding.textCartTotal.text = "€ " + formatter.format(totalAmount.toDouble() / 100)

            if (totalQty == 1) {
                binding.textItems.text = "(un prodotto)"
            } else {
                binding.textItems.text = "($totalQty prodotti)"
            }
        })

        val recyclerView = binding.cartItemList

        recyclerView.layoutManager = LinearLayoutManager(activity)

        val margin = resources.getDimensionPixelSize(R.dimen.pizza_card_margin)
        recyclerView.addItemDecoration(MarginItemDecoration(margin))

        recyclerView.adapter = adapter

        binding.cartBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.navigateToCheckout.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_checkoutFragment)
        }
    }

}