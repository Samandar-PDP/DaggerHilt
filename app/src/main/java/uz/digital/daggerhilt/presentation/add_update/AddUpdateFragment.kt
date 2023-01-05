package uz.digital.daggerhilt.presentation.add_update

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.digital.daggerhilt.R
import uz.digital.daggerhilt.databinding.FragmentAddUpdateBinding
import uz.digital.daggerhilt.model.Post
import uz.digital.daggerhilt.util.snackBar

@AndroidEntryPoint
class AddUpdateFragment : Fragment(R.layout.fragment_add_update) {
    private var _binding: FragmentAddUpdateBinding? = null
    private val binding get() = _binding!!
    private var id: Int? = null
    private val viewModel: AddUpdateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt("id")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddUpdateBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        if (id != null) {
            binding.toolbar.title = "Update Post"
            binding.btnAddUpdate.text = "Update"
            viewModel.onEvent(AddUpdateEvent.OnGetOnePost(id!!))
        } else {
            binding.toolbar.title = "Create Post"
            binding.btnAddUpdate.text = "Create"
        }
        observeViewModel()
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnAddUpdate.setOnClickListener {
            if (id != null) {
                viewModel.onEvent(AddUpdateEvent.OnUpdatePost(id!!))
            } else {
                val title = binding.tvTitle.text.toString().trim()
                val body = binding.tvBody.text.toString().trim()
                if (title.isNotBlank() && body.isNotBlank()) {
                    viewModel.onEvent(AddUpdateEvent.OnCreatePost(Post(title = title, body = body)))
                } else {
                    snackBar("Enter data!")
                }
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is AddUpdateState.Loading -> {
                        binding.pr.isVisible = true
                        binding.btnAddUpdate.isVisible = false
                    }
                    is AddUpdateState.Idle -> Unit
                    is AddUpdateState.SuccessUpdated -> {
                        snackBar("Successfully updated!")
                        binding.pr.isVisible = false
                    }
                    is AddUpdateState.SuccessCreated -> {
                        snackBar("Successfully created!")
                        binding.pr.isVisible = false
                        findNavController().popBackStack()
                    }
                    is AddUpdateState.Error -> {
                        binding.pr.isVisible = false
                        snackBar(it.message)
                    }
                    is AddUpdateState.SuccessPostGot -> {
                        binding.apply {
                            pr.isVisible = false
                            btnAddUpdate.isVisible = true
                            tvTitle.setText(it.post.title)
                            tvBody.setText(it.post.body)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}