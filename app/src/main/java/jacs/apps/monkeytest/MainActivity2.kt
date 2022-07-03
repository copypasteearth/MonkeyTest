package jacs.apps.monkeytest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import jacs.apps.monkeytest.ui.theme.RapptrGithubActionsPlaygroundTheme

class MainActivity2 : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RapptrGithubActionsPlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .semantics { testTagsAsResourceId = true },
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

}

@Composable
fun Greeting(name: String) {
    val context = LocalContext.current
    var username = remember{ mutableStateOf("")}
    var password = remember{ mutableStateOf("")}
    Column() {
        TextField(value = username.value, onValueChange = {
            username.value = it
        }, modifier = Modifier
            .testTag("cusername")
            .fillMaxWidth())
        TextField(value = password.value, onValueChange = {
            password.value = it
        },
            modifier = Modifier
                .testTag("cpassword")
                .fillMaxWidth(), visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = {
            if (username.value == "copypasteearth@gmail.com" && password.value == "test123") {
                val sharedPref = context.getSharedPreferences("githubactions",Context.MODE_PRIVATE)
                with (sharedPref.edit()) {
                    putBoolean("loggedin", true)
                    apply()
                }
                val intent = Intent(context, MainActivity3::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        },
            Modifier
                .fillMaxWidth()
                .testTag("clogin")) {
            Text(text = "Login")
        }


    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RapptrGithubActionsPlaygroundTheme {
        Greeting("Android")
    }
}