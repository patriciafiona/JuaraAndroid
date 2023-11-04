package com.patriciafiona.learningforkids.ui.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patriciafiona.learningforkids.ui.theme.vividOrange
import com.patriciafiona.learningforkids.utils.Utils.dpToPx
import com.patriciafiona.learningforkids.utils.Utils.pxToDp

private fun trianglePath(size: Float) = androidx.compose.ui.graphics.Path().apply {
    // Moves to top center position
    moveTo(size / 2f, 0f)
    // Add line to bottom right corner
    lineTo(size, size)
    // Add line to bottom left corner
    lineTo(0f, size)
}

private fun triangleFlippedPath(size: Float) = androidx.compose.ui.graphics.Path().apply {
    // Moves to top center position
    moveTo(0F, 0f)
    // Add line to bottom right corner
    lineTo(size, 0F)
    // Add line to bottom left corner
    lineTo(size / 2f, size)
}

@Composable
fun TriangleShape(
    size: Float,
    color: Color
){
    Canvas(modifier = Modifier.size(size.toInt().pxToDp()), onDraw = {
        drawPath(
            color = color,
            path = trianglePath(size)
        )
    })
}

@Composable
fun TriangleFlippedShape(
    size: Float,
    color: Color
){
    Canvas(modifier = Modifier.size(size.toInt().pxToDp()), onDraw = {
        drawPath(
            color = color,
            path = triangleFlippedPath(size)
        )
    })
}

@Preview(showBackground = true)
@Composable
fun TriangleShapePreview(){
    val shapeSize = 70f
    TriangleShape(size = shapeSize, color = vividOrange)
}

@Preview(showBackground = true)
@Composable
fun TriangleFlippedShapePreview(){
    val shapeSize = 70f
    TriangleFlippedShape(size = shapeSize, color = vividOrange)
}