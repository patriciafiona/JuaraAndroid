package com.patriciafiona.tentangku.helper

sealed class BackPress {
    object Idle : BackPress()
    object InitialTouch : BackPress()
}