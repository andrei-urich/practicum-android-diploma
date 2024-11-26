package ru.practicum.android.diploma.ui.filters.area

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentRegionFilterBinding
import ru.practicum.android.diploma.domain.filters.area.model.Region
import ru.practicum.android.diploma.presentation.filters.area.RegionFilterViewModel

class RegionFilterFragment : Fragment() {
    private var _binding: FragmentRegionFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegionFilterViewModel by viewModel()
    private val regionList = mutableListOf<Region>()
    private var searchText = EMPTY_STRING
    private val adapter: AreaListAdapter by lazy {
        AreaListAdapter(regionList, viewModel::setRegion)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
//                    viewModel.clearScreen(true)
                }
                viewModel.getSearchText(searchText)
            }

            override fun afterTextChanged(s: Editable?) {
                println()
            }
        }
        binding.searchEditText.addTextChangedListener(searchTextWatcher)

    }

    private fun renderState(pair: Pair<List<Region>?, Int?>) {
        binding.pbRegion.visibility = View.GONE
        when (pair.first) {
            null -> {
                binding.rvRegion.visibility = View.GONE
                val errorCode = pair.second
                if (errorCode != null) {
                    showError(errorCode)
                } else {
                    clearScreen()
                }
            }

            else -> {

                Log.d("MY", pair.first.toString())

                binding.rvRegion.visibility = View.VISIBLE
                binding.rvRegion.adapter = adapter
                regionList.clear()
                regionList.addAll(pair.first as MutableList<Region>)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun showError(error: Int) {
        binding.rvRegion.visibility = View.GONE
        when (error) {
            NOTHING_FOUND -> {
                binding.errorNoRegion.visibility = View.VISIBLE
            }

            else -> {
                binding.errorNoList.visibility = View.VISIBLE
            }
        }
    }

    private fun clearScreen() {
        binding.rvRegion.visibility = View.VISIBLE
        binding.errorNoRegion.visibility = View.GONE
        binding.errorNoList.visibility = View.GONE
        viewModel.getAreaList()
    }

    private companion object {
        const val EMPTY_STRING = ""
        const val NOTHING_FOUND = 0
    }
}
