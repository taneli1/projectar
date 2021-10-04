package com.example.projectar.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectar.R
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.utils.ProductCreator
import com.example.projectar.databinding.FragmentComposeBinding
import com.example.projectar.di.Injector
import com.example.projectar.ui.screens.MainList
import com.example.projectar.ui.screens.SingleProduct
import com.example.projectar.ui.theme.ProjectarTheme
import com.example.projectar.ui.utils.NavUtils
import com.example.projectar.ui.viewmodel.ProductViewModel


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
                    SetUp()
//                TestComposable.ArScreenTest()
                    Row() {
                        Button(onClick = { ProductCreator.nuke(db) }) {
                            Text(text = "Nuke")
                        }
                        Button(onClick = { ProductCreator.createProducts(db) }) {
                            Text(text = "Create")
                        }
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
    fun SetUp() {
        val viewModel: ProductViewModel = viewModel(
            factory = Injector.provideProductViewModelFactory(db, requireContext())
        )
        val data: List<Product> by viewModel.products.observeAsState(listOf())

        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "testList") {
            composable(
                "singleProduct/{product}",
                arguments = listOf(navArgument("product") {
                    type = NavType.LongType
                })
            ) { backStackEntry ->
                backStackEntry.arguments?.getLong("product")?.let { json ->
                    val product = data.find { it.data.id == json }
                    if (product != null) {
                        SingleProduct(product = product, navController)
                    }
                }
            }
            composable("testList") {
                MainList(data) {
                    NavUtils.navigate(
                        navController,
                        "singleProduct/$it"
                    )
                }
            }
        }
    }
}
