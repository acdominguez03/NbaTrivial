package com.example.nbatrivial.presentation.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nbatrivial.R
import com.example.nbatrivial.domain.model.Game
import com.example.nbatrivial.presentation.viewModels.RankingViewModel
import com.example.nbatrivial.utils.FullScreenLoading
import com.example.nbatrivial.utils.UIEvent
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingScreen(
    viewModel: RankingViewModel = koinViewModel()
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

    RankingContent(
        games = state.games,
        isLoading = state.isLoading
    )
}

@Composable
fun RankingContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    games: List<Game> = emptyList()
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(vertical = 20.dp, horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 20.dp, horizontal = 10.dp),
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            content = {
                items(games.size) { index ->
                    RankingItem(item = games[index], index = index)
                }
            }
        )
        if (isLoading) FullScreenLoading()
    }
}

@Composable
fun RankingItem(
    item: Game,
    index: Int
) {
    val circleBackground = colorResource(id = R.color.top3)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(5.dp))
            .background(
                when (index) {
                    0 -> colorResource(id = R.color.top1)
                    1 -> colorResource(id = R.color.top2)
                    2 -> colorResource(id = R.color.top3)
                    else -> colorResource(id = R.color.restTop)
                }
            )
    ) {
        Box(modifier = Modifier.fillMaxWidth().padding(vertical = 30.dp, horizontal = 40.dp)) {
            Row(
                modifier = Modifier.align(Alignment.CenterStart),
                horizontalArrangement = Arrangement.spacedBy(40.dp)
            ) {
                when {
                    index == 0 -> {
                        Top3Item(color = colorResource(id = R.color.top1), index = index)
                    }
                    index == 1 -> {
                        Top3Item(color = colorResource(id = R.color.top2), index = index)
                    }
                    index == 2 -> {
                        Top3Item(color = colorResource(id = R.color.top3), index = index)
                    }
                    index > 2 -> {
                        Text(
                            modifier = Modifier
                                .drawBehind {
                                    drawCircle(
                                        color = circleBackground,
                                        radius = this.size.maxDimension
                                    )
                                },
                            text = (index + 1).toString(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }
                }

                Text(
                    text = item.username,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Text(
                modifier = Modifier.align(Alignment.CenterEnd),
                text = item.score.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun Top3Item(
    color: Color,
    index: Int
) {
    Text(
        modifier = Modifier
            .drawBehind {
                drawCircle(
                    color = Color.White,
                    radius = this.size.maxDimension
                )
            },
        text = (index + 1).toString(),
        fontSize = 20.sp,
        fontWeight = FontWeight.ExtraBold,
        color = color
    )
}
