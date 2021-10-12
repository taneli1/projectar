package com.example.projectar.ui.fragment


import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.projectar.R
import com.example.projectar.ui.utils.ArViewUiProvider
import com.google.ar.sceneform.ux.ArFragment

/**
 * A Fragment class which provides the AR-View for the app.
 *
 * This fragment handles the augmented reality stuff. Since we cannot override
 * the onCreate method of the ArFragment() class, adding Compose layouts
 * directly from here is not possible. (it is possible to override,
 * but becomes problematic when trying to add compose layouts)
 *
 * For this reason, the parent Activity needs to implement ArViewUiProvider
 * to handle the additional UI elements of this fragment.
 */
class ArViewFragment : ArFragment() {
    private var uiProvider: ArViewUiProvider? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Fix to back button errors
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_fragment_ar_view_to_fragment_compose)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiProvider = requireActivity() as ArViewUiProvider
        uiProvider?.setupInterface(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        uiProvider?.hideInterface()
        uiProvider = null
    }


}