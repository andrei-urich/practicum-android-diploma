package ru.practicum.android.diploma.ui.filters.area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentAreaFilterBinding
import ru.practicum.android.diploma.presentation.filters.area.AreaFilterViewModel
import ru.practicum.android.diploma.presentation.filters.area.AreaState

class AreaFilterFragment : Fragment() {
    private var _binding: FragmentAreaFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AreaFilterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAreaFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.countryBtnLayout.setOnClickListener {
            findNavController().navigate(
                R.id.action_areaFilterFragment_to_countryFilterFragment
            )
        }

        binding.regionBtnLayout.setOnClickListener {
            findNavController().navigate(
                R.id.action_areaFilterFragment_to_regionFilterFragment
            )
        }

        viewModel.getStateLiveData().observe(viewLifecycleOwner) { state ->
            renderState(state)
        }
    }

    private fun renderState(state: AreaState) {
        println(state)
    }
}
