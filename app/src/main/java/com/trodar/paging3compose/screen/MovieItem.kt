package com.trodar.paging3compose.screen

import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.gif.AnimatedImageDecoder
import coil3.request.crossfade
import com.trodar.paging3compose.model.MovieModel
import com.trodar.paging3compose.model.MovieSearch
import com.trodar.paging3compose.ui.theme.Paging3ComposeTheme

@Composable
fun MovieItem(
    movie: MovieModel,
    onNavigateToDetail: (String) -> Unit,
) {
    val context = LocalContext.current
    val imageLoader =
        ImageLoader.Builder(context)
            .components {
                add(AnimatedImageDecoder.Factory())
            }
            .crossfade(true)
            .build()

    Column(
        modifier = Modifier.padding(horizontal = 8.dp).clickable { onNavigateToDetail(movie.imdbId) },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = Uri.parse(movie.poster),
            contentDescription = "Poster",
            imageLoader = imageLoader,
            modifier = Modifier
                .height(256.dp)
                .clip(RoundedCornerShape(6.dp)),
            contentScale = ContentScale.Crop,
            onError = {
                Log.d("MAcK", it.result.throwable.message.toString())
            }
        )
        Text(
            text = movie.title,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = 10.sp,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
        Row {
           Text(
               text = movie.type,
               color = MaterialTheme.colorScheme.onPrimaryContainer,
               fontSize = 10.sp,

           )
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = movie.year,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 10.sp,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMovieModel(){
    Paging3ComposeTheme{
        val item: MovieModel = MovieSearch(
            imdbId = "s23423",
            title = "Son and sun",
            year = "1978",
            type = "Drama",
            poster = ""
        )
        MovieItem(
            item,
            onNavigateToDetail = {}
        )
    }
}


