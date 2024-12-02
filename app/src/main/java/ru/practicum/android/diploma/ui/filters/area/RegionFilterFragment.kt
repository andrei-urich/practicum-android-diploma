package ru.practicum.android.diploma.ui.filters.area

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentRegionFilterBinding
import ru.practicum.android.diploma.domain.filters.area.model.Region
import ru.practicum.android.diploma.presentation.filters.area.RegionFilterViewModel
import ru.practicum.android.diploma.presentation.filters.area.SearchAreaState

class RegionFilterFragment : Fragment() {
    private var _binding: FragmentRegionFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegionFilterViewModel by viewModel()
    private val regionList = mutableListOf<Region>()
    private var searchText = EMPTY_STRING
    private val adapter: AreaListAdapter by lazy {
        AreaListAdapter(regionList, viewModel::setRegion)
    }
    private val inputMethodManager by lazy {
        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegionFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvRegion.layoutManager = LinearLayoutManager(context)
        binding.rvRegion.adapter = adapter
        binding.toolbar.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.pbRegion.visibility = View.VISIBLE
        viewModel.getAreaList()
        viewModel.getRegionsListState().observe(viewLifecycleOwner) {
            renderState(it)
        }
        viewModel.getScreenExitTrigger().observe(viewLifecycleOwner) { flag ->
            if (flag) {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }

        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                println()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchText = s.toString()
                if (s?.isEmpty() == true) {
                    viewModel.clearScreen(true)
                } else {
                    binding.iconSearch.visibility = View.GONE
                }
                viewModel.getSearchText(searchText)
            }

            override fun afterTextChanged(s: Editable?) {
                println()
            }
        }
        binding.searchEditText.addTextChangedListener(searchTextWatcher)

    }

    private fun renderState(state: SearchAreaState) {
        when (state) {
            SearchAreaState.Prepared -> {
                clearScreenAndReload()
            }

            SearchAreaState.Loading -> {
                clearScreen()
                binding.pbRegion.visibility = View.VISIBLE
            }

            is SearchAreaState.Content -> {
                clearScreen()
                if (state.areaList.isNotEmpty()) {
                    binding.rvRegion.visibility = View.VISIBLE
                    binding.rvRegion.adapter = adapter
                    regionList.clear()
                    regionList.addAll(state.areaList as MutableList<Region>)
                    adapter.notifyDataSetChanged()

                } else {
                    showError(NOTHING_FOUND)
                }
            }

            is SearchAreaState.Error -> {
                clearScreen()
                showError(state.resultCode)
            }

        }
    }

    private fun showError(error: Int?) {
        when (error) {
            NOTHING_FOUND -> {
                binding.errorNoRegion.visibility = View.VISIBLE
            }

            else -> {
                binding.errorNoList.visibility = View.VISIBLE
            }
        }
    }

    private fun clearScreenAndReload() {
        clearScreen()
        binding.iconSearch.visibility = View.VISIBLE
        binding.rvRegion.visibility = View.VISIBLE
        viewModel.getAreaList()
    }

    private fun clearScreen() {
        inputMethodManager?.hideSoftInputFromWindow(binding.regionFilter.windowToken, 0)
        binding.errorNoRegion.visibility = View.GONE
        binding.errorNoList.visibility = View.GONE
        binding.rvRegion.visibility = View.GONE
        binding.pbRegion.visibility = View.GONE
    }

    private companion object {
        const val EMPTY_STRING = ""
        const val NOTHING_FOUND = 0
    }
}
