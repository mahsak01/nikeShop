package com.example.nikeshop.data.model

import androidx.annotation.StringRes

data class EmptyState(
    val mustShow: Boolean,
    @StringRes val messageResId: Int = 0,
    val mostShowCallToActionButton: Boolean = false
)
