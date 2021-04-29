package com.cafrecode.citadel.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.cafrecode.citadel.databinding.FragmentHomeBinding
import com.cafrecode.citadel.ui.QrScanActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        binding.walletAddress.setEndIconOnClickListener {
            startActivityForResult(
                Intent(requireActivity(), QrScanActivity::class.java),
                REQUEST_CODE
            )
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //Get the wallet address and do with it what you need
                Log.i(TAG, "Wallet address is: " + data?.getStringExtra(WALLET_ADDRESS))
                data?.getStringExtra(WALLET_ADDRESS)?.let { checkAccount(it) }
            }
        }
    }

    private fun checkAccount(address: String) {
        viewModel.accountExists(address).observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "ApiResponse: $it")

        })
    }

    companion object {
        val TAG = HomeFragment::class.java.simpleName

        const val REQUEST_CODE = 2021
        const val WALLET_ADDRESS = "wallet_address"
    }
}


