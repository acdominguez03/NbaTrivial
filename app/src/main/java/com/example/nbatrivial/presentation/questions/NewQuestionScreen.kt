package com.example.nbatrivial.presentation.questions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nbatrivial.R
import com.example.nbatrivial.presentation.viewModels.SetQuestionViewModel
import com.example.nbatrivial.utils.UIEvent
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewQuestionScreen(
    viewModel: SetQuestionViewModel = koinViewModel()
) {
    val focusManager = LocalFocusManager.current
    var questionText by remember { mutableStateOf("") }
    var answerOneText by remember { mutableStateOf("") }
    var answerTwoText by remember { mutableStateOf("") }
    var answerThreeText by remember { mutableStateOf("") }
    var correctAnswerText by remember { mutableStateOf("") }

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

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.add_new_question),
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )

        CustomTextField(
            value = questionText,
            onValueChange = { questionText = it },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(painter = painterResource(id = R.drawable.question), contentDescription = stringResource(id = R.string.question))
                }
            },
            focusManager = focusManager,
            label = stringResource(id = R.string.question)
        )

        CustomTextField(
            value = answerOneText,
            onValueChange = { answerOneText = it },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(painter = painterResource(id = R.drawable.answer_one), contentDescription = stringResource(id = R.string.answer_one))
                }
            },
            focusManager = focusManager,
            label = stringResource(id = R.string.answer_one)
        )

        CustomTextField(
            value = answerTwoText,
            onValueChange = { answerTwoText = it },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(painter = painterResource(id = R.drawable.answer_two), contentDescription = stringResource(id = R.string.answer_two))
                }
            },
            focusManager = focusManager,
            label = stringResource(id = R.string.answer_two)
        )

        CustomTextField(
            value = answerThreeText,
            onValueChange = { answerThreeText = it },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(painter = painterResource(id = R.drawable.answer_three), contentDescription = stringResource(id = R.string.answer_three))
                }
            },
            focusManager = focusManager,
            label = stringResource(id = R.string.answer_three)
        )

        CustomTextField(
            value = correctAnswerText,
            onValueChange = { correctAnswerText = it },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(painter = painterResource(id = R.drawable.correct_answer), contentDescription = stringResource(id = R.string.correct_answer))
                }
            },
            focusManager = focusManager,
            label = stringResource(id = R.string.correct_answer)
        )

        Button(
            onClick = {
                viewModel.setQuestion(
                    viewModel.createNewQuestion(
                        question = questionText,
                        answerOne = answerOneText,
                        answerTwo = answerTwoText,
                        answerThree = answerThreeText,
                        correctAnswer = correctAnswerText,
                        image = null
                    )
                )
            },
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.create),
                modifier = Modifier.padding(horizontal = 10.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        if (state.resetData) {
            questionText = ""
            answerOneText = ""
            answerTwoText = ""
            answerThreeText = ""
            correctAnswerText = ""
        }
    }
}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: @Composable () -> Unit,
    focusManager: FocusManager = LocalFocusManager.current,
    label: String
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = {
            Text(text = label)
        },
        shape = RoundedCornerShape(5.dp),
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )
    )
}
