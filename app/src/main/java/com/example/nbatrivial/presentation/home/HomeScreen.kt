package com.example.nbatrivial.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nbatrivial.R
import com.example.nbatrivial.presentation.navigation.Screen
import com.example.nbatrivial.utils.CustomButton

@Composable
fun HomeScreen(
    navController: NavController
) {
    HomeContent(
        navController = navController
    )
}

@Composable
fun HomeContent(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(50.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = stringResource(id = R.string.title),
            fontSize = 50.sp,
            fontWeight = FontWeight.ExtraBold
        )

        CustomButton(
            text = stringResource(id = R.string.play),
            onClick = { navController.navigate(Screen.GameScreen.route) }
        )

        CustomButton(
            text = stringResource(id = R.string.rank),
            onClick = { navController.navigate(Screen.RankingScreen.route) }
        )

        CustomButton(
            text = stringResource(id = R.string.add_question),
            onClick = {
                navController.navigate(Screen.NewQuestionScreen.route)
            }
        )

        Image(
            painter = painterResource(id = R.drawable.nba_logo),
            contentDescription = stringResource(id = R.string.logo)
        )
    }
}


