package ru.practicum.android.diploma.ui.filters.area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentAreaFilterBinding
import ru.practicum.android.diploma.presentation.filters.area.AreaFilterViewModel

class AreaFilterFragment : Fragment() {
    private var _binding: FragmentAreaFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AreaFilterViewModel by viewModel()
    private var countryIsBlank = true
    private var regionIsBlank = true

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

        binding.countryBtnTrailingIcon.setOnClickListener { viewModel.clearCountry() }
        binding.regionBtnTrailingIcon.setOnClickListener { viewModel.clearRegion() }

        binding.toolbar.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.countryBtnLayout.setOnClickListener {
            if (countryIsBlank) {
                findNavController().navigate(
                    R.id.action_areaFilterFragment_to_countryFilterFragment
                )
            }
        }

        binding.regionBtnLayout.setOnClickListener {
            if (regionIsBlank) {
                findNavController().navigate(
                    R.id.action_areaFilterFragment_to_regionFilterFragment
                )
            }
        }

        binding.btApply.setOnClickListener {
            viewModel.setFilter()
        }

        viewModel.getFilterValueLiveData().observe(viewLifecycleOwner) {
            renderData(it)
        }
        viewModel.getButtonChoiceVisibilityLiveData().observe(viewLifecycleOwner) { flag ->
            if (flag) {
                binding.btApply.visibility = View.VISIBLE
            } else {
                binding.btApply.visibility = View.GONE
            }
        }
        viewModel.getScreenExitTrigger().observe(viewLifecycleOwner) { flag ->
            if (flag) requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun clearCountryField() {
        binding.countryBtnTitle.visibility = View.VISIBLE
        binding.countryName.visibility = View.GONE
        binding.countrySupportText.visibility = View.GONE
        binding.countryBtnTrailingIcon.setImageDrawable(
            AppCompatResources.getDrawable(
                requireActivity(),
                R.drawable.leading_icon
            )
        )
        binding.countryBtnTrailingIcon.isClickable = false
        countryIsBlank = true
        binding.btApply.visibility = View.GONE
    }

    private fun clearRegionField() {
        binding.regionBtnTitle.visibility = View.VISIBLE
        binding.regionName.visibility = View.GONE
        binding.regionSupportText.visibility = View.GONE
        binding.regionBtnTrailingIcon.setImageDrawable(
            AppCompatResources.getDrawable(
                requireActivity(),
                R.drawable.leading_icon
            )
        )
        binding.regionBtnTrailingIcon.isClickable = false
        regionIsBlank = true
    }

    private fun renderData(pair: Pair<String, String>) {
        if (pair.first.isNotBlank()) {
            binding.countryBtnTitle.visibility = View.GONE
            binding.countryName.visibility = View.VISIBLE
            binding.countrySupportText.visibility = View.VISIBLE
            binding.countryName.text = pair.first
            binding.countryBtnTrailingIcon.setImageDrawable(
                AppCompatResources.getDrawable(
                    requireActivity(),
                    R.drawable.closing_icon
                )
            )
            binding.countryBtnTrailingIcon.isClickable = true
            countryIsBlank = false
        } else {
            clearCountryField()
        }
        if (pair.second.isNotBlank()) {
            binding.regionBtnTitle.visibility = View.GONE
            binding.regionName.visibility = View.VISIBLE
            binding.regionSupportText.visibility = View.VISIBLE
            binding.regionName.text = pair.second
            binding.regionBtnTrailingIcon.setImageDrawable(
                AppCompatResources.getDrawable(
                    requireActivity(),
                    R.drawable.closing_icon
                )
            )
            binding.regionBtnTrailingIcon.isClickable = true
            regionIsBlank = false
        } else {
            clearRegionField()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFilterSettings()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
