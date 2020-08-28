package com.example.plantsbabysitter.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.plantsbabysitter.R
import com.example.plantsbabysitter.data.DataResources
import com.example.plantsbabysitter.ui.MainViewModelFactory
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlin.math.cos
import kotlin.math.sin


class HomeFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModelFactory()
        ).get(HomeViewModel::class.java)
    }

    private lateinit var chart: LineChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.go_to_login_button).setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_login)
        }
        view.findViewById<Button>(R.id.water_now_button).setOnClickListener {
            context?.let { context -> viewModel.waterPlant(context) }
        }
        view.findViewById<Button>(R.id.request_state_button).setOnClickListener {
            context?.let { context -> viewModel.requestPlantData(context) }
        }

        // Example chart
        chart = view.findViewById(R.id.chart)
        val values1: MutableList<Entry> = ArrayList()
        for (x in 1..10) {
            var num = (sin(x.toDouble()).toFloat()*100)
            if (num < 0) {
                num *=-1
            }
            values1.add(Entry(x.toFloat(), num))
        }
        val values2: MutableList<Entry> = ArrayList()
        for (x in 1..10) {
            var num = (cos(x.toDouble()).toFloat()*100)
            if (num < 0) {
                num *=-1
            }
            values2.add(Entry(x.toFloat(), num))
        }

        val setValues1 = LineDataSet(values1, "sin of x")
        setValues1.axisDependency = (YAxis.AxisDependency.LEFT)
        val setValues2 = LineDataSet(values2, "cos of x")
        setValues2.axisDependency = (YAxis.AxisDependency.LEFT)
        setValues2.color = R.color.colorPrimary

        val dataSets: MutableList<ILineDataSet> = ArrayList()
        dataSets.add(setValues1)
        dataSets.add(setValues2)

        val data = LineData(dataSets)
        chart.data = data
        chart.invalidate() // refresh

        observeData()
    }

    private fun observeData() {
        context?.let { viewModel.getPlantLiveData(it) }?.observe(viewLifecycleOwner, { result ->
            when (result) {
                // TODO: 21/AUG/2020 Do something with data
                is DataResources.Success -> {
                    print(result.data)
                    Toast.makeText(context, "Some data received", Toast.LENGTH_SHORT).show()
                }
                is DataResources.Failure -> {
                    Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}