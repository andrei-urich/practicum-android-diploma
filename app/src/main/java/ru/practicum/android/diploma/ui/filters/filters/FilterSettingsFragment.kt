package ru.practicum.android.diploma.ui.filters.filters

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding
import ru.practicum.android.diploma.presentation.filters.settings.FilterSettingsViewModel
import ru.practicum.android.diploma.presentation.filters.settings.FiltersUIModel
import ru.practicum.android.diploma.util.debounce

class FilterSettingsFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FilterSettingsViewModel>()
    private val saveSalaryTargetDebounce by lazy {
        debounce<String>(SAVE_SALARY_TARGET_DEBOUNCE_DELAY, viewLifecycleOwner.lifecycleScope, true) { newSalary ->
            viewModel.saveNewSalaryTarget(newSalary)
        }
    }
    private val textWatcher by lazy {
        object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                println()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                saveSalaryTargetDebounce(s.toString())
                if (s.isNullOrEmpty() && !binding.textInputEditTextSalary.isFocused) {
                    binding.textInputLayoutSalary.defaultHintTextColor =
                        ColorStateList.valueOf(requireContext().getColor(R.color.grey))
                } else if (!binding.textInputEditTextSalary.isFocused) {
                    binding.textInputLayoutSalary.defaultHintTextColor =
                        ColorStateList.valueOf(requireContext().getColor(R.color.black_universal))
                }
            }

            override fun afterTextChanged(s: Editable?) {
                println()
            }
        }

    }

    companion object {
        private const val SAVE_SALARY_TARGET_DEBOUNCE_DELAY = 100L
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getIsFiltersOnLD().observe(viewLifecycleOwner) { if (it) showClearButton() else hideClearButton() }
        viewModel.getFiltersConfigurationLD().observe(viewLifecycleOwner) { renderFilters(it) }
        viewModel.getIsFiltresChangedLD().observe(viewLifecycleOwner) {
            if (it) showApplyButton() else hideApplyButton()
        }
        viewModel.getFiltersConfiguration()
        binding.edIndustry.addTextChangedListener {
            if (it.isNullOrEmpty()) {
                makeGone(binding.clearIndustryButton)
                binding.edIndustryLayout.defaultHintTextColor =
                    ColorStateList.valueOf(requireContext().getColor(R.color.grey))
            } else {
                makeVisible(binding.clearIndustryButton)
                binding.edIndustryLayout.defaultHintTextColor =
                    ColorStateList.valueOf(requireContext().getColor(R.color.black_day_night))
            }
        }

        binding.edWorkPlace.addTextChangedListener {
            if (it.isNullOrEmpty()) {
                makeGone(binding.clearAreaButton)
                binding.edWorkPlaceLayout.defaultHintTextColor =
                    ColorStateList.valueOf(requireContext().getColor(R.color.grey))
            } else {
                makeVisible(binding.clearAreaButton)
                binding.edWorkPlaceLayout.defaultHintTextColor =
                    ColorStateList.valueOf(requireContext().getColor(R.color.black_day_night))
            }
        }
        binding.textInputEditTextSalary.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.textInputLayoutSalary.defaultHintTextColor =
                    ColorStateList.valueOf(requireContext().getColor(R.color.blue))
            } else {
                if (binding.textInputEditTextSalary.text.isNullOrEmpty()) {
                    binding.textInputLayoutSalary.defaultHintTextColor =
                        ColorStateList.valueOf(requireContext().getColor(R.color.grey))
                } else {
                    binding.textInputLayoutSalary.defaultHintTextColor =
                        ColorStateList.valueOf(requireContext().getColor(R.color.black_universal))
                }
            }
        }
        binding.textInputEditTextSalary.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.textInputLayoutSalary.clearFocus()
            }
            false
        }
        binding.clearAreaButton.setOnClickListener { viewModel.clearAreas() }
        binding.clearIndustryButton.setOnClickListener { viewModel.clearIndustry() }
        binding.checkBoxSalary.setOnClickListener {
            viewModel.saveSalaryShowCheckFilter(binding.checkBoxSalary.isChecked)
        }
        binding.textInputEditTextSalary.addTextChangedListener(textWatcher)
        binding.backFromFilter.setOnClickListener { backPress() }
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backPress()
            }
        })
        binding.edWorkPlace.setOnClickListener {
            findNavController().navigate(R.id.action_filterSettingsFragment_to_areaFilterFragment)
        }
        binding.edIndustry.setOnClickListener {
            findNavController().navigate(R.id.action_filterSettingsFragment_to_industryFilterFragment)
        }
        binding.btClear.setOnClickListener { viewModel.clearFilters() }
        binding.btApply.setOnClickListener {
            viewModel.fixFiltres()
            viewModel.forceSearch()
            findNavController().navigateUp()
        }
    }

    private fun hideClearButton() { makeGone(binding.btClear) }
    private fun showClearButton() { makeVisible(binding.btClear) }
    private fun hideApplyButton() { makeGone(binding.btApply) }
    private fun showApplyButton() { makeVisible(binding.btApply) }
    private fun renderFilters(filterUI: FiltersUIModel) {
        with(binding) {
            edWorkPlace.setText(filterUI.areaNCity)
            edIndustry.setText(filterUI.industry)
            textInputEditTextSalary.setText(filterUI.salaryTarget)
            checkBoxSalary.isChecked = filterUI.salaryShowChecked
        }
    }
    private fun makeVisible(view: View) {
        view.visibility = View.VISIBLE
    }

    private fun makeGone(view: View) {
        view.visibility = View.GONE
    }
    private fun backPress() {
        viewModel.fixFiltres()
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        })
    }
}
