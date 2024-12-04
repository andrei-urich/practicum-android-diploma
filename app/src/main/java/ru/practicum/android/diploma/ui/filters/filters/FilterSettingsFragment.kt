package ru.practicum.android.diploma.ui.filters.filters

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
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
                if (!binding.textInputETSlry.isFocused) {
                    if (s.isNullOrEmpty()) {
                        binding.textInputLTSlry.defaultHintTextColor =
                            ColorStateList.valueOf(requireContext().getColor(R.color.search_hint_day_night))
                    } else {
                        binding.textInputLTSlry.defaultHintTextColor =
                            ColorStateList.valueOf(requireContext().getColor(R.color.black_universal))
                    }
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
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() { backPress() }
        })
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getIsFiltersOnLD().observe(viewLifecycleOwner) { makeVisibleOrGone(binding.btClear, it) }
        viewModel.getFiltersConfigurationLD().observe(viewLifecycleOwner) { renderFilters(it) }
        viewModel.getIsFiltresChangedLD().observe(viewLifecycleOwner) { makeVisibleOrGone(binding.btApply, it) }
        viewModel.getFiltersConfiguration()
        with(binding) {
            edIndustry.addTextChangedListener { clrArIndLT(it, edIndustryLayout, clearIndustryButton) }
            edWorkPlace.addTextChangedListener { clrArIndLT(it, edWorkPlaceLayout, clearAreaButton) }
            val originalFocusListener = textInputETSlry.onFocusChangeListener
            textInputETSlry.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                originalFocusListener.onFocusChange(v, hasFocus)
                if (hasFocus) { setHintColor(textInputLTSlry, R.color.blue) } else {
                    if (textInputETSlry.text.isNullOrEmpty()) {
                        setHintColor(textInputLTSlry, R.color.search_hint_day_night)
                    } else { setHintColor(textInputLTSlry, R.color.black_universal) }
                }
            }
        }
        binding.textInputETSlry.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) binding.textInputLTSlry.clearFocus()
            false
        }
        binding.clearAreaButton.setOnClickListener { viewModel.clearAreas() }
        binding.clearIndustryButton.setOnClickListener { viewModel.clearIndustry() }
        binding.checkBoxSalary.setOnClickListener { viewModel.saveSalaryCheck(binding.checkBoxSalary.isChecked) }
        binding.textInputETSlry.addTextChangedListener(textWatcher)
        binding.backFromFilter.setOnClickListener { backPress() }
        with(binding) {
            edWorkPlace.setOnClickListener { findNavController().navigate(R.id.action_fltrSttngsFT_to_arFltrFT) }
            edIndustry.setOnClickListener { findNavController().navigate(R.id.action_fltrSttngsFT_to_indFltrFT) }
            btClear.setOnClickListener { viewModel.clearFilters() }
            btApply.setOnClickListener {
                viewModel.fixFiltres()
                viewModel.forceSearch()
                findNavController().navigateUp()
            }
        }
    }
    private fun renderFilters(filterUI: FiltersUIModel) {
        with(binding) {
            edWorkPlace.setText(filterUI.areaNCity)
            edIndustry.setText(filterUI.industry)
            textInputETSlry.setText(filterUI.salaryTarget)
            checkBoxSalary.isChecked = filterUI.salaryShowChecked
        }
    }
    private fun makeVisibleOrGone(view: View, visFlag: Boolean) {
        if (visFlag) view.visibility = View.VISIBLE else view.visibility = View.GONE
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
    private fun setHintColor(tIL: TextInputLayout, clrId: Int) {
        tIL.defaultHintTextColor = ColorStateList.valueOf(requireContext().getColor(clrId))
    }
    private fun clrArIndLT(text: Editable?, textLayout: TextInputLayout, clrButton: ImageView) {
        if (text.isNullOrEmpty()) {
            makeVisibleOrGone(clrButton, false)
            setHintColor(textLayout, R.color.grey)
        } else {
            makeVisibleOrGone(clrButton, true)
            setHintColor(textLayout, R.color.black_day_night)
        }
    }
}
