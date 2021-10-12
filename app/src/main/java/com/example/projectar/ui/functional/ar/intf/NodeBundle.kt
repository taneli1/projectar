package com.example.projectar.ui.functional.ar.intf

import com.google.ar.sceneform.Node

data class NodeBundle<T>(
    val data: Map<T, List<Node>>
)