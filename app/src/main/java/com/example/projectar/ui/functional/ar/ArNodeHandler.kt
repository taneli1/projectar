package com.example.projectar.ui.functional.ar

import com.google.ar.sceneform.Node

interface ArNodeHandler<T> {

    fun saveNodePositions(models: Map<T, Node>)
}