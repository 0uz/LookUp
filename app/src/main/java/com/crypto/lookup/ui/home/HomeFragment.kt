package com.crypto.lookup.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.crypto.lookup.data.listeners.onGetDataListListener
import com.crypto.lookup.data.listeners.onGetDataListener
import com.crypto.lookup.databinding.FragmentHomeBinding
import com.crypto.lookup.ui.login.UserViewModel
import com.google.firebase.firestore.DocumentSnapshot

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var sharedViewModel: UserViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var signalCoinService : SignalCoinService

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
        val layoutManagerSignalCoin = LinearLayoutManager(context)
        binding.signalRecylerView.layoutManager = layoutManagerSignalCoin
        binding.signalRecylerView.adapter = homeViewModel.signalCoinAdapter
//        homeViewModel.fakeData() TODO IT IS FAKE DATA
        homeViewModel.signalCoinListData.observe(viewLifecycleOwner) {


        }
        homeViewModel.signalCoinListData.postValue(SignalCoinList(arrayListOf(SignalCoin(symbol = "BNBUSDT",null,null,null,null))))

        homeViewModel.initData(arrayListOf(
            SignalCoin(symbol = "BTCUSDT",null,null,null,null),
            SignalCoin(symbol = "ETHUSDT",null,null,null,null),
        ))
        Log.w("QUERRY",homeViewModel.signalCoinListData.value.toString())
//        signalCoinService.retrieve(homeViewModel.signalCoinListData.value!! ,object :onGetDataListListener{
//
//            override fun onSuccess(data: List<DocumentSnapshot>) {
//                Log.w("DATA",data.toString())
//            }
//
//            override fun onFailed(e: Exception) {
//
//            }
//
//        })
        homeViewModel.signalCoinListData.postValue(SignalCoinList(arrayListOf(SignalCoin(symbol = "BTCUSDT",null,null,null,null))))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}