package com.remote.source

//import com.android.domain.model.LoginData
//import com.remote.dto.AccountDto
//import com.remote.dto.login.GuestSessionDto
//import com.remote.dto.login.LoginDto
//import com.remote.dto.login.RequestTokenDto
//import com.remote.dto.login.SessionDto
//import com.utils.ACCOUNT
//import com.utils.CineVerseExceptions
//import com.utils.LOGIN
//import com.utils.NEW_GUEST_SESSION
//import com.utils.NEW_SESSION
//import com.utils.NEW_TOKEN
//import com.utils.PASSWORD
//import com.utils.REQUEST_TOKEN
//import com.utils.SESSION_ID_WITH_EQUAL
//import com.utils.USERNAME
//import com.utils.performCall
//import io.ktor.client.HttpClient
//import io.ktor.http.HttpMethod
//import kotlin.Unit

class LoginRemoteDataSource(
//    private val client: HttpClient
) {
//    suspend fun createRequestToken(): String {
//        val response = client.performCall<Unit, RequestTokenDto>(
//            method = HttpMethod.Companion.Get,
//            path = NEW_TOKEN
//        )
//        if (!response.success){
//            throw CineVerseExceptions.AuthenticationException(response.statusMessage ?: "")
//        }
//        return response.requestToken ?: ""
//    }
//
//    suspend fun validateLoginWithRequestToken(requestToken: String, loginData: LoginData): Boolean {
//        val response = client.performCall<Unit, LoginDto>(
//            method = HttpMethod.Companion.Post,
//            path = "${LOGIN}?$USERNAME${loginData.username}&${PASSWORD}" +
//                    "${loginData.password}&${REQUEST_TOKEN}${requestToken}"
//        )
//        if (!response.success){
//            throw CineVerseExceptions.AuthenticationException(response.statusMessage ?: "")
//        }
//        return true
//    }
//
//    suspend fun createAuthenticatedUserSession(requestToken: String): String{
//        val response = client.performCall<Unit, SessionDto>(
//        method = HttpMethod.Companion.Post,
//        path = "${NEW_SESSION}?${REQUEST_TOKEN}${requestToken}"
//        )
//        if (!response.success){
//            throw CineVerseExceptions.AuthenticationException(response.statusMessage ?: "")
//        }
//        return response.sessionId ?: ""
//    }
//
//    suspend fun createGuestUserSession(): Pair<String, String>{
//        val response = client.performCall<Unit, GuestSessionDto>(
//            method = HttpMethod.Companion.Get,
//            path = NEW_GUEST_SESSION
//        )
//        if (!response.success){
//            throw CineVerseExceptions.AuthenticationException(response.statusMessage ?: "")
//        }
//        return Pair(response.guestSessionId ?: "",response.expiresAt ?: "")
//    }
//
//    suspend fun getUserId(sessionId: String): Long{
//        val response = client.performCall<Unit, AccountDto>(
//            method = HttpMethod.Companion.Get,
//            path = "$ACCOUNT?$SESSION_ID_WITH_EQUAL$sessionId"
//        )
//        if (response.success == false){
//            throw CineVerseExceptions.AuthenticationException(response.statusMessage ?: "")
//        }
//        return response.id ?: 0
//    }

}