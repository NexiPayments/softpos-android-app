package com.example.pizzapay.ui.redirect

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pizzapay.MainActivity
import com.example.pizzapay.R
import com.example.pizzapay.databinding.ActivityRevertResultBinding

class RevertResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRevertResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRevertResultBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.revertNewOrderButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            intent.putExtra("CLEAR_CART", true)

            startActivity(intent)

            setResult(Activity.RESULT_OK)

            finish()
        }

        // Get intent content and show to UI
        binding.transactionIdContainerRevert.text = intent.data?.getQueryParameter("callerTrxId")
        binding.revertFailedDebug.text = intent.toString()

        // Check intent content value and show some text
        if (intent.data?.getQueryParameter("result") == "0") {
            binding.operationResultTitleRevert.text = getString(R.string.revert_success_text_1)
        } else {
            binding.operationResultTitleRevert.text = getString(R.string.revert_failed_text_1)
        }
    }

}