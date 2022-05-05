package com.crypto.lookup.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.crypto.lookup.data.listeners.onGetDataListListener
import com.crypto.lookup.databinding.FragmentHomeBinding
import com.crypto.lookup.ui.login.UserViewModel
import com.google.firebase.firestore.DocumentSnapshot

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var sharedViewModel: UserViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var signalCoinService: SignalCoinService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        sharedViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        signalCoinService = SignalCoinService(SignalCoinFirebaseDaoImpl())
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeProgressBar.visibility = View.VISIBLE
        binding.signalScrollView.visibility = View.INVISIBLE


        val layoutManagerSignalCoin = LinearLayoutManager(context)
        binding.signalRecylerView.layoutManager = layoutManagerSignalCoin
        binding.signalRecylerView.adapter = homeViewModel.signalCoinAdapter

        initData()
        homeViewModel.signalCoinListData.observe(viewLifecycleOwner) {
            homeViewModel.setAdapterData(it)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun initData() {
        signalCoinService.retrieve(sharedViewModel.getCurrentUser().subscribedCoins, object : onGetDataListListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onSuccess(data: List<DocumentSnapshot>) {
                Log.w("DATA", data.toString())
                val coinData = ArrayList<SignalCoin>()
                data.forEach {
                    if (data.isNotEmpty()) coinData.add(it.toObject(SignalCoin::class.java)!!)
                }
                homeViewModel.dataUpdate(5000, coinData)
                binding.homeProgressBar.visibility = View.GONE
                binding.signalScrollView.visibility = View.VISIBLE
                if (coinData.size < 1) binding.signalNoCoin.visibility = View.VISIBLE
                else binding.signalNoCoin.visibility = View.GONE
            }

            override fun onFailed(e: Exception) {

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}