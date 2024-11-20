package ru.practicum.android.diploma.ui.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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
import ru.practicum.android.diploma.util.EMPTY_STRING
import ru.practicum.android.diploma.util.RESULT_CODE_BAD_REQUEST
import ru.practicum.android.diploma.util.RESULT_CODE_NO_INTERNET_ERROR
import ru.practicum.android.diploma.util.RESULT_CODE_SERVER_ERROR
import ru.practicum.android.diploma.util.ZERO

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
                    viewModel.clearScreen(true)
                    binding.vacancyListRv.visibility = View.GONE
                    binding.vacanciesFound.visibility = View.GONE
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

        viewModel.getSearchStateLiveData().observe(viewLifecycleOwner) { pair ->
            changeContentVisibility(pair.first, pair.second)
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
                    if (pos == itemsCount - 1) {
                        viewModel.getNextPage()
                    }
                }
            }
        })
    }

    private fun showVacancy(vacancyId: String?) {
        findNavController().navigate(
            R.id.action_searchFragment_to_vacancyDetailsFragment,
            VacancyDetailsFragment.createArgs(vacancyId)
        )
    }

    private fun changeContentVisibility(searchState: SearchState, position: Int?) {
        when (searchState) {
            is SearchState.Prepared -> {
                clearScreen()
                startPlaceholderVisibility(true)
            }

            is SearchState.LoadingError -> {
                showSearchError(searchState.resultCode, false)
            }

            is SearchState.NextPageLoadingError -> {
                showSearchError(searchState.resultCode, true)
            }

            is SearchState.Content -> {
                clearScreen()
                vacancies.clear()
                vacancies.addAll(searchState.vacancyList.toMutableList())
                renderContent(vacancies, position)
            }


            is SearchState.Loading -> {
                clearScreen()
                binding.mainProgressBar.visibility = View.VISIBLE
            }

            is SearchState.LoadingNextPage -> {
                clearScreen()
                binding.recyclerViewProgressBar.visibility = View.VISIBLE
            }
        }
    }

    private fun clearScreen() {
        clearPlaceholders()
        inputMethodManager?.hideSoftInputFromWindow(binding.searchScreen.windowToken, 0)
    }

    private fun clearPlaceholders() {
        binding.placeholderNoVacancyList.visibility = View.GONE
        binding.placeholderServerError.visibility = View.GONE
        binding.placeholderImage.visibility = View.GONE
        binding.placeholderNoInternet.visibility = View.GONE
        binding.placeholderNoVacancyListMessage.visibility = View.GONE
        binding.placeholderNoInternetMessage.visibility = View.GONE
        binding.placeholderServerErrorMessage.visibility = View.GONE
        binding.vacanciesFound.visibility = View.GONE
        binding.mainProgressBar.visibility = View.GONE
        binding.recyclerViewProgressBar.visibility = View.GONE
    }

    private fun showSearchError(codeError: Int?, isLoadingNextPage: Boolean) {
        if (isLoadingNextPage) {
            binding.recyclerViewProgressBar.visibility = View.GONE
            when (codeError) {
                RESULT_CODE_NO_INTERNET_ERROR -> {
                    val message = requireActivity().resources.getString(R.string.toast_internet_throwable)
                    Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
                }

                RESULT_CODE_SERVER_ERROR, RESULT_CODE_BAD_REQUEST -> {
                    val message = requireActivity().resources.getString(R.string.toast_unknown_error)
                    Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            binding.mainProgressBar.visibility = View.GONE
            binding.vacancyListRv.visibility = View.GONE
            binding.vacanciesFound.visibility = View.GONE
            inputMethodManager?.hideSoftInputFromWindow(binding.searchScreen.windowToken, 0)
            when (codeError) {
                null -> {
                    binding.placeholderNoVacancyList.visibility = View.VISIBLE
                    binding.placeholderNoVacancyListMessage.visibility = View.VISIBLE
                    binding.vacanciesFound.visibility = View.VISIBLE
                    binding.vacanciesFound.text = requireActivity().getString(R.string.no_vacancies_found_title)
                }

                RESULT_CODE_NO_INTERNET_ERROR -> {
                    binding.placeholderNoInternet.visibility = View.VISIBLE
                    binding.placeholderNoInternetMessage.visibility = View.VISIBLE
                }

                RESULT_CODE_SERVER_ERROR, RESULT_CODE_BAD_REQUEST -> {
                    binding.placeholderServerError.visibility = View.VISIBLE
                    binding.placeholderServerErrorMessage.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun startPlaceholderVisibility(flag: Boolean) {
        if (flag) {
            clearScreen()
            binding.placeholderImage.visibility = View.VISIBLE
            binding.iconSearch.visibility = View.VISIBLE
        } else {
            binding.placeholderImage.visibility = View.GONE
            binding.iconSearch.visibility = View.GONE
        }
    }

    private fun renderContent(vacancies: List<VacancyShort>, position: Int?) {
        if (vacancies.isNotEmpty()) {
            binding.vacanciesFound.text = requireActivity().resources.getQuantityString(
                R.plurals.vacancy_number,
                vacancies[ZERO].found,
                vacancies[ZERO].found
            )
            binding.vacancyListRv.visibility = View.VISIBLE
            binding.vacanciesFound.visibility = View.VISIBLE
            binding.vacancyListRv.adapter = searchAdapter
            searchAdapter.notifyDataSetChanged()

            if (position != null) {
                binding.vacancyListRv.scrollToPosition(position)
            }
        } else {
            showSearchError(null, false)
        }
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
