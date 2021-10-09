package com.example.projectar.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import com.example.projectar.R
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.databinding.FragmentComposeBinding
import com.example.projectar.ui.functional.viewmodel.ProductViewModelImpl
import com.example.projectar.ui.testing.TestComposable.DbButtons
import com.example.projectar.ui.theme.ProjectarTheme
import com.example.projectar.ui.utils.NavFunction
import com.example.projectar.ui.utils.NavUtils


class ComposeFragment : Fragment() {
    private val db by lazy { ApplicationDatabase.get(requireContext()) }
    private var _binding: FragmentComposeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentComposeBinding.inflate(inflater, container, false)
        val view = binding.root

        view.findViewById<ComposeView>(R.id.compose_view).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ProjectarTheme {
                    SetUp() { dest ->
                        findNavController().navigate(dest)

                    }
                }
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    @Composable
    fun SetUp(navFunction: NavFunction) {
        val viewModel: ProductViewModelImpl = viewModel(requireActivity())
        val navController = rememberNavController()
        NavUtils.CreateNavigator(navController, viewModel, navFunction)
    }
}
