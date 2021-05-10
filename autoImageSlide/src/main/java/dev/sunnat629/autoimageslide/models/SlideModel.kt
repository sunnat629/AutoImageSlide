package dev.sunnat629.autoimageslide.models

import dev.sunnat629.autoimageslide.constants.ScaleTypes

/**
 * @author  S M Mohi Us Sunnat
 * @date    10/5/21
 * Copyright ©2021. All rights reserved.
 */

data class SlideModel(
    val imageUrl: String? = null,
    val title: String? = null,
    val scaleType: ScaleTypes? = null,
    val imagePath: Int? = 0,
)