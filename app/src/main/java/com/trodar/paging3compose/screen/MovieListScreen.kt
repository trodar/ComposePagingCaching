package com.trodar.paging3compose.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.trodar.paging3compose.model.MovieModel
import com.trodar.paging3compose.ui.theme.backgroundColors


@Composable
fun MovieListScreen(
    paddingValues: PaddingValues,
    movieListViewModel: MovieListViewModel = hiltViewModel(),
    onNavigateToDetail: (String) -> Unit,
) {
    val moviePaging = movieListViewModel.moviesState.collectAsLazyPagingItems()

    MovieListScreen(
        paddingValues = paddingValues,
        moviePaging = moviePaging,
        onNavigateToDetail = onNavigateToDetail,
    )
}

@Composable
fun MovieListScreen(
    paddingValues: PaddingValues,
    moviePaging: LazyPagingItems<MovieModel>,
    onNavigateToDetail: (String) -> Unit,
) {
    LazyVerticalGrid(
        state = rememberLazyGridState(),
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(colorStops = backgroundColors()))
            .padding(top = paddingValues.calculateTopPadding())
            .padding(top = 8.dp)
    ) {
        items(
            count = moviePaging.itemCount,
        ) { index ->

            val movie = moviePaging[index]

            movie?.let {
                MovieItem(
                    movie = movie,
                    onNavigateToDetail = onNavigateToDetail,
                )
            }
        }
        progressIndicator(moviePaging)
    }
}

fun LazyGridScope.progressIndicator(
    moviePaging: LazyPagingItems<MovieModel>
){
    val loadState = moviePaging.loadState.mediator
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(2) }
    item(span = span) {
        if (loadState?.refresh == LoadState.Loading) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = "Refresh Loading"
                )

                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }

        if (loadState?.append == LoadState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(48.dp),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }

        if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
            val isPaginatingError =
                (loadState.append is LoadState.Error) || moviePaging.itemCount > 1
            val error = if (loadState.append is LoadState.Error)
                (loadState.append as LoadState.Error).error
            else
                (loadState.refresh as LoadState.Error).error

            val modifier = if (isPaginatingError) {
                Modifier.padding(8.dp)
            } else {
                Modifier.fillMaxSize()
            }
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (!isPaginatingError) {
                    Icon(
                        modifier = Modifier
                            .size(64.dp),
                        imageVector = Icons.Rounded.Warning, contentDescription = null
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = error.message ?: error.toString(),
                    textAlign = TextAlign.Center,
                )

                Button(
                    onClick = {
                        moviePaging.refresh()
                    },
                    content = {
                        Text(text = "Refresh")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White,
                    )
                )
            }
        }
    }
}