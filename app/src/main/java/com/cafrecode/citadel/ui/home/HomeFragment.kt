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
import com.cafrecode.citadel.utils.SharedPrefsUtil
import com.cafrecode.citadel.vo.responses.core.ApiErrorResponse
import com.cafrecode.citadel.vo.responses.core.ApiSuccessResponse
import com.cafrecode.citadel.vo.responses.core.GeneralData
import com.cafrecode.citadel.vo.responses.core.currencyFormat
import com.cafrecode.citadel.vo.responses.core.currencyFormatShort
import com.cafrecode.citadel.vo.responses.core.hashrateFormat
import com.cafrecode.citadel.vo.responses.core.round
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    //Instance concerns
    private lateinit var generalInfo: GeneralData
    private lateinit var costInfo: GeneralData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)

        binding.walletAddress.setEndIconOnClickListener {
            startActivityForResult(
                Intent(requireActivity(), QrScanActivity::class.java),
                REQUEST_CODE
            )
        }

        if (SharedPrefsUtil.getDefaultAddress(requireActivity()).isNullOrEmpty()) {
            binding.empty.visibility = View.VISIBLE
            binding.content.visibility = View.GONE
        } else {
            binding.empty.visibility = View.GONE
            binding.content.visibility = View.VISIBLE
            loadStats(SharedPrefsUtil.getDefaultAddress(requireActivity())!!)
            binding.refresh.isRefreshing = true
        }

        binding.refresh.setOnRefreshListener {
            loadStats(SharedPrefsUtil.getDefaultAddress(requireActivity())!!)
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
        //Sanitize address here - monero etc?
        //TODO Refine:
        val cleanAddress = address.replace("monero:", "")

        viewModel.accountExists(cleanAddress).observe(viewLifecycleOwner, {
            Log.d(TAG, "ApiResponse: $it")

            if (it is ApiSuccessResponse) {
                if (it.body.status) {//status is true/false
                    Log.i(TAG, "Found account" + it.body.data)
                    SharedPrefsUtil.setDefaultAddress(requireActivity(), cleanAddress)
                    loadStats(cleanAddress)
                    //we found your account, cache it on shared prefs for later
                } else {
                    Snackbar.make(binding.root, "Failed: " + it.body.data, Snackbar.LENGTH_LONG)
                        .show()
                }
            }

        })
    }

    //TODO: This should be cleaner, with co-routines etc?
    private fun loadStats(address: String) {
        //Hackity. Refine flow later
        // Do these things reactively
        binding.empty.visibility = View.GONE
        binding.content.visibility = View.VISIBLE

        viewModel.generalInfo(address).observe(viewLifecycleOwner, {

            binding.refresh.isRefreshing = false

            if (it is ApiSuccessResponse) {
                generalInfo = it.body.data
                binding.balance = it.body.data.balance.currencyFormat("XMR")
                binding.hashrate = it.body.data.hashrate.hashrateFormat()
                binding.unconfirmedBalance = it.body.data.unconfirmedBalance.currencyFormat("XMR")
                //The two happen after info has been set. maybe find a cleaner way to do this
                loadCurrencies()
                fetchAccountInfo()
            } else if (it is ApiErrorResponse) {
                Log.e(TAG, "Error: " + it.errorMessage)
                Snackbar.make(binding.root, "Unable to fetch data", Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun loadCurrencies() {
        viewModel.prices().observe(viewLifecycleOwner, Observer {
            if (it is ApiSuccessResponse) {
                costInfo = it.body.data
                setCurrencies()
            }
        })
    }

    private fun fetchAccountInfo() {
        viewModel.account(SharedPrefsUtil.getDefaultAddress(requireActivity())!!)
            .observe(viewLifecycleOwner, Observer {

                if (it is ApiSuccessResponse) {
                    loadLimitStatus(generalInfo, it.body.data)
                }
            })
    }

    private fun setCurrencies() {
        //Assumes both general info and currencies are loaded
        if (this::generalInfo.isInitialized && this::costInfo.isInitialized) {
            Log.d(TAG, "Cost Info: $costInfo")
            binding.usd = (generalInfo.balance * costInfo.priceUsd).currencyFormatShort("USD")
            binding.btc = (generalInfo.balance * costInfo.priceBtc).currencyFormatShort("BTC")
            binding.local = (generalInfo.balance * costInfo.priceGbp).currencyFormatShort("GBP")
        }
    }

    private fun loadLimitStatus(info: GeneralData, account: GeneralData) {

        val progress = (info.balance / account.payout * 100).toFloat()
        binding.remaining.circleView.setValue(progress!!)
        binding.remaining.circleView.setText(progress.round(2).toString())
    }

    private fun loadAds() {
        val adLoader = AdLoader.Builder(requireActivity(), "ca-app-pub-3940256099942544/2247696110")
            .forNativeAd { ad: NativeAd ->
                // Show the ad.

            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Handle the failure by logging, altering the UI, and so on.
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    // Methods in the NativeAdOptions.Builder class can be
                    // used here to specify individual options settings.
                    .build()
            )
            .build()
        adLoader.loadAd(AdRequest.Builder().build())
    }

    companion object {
        val TAG: String = HomeFragment::class.java.simpleName

        const val REQUEST_CODE = 2021
        const val WALLET_ADDRESS = "wallet_address"
    }
}


