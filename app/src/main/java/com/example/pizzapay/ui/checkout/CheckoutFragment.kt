package com.example.pizzapay.ui.checkout

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
import com.example.pizzapay.NexiApp2App
import com.example.pizzapay.databinding.FragmentCheckoutBinding
import com.example.pizzapay.ui.cart.CartViewModel
import com.example.pizzapay.ui.cart.CartViewModelFactory

class CheckoutFragment : Fragment() {

    private lateinit var checkoutViewModel: CheckoutViewModel
    private lateinit var cartViewModel: CartViewModel

    private var _binding: FragmentCheckoutBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spLogin = activity?.getSharedPreferences("login", MODE_PRIVATE)

        val sessionId = "" + spLogin?.getString("session_id", "")
        val username = "" + spLogin?.getString("username", "")

        // Disable payment button until SDK is connected
        binding.checkoutProceedToPayment.setEnabled(false)

        // Initialize SDK, that will be used for payment operation
        val app2appContainer =
            NexiApp2App().getApp2App(activity as FragmentActivity, view, sessionId, username, fun() {
                // Enable payment button when the SDK is connected
                binding.checkoutProceedToPayment.setEnabled(true)
            })

        if (app2appContainer?.configResponse != null) {
            binding.pointOfSaleContainer.text =
                app2appContainer.configResponse.android_app_user_config.point_of_sale
            binding.terminalIdContainer.text =
                app2appContainer.configResponse.android_app_user_config.terminal_id_softpos
        }

        cartViewModel = ViewModelProvider(
            activity as FragmentActivity, CartViewModelFactory()
        )[CartViewModel::class.java]

        checkoutViewModel = ViewModelProvider(
            activity as FragmentActivity, CheckoutViewModelFactory()
        )[CheckoutViewModel::class.java]

        binding.sendTicketSwitch.isChecked = true
        binding.urlTicketSwitch.isChecked = true

        binding.checkoutBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.checkoutProceedToPayment.setOnClickListener {
            if (app2appContainer?.app2AppIPC != null) {
                binding.debugTitle.visibility = View.GONE
                binding.debugContainer.visibility = View.GONE

                binding.checkoutProgressBar.visibility = View.VISIBLE

                var amount = 0

                cartViewModel.cartItems.value?.forEach {
                    amount += it.value.qty * it.value.product.price
                }

                // Start payment operation
                checkoutViewModel.pay(
                    app2appContainer.app2AppIPC,
                    amount,
                    binding.checkoutEditTextName.text.toString(),
                    binding.checkoutEditTextProvince.text.toString(),
                    binding.checkoutEditTextCity.text.toString(),
                    binding.checkoutEditTextPostalCode.text.toString(),
                    binding.checkoutEditTextPostalAddress.text.toString(),
                    binding.checkoutEditTextEmailAddress.text.toString(),
                    binding.checkoutEditTextPhoneAddress.text.toString(),
                    binding.sendTicketSwitch.isChecked,
                    binding.urlTicketSwitch.isChecked
                )
            } else {
                binding.debugTitle.visibility = View.VISIBLE
                binding.debugContainer.visibility = View.VISIBLE

                binding.debugContainer.text =
                    "C'è stato un errore nell'inizializzazione del pagamento"
            }
        }

        checkoutViewModel.payResult.observe(activity as FragmentActivity, Observer {
            val payResult = it ?: return@Observer

            binding.checkoutProgressBar.visibility = View.GONE

            if (!payResult.isPaymentSuccess) {
                binding.debugTitle.visibility = View.VISIBLE
                binding.debugContainer.visibility = View.VISIBLE

                binding.debugContainer.text =
                    "Pagamento non avviato -- {" + payResult.exceptionString + "} -- {" + payResult.isSuccessString + "}"
            }
        })
    }

}