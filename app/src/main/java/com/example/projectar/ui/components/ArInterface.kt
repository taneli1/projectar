package com.example.projectar.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.ui.functional.ar.ArViewManager
import com.example.projectar.ui.testing.TestComposable

/**
 * UI for the AR View
 */
@Composable
fun ArInterface(
    arViewManager: ArViewManager,
    db: ApplicationDatabase // TODO Remove
) {
    Row() {
        TestComposable.DbButtons(db)
        Button(onClick = { arViewManager.addModel(0L) }) {

        }
    }
}