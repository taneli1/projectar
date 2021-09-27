package com.example.projectar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projectar.ui.theme.ProjectarTheme
import com.example.projectar.ui.theme.Shapes
import com.example.projectar.ui.theme.darkGrey
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        data.add(Message("kala", "kukko"))
        Log.d("TEST", data.size.toString())

        setContent {
            ProjectarTheme {
                TestBox(msg = Message("first", "second"))
            }
        }
    }
}

data class Message(val first: String, val second: String)

val data = mutableListOf<Message>()

/*
@Composable
fun LargeBox(){
    LazyColumn(){
        items(
            items =
        )
    }
}
*/
@Composable
fun TestBox(msg: Message) {
    Column (Modifier
        .clip(Shapes.medium)){
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "picture",
            contentScale = ContentScale.Crop
            )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(darkGrey)
        ) {
            Text(text = msg.first, color = Color.White)
            Text(text = msg.second, color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProjectarTheme {
        TestBox(msg = Message("first", "second"))
    }
}