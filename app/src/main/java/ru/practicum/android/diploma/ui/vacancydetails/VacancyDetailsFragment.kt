package ru.practicum.android.diploma.ui.vacancydetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsViewModel
import ru.practicum.android.diploma.util.DETAILS_VACANCY_ID

class VacancyDetailsFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<VacancyDetailsViewModel>()
    private var detailsVacancyID: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsVacancyID = requireArguments().getString(DETAILS_VACANCY_ID)
        if (detailsVacancyID != null) {
            viewModel.getDetailsVacancy(detailsVacancyID!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
