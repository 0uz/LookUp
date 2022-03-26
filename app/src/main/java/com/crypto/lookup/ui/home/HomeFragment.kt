package com.crypto.lookup.ui.home

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.binance.api.client.BinanceApiClientFactory
import com.binance.api.client.BinanceApiRestClient
import com.binance.api.client.BinanceApiWebSocketClient
import com.binance.api.client.domain.market.CandlestickInterval
import com.crypto.lookup.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val candleStickChart: CandleStickChart = binding.candleStickChart
        candleStickChart.axisRight.isEnabled = false
        homeViewModel.getData().observe(this, Observer {
            Log.w("OBSERVE","TEST")
            val candleDataSet = CandleDataSet(it,"Data")
            val candleData = CandleData(candleDataSet)
            candleStickChart.data = candleData;
            candleDataSet.axisDependency = YAxis.AxisDependency.LEFT
            candleDataSet.shadowColor = Color.GRAY
            candleDataSet.shadowWidth = 0.5f
            candleDataSet.decreasingColor = Color.RED
            candleDataSet.increasingColor = Color.GREEN
            candleDataSet.neutralColor = Color.BLUE
            candleDataSet.increasingPaintStyle = Paint.Style.FILL
            candleDataSet.decreasingPaintStyle = Paint.Style.FILL
            candleStickChart.invalidate()
        })

    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

    }







    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}