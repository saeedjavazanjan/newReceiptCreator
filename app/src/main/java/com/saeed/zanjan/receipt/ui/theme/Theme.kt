package com.saeed.zanjan.receipt.ui.theme

import android.app.Activity
import android.graphics.fonts.Font
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.presentation.ui.registration.RegistrationScreen
import androidx.compose.material3.Typography
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.saeed.zanjan.receipt.presentation.components.CircularIndeterminateProgressBar

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)


@Composable
fun NewReceiptCreatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    displayProgressBar: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        typography = Typography,
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
    ){
        val systemUiController: SystemUiController = rememberSystemUiController()

        systemUiController.isStatusBarVisible = true // Status bar
        systemUiController.isNavigationBarVisible = true // Navigation bar
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = if (!darkTheme) Color.White else Color.Black)
        ){

            Column{
             //   ConnectivityMonitor(isNetworkAvailable = isNetworkAvailable)
                content()
            }


            CircularIndeterminateProgressBar(isDisplayed = displayProgressBar)

         /*   ProcessDialogQueue(
                dialogQueue = dialogQueue,
            )*/

        }
    }

}
/*
@Composable
fun AppContent() {
    // Initialize a NavHostController for navigation
    val navController = rememberNavController()

    // Create a NavHost with the navController
    NavHost(navController = navController, startDestination = "registration") {
        composable("registration") {
            RegistrationScreen(
                onSignInClicked = {
                                  */
/* Handle sign-in logic *//*

                                  },
                onSignUpClicked = {
                                  */
/* Handle sign-up logic *//*

                                  }
               */
/* onSendOtpClicked = {
                *//*
*/
/* Handle OTP logic *//*
*/
/*
                }*//*

            )
        }

    }
}*/
