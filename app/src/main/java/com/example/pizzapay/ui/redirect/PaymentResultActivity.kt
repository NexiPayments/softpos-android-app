package com.example.pizzapay.ui.redirect

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.pizzapay.MainActivity
import com.example.pizzapay.NexiApp2App
import com.example.pizzapay.R
import com.example.pizzapay.databinding.ActivityPaymentResultBinding

class PaymentResultActivity : AppCompatActivity() {

    private lateinit var rootView: View

    private lateinit var operationResultViewModel: OperationResultViewModel

    private lateinit var binding: ActivityPaymentResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaymentResultBinding.inflate(layoutInflater)

        setContentView(binding.root)

        rootView = findViewById(android.R.id.content)

        val spLogin = getSharedPreferences("login", MODE_PRIVATE)

        val sessionId = "" + spLogin.getString("session_id", "")
        val username = "" + spLogin.getString("username", "")

        // Disable revert button until SDK is connected
        binding.revertTransaction.setEnabled(false)

        // Initialize SDK, that will be used for revert operation
        val app2appContainer = NexiApp2App().getApp2App(this, rootView, sessionId, username, fun() {
            // Enable revert button when the SDK is connected
            binding.revertTransaction.setEnabled(true)
        })

        operationResultViewModel = ViewModelProvider(
            this, OperationResultViewModelFactory()
        )[OperationResultViewModel::class.java]

        binding.revertTransaction.setOnClickListener {
            if (app2appContainer?.app2AppIPC != null) {
                // Start revert operation
                operationResultViewModel.revert(
                    app2appContainer.app2AppIPC, // SDK object
                    intent.data?.getQueryParameter("amount")?.toInt() ?: 0,
                    intent.data?.getQueryParameter("terminalId") ?: ""
                )
            } else {
                binding.paymentFailedDebug.text = getString(R.string.revert_text_2)
            }
        }

        binding.newOrderButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            intent.putExtra("CLEAR_CART", true)

            startActivity(intent)

            setResult(Activity.RESULT_OK)

            finish()
        }

        // Get intent content and show to UI
        binding.transactionIdContainer.text = intent.data?.getQueryParameter("callerTrxId")
        binding.paymentFailedDebug.text = intent.toString()

        // Check intent content value and show button and text
        if (intent.data?.getQueryParameter("result") == "0") {
            binding.operationResultTitle.text = getString(R.string.payment_success_text_1)

            binding.revertTransaction.visibility = View.VISIBLE
        } else {
            binding.operationResultTitle.text = getString(R.string.payment_failed_text_1)

            binding.revertTransaction.visibility = View.GONE
        }
    }

}