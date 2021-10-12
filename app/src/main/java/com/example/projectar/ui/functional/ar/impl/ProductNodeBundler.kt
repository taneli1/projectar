package com.example.projectar.ui.functional.ar.impl

import com.example.projectar.ui.functional.ar.intf.ArNodeBundler
import com.example.projectar.ui.functional.ar.intf.NodeBundle
import com.google.ar.sceneform.Node

class ProductNodeBundler(
    private val savedBundle: NodeBundle<Long>? = null,
    private val save: (nodeBundle: NodeBundle<Long>) -> Unit
) : ArNodeBundler<Long> {
    private var temp: NodeBundle<Long>? = null

    override fun saveNodePositions(data: Map<Long, List<Node>>) {
        save(NodeBundle(data))
    }

    override fun loadSavedNodes(): NodeBundle<Long>? {
        return savedBundle
    }

    override fun loadFromTemp(): NodeBundle<Long>? {
        return temp
    }

}