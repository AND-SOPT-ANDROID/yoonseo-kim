package org.sopt.and

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import org.sopt.and.signin.SignInActivity
import org.sopt.and.ui.theme.ANDANDROIDTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ANDANDROIDTheme {
                val context = LocalContext.current
                Intent(context, SignInActivity::class.java).run {
                    context.startActivity(this)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ANDANDROIDTheme {}
}