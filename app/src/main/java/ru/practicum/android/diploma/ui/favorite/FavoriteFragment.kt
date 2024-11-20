package ru.practicum.android.diploma.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoriteBinding
import ru.practicum.android.diploma.domain.search.models.VacancyShort
import ru.practicum.android.diploma.presentation.favorite.FavoriteViewModel
import ru.practicum.android.diploma.presentation.favorite.FavoritesScreenState
import ru.practicum.android.diploma.presentation.favorite.VacanciesRecyclerViewAdapter
import ru.practicum.android.diploma.ui.vacancydetails.VacancyDetailsFragment
import ru.practicum.android.diploma.util.CLICK_FAVORITE_DEBOUNCE_DELAY
import ru.practicum.android.diploma.util.debounce

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FavoriteViewModel>()
    private val vacancyList: MutableList<VacancyShort> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingScreenState()
        binding.favoriteEmptyListPlaceholder.setImageResource(R.drawable.empty_list_image)
        binding.favoritesCantGetListPlaceholder.setImageResource(R.drawable.cant_take_list_of_vacancy_or_region_image)
        binding.favoritesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val favoriteRecyclerViewAdapter = VacanciesRecyclerViewAdapter(vacancyList)
        binding.favoritesRecyclerView.adapter = favoriteRecyclerViewAdapter
        val onVacancyClickDebounce =
            debounce<String?>(CLICK_FAVORITE_DEBOUNCE_DELAY, viewLifecycleOwner.lifecycleScope, false) {
                findNavController().navigate(
                    R.id.action_favoriteFragment_to_vacancyDetailsFragment,
                    VacancyDetailsFragment.createArgs(it)
                )
            }
        favoriteRecyclerViewAdapter.setOnClickListener(object : VacanciesRecyclerViewAdapter.OnClickListener {
            override fun onClick(position: Int, vacancy: VacancyShort) {
                onVacancyClickDebounce(vacancy.vacancyId)
            }

        })
        viewModel.getFavoriteVacanciesScreenStateLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is FavoritesScreenState.LoadingFavoriteScreen -> loadingScreenState()
                is FavoritesScreenState.EmptyFavoriteScreen -> emptyScreenState()
                is FavoritesScreenState.FilledFavoriteScreen -> {
                    vacancyList.clear()
                    vacancyList.addAll(it.listOfFavoriteVacancies.reversed())
                    favoriteRecyclerViewAdapter.notifyDataSetChanged()
                    filledScreenState()
                }

                is FavoritesScreenState.Error -> showErrorPlaceholders()
            }
        }
        viewModel.getFavoriteVacanciesList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showErrorPlaceholders() {
        with(binding) {
            favoritesCantGetListPlaceholder.visibility = View.VISIBLE
            favoritesPlaceholderCantGetListText.visibility = View.VISIBLE
            favoriteProgressBar.visibility = View.INVISIBLE
            favoritesRecyclerView.visibility = View.INVISIBLE
            favoriteEmptyListPlaceholder.visibility = View.INVISIBLE
            favoritesPlaceholderEmptyListText.visibility = View.INVISIBLE
        }
    }

    private fun loadingScreenState() {
        with(binding) {
            favoriteProgressBar.visibility = View.VISIBLE
            favoritesRecyclerView.visibility = View.INVISIBLE
            favoriteEmptyListPlaceholder.visibility = View.INVISIBLE
            favoritesPlaceholderEmptyListText.visibility = View.INVISIBLE
            favoritesPlaceholderCantGetListText.visibility = View.INVISIBLE
            favoritesCantGetListPlaceholder.visibility = View.INVISIBLE
        }
    }

    private fun emptyScreenState() {
        with(binding) {
            favoriteProgressBar.visibility = View.INVISIBLE
            favoritesRecyclerView.visibility = View.INVISIBLE
            favoriteEmptyListPlaceholder.visibility = View.VISIBLE
            favoritesPlaceholderEmptyListText.visibility = View.VISIBLE
            favoritesPlaceholderCantGetListText.visibility = View.INVISIBLE
            favoritesCantGetListPlaceholder.visibility = View.INVISIBLE
        }
    }

    private fun filledScreenState() {
        with(binding) {
            favoriteProgressBar.visibility = View.INVISIBLE
            favoritesRecyclerView.visibility = View.VISIBLE
            favoriteEmptyListPlaceholder.visibility = View.INVISIBLE
            favoritesPlaceholderEmptyListText.visibility = View.INVISIBLE
            favoritesPlaceholderCantGetListText.visibility = View.INVISIBLE
            favoritesCantGetListPlaceholder.visibility = View.INVISIBLE
        }
    }
}
