package ru.practicum.android.diploma.ui.filters

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.domain.filters.Filters
import ru.practicum.android.diploma.presentation.filters.settings.FilterSettingsViewModel
import ru.practicum.android.diploma.util.debounce

class FilterSettingsFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FilterSettingsViewModel>()

    companion object {
        private const val SAVE_SALARY_TARGET_DEBOUNCE_DELAY = 100L
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getIsFiltersOnLD().observe(viewLifecycleOwner) {
            if (it) showClearButton() else hideClearButton()
        }
        viewModel.getFiltersConfigurationLD().observe(viewLifecycleOwner) {
            renderFilters(it)
        }
        viewModel.getIsFiltresChangedLD().observe(viewLifecycleOwner) {
            if (it) showApplyButton() else hideApplyButton()
        }
        viewModel.getFiltersConfiguration()
        val saveSalaryTargetDebounce =
            debounce<String>(SAVE_SALARY_TARGET_DEBOUNCE_DELAY, viewLifecycleOwner.lifecycleScope, true) { newSalary ->
                viewModel.saveNewSalaryTarget(newSalary)
            }
        binding.textInputEditTextSalary.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                println()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                saveSalaryTargetDebounce(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                println()
            }

        })
        binding.checkBoxSalary.setOnClickListener {
            viewModel.saveSalaryShowCheckFilter(binding.checkBoxSalary.isChecked)
        }

        binding.backFromFilter.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.edWorkPlace.setOnClickListener {
            findNavController().navigate(R.id.action_filterSettingsFragment_to_areaFilterFragment)
        }
        binding.edIndustry.setOnClickListener {
            findNavController().navigate(R.id.action_filterSettingsFragment_to_industryFilterFragment)
        }
        binding.btClear.setOnClickListener {
            viewModel.clearFilters()
        }
        binding.btApply.setOnClickListener {
            viewModel.fixFiltres()
        }
    }

    private fun hideClearButton() {
        binding.btClear.visibility = View.GONE
    }

    private fun showClearButton() {
        binding.btClear.visibility = View.VISIBLE
    }

    private fun hideApplyButton() {
        binding.btApply.visibility = View.GONE
    }

    private fun showApplyButton() {
        binding.btApply.visibility = View.VISIBLE
    }

    private fun renderFilters(filter: Filters) {
        with(binding) {
            edWorkPlace.setText(filter.getAreaNCity())
            edIndustry.setText(filter.getIndustry())
            textInputEditTextSalary.setText(filter.getSalaryTarget())
            checkBoxSalary.isChecked = filter.getSalaryShowChecked()
        }
    }
}
