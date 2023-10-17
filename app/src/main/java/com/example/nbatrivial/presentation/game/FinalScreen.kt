package com.example.nbatrivial.presentation.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nbatrivial.R
import com.example.nbatrivial.presentation.navigation.Screen
import com.example.nbatrivial.presentation.questions.CustomTextField
import com.example.nbatrivial.presentation.viewModels.FinalScreenViewModel
import com.example.nbatrivial.utils.CustomButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun FinalScreen(
    navController: NavController,
    viewModel: FinalScreenViewModel = koinViewModel()
) {
    var username by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Text(
            text = stringResource(id = R.string.your_puntuation),
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 100.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 20.dp),
                    text = "${viewModel.finalScore}",
                    fontSize = 25.sp
                )
            }
        }

        CustomTextField(
            value = username,
            onValueChange = { username = it },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(painter = painterResource(id = R.drawable.baseline_person_outline_24), contentDescription = stringResource(id = R.string.username))
                }
            },
            label = stringResource(id = R.string.username)
        )

        Text(
            text = stringResource(id = R.string.identify),
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Box(contentAlignment = Alignment.Center) {
            CustomButton(
                text = stringResource(id = R.string.upload),
                onClick = {
                    viewModel.setNewGame(
                        viewModel.createNewGame(username, viewModel.finalScore)
                    )
                    if (viewModel.state.goToHome) {
                        viewModel.state.goToHome = false
                        navController.clearBackStack("final_screen")
                        navController.navigate(Screen.HomeScreen.route)
                    }
                }
            )
        }

        Image(
            painter = painterResource(id = R.drawable.nba_logo),
            contentDescription = stringResource(id = R.string.logo)
        )
    }
}
