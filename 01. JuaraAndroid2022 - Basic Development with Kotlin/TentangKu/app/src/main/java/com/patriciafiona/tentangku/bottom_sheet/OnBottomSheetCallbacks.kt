package com.patriciafiona.tentangku.bottom_sheet

import android.view.View

interface OnBottomSheetCallbacks {
    fun onStateChanged(bottomSheet: View, newState: Int)
}