package com.plcoding.data.models

import com.plcoding.other.Constants.TYPE_CUR_ROUND_DRAW_INFO

data class RoundDrawInfo(
    val data: List<String>
): BaseModel(TYPE_CUR_ROUND_DRAW_INFO)
