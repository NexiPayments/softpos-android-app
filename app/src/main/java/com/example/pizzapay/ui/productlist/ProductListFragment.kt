package com.example.pizzapay.ui.productlist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzapay.R
import com.example.pizzapay.databinding.FragmentProductListBinding
import com.example.pizzapay.ui.MarginItemDecoration
import com.example.pizzapay.ui.cart.CartViewModel
import com.example.pizzapay.ui.cart.CartViewModelFactory
import com.example.pizzapay.ui.login.LoginActivity

class ProductListFragment : Fragment() {

    private lateinit var cartViewModel: CartViewModel

    private var _binding: FragmentProductListBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartViewModel = ViewModelProvider(
            activity as FragmentActivity,
            CartViewModelFactory()
        )[CartViewModel::class.java]

        cartViewModel.cartItems.observe(activity as FragmentActivity, Observer { cartItems ->
            var counter: Int = 0

            cartItems.forEach {
                counter += it.value.qty
            }

            binding.cartItemCounter.text = counter.toString()
        })

        val recyclerView = binding.pizzaCardList

        val pizzaArrayList: ArrayList<ProductItemModel> = ArrayList()

        pizzaArrayList.add(
            ProductItemModel(
                "margherita",
                R.drawable.pizza_uno,
                750,
                "Margherita",
                "Pomodoro, mozzarella, origano"
            )
        )
        pizzaArrayList.add(
            ProductItemModel(
                "napoli",
                R.drawable.pizza_due,
                850,
                "Napoli",
                "Pomodoro, mozzarella, origano, acciughe"
            )
        )
        pizzaArrayList.add(
            ProductItemModel(
                "romana",
                R.drawable.pizza_uno,
                950,
                "Romana",
                "Pomodoro, mozzarella, origano, acciughe, capperi"
            )
        )
        pizzaArrayList.add(
            ProductItemModel(
                "tonno", R.drawable.pizza_due, 450, "Tonno", "Pomodoro, mozzarella, tonno"
            )
        )

        // we are initializing our adapter class and passing our arraylist to it.
        val adapter = ProductItemAdapter(pizzaArrayList, cartViewModel)

        // in below two lines we are setting layout manager and adapter to our recycler view.
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        val margin = resources.getDimensionPixelSize(R.dimen.pizza_card_margin)
        recyclerView.addItemDecoration(MarginItemDecoration(margin))

        binding.cartButton.setOnClickListener {
            findNavController().navigate(R.id.action_produtListFragment_to_cartFragment)
        }

        val spLogin = activity?.getSharedPreferences("login", MODE_PRIVATE)

        binding.logoutButton.setOnClickListener {
            spLogin?.edit()?.putBoolean("is_logged_in", false)?.apply()
            spLogin?.edit()?.remove("session_id")?.apply()

            val intent = Intent(activity, LoginActivity::class.java)

            startActivity(intent)

            activity?.setResult(Activity.RESULT_OK)

            activity?.finish()
        }
    }

}