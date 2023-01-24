package com.patriciafiona.tentangku.utils

sealed class BackPress {
    object Idle : BackPress()
    object InitialTouch : BackPress()
}