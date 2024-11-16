package ru.practicum.android.diploma.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFavoriteBinding
import ru.practicum.android.diploma.domain.search.models.VacancyShort
import ru.practicum.android.diploma.presentation.favorite.FavoriteViewModel
import ru.practicum.android.diploma.presentation.favorite.FavoritesScreenState
import ru.practicum.android.diploma.presentation.favorite.VacanciesRecyclerViewAdapter

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
        binding.favoritesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val favoriteRecyclerViewAdapter = VacanciesRecyclerViewAdapter(vacancyList)
        binding.favoritesRecyclerView.adapter = favoriteRecyclerViewAdapter
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
            }
        }
        viewModel.getTestList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadingScreenState() {
        with(binding) {
            favoriteProgressBar.visibility = View.VISIBLE
            favoritesRecyclerView.visibility = View.INVISIBLE
            favoriteEmptyListPlaceholder.visibility = View.INVISIBLE
        }
    }

    private fun emptyScreenState() {
        with(binding) {
            favoriteProgressBar.visibility = View.INVISIBLE
            favoritesRecyclerView.visibility = View.INVISIBLE
            favoriteEmptyListPlaceholder.visibility = View.VISIBLE
        }
    }

    private fun filledScreenState() {
        with(binding) {
            favoriteProgressBar.visibility = View.INVISIBLE
            favoritesRecyclerView.visibility = View.VISIBLE
            favoriteEmptyListPlaceholder.visibility = View.INVISIBLE
        }
    }
}
