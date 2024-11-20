package ru.practicum.android.diploma.ui.vacancydetails

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.vacancydetails.BadRequestError
import ru.practicum.android.diploma.data.vacancydetails.ErrorType
import ru.practicum.android.diploma.data.vacancydetails.NoInternetError
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.databinding.FragmentVacancydetailsItemsBinding
import ru.practicum.android.diploma.domain.vacancydetails.models.DetailsNotFoundType
import ru.practicum.android.diploma.domain.vacancydetails.models.VacancyDetails
import ru.practicum.android.diploma.presentation.vacancydetails.VacancyDetailsViewModel
import ru.practicum.android.diploma.presentation.vacancydetails.model.VacancyDetailsState
import ru.practicum.android.diploma.util.DETAILS_VACANCY_ID
import ru.practicum.android.diploma.util.Formatter

class VacancyDetailsFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<VacancyDetailsViewModel>()
    private var vacancy: VacancyDetails? = null
    private var vacancyID: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vacancyID = requireArguments().getString(DETAILS_VACANCY_ID)
        if (vacancyID != null) {
            viewModel.getVacancy(vacancyID!!)
        }
        viewModel.observeVacancyState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is VacancyDetailsState.Content -> {
                    showContent(state)
                }

                is VacancyDetailsState.NoInternet -> {
                    viewModel.checkVacancyInDatabase(vacancyID!!) { exists ->
                        if (exists) {
                            viewModel.getVacancyDatabase(vacancyID!!)
                        } else {
                            showTypeErrorOrEmpty(NoInternetError())
                        }
                    }
                }

                is VacancyDetailsState.Empty -> {
                    showTypeErrorOrEmpty(DetailsNotFoundType())
                    viewModel.deleteFavouriteVacancy(vacancyID!!)
                }

                is VacancyDetailsState.Error -> {
                    showTypeErrorOrEmpty(state.errorType)
                }

                is VacancyDetailsState.Loading -> {
                    showLoading()
                }

                is VacancyDetailsState.isFavorite -> checkFavouriteIcon(state.isFav)
                else -> {}
            }

        }
        setBindings()
    }

    private fun showContent(state: VacancyDetailsState.Content) {
        vacancy = state.vacancy
        hideErrorsAndLoading()
        showVacancyContent(state)
        binding.itemVacancyDetails.itemVacancyDetailsView.visibility = View.VISIBLE
        viewModel.isFavorite(vacancy!!.hhID)
    }

    private fun setBindings() {
        binding.favoriteVacansyIv.setOnClickListener {
            checkIsFavourite(viewModel.getFavouriteState())
        }

        binding.shareVacansyIv.setOnClickListener {
            val intent = viewModel.getSharingIntent()
            if (intent != null) {
                startActivity(intent)
            }
        }

        binding.arrowBackIv.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun checkFavouriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.favoriteVacansyIv.setImageResource(R.drawable.favorite_icon_active)
        } else {
            binding.favoriteVacansyIv.setImageResource(R.drawable.favorite_icon_normal)
        }
    }

    private fun checkIsFavourite(favouriteState: Boolean) {
        if (favouriteState) {
            vacancy!!.hhID.let { id -> viewModel.deleteFavouriteVacancy(id) }
        } else {
            viewModel.addToFavById(vacancy!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun hideErrorsAndLoading() {
        binding.detailsProgressBar.visibility = View.GONE
        binding.errorPlaceholderIv.visibility = View.GONE
        binding.errorPlaceholderTv.visibility = View.GONE
    }

    private fun showTypeErrorOrEmpty(type: ErrorType) {
        binding.detailsProgressBar.visibility = View.GONE
        binding.errorPlaceholderIv.visibility = View.VISIBLE
        binding.errorPlaceholderTv.visibility = View.VISIBLE
        binding.itemVacancyDetails.itemVacancyDetailsView.visibility = View.GONE
        when (type) {
            is BadRequestError -> {
                binding.errorPlaceholderTv.text = getString(R.string.toast_unknown_error)
                binding.errorPlaceholderIv.setImageResource(R.drawable.server_error_vacancy_image)
            }

            is NoInternetError -> {
                binding.errorPlaceholderTv.text = getString(R.string.toast_internet_throwable)
                binding.errorPlaceholderIv.setImageResource(R.drawable.no_internet_image)
            }

            else -> {
                binding.errorPlaceholderTv.text = getString(R.string.no_vacancies_found_title)
                binding.errorPlaceholderIv.setImageResource(R.drawable.cant_take_list_of_vacancy_or_region_image)
            }
        }
    }

    private fun showLoading() {
        binding.itemVacancyDetails.itemVacancyDetailsView.visibility = View.GONE
        binding.detailsProgressBar.visibility = View.VISIBLE
        binding.errorPlaceholderIv.visibility = View.GONE
        binding.errorPlaceholderTv.visibility = View.GONE
    }

    private fun showVacancyContent(state: VacancyDetailsState.Content) {
        val vacancyDetailsBinding = FragmentVacancydetailsItemsBinding.bind(binding.root)
        setVacancyTitle(vacancyDetailsBinding, state)
        setCompanyDetails(vacancyDetailsBinding, state)
        setAddress(vacancyDetailsBinding, state)
        setSalary(vacancyDetailsBinding, state)
        setKeySkills(vacancyDetailsBinding, state)
        setDescription(vacancyDetailsBinding, state)
        setExperience(vacancyDetailsBinding, state)
        setEmployment(vacancyDetailsBinding, state)
        setCompanyLogo(vacancyDetailsBinding, state)
    }

    private fun setVacancyTitle(binding: FragmentVacancydetailsItemsBinding, state: VacancyDetailsState.Content) {
        binding.nameVacancyTv.text = "${state.vacancy.name}"
        binding.cardInfoCompanyCv.visibility = View.VISIBLE
        binding.nameVacancyTv.visibility = View.VISIBLE
    }

    private fun setCompanyDetails(binding: FragmentVacancydetailsItemsBinding, state: VacancyDetailsState.Content) {
        binding.nameCompanyTv.text = state.vacancy.employerInfo.employerName
        binding.nameCompanyTv.visibility = View.VISIBLE
    }

    private fun setAddress(binding: FragmentVacancydetailsItemsBinding, state: VacancyDetailsState.Content) {
        val address = state.vacancy.details.address
        if (address != null) {
            val addressParts = listOfNotNull(address.street, address.building, address.city).filter { it.isNotBlank() }
            val addressText = addressParts.joinToString(", ")
            binding.adressCompanyTv.text = addressText.ifBlank { state.vacancy.employerInfo.areaName }
        } else {
            binding.adressCompanyTv.text = state.vacancy.employerInfo.areaName
        }
    }

    private fun setSalary(binding: FragmentVacancydetailsItemsBinding, state: VacancyDetailsState.Content) {
        val salary = Formatter.formatSalary(requireContext(), state.vacancy.salaryInfo)
        binding.vacancySalaryTv.text = salary
        binding.vacancySalaryTv.visibility = View.VISIBLE
    }

    private fun setKeySkills(binding: FragmentVacancydetailsItemsBinding, state: VacancyDetailsState.Content) {
        val keySkills = state.vacancy.details.keySkills
        if (keySkills.isEmpty()) {
            binding.keySkills.visibility = View.GONE
            binding.vacancyKeySkillsTv.visibility = View.GONE
        } else {
            binding.keySkills.visibility = View.VISIBLE
            binding.vacancyKeySkillsTv.visibility = View.VISIBLE
            val skillsText = keySkills.joinToString(separator = "\n") { "•  ${it.name}" }
            binding.vacancyKeySkillsTv.text = skillsText
        }
    }

    private fun setDescription(binding: FragmentVacancydetailsItemsBinding, state: VacancyDetailsState.Content) {
        val htmlDescription = state.vacancy.details.description
        binding.descriptionVacancy.text = Html.fromHtml(htmlDescription, Html.FROM_HTML_MODE_COMPACT)
        binding.vacancyDescriptionTv.visibility = View.VISIBLE
        binding.descriptionVacancy.visibility = View.VISIBLE
    }

    private fun setExperience(binding: FragmentVacancydetailsItemsBinding, state: VacancyDetailsState.Content) {
        binding.valueExperienceTv.text = state.vacancy.details.experience?.name
        binding.experienceTv.visibility = View.VISIBLE
        binding.valueExperienceTv.visibility = View.VISIBLE
    }

    private fun setEmployment(binding: FragmentVacancydetailsItemsBinding, state: VacancyDetailsState.Content) {
        val employment = state.vacancy.details.employment?.name ?: ""
        val schedule = state.vacancy.details.schedule?.name ?: ""
        binding.formatWorkTv.text = "$employment, $schedule"
    }

    private fun setCompanyLogo(binding: FragmentVacancydetailsItemsBinding, state: VacancyDetailsState.Content) {
        val placeHolderCornerRadius: Int = resources.getDimensionPixelSize(R.dimen.font_12)
        Glide.with(binding.root)
            .load(state.vacancy.employerInfo.employerLogoUrl)
            .placeholder(R.drawable.placeholder)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .transform(CenterCrop(), RoundedCorners(placeHolderCornerRadius))
            .into(binding.logoCompanyIv)
    }

    companion object {
        fun createArgs(vacancyId: String?) = bundleOf(
            DETAILS_VACANCY_ID to vacancyId
        )
    }
}
