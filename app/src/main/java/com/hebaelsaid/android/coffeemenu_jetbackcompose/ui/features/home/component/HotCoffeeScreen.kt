package com.hebaelsaid.android.coffeemenu_jetbackcompose.ui.features.home.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hebaelsaid.android.coffeemenu_jetbackcompose.ui.Screen
import com.hebaelsaid.android.coffeemenu_jetbackcompose.R
import com.hebaelsaid.android.coffeemenu_jetbackcompose.ui.features.home.viewmodel.HotCoffeeListViewModel
import com.hebaelsaid.android.coffeemenu_jetbackcompose.utils.connectivityStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi

private const val TAG = "HotCoffeeScreen"

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun HotCoffeeScreen(
    navController: NavController,
    viewModel: HotCoffeeListViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        if(connectivityStatus()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Log.d(TAG, "HotCoffeeScreen: state.modelItem.size: ${state.modelItem.size}")
                items(state.modelItem.size) { coffeeModels ->
                    CoffeeListItem(model = state.modelItem[coffeeModels]) { model ->
                        Log.d(
                            TAG,
                            "HotCoffeeScreen: nav controller: ${navController.currentDestination}"
                        )
                        // NavHost(navController = navController, graph = )
                        navController.navigate(Screen.OnBoardingScreen.route + "/${Screen.CoffeeDetailsScreen.route}")
                    }
                    Divider()
                }
            }
            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }else{
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.network) , contentDescription = "no internet connection")
                Text(text = "No Internet Connection!")
            }
        }
    }
}