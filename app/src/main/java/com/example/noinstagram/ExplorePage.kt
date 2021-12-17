package com.example.noinstagram

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noinstagram.ui.components.ExplorePostSection
import com.example.noinstagram.ui.components.SearchSection
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

@ExperimentalFoundationApi
@Composable
fun ExploreScreen() {
    var value by remember { mutableStateOf("") }
    val view = LocalView.current
    var refreshing by remember { mutableStateOf(false) }
    //refresh
    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(2000)
            refreshing = false
        }
    }
    SwipeRefresh(
        state = rememberSwipeRefreshState(refreshing),
        onRefresh = { refreshing = true },
        indicator = { state, refreshTriggerDistance ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = refreshTriggerDistance,
                scale = true
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(4.dp))
            SearchSection(
                value = value,
                label = "",
                onDoneActionClick =
                {
                    view.clearFocus()
                },
                onValueChanged = {},
                onClearClick = {
                    value = ""
                    view.clearFocus()
                },
            )
            Spacer(modifier = Modifier.height(25.dp))
            ExplorePostSection()
        }
    }

}

@ExperimentalFoundationApi
@Composable
@Preview
fun ExplorePreview() {
    ExploreScreen()
}