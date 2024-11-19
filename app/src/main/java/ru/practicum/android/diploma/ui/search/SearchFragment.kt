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
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.search.models.VacancyShort
import ru.practicum.android.diploma.presentation.search.SearchState
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.ui.vacancydetails.VacancyDetailsFragment
import ru.practicum.android.diploma.util.CONNECTION_ERROR
import ru.practicum.android.diploma.util.EMPTY_STRING
import ru.practicum.android.diploma.util.ERROR
import ru.practicum.android.diploma.util.FIRST
import ru.practicum.android.diploma.util.LOADING
import ru.practicum.android.diploma.util.NEXT
import ru.practicum.android.diploma.util.PREVIOUS
import ru.practicum.android.diploma.util.SEARCH_ERROR
import ru.practicum.android.diploma.util.SECOND
import ru.practicum.android.diploma.util.SHOW_RESULT

class SearchFragment : Fragment() {
    private var searchText = EMPTY_STRING
    private var _viewBinding: FragmentSearchBinding? = null
    private val binding get() = _viewBinding!!
    private var vacancies = mutableListOf<VacancyShort>()
    private val viewModel: SearchViewModel by viewModel()
    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter(vacancies, viewModel::showVacancy)
    }
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

        startPlaceholderVisibility(true)
        binding.vacancyListRv.layoutManager = LinearLayoutManager(requireActivity())

        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                println()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchText = s.toString()
                if (binding.searchEditText.hasFocus() && s?.isEmpty() == true) {
                    startPlaceholderVisibility(true)
                    binding.vacancyListRv.visibility = View.GONE
                } else {
                    startPlaceholderVisibility(false)
                }
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

        binding.vacancyListRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val pos = (binding.vacancyListRv.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = searchAdapter.itemCount
                    if (pos >= itemsCount - FIRST) {
                        viewModel.getNextPage(NEXT)
                    }
                }

//                if (dy < 0) {
//                    val pos =
//                        (binding.vacancyListRv.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
//                    if (pos <= SECOND) {
//                        viewModel.getNextPage(PREVIOUS)
//                    }
//                }
            }
        })
    }

    private fun showVacancy(vacancyId: String?) {
        findNavController().navigate(
            R.id.action_searchFragment_to_vacancyDetailsFragment,
            VacancyDetailsFragment.createArgs(vacancyId)
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
                clearScreen(showCase)
                binding.mainProgressBar.visibility = View.VISIBLE
            }

            SHOW_RESULT -> {
                clearScreen(showCase)

                binding.vacancyListRv.visibility = View.VISIBLE

                if (vacancies.isNotEmpty()) {
                    binding.vacanciesFound.text = vacancies.get(0).found.toString()
                    binding.vacanciesFound.visibility = View.VISIBLE
                    binding.mainProgressBar.visibility = View.GONE
                    binding.vacancyListRv.adapter = searchAdapter
                    searchAdapter.notifyDataSetChanged()

                } else {
                    binding.mainProgressBar.visibility = View.GONE
                    showSearchError(SEARCH_ERROR)
                }
            }
        }
    }

    private fun clearScreen(s: CharSequence?) {
        if (!s.isNullOrBlank()) {
            clearPlaceholders()
            inputMethodManager?.hideSoftInputFromWindow(binding.searchScreen.windowToken, 0)
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
        binding.vacanciesFound.visibility = View.GONE
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

    private fun startPlaceholderVisibility(flag: Boolean) {
        if (flag) {
            binding.placeholderImage.visibility = View.VISIBLE
            binding.iconSearch.visibility = View.VISIBLE
        } else {
            binding.placeholderImage.visibility = View.GONE
            binding.iconSearch.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
