package edu.nku.classapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import edu.nku.classapp.R
import edu.nku.classapp.databinding.FragmentCharacterDetailBinding
import edu.nku.classapp.model.GameOfThronesCharacterResponse
import edu.nku.classapp.viewmodel.GameOfThronesCharacterDetailViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameOfThronesCharacterDetailFragment : Fragment() {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding
        get() = _binding!!

    private val gameOfThronesCharacterDetailViewModel: GameOfThronesCharacterDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)

        setUpObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt(BUNDLE_ID) ?: return
        gameOfThronesCharacterDetailViewModel.fetchCharacterDetail(id)
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            gameOfThronesCharacterDetailViewModel.charactersDetail.collect { event ->
                when (event) {
                    is GameOfThronesCharacterDetailViewModel.GameOfThronesCharacterDetailState.Loading -> {
                        binding.errorMessage.isVisible = false
                        binding.progressBar.isVisible = true
                        binding.contentContainer.isVisible = false
                    }

                    is GameOfThronesCharacterDetailViewModel.GameOfThronesCharacterDetailState.Success -> {
                        binding.progressBar.isVisible = false
                        binding.contentContainer.isVisible = true
                        binding.errorMessage.isVisible = false
                        bindCharacterDetails(event.character)
                    }

                    is GameOfThronesCharacterDetailViewModel.GameOfThronesCharacterDetailState.Failure -> {
                        binding.contentContainer.isVisible = false
                        binding.progressBar.isVisible = false
                        binding.errorMessage.isVisible = true
                    }
                }
            }
        }
    }

    private fun bindCharacterDetails(detail: GameOfThronesCharacterResponse.Character) {
        binding.apply {
            characterTitleDetail.text = getString(R.string.title, detail.title ?: "N/A")
            fullNameDetail.text =
                getString(R.string.full_name, detail.fullName ?: "Unknown")
            familyDetail.text =
                getString(R.string.family, detail.family ?: "N/A")
            idDetail.text = getString(R.string.id, detail.id ?: 0)
            firstNameDetail.text =
                getString(R.string.first_name, detail.firstName)
            lastNameDetail.text =
                getString(R.string.last_name, detail.lastName)

            Glide.with(root)
                .load(detail.imageUrl)
                .into(characterImageDetail)
        }
    }


    companion object {
        private const val BUNDLE_ID = "character_id"
        fun newInstance(id: Int) = GameOfThronesCharacterDetailFragment().apply {
            arguments = bundleOf(BUNDLE_ID to id)
        }
    }
}