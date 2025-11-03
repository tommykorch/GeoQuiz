package com.example.geoquiz

import android.os.Bundle
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
    Toast.makeText(context, "Ваш счет: $score из $quizSize", Toast.LENGTH_LONG).show()
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
@Composable
fun DemoScreen(modifier: Modifier = Modifier) {
    var questionIndex by remember { mutableStateOf(0) }
    var lastAnswerCorrect by remember { mutableStateOf<Boolean?>(null) }
    var answered by remember { mutableStateOf(false) }
    var score by remember { mutableStateOf(0) }
    val quizSize = quizQuestions.size

    val question = quizQuestions[questionIndex]



    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize().padding(15.dp)
    ) {
            val question = quizQuestions.getOrNull(questionIndex) ?: return
            Text(text = question.text, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(24.dp))

            if (!answered) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(onClick = {
                        lastAnswerCorrect = (question.answer == true)
                        if (lastAnswerCorrect == true) score++
                        answered = true
                    }) { Text("True") }
                    Button(onClick = {
                        lastAnswerCorrect = (question.answer == false)
                        if (lastAnswerCorrect == true) score++
                        answered = true
                    }) { Text("False") }
                }
                if (questionIndex == quizSize - 1) {
                    ShowScoreToast(score, quizSize)
                }
            } else {
                Button(
                    onClick = {
                        questionIndex++
                        answered = false
                        lastAnswerCorrect = null
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Next")
                }
            }

    }
}


