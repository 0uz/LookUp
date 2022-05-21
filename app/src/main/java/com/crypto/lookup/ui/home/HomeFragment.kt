package com.crypto.lookup.ui.home

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.crypto.lookup.data.listeners.onGetDataListListener
import com.crypto.lookup.data.listeners.onGetNoDataListener
import com.crypto.lookup.databinding.FragmentHomeBinding
import com.crypto.lookup.ui.login.UserViewModel
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.Job
import java.text.DecimalFormat

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var sharedViewModel: UserViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var signalCoinService: SignalCoinService
    private var dataUpdate: Job? = null
    private var updatedSignals = 0L
    private var signalSize = 0L

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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeProgressBar.visibility = View.VISIBLE
        binding.signalScrollView.visibility = View.INVISIBLE
        binding.homeInfoLayout.visibility = View.INVISIBLE


        val layoutManagerSignalCoin = LinearLayoutManager(context)
        binding.signalRecylerView.layoutManager = layoutManagerSignalCoin
        binding.signalRecylerView.adapter = homeViewModel.signalCoinAdapter
        initData()
        homeViewModel.signalCoinListData.observe(viewLifecycleOwner) { data ->
            homeViewModel.setAdapterData(data)
            val df = DecimalFormat("#.##")
            var currentSum: Float = 0F
            var closedSum: Float = 0F
            data.signalCoins.forEach {
                if (it.isOpen) {
                    currentSum += (it.currentPrice - it.openPrice) / it.openPrice * 100
                } else {
                    closedSum += (it.closePrice!! - it.openPrice) / it.openPrice * 100
                }
            }
            binding.totalCurrentProfit.text = "%" + df.format(currentSum)
            binding.totalCurrentProfit.setTextColor(if (currentSum > 0) Color.GREEN else Color.RED)
            binding.totalProfit.text = "%" + df.format(closedSum)
            binding.totalProfit.setTextColor(if (closedSum > 0) Color.GREEN else Color.RED)


            if (data.signalCoins.size > 1) {
                binding.signalScrollView.visibility = View.VISIBLE
                binding.homeInfoLayout.visibility = View.VISIBLE
                binding.signalNoCoin.visibility = View.GONE
            } else {
                binding.signalNoCoin.visibility = View.VISIBLE
            }
            binding.homeProgressBar.visibility = View.GONE

            binding.liveDataPB.setProgress(((updatedSignals * 100) / signalSize).toInt(), true)
            if (signalSize == updatedSignals) {
                binding.liveDataPB.visibility = View.GONE
                binding.totalCurrentProfit.visibility = View.VISIBLE

            }

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    fun initData() {
        signalCoinService.retrieve(sharedViewModel.getCurrentUser().subscribedCoins, object : onGetDataListListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onSuccess(data: List<DocumentSnapshot>) {
                val coinData = ArrayList<SignalCoin>()
                data.forEach {
                    if (data.isNotEmpty()) coinData.add(it.toObject(SignalCoin::class.java)!!)
                }
                signalSize = coinData.stream().filter { it.isOpen }.count()

//                val first8 = arrayListOf<SignalCoin>()
//                first8.addAll(coinData.subList(0,8))
//                homeViewModel.dataUpdate(5000, first8, true) TODO for optimize lazy

                binding.totalOpenPosition.text = coinData.stream().filter { it.isOpen == true }.count().toString()
                binding.totalClosedPosition.text = coinData.stream().filter { it.isOpen == false }.count().toString()



                dataUpdate = homeViewModel.dataUpdate(5000, coinData, object : onGetNoDataListener {
                    override fun onSuccess() {
                        updatedSignals++
                    }

                    override fun onFailed(e: Exception) {
                        TODO("Not yet implemented")
                    }

                })


            }

            override fun onFailed(e: Exception) {

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        dataUpdate?.cancel()
    }
}