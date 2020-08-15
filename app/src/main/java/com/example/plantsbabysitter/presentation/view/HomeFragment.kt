package com.example.plantsbabysitter.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.plantsbabysitter.R
import com.example.plantsbabysitter.presentation.viewmodel.HomeViewModel
import com.example.plantsbabysitter.presentation.viewmodel.MainViewModelFactory
import com.example.plantsbabysitter.util.DataResources


class HomeFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this,
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
        view.findViewById<Button>(R.id.continue_button).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        observeDataAndUpdateUI()
    }

    private fun observeDataAndUpdateUI() {
        val data = viewModel.getPruebaLiveData()
        data.observe(viewLifecycleOwner, Observer {
            when(it) {
                is DataResources.Success -> {
                    text.text = it.data.value.toString()
                }
                is DataResources.Failure -> {
                    text.text = "error"
                }
            }
        })
    }
}