package jacs.apps.monkeytest

import android.app.ActivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jacs.apps.monkeytest.ui.theme.RapptrGithubActionsPlaygroundTheme
import java.lang.NullPointerException

class MainActivity3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RapptrGithubActionsPlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting2("You Did it Monkey!!!!!")
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String) {
    var count = 15
    var row = 0
    var col = 0
    Text(text = "Hello $name!")
    LazyColumn(Modifier.fillMaxHeight().fillMaxWidth()){
        items(10){ ha ->
            row = ha
            LazyRow(){
                items(15){ index ->
                    col = index
                    Button(onClick = {
                        if (ha == 9) {
                            if ((count - index) == 2) {
                                    throw NullPointerException("this was row: $ha and column: $index")
                            }
                        }
                    }) {
                        Text(text = (index).toString())
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    RapptrGithubActionsPlaygroundTheme {
        Greeting2("Android")
    }
}