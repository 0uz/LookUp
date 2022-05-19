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
        binding.homeInfoLayout.visibility = View.INVISIBLE


        val layoutManagerSignalCoin = LinearLayoutManager(context)
        binding.signalRecylerView.layoutManager = layoutManagerSignalCoin
        binding.signalRecylerView.adapter = homeViewModel.signalCoinAdapter
        initData()
        homeViewModel.signalCoinListData.observe(viewLifecycleOwner) { data ->
            homeViewModel.setAdapterData(data)
            val df = DecimalFormat("#.##")
            var sum: Float = 0F
            data.signalCoins.forEach {
                sum += (it.currentPrice - it.openPrice) / it.openPrice * 100
            }
            binding.totalProfit.text = "%" + df.format(sum)
            binding.totalProfit.setTextColor(Color.GREEN)
            if (sum < 0) binding.totalProfit.setTextColor(Color.RED)

            if (data.signalCoins.size > 1) {
                binding.signalScrollView.visibility = View.VISIBLE
                binding.homeInfoLayout.visibility = View.VISIBLE
                binding.signalNoCoin.visibility = View.GONE
            } else {
                binding.signalNoCoin.visibility = View.VISIBLE
            }
            binding.homeProgressBar.visibility = View.GONE

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

//                val first8 = arrayListOf<SignalCoin>()
//                first8.addAll(coinData.subList(0,8))
//                homeViewModel.dataUpdate(5000, first8, true) TODO for optimize lazy

                binding.totalOpenPosition.text = coinData.stream().filter { it.isOpen == true }.count().toString()
                binding.totalClosedPosition.text = coinData.stream().filter { it.isOpen == false }.count().toString()



                dataUpdate = homeViewModel.dataUpdate(5000, coinData)

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