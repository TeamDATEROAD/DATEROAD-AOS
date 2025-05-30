package org.sopt.teamdateroad.data.dataremote.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.sopt.teamdateroad.data.dataremote.model.request.RequestSignInDto
import org.sopt.teamdateroad.data.dataremote.model.request.RequestWithdrawDto
import org.sopt.teamdateroad.data.dataremote.model.response.ResponseAuthDto
import org.sopt.teamdateroad.data.dataremote.util.ApiConstraints.API
import org.sopt.teamdateroad.data.dataremote.util.ApiConstraints.CHECK
import org.sopt.teamdateroad.data.dataremote.util.ApiConstraints.DELETE_METHOD
import org.sopt.teamdateroad.data.dataremote.util.ApiConstraints.IS_DEFAULT_IMAGE
import org.sopt.teamdateroad.data.dataremote.util.ApiConstraints.NAME
import org.sopt.teamdateroad.data.dataremote.util.ApiConstraints.SIGNUP
import org.sopt.teamdateroad.data.dataremote.util.ApiConstraints.SIGN_IN
import org.sopt.teamdateroad.data.dataremote.util.ApiConstraints.SIGN_OUT
import org.sopt.teamdateroad.data.dataremote.util.ApiConstraints.TAG
import org.sopt.teamdateroad.data.dataremote.util.ApiConstraints.TAGS
import org.sopt.teamdateroad.data.dataremote.util.ApiConstraints.USERS
import org.sopt.teamdateroad.data.dataremote.util.ApiConstraints.USER_SIGN_UP_DATA
import org.sopt.teamdateroad.data.dataremote.util.ApiConstraints.VERSION
import org.sopt.teamdateroad.data.dataremote.util.ApiConstraints.WITHDRAW
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface AuthService {
    @DELETE("$API/$VERSION/$USERS/$SIGN_OUT")
    suspend fun deleteSignOut()

    @HTTP(method = DELETE_METHOD, hasBody = true, path = "$API/$VERSION/$USERS/$WITHDRAW")
    suspend fun deleteWithdraw(
        @Body requestWithdrawDto: RequestWithdrawDto
    )

    @GET("$API/$VERSION/$USERS/$CHECK")
    suspend fun getNicknameCheck(
        @Query(NAME) name: String
    ): Response<Unit>

    @POST("$API/$VERSION/$USERS/$SIGN_IN")
    suspend fun postSignIn(
        @Body requestSignInDto: RequestSignInDto
    ): ResponseAuthDto

    @Multipart
    @POST("$API/$VERSION/$USERS/$SIGNUP")
    suspend fun postSignUp(
        @Part image: MultipartBody.Part?,
        @Part(USER_SIGN_UP_DATA) userSignUpData: RequestBody,
        @Part(TAG) tags: RequestBody
    ): ResponseAuthDto

    @Multipart
    @PATCH("$API/$VERSION/$USERS")
    suspend fun patchProfile(
        @Part(NAME) name: RequestBody,
        @Part(TAGS) tags: RequestBody,
        @Part image: MultipartBody.Part?,
        @Part(IS_DEFAULT_IMAGE)isDefaultImage: RequestBody
    )
}
