package com.example.plantsbabysitter.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.plantsbabysitter.R
import com.example.plantsbabysitter.data.DataResources
import com.example.plantsbabysitter.ui.MainViewModelFactory


class HomeFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModelFactory()
        ).get(HomeViewModel::class.java)
    }
    private lateinit var text: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text = view.findViewById(R.id.plant_state_text)
        view.findViewById<Button>(R.id.go_to_login_button).setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_login)
        }
        view.findViewById<Button>(R.id.request_state_button).setOnClickListener {
            context?.let { context -> viewModel.requestPlantData(context) }
        }

        observeDataAndUpdateUI()
    }

    private fun observeDataAndUpdateUI() {
        val data = viewModel.getPruebaLiveData()
        data.observe(viewLifecycleOwner, { result ->
            when (result) {
                is DataResources.Success -> {
                    text.text = result.data.value.toString()
                }
                is DataResources.Failure -> {
                    text.text = "error"
                }
            }
        })
    }
}