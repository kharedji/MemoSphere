package com.kharedji.memosphere.presentation.screens.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kharedji.memosphere.R
import com.kharedji.memosphere.presentation.screens.blog.BlogsScreen
import com.kharedji.memosphere.presentation.screens.note.NotesScreen
import com.kharedji.memosphere.presentation.screens.note.NotesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    paddingValues: PaddingValues = PaddingValues(),
    navController: NavController? = null
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        2
    }

    val scope = rememberCoroutineScope()
    val tabData = listOf(
        "Blogs" to R.drawable.ic_feed,
        "Notes" to R.drawable.ic_note
    )

    BackHandler {
        if (pagerState.currentPage != 0) {
            scope.launch {
                pagerState.animateScrollToPage(0)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            divider = {
                      Spacer(modifier = Modifier.height(5.dp))
            },
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    height = 3.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            tabData.forEachIndexed { index, (title, icon) ->
                LeadingIconTab(
                    icon = { Icon(imageVector = ImageVector.vectorResource(id = icon), contentDescription = null) },
                    text = { /*Text(title)*/ },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
        val notesViewModel: NotesViewModel = hiltViewModel()

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) { page ->
            when (page) {
                0 -> BlogsScreen(navController = navController)
                1 -> NotesScreen(navController = navController, notesViewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}