package com.example.projectar.ui.fragment


import android.os.Bundle
import android.view.View
import com.example.projectar.ui.utils.ArViewUiProvider
import com.google.ar.sceneform.ux.ArFragment

private const val TAG = "ArViewFragment"


class ArViewFragment : ArFragment() {
    private var uiProvider: ArViewUiProvider? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiProvider = requireActivity() as ArViewUiProvider
        uiProvider?.showInterface()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        uiProvider?.hideInterface()
        uiProvider = null
    }

}