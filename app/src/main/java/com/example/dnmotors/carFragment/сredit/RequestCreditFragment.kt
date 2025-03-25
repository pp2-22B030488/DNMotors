package com.example.dnmotors.carFragment.сredit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.dnmotors.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class RequestCreditFragment : Fragment() {
    private var selectedCityIndex = -1  // Запоминаем выбранный город
    private val cities = arrayOf(
        "Актау", "Актобе", "Алматы", "Астана", "Атырау",
        "Караганда", "Кокшетау", "Костанай", "Кызылорда", "Павлодар"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_request_credit, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val tvSelectedCity = view.findViewById<TextView>(R.id.tv_selected_city)

        tvSelectedCity.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Выберите город")
                .setSingleChoiceItems(cities, selectedCityIndex) { _, which ->
                    selectedCityIndex = which
                }
                .setPositiveButton("ОК") { dialog, _ ->
                    if (selectedCityIndex != -1) {
                        tvSelectedCity.text = cities[selectedCityIndex]
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Отмена") { dialog, _ -> dialog.dismiss() }
                .show()
        }


    }
}


