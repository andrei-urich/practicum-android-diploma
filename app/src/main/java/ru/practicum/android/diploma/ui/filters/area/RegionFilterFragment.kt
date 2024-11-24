package ru.practicum.android.diploma.ui.filters.area

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filters.area.RegionFilterViewModel

class RegionFilterFragment : Fragment() {

    companion object {
        fun newInstance() = RegionFilterFragment()
    }

    private val viewModel: RegionFilterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_region_filter, container, false)
    }
}
