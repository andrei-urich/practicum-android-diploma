package ru.practicum.android.diploma.ui.filters.area

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practicum.android.diploma.databinding.FragmentCountryFilterBinding
import ru.practicum.android.diploma.presentation.filters.area.CountryFilterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CountryFilterFragment : Fragment() {
    private var _binding: FragmentCountryFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CountryFilterViewModel by viewModel()
    private val countryList = mutableListOf<String>()
    private val adapter: AreaListAdapter by lazy {
        AreaListAdapter(countryList, viewModel::setArea)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCountry.layoutManager = LinearLayoutManager(context)
        binding.rvCountry.adapter = adapter
        binding.toolbar.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.pbCountry.visibility = View.VISIBLE
        viewModel.getAreaList()
    }
}
