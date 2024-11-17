package ru.practicum.android.diploma.ui.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.search.models.VacancyShort
import ru.practicum.android.diploma.presentation.search.SearchState
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.util.CONNECTION_ERROR
import ru.practicum.android.diploma.util.EMPTY_STRING
import ru.practicum.android.diploma.util.ERROR
import ru.practicum.android.diploma.util.LOADING
import ru.practicum.android.diploma.util.SEARCH_ERROR
import ru.practicum.android.diploma.util.SHOW_RESULT

class SearchFragment : Fragment() {
    private var searchText = EMPTY_STRING
    private var _viewBinding: FragmentSearchBinding? = null
    private val binding get() = _viewBinding!!
    private var vacancies = mutableListOf<VacancyShort>()
    private val viewModel: SearchViewModel by viewModel()
    private val searchAdapter = SearchAdapter(vacancies, viewModel::showVacancy)
    private val inputMethodManager by lazy {
        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchEditText.setText(searchText)

        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                println()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchText = s.toString()
                clearScreen(s)
                viewModel.getSearchText(searchText)
            }

            override fun afterTextChanged(s: Editable?) {
                println()
            }
        }
        binding.searchEditText.addTextChangedListener(searchTextWatcher)

        viewModel.getSearchStateLiveData().observe(viewLifecycleOwner) { searchState ->
            when (searchState) {
                is SearchState.Error -> {
                    changeContentVisibility(showCase = ERROR)
                }

                is SearchState.Content -> {
                    vacancies.clear()
                    vacancies.addAll(searchState.vacancyList.toMutableList())
                    changeContentVisibility(showCase = SHOW_RESULT)
                }

                is SearchState.Loading -> {
                    changeContentVisibility(showCase = LOADING)
                }

                else -> {
                    changeContentVisibility(showCase = ERROR)
                }
            }
        }

        viewModel.getOpenTrigger().observe(viewLifecycleOwner) { vacancy ->
            showVacancy(vacancy.vacancyId)
        }

    }

    private fun showVacancy(vacancyId: String?) {
        println(vacancyId)
        findNavController().navigate(
            R.id.action_searchFragment_to_vacancyDetailsFragment
        )
    }

    private fun changeContentVisibility(showCase: String) {
        when (showCase) {
            ERROR -> {
                binding.mainProgressBar.visibility = View.GONE
                inputMethodManager?.hideSoftInputFromWindow(binding.searchScreen.windowToken, 0)
                showSearchError(CONNECTION_ERROR)
            }

            LOADING -> {
                clearPlaceholders()
                binding.mainProgressBar.visibility = View.VISIBLE
            }

            SHOW_RESULT -> {
                clearPlaceholders()
                binding.mainProgressBar.visibility = View.GONE
                binding.vacancyListRv.adapter = searchAdapter
                binding.vacancyListRv.layoutManager = LinearLayoutManager(requireActivity())
                binding.vacancyListRv.visibility = View.VISIBLE

                if (vacancies.isNotEmpty()) {
                    searchAdapter.notifyDataSetChanged()

                } else {
                    binding.mainProgressBar.visibility = View.GONE
                    showSearchError(SEARCH_ERROR)
                }
            }
        }
    }

    private fun clearScreen(s: CharSequence?) {
        if (s.isNullOrBlank()) {
            binding.searchEditText.setText(EMPTY_STRING)
            clearPlaceholders()
            inputMethodManager?.hideSoftInputFromWindow(binding.searchScreen.windowToken, 0)
            binding.searchEditText.clearFocus()
        }
    }

    private fun clearPlaceholders() {
        binding.placeholderNoVacancyListMessage.visibility = View.GONE
        binding.placeholderServerError.visibility = View.GONE
        binding.placeholderImage.visibility = View.GONE
        binding.placeholderNoInternet.visibility = View.GONE
        binding.placeholderNoVacancyListMessage.visibility = View.GONE
        binding.placeholderNoInternetMessage.visibility = View.GONE
        binding.placeholderServerErrorMessage.visibility = View.GONE
    }

    private fun showSearchError(codeError: String) {
        if (codeError == SEARCH_ERROR) {
            binding.placeholderNoVacancyList.visibility = View.VISIBLE
            vacancies.clear()
            searchAdapter.notifyDataSetChanged()

        } else {
            binding.placeholderServerError.visibility = View.VISIBLE
            vacancies.clear()
            searchAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

}
