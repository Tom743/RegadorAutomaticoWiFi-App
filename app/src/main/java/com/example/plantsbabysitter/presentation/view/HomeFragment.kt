package com.example.plantsbabysitter.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.plantsbabysitter.R
import com.example.plantsbabysitter.domain.repository.RepositoryImpl
import com.example.plantsbabysitter.domain.usecases.UseCaseImpl
import com.example.plantsbabysitter.presentation.viewmodel.HomeViewModel
import com.example.plantsbabysitter.presentation.viewmodel.MainViewModelFactory

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {
    
    private val viewModel by lazy {
        ViewModelProvider(this,
            MainViewModelFactory(UseCaseImpl(RepositoryImpl()))
        ).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.continue_button).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
}