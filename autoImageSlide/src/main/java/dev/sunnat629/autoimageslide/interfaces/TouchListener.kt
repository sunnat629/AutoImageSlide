package dev.sunnat629.autoimageslide.interfaces

import dev.sunnat629.autoimageslide.constants.ActionTypes


/**
 * @author  S M Mohi Us Sunnat
 * @date    10/5/21
 * Copyright ©2021. All rights reserved.
 */

interface TouchListener {
    /**
     * Click listener touched item function.
     *
     * @param  touched  slider boolean
     */
    fun onTouched(touched: ActionTypes)
}