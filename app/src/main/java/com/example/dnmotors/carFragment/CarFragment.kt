package com.example.dnmotors.carFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.dnmotors.R
import com.example.dnmotors.databinding.FragmentCarBinding

class CarFragment : Fragment() {

    private var _binding: FragmentCarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        val logo = view.findViewById<ImageView>(R.id.logo)

        logo.setOnClickListener {
            // Добавь действие, например, показать Toast
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}