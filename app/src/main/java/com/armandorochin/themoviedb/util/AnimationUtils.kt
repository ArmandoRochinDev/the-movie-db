package com.armandorochin.themoviedb.util

fun lerp(startValue: Float, endValue: Float, fraction: Float) = startValue + fraction * (endValue - startValue)