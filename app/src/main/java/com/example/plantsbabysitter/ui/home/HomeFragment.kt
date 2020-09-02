package com.example.plantsbabysitter.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
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
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.firebase.database.DataSnapshot
import java.text.DateFormat.SHORT
import java.text.DateFormat.getTimeInstance
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModelFactory()
        ).get(HomeViewModel::class.java)
    }

    private lateinit var chart: LineChart
    private val humidityValues: MutableList<Entry> = ArrayList()
    private val temperatureValues: MutableList<Entry> = ArrayList()
    private val lightValues: MutableList<Entry> = ArrayList()

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

        chart = view.findViewById(R.id.chart)
        chart.setDrawGridBackground(false)
        chart.setNoDataText(getString(R.string.no_data_message))
        chart.setNoDataTextColor(Color.BLACK)
        val description = Description()
        description.text = getString(R.string.chart_label)
        chart.description = description
        val xAxis = chart.xAxis
        xAxis.setValueFormatter { value, _ ->
            val dateFormat = getTimeInstance(SHORT)
            dateFormat.format(Date(value.toLong()*1000))
        }
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelRotationAngle = 90f

        observeData()
    }

    @SuppressLint("RestrictedApi")  // TODO 29/AUG/2020 Try to avoid this
    private fun refreshChartData(data: DataSnapshot) {
        for (telemetry in data.children) {
            if (telemetry.ref.path.toString() != getString(R.string.water_now_path)) {
                val time = telemetry.child("time").value.toString().toFloat()
                humidityValues.add(
                    Entry(time, telemetry.child("humidity").value.toString().toFloat())
                )
                temperatureValues.add(
                    Entry(time, telemetry.child("temperature").value.toString().toFloat())
                )
                lightValues.add(Entry(time, telemetry.child("light").value.toString().toFloat()))
            }
        }

        val humidityValuesSet = LineDataSet(humidityValues, getString(R.string.humidity))
        humidityValuesSet.axisDependency = YAxis.AxisDependency.LEFT
        humidityValuesSet.setColors(Color.BLUE)
        humidityValuesSet.setCircleColor(Color.BLUE)
        humidityValuesSet.setDrawCircleHole(false)

        val temperatureValuesSet = LineDataSet(temperatureValues, getString(R.string.temperature))
        temperatureValuesSet.axisDependency = YAxis.AxisDependency.LEFT
        temperatureValuesSet.setColors(Color.RED)
        temperatureValuesSet.setCircleColor(Color.RED)
        temperatureValuesSet.setDrawCircleHole(false)

        val lightValuesSet = LineDataSet(lightValues, getString(R.string.light))
        lightValuesSet.axisDependency = YAxis.AxisDependency.LEFT
        lightValuesSet.setColors(Color.GREEN)
        lightValuesSet.setCircleColor(Color.GREEN)
        lightValuesSet.setDrawCircleHole(false)

        val dataSets: MutableList<ILineDataSet> = ArrayList()
        dataSets.add(humidityValuesSet)
        dataSets.add(temperatureValuesSet)
        dataSets.add(lightValuesSet)

        chart.data = LineData(dataSets)
        chart.invalidate()

        Toast.makeText(context, getString(R.string.data_updated), Toast.LENGTH_SHORT).show()
    }

    private fun observeData() {
        context?.let { viewModel.getPlantLiveData(it) }?.observe(viewLifecycleOwner, { result ->
            when (result) {
                is DataResources.Success -> {
                    //if (result.data is real data) { // TODO
                    refreshChartData(result.data)
                    //}
                }
                is DataResources.Failure -> {
                    Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}