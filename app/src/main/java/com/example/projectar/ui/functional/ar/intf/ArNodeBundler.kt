package com.example.projectar.ui.functional.ar.intf

import com.google.ar.sceneform.Node

interface ArNodeBundler<T> {
    fun saveNodePositions(data: Map<T, List<Node>>)

    fun loadSavedNodes(): NodeBundle<T>?

    fun loadFromTemp(): NodeBundle<T>?
}