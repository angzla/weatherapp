package com.example.weather.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.weather.data.WeatherResult
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.example.weather.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    cityLocation: String
) {
    LaunchedEffect(Unit) {
        mainViewModel.getWeather(cityLocation, "metric", "808ec91591e0ff979a1e618ee2462adb")
    }
    Column {

        when (mainViewModel.weatherUiState) {
            is WeatherUiState.Init -> {}
            is WeatherUiState.Loading -> CircularProgressIndicator()
            is WeatherUiState.Success ->
                WeatherPhotoCard(
                    (mainViewModel.weatherUiState as WeatherUiState.Success).weatherResult,
                    cityLocation
                )
            is WeatherUiState.Error -> Text(
                text = "Error: " +
                        "${(mainViewModel.weatherUiState as WeatherUiState.Error).errorMsg}"
            )
        }
    }
}


@Composable
fun WeatherPhotoCard (
    weatherResult: WeatherResult,
    cityLocation: String
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = stringResource(R.string.weather, cityLocation)
            )

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://openweathermap.org/img/w/${
                        weatherResult.weather?.get(0)?.icon
                    }.png")
                    .crossfade(true)
                    .build(),
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )

            Text(
                text = stringResource(R.string.temperature, weatherResult.main?.temp.toString())
            )

            Text(
                text = "Feels Like: ${weatherResult.main?.feelsLike}"
            )

            Text(
                text = "Max Temperature: ${weatherResult.main?.tempMax}"
            )

            Text(
                text = "Min Temperature: ${weatherResult.main?.tempMin}"
            )

            val cityMarker = weatherResult.coord?.let { coord ->
                LatLng(coord.lat ?: 0.0, coord.lon ?: 0.0)
            }

            val cameraPositionState = rememberCameraPositionState {
                cityMarker?.let { marker ->
                    position = CameraPosition.fromLatLngZoom(marker, 10f)
                }
            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                cityMarker?.let { marker ->
                    Marker(
                        state = MarkerState(position = marker),
                        title = cityLocation,
                        snippet = "Marker in ${cityLocation}"
                    )
                }
            }
        }
    }
}


