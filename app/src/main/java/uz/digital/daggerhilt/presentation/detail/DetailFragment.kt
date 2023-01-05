package uz.digital.daggerhilt.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.digital.daggerhilt.R
import uz.digital.daggerhilt.databinding.FragmentDetailBinding
import uz.digital.daggerhilt.model.Post
import uz.digital.daggerhilt.util.snackBar

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var id: Int? = null
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt("id")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        viewModel.onEvent(DetailEvent.OnGetOnePost(id ?: 1))
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is DetailState.Loading -> {
                        binding.pr.isVisible = true
                        binding.btnDelete.isVisible = false
                    }
                    is DetailState.Error -> {
                        binding.pr.isVisible = false
                        binding.btnDelete.isVisible = true
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                    is DetailState.Success -> {
                        binding.pr.isVisible = false
                        binding.btnDelete.isVisible = true
                        binding.tvTitle.text = state.post.title
                        binding.tvBody.text = state.post.body
                    }
                    is DetailState.SuccessDeleted -> {
                        binding.pr.isVisible = false
                        binding.btnDelete.isVisible = false
                        snackBar("Successfully deleted!")
                        findNavController().popBackStack()
                    }
                }
            }
        }
        binding.btnDelete.setOnClickListener {
            viewModel.onEvent(DetailEvent.OnDelete(id ?: 1))
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnUpdate.setOnClickListener {
            val postId = bundleOf("id" to this.id)
            findNavController().navigate(R.id.action_detailFragment_to_addUpdateFragment, postId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}