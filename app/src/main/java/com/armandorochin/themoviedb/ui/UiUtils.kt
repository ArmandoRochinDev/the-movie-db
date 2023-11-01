package com.armandorochin.themoviedb.ui

import android.content.Context

fun calculateNoOfColumns(
    context: Context,
    columnWidthDp: Float // For example columnWidthdp=180
): Int {
    val displayMetrics = context.resources.displayMetrics
    val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
    return (screenWidthDp / columnWidthDp + 0.5).toInt() // +0.5 for correct rounding to int.
}