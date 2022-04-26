package com.crypto.lookup.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.crypto.lookup.databinding.FragmentDashboardBinding
import com.crypto.lookup.ui.login.UserViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var sharedViewModel: UserViewModel
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashboardViewModel.setCurUser(sharedViewModel.getCurrentUser())
        val layoutManager = LinearLayoutManager(context)
        recyclerViewCoins.layoutManager = layoutManager
        recyclerViewCoins.adapter = dashboardViewModel.addCoinPriceAdapter



        dashboardViewModel.coinListData.observe(this, Observer {
            dashboardViewModel.setAdapterData(it.coins)
//            dashboardViewModel.setSubscribedCoinsData() TODO
            binding.dashboardPB.isVisible = false
            binding.scrolviewDashview.isVisible = true
        })

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                dashboardViewModel.addCoinPriceAdapter.filter.filter(query)
                return false
            }

        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}