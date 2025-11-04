package com.example.geoquiz

import android.os.Bundle
import android.view.Surface
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geoquiz.ui.theme.GeoQuizTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoQuizTheme {
                Scaffold { innerPadding ->
                    DemoScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
data class QuizQuestion(
    val text: String,
    val answer: Boolean
)

val quizQuestions = listOf(
    QuizQuestion("Canberra is the capital of Australia.", true),
    QuizQuestion("The Pacific Ocean is larger than the Atlantic Ocean.", true),
    QuizQuestion("The Suez Canal connects the Red Sea and the Indian Ocean.", false),
    QuizQuestion("The source of the Nile River is in Egypt.", false),
    QuizQuestion("The Amazon River is the longest river in the Americas.", true),
    QuizQuestion("Lake Baikal is the world's oldest and deepest freshwater lake.", true)
)
@Composable
fun DemoText(message: String, fontSize: Float) {
    Text(
        text = message,
        fontSize = fontSize.sp,
        fontWeight = FontWeight.Bold,

        )
}
@Composable
fun ShowScoreToast(score: Int, quizSize: Int) {
    val context = LocalContext.current
    Toast.makeText(context, "Your score: $score out of $quizSize", Toast.LENGTH_LONG).show()
}
@Preview(showSystemUi = true)
@Composable
fun DemoTextPreview() {
    GeoQuizTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            com.example.geoquiz.DemoScreen(modifier = Modifier.padding(innerPadding))
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoScreen(modifier: Modifier = Modifier) {
    var questionIndex by remember { mutableStateOf(0) }
    var lastAnswerCorrect by remember { mutableStateOf<Boolean?>(null) }
    var answered by remember { mutableStateOf(false) }
    var score by remember { mutableStateOf(0) }
    val quizSize = quizQuestions.size
    val purpleColor = Color(0xFF6200EE)
    val question = quizQuestions[questionIndex]



    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier.fillMaxSize().padding(15.dp)
    ) {
            val question = quizQuestions.getOrNull(questionIndex) ?: return
            Text(text = "${questionIndex + 1}. ${question.text}", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        lastAnswerCorrect = (question.answer == true)
                        if (lastAnswerCorrect == true) score++
                        answered = true
                    },
                    enabled = !answered,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = purpleColor,
                        contentColor = Color.White
                    )
                ) { Text("True") }
                Button(
                    onClick = {
                        lastAnswerCorrect = (question.answer == false)
                        if (lastAnswerCorrect == true) score++
                        answered = true
                    },
                    enabled = !answered,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = purpleColor,
                        contentColor = Color.White
                    )
                ) { Text("False") }
            }

            Spacer(modifier = Modifier.height(24.dp))
            if (answered) {
                if (questionIndex == quizSize-1) {
                    ShowScoreToast(score, quizSize)
                    Text(text = "You have answered all the questions!", fontSize = 20.sp, color = Color.Green)
                } else {
                    Button(
                        onClick = {
                            questionIndex++
                            answered = false
                            lastAnswerCorrect = null
                        },
                        modifier = Modifier.align(Alignment.End),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = purpleColor,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Next")
                    }
                }
            }
    }
}


