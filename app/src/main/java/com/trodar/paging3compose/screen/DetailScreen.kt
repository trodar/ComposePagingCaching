package com.trodar.paging3compose.screen

import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.Cottage
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.gif.AnimatedImageDecoder
import coil3.request.crossfade
import com.trodar.paging3compose.R
import com.trodar.paging3compose.model.MovieDetail
import com.trodar.paging3compose.ui.theme.Paging3ComposeTheme

@Composable
fun DetailScreen(
    imdbId: String,
    paddingValues: PaddingValues,
    onBack: () -> Unit,
    detailViewModel: DetailViewModel = hiltViewModel<DetailViewModel, DetailViewModel.Factory>(
        creationCallback = { factory -> factory.create(imdbId = imdbId) }
    )
) {
    val detailState = detailViewModel.uiState.collectAsState()

    when (detailState.value) {

        DetailUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimaryContainer)
            }

        }

        is DetailUiState.Error -> {
            Text("Something wrong")
        }

        is DetailUiState.Success -> {
            DetailItem(
                detail = (detailState.value as DetailUiState.Success).movieDetail,
                paddingValues = paddingValues,
                onBack = onBack
            )
        }
    }
}

@Composable
fun DetailItem(
    detail: MovieDetail,
    paddingValues: PaddingValues,
    onBack: () -> Unit,
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
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = paddingValues.calculateTopPadding())
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)


    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = detail.title,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        AsyncImage(
            model = Uri.parse(detail.poster),
            contentDescription = "Poster",
            imageLoader = imageLoader,
            modifier = Modifier
                .height(320.dp)
                .clip(RoundedCornerShape(6.dp)),
            contentScale = ContentScale.FillHeight,
            placeholder = painterResource(R.drawable.movie_holder),
            onError = { error ->
                Log.d("MAcK", error.result.throwable.message.toString())
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Info(detail)

    }
}

@Composable
fun Info(detail: MovieDetail) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    )
    {
        Column(modifier = Modifier.weight(2f)) {
            TextPrimary(
                text = detail.title,
                fontSize = 20.sp,
            )
            TextSecondary(text = detail.genre)
            TextSecondary(text = detail.boxOffice)

        }
        Column(modifier = Modifier.weight(1f)) {
            DetailInfoItem(Icons.Rounded.DateRange, detail.released)
            DetailInfoItem(Icons.Rounded.AccessTime, detail.runtime)
            DetailInfoItem(Icons.Rounded.Cottage, detail.country)
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        TextSecondary(detail.plot)
        Spacer(modifier = Modifier.height(16.dp))
        TextPrimary(detail.awards)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {

            TextSecondary("Director:")
            Spacer(modifier = Modifier.width(8.dp))
            TextPrimary(detail.director)

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {

            TextSecondary("Actors:")
            Spacer(modifier = Modifier.width(8.dp))
            TextPrimary(detail.actors)

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {

            TextSecondary("Country:")
            Spacer(modifier = Modifier.width(8.dp))
            TextPrimary(detail.country)

        }
    }

}

@Composable
fun DetailInfoItem(image: ImageVector, text: String) {
    Row {
        Icon(
            imageVector = image,
            contentDescription = "icon",
            modifier = Modifier.size(20.dp),
        )
        Spacer(modifier = Modifier.padding(start = 4.dp))
        TextSecondary(text = text)
    }
}

@Composable
fun TextPrimary(
    text: String,
    fontSize: TextUnit = 12.sp
) {
    Text(
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = fontSize,
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )
}

@Composable
fun TextSecondary(
    text: String,
    fontSize: TextUnit = 12.sp,
) {
    Text(
        text = text,
        fontSize = fontSize,
        color = MaterialTheme.colorScheme.onSecondaryContainer
    )
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewDetail() {

    val detail = MovieDetail(
        title = "Guardians of the Galaxy Vol. 2",
        year = "2017",
        rated = "PG-13",
        released = "05 May 2017",
        runtime = "136 min",
        genre = "Action, Adventure, Comedy",
        director = "James Gunn",
        actors = "Chris Pratt, Zoe Saldana, Dave Bautista",
        plot = "The Guardians struggle to keep together as a team while dealing with their personal family issues, notably Star-Lord's encounter with his father, the ambitious celestial being Ego.",
        country = "United States",
        awards = "Nominated for 1 Oscar. 15 wins & 60 nominations total",
        poster = "https://m.media-amazon.com/images/M/MV5BNWE5MGI3MDctMmU5Ni00YzI2LWEzMTQtZGIyZDA5MzQzNDBhXkEyXkFqcGc@._V1_SX300.jpg",
        imdbId = "tt3896198",
        boxOffice = "\$389,813,101"
    )
    Paging3ComposeTheme {
        DetailItem(
            detail = detail,
            paddingValues = PaddingValues()
        ) {

        }

    }
}


























