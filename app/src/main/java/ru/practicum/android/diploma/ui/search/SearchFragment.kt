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
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.search.models.VacancyShort
import ru.practicum.android.diploma.presentation.search.SearchState
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.util.EMPTY_STRING

class SearchFragment : Fragment() {
    private var searchText = EMPTY_STRING
    private var _viewBinding: FragmentSearchBinding? = null
    private val binding get() = _viewBinding!!

    private var vacancies = mutableListOf<VacancyShort>()

    private val viewModel: SearchViewModel by viewModel()
    lateinit var searchAdapter: SearchAdapter

    private val inputMethodManager by lazy { ->
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

        val searchFilter = binding.searchFilter
        val searchBar = binding.searchEditText
        searchBar.setText(searchText)
        searchAdapter = SearchAdapter(vacancies, viewModel::showVacancy)


        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                println()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchText = s.toString()
                clearScreen(clearScreenFlag(s))
                if (searchBar.hasFocus() && s?.isEmpty() == true) viewModel.onSearchTextChanged(true) else viewModel.onSearchTextChanged(
                    false
                )
                viewModel.getSearchText(searchText)
            }

            override fun afterTextChanged(s: Editable?) {
                println()
            }
        }
        searchBar.addTextChangedListener(searchTextWatcher)

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

                else -> {}
            }
        }

        viewModel.getOpenTrigger().observe(viewLifecycleOwner) { vacancy ->
            showVacancy(vacancy.vacancyId)
        }

    }

    private fun showVacancy(vacancyId: String?) {
        val action: NavDirections = SearchFragmentDirections.actionSearchFragmentToVacancyDetailsFragment
        findNavController().navigate(
            action
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

                if (vacancies.isNotEmpty() == true) {
                    searchAdapter.notifyDataSetChanged()

                } else {
                    binding.mainProgressBar.visibility = View.GONE
                    showSearchError(SEARCH_ERROR)
                }
            }
        }
    }

    // метод очистки
    private fun clearScreenFlag(s: CharSequence?): Boolean {
        if (s.isNullOrBlank()) {
            return true
        }
        clearPlaceholders()
        return false
    }

    private fun clearScreen() {
        binding.searchEditText.setText(EMPTY_STRING)
        clearPlaceholders()
        inputMethodManager?.hideSoftInputFromWindow(binding.searchScreen.windowToken, 0)
        viewModel.onClearButtonChangeListener(false)
        binding.searchEditText.clearFocus()
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
        viewModel.showSearchErrorChangeChangeListener(false)
        if (codeError.equals(SEARCH_ERROR)) {
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

    companion object {
        private const val ERROR = "ERROR"
        private const val SEARCH_ERROR = "SEARCH_ERROR"
        private const val CONNECTION_ERROR = "CONNECTION_ERROR"
        private const val LOADING = "LOADING"
        private const val SHOW_RESULT = "SHOW_RESULT"
    }
}
