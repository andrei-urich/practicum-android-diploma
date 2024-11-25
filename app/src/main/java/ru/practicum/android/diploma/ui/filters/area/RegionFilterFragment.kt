package ru.practicum.android.diploma.ui.filters.area

import android.os.Bundle
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
    }

    private fun renderState(pair: Pair<List<Region>?, Int?>) {
        binding.pbRegion.visibility = View.GONE
        when (pair.first) {
            null -> {
                binding.rvRegion.visibility = View.GONE
            }

            else -> {
                binding.rvRegion.visibility = View.VISIBLE
                binding.rvRegion.adapter = adapter
                regionList.clear()
                regionList.addAll(pair.first as MutableList<Region>)
                adapter.notifyDataSetChanged()
            }
        }
    }
}
