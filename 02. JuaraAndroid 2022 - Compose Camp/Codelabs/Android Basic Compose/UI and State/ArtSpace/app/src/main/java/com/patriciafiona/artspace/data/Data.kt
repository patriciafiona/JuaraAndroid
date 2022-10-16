package com.patriciafiona.artspace.data

import com.patriciafiona.artspace.R
import java.util.*
import kotlin.collections.ArrayList

object Data {
    fun getAllData(): ArrayList<Gallery>{
        return arrayListOf<Gallery>(
            Gallery(
                id = 1,
                title = "Bread Clip Hearth",
                creator = "Tanaka Tatsuya",
                added = "2022-07-18",
                description = "パンの留め具で暖を取る \n" +
                        "【MINIATURE LIFE展 in 釧路】\n" +
                        "MINIATURE LIFE EXHIBITION in Kushiro\n" +
                        "until: Sep. 11, 2022",
                likes = 47679,
                image = R.drawable.tt_image_01
            ),
            Gallery(
                id = 2,
                title = "Flaming Hot",
                creator = "Tanaka Tatsuya",
                added = "2022-07-20",
                description = "燃え上がるような辛さ \n" +
                        "【MINIATURE LIFE展 in 釧路】\n" +
                        "MINIATURE LIFE EXHIBITION in Kushiro\n" +
                        "until: Sep. 11, 2022",
                likes = 45237,
                image = R.drawable.tt_image_02
            ),
            Gallery(
                id = 3,
                title = "Remote Work",
                creator = "Tanaka Tatsuya",
                added = "2022-08-07",
                description = "リモート屋台 \n" +
                        "【MINIATURE LIFE展 in 釧路】\n" +
                        "MINIATURE LIFE EXHIBITION in Kushiro\n" +
                        "until: Sep. 11, 2022",
                likes = 82119,
                image = R.drawable.tt_image_03
            ),
            Gallery(
                id = 4,
                title = "If you cry, you lose.",
                creator = "Tanaka Tatsuya",
                added = "2022-09-12",
                description = "先に泣いた方が負け\n" +
                        "【MINIATURE LIFE展2 in 長野】\n" +
                        "MINIATURE LIFE EXHIBITION2 in Nagano\n" +
                        "Sep. 16 - Oct. 30, 2022",
                likes = 64863,
                image = R.drawable.tt_image_04
            ),
            Gallery(
                id = 5,
                title = "Knife Restaurant",
                creator = "Tanaka Tatsuya",
                added = "2022-09-13",
                description = "鋭利”目的の飲食店 \n" +
                        "【MINIATURE LIFE展 in 釧路】\n" +
                        "MINIATURE LIFE EXHIBITION in Kushiro\n" +
                        "until: Sep. 11, 2022",
                likes = 47679,
                image = R.drawable.tt_image_05
            ),
            Gallery(
                id = 6,
                title = "Potatowriter",
                creator = "Tanaka Tatsuya",
                added = "2022-07-20",
                description = "かわ”ったタイプライター \n" +
                        "【MINIATURE LIFE展2 in 長野・上田】\n" +
                        "MINIATURE LIFE EXHIBITION2 in Nagano\n" +
                        "Sep. 16 - Oct. 30, 2022",
                likes = 50388,
                image = R.drawable.tt_image_06
            ),
            Gallery(
                id = 7,
                title = "OR-ange",
                creator = "Tanaka Tatsuya",
                added = "2022-08-18",
                description = "未完の手術 \n" +
                        "【MINIATURE LIFE展 in 釧路】\n" +
                        "MINIATURE LIFE EXHIBITION in Kushiro\n" +
                        "until: Sep. 11, 2022",
                likes = 93274,
                image = R.drawable.tt_image_07
            ),
            Gallery(
                id = 8,
                title = "SNS (Social Networking Snack)",
                creator = "Tanaka Tatsuya",
                added = "2022-07-20",
                description = "SNS（サクサクなスナック \n" +
                        "【MINIATURE LIFE展2 in 静岡】\n" +
                        "MINIATURE LIFE EXHIBITION2 in Shizuoka\n" +
                        "❗️本日最終日❗️\n" +
                        "until: Jul. 10, 2022",
                likes = 82914,
                image = R.drawable.tt_image_08
            ),
            Gallery(
                id = 9,
                title = "Sharpen the Sense",
                creator = "Tanaka Tatsuya",
                added = "2022-07-04",
                description = "とんがっていこうぜ！ \n" +
                        "【MINIATURE LIFE展2 in 静岡】\n" +
                        "MINIATURE LIFE EXHIBITION2 in Shizuoka\n" +
                        "until: Jul. 10, 2022",
                likes = 84362,
                image = R.drawable.tt_image_09
            ),
            Gallery(
                id = 10,
                title = "Irori (Sunken Hearth)t",
                creator = "Tanaka Tatsuya",
                added = "2022-06-30",
                description = "ファンデーションをリノベーション \n" +
                        "【MINIATURE LIFE展2 in 静岡】\n" +
                        "MINIATURE LIFE EXHIBITION2 in Shizuoka\n" +
                        "until: Jul. 10, 2022",
                likes = 55572,
                image = R.drawable.tt_image_10
            ),
        )
    }
}