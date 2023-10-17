package com.example.nbatrivial.presentation.game

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.nbatrivial.R
import com.example.nbatrivial.domain.model.Question
import com.example.nbatrivial.presentation.navigation.Screen
import com.example.nbatrivial.presentation.viewModels.GameViewModel
import com.example.nbatrivial.utils.CustomGameButton
import com.example.nbatrivial.utils.FullScreenLoading
import com.example.nbatrivial.utils.MAX_SECONDS
import com.example.nbatrivial.utils.UIEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    viewModel: GameViewModel = koinViewModel(),
    navController: NavController
) {
    val state = viewModel.state
    val eventFlow = viewModel.eventFlow
    val scaffoldState = rememberBottomSheetScaffoldState()

    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is UIEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    GameContent(
        navController = navController,
        questions = state.questions,
        isLoading = state.isLoading,
        questionNumber = state.questionNumber,
        viewModel = viewModel
    )
}

@Composable
fun GameContent(
    navController: NavController,
    questions: List<Question> = emptyList(),
    isLoading: Boolean = false,
    questionNumber: Int = 0,
    viewModel: GameViewModel
) {
    LaunchedEffect(key1 = viewModel.progressTimer) {
        while (viewModel.progressTimer <= 1f) {
            delay(1000L)
            viewModel.progressTimer += 0.034f
            viewModel.seconds += 1
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text(
            text = "Pregunta " + (questionNumber + 1),
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Text(text = viewModel.seconds.toString(), fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)

        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(10.dp)),
            progress = viewModel.progressTimer
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (questions.isNotEmpty()) {
                        viewModel.state.questions[viewModel.state.questionNumber].image.let {
                            if (it != null) {
                                AsyncImage(
                                    model = it,
                                    contentDescription = "image",
                                    modifier = Modifier.clip(CircleShape).width(200.dp).height(200.dp)
                                )
                            }
                        }
                    }

                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        text = if (questions.isNotEmpty()) questions[questionNumber].question else "",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 25.sp
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp),
            content = {
                items(
                    if (questions.isNotEmpty()) viewModel.state.questions[viewModel.state.questionNumber].answers.size else 0
                ) { index ->
                    CustomGameButton(
                        text = if (questions.isNotEmpty()) questions[questionNumber].answers[index] else "",
                        backgroundColor = if (viewModel.correctOrNotAnswerState && viewModel.answerState == index) {
                            colorResource(id = R.color.progress_green)
                        } else if (!viewModel.correctOrNotAnswerState && viewModel.answerState == index) {
                            colorResource(id = R.color.red_answer)
                        } else if (viewModel.correctAnswerIndex == index) {
                            colorResource(id = R.color.progress_green)
                        } else {
                            colorResource(id = R.color.light_blue)
                        }
                    ) {
                        viewModel.checkCorrectAnswer(
                            answer = questions[questionNumber].answers[index],
                            index
                        )
                    }
                }
            }
        )
    }

    if (viewModel.finishGame) {
        viewModel.finishGame = false
        navController.navigate(Screen.FinalScreen.route)
    }
    if (isLoading) FullScreenLoading()
    if (viewModel.seconds >= MAX_SECONDS) viewModel.outOfTimeResponse()
}
