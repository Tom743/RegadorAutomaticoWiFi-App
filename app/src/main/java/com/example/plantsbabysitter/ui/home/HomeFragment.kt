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

        chart = view.findViewById(R.id.chart)

        view.findViewById<Button>(R.id.go_to_login_button).setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_login)
        }
        view.findViewById<Button>(R.id.water_now_button).setOnClickListener {
            context?.let { context -> viewModel.waterPlant(context) }
        }
        view.findViewById<Button>(R.id.request_state_button).setOnClickListener {
            context?.let { context -> viewModel.requestPlantData(context) }
        }

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