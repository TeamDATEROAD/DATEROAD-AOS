package org.sopt.teamdateroad.data.dataremote.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMetaDto(
    @SerializedName("is_end")
    val isEnd: Boolean
)
