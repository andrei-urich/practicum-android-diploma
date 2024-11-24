package ru.practicum.android.diploma.ui.filters.area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentRegionFilterBinding
import ru.practicum.android.diploma.presentation.filters.area.RegionFilterViewModel

class RegionFilterFragment : Fragment() {
    private var _binding: FragmentRegionFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegionFilterViewModel by viewModel()
    private val regionList = mutableListOf<String>()
    private val adapter: AreaListAdapter by lazy {
        AreaListAdapter(regionList, viewModel::setArea)
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
    }
}
