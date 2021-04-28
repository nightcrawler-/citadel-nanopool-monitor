package com.cafrecode.citadel.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cafrecode.citadel.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

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

        if (resultCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //Get the wallet address and do with it what you need
                Log.i(TAG, "Wallet address is: " + data?.getStringExtra("WALLET_ADDRESS"))
            }
            //This is the result you need
        }
    }

    companion object {
        val TAG = HomeFragment::class.java.simpleName

        const val REQUEST_CODE = 2021
        const val WALLET_ADDRESS = "wallet_address"
    }
}

// Intent data = new Intent();
// String text = "Result to be returned...."
// //---set the data to pass back---
// data.setData(Uri.parse(text));
// setResult(RESULT_OK, data);
// //---close the activity---
// finish();
//
// fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
//     if (requestCode == request_Code) {
//         if (resultCode == RESULT_OK) {
//             val returnedResult = data.data.toString()
//             // OR
//             // String returnedResult = data.getDataString();
//         }
//     }
// }