package sheridan.adolfo_david_romero.prototype_device.domain.api

/**
 * Student ID: 991555778
 * Prototype-Device
 * created by davidromero
 * on 2024-10-30
 **/
import android.util.Log
import okhttp3.*
import java.io.IOException
import java.security.MessageDigest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DigestAuthenticator(
    private val client: OkHttpClient,
    private val username: String,
    private val password: String
) {
    suspend fun authenticate(url: String): String {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("DigestAuthenticator", "Starting initial unauthenticated request")

                // Initial unauthenticated request
                val request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()

                if (response.code == 401) {
                    Log.d("DigestAuthenticator", "Received 401 Unauthorized, parsing WWW-Authenticate header")

                    // Parse WWW-Authenticate header for Digest details
                    val wwwAuthenticate = response.header("WWW-Authenticate")
                    if (wwwAuthenticate == null) {
                        Log.e("DigestAuthenticator", "Authentication header not found")
                        return@withContext "Authentication header not found"
                    }

                    Log.d("DigestAuthenticator", "WWW-Authenticate header: $wwwAuthenticate")
                    val authDetails = parseAuthDetails(wwwAuthenticate)

                    // Build digest header using parsed details
                    val authHeader = buildDigestHeader(url, authDetails)
                    if (authHeader != null) {
                        Log.d("DigestAuthenticator", "Built Digest Authorization header: $authHeader")

                        // Retry request with Authorization header
                        val authenticatedRequest = request.newBuilder()
                            .header("Authorization", authHeader)
                            .build()
                        val authenticatedResponse = client.newCall(authenticatedRequest).execute()

                        return@withContext if (authenticatedResponse.isSuccessful) {
                            Log.d("DigestAuthenticator", "Authentication successful")
                            "Connection Successful: ${authenticatedResponse.body?.string()}"
                        } else {
                            Log.e("DigestAuthenticator", "Failed to authenticate - Response code: ${authenticatedResponse.code}")
                            "Connection Failed: ${authenticatedResponse.code} - ${authenticatedResponse.message}"
                        }
                    } else {
                        Log.e("DigestAuthenticator", "Failed to build Authorization header")
                    }
                } else {
                    Log.e("DigestAuthenticator", "Unexpected response code: ${response.code}")
                }
                "Connection Error: Failed to authenticate"
            } catch (e: IOException) {
                Log.e("DigestAuthenticator", "Connection Error: ${e.message}")
                "Connection Error: ${e.message}"
            }
        }
    }

    private fun parseAuthDetails(header: String): Map<String, String> {
        val authParams = mutableMapOf<String, String>()
        // Remove "Digest " prefix and split parameters by commas
        header.removePrefix("Digest ").split(", ").forEach { part ->
            val (key, value) = part.split("=").map { it.trim().replace("\"", "") }
            authParams[key] = value
        }
        Log.d("DigestAuthenticator", "Parsed auth details: $authParams")
        return authParams
    }

    private fun buildDigestHeader(url: String, authDetails: Map<String, String>): String? {
        val realm = authDetails["realm"] ?: return null
        val nonce = authDetails["nonce"] ?: return null
        val qop = authDetails["qop"] ?: "auth"
        val nc = "00000001"  // Nonce count (should increment per request)
        val cnonce = "0a4f113b"  // Fixed client nonce for testing

        // Calculate HA1, HA2, and response
        val ha1 = md5("$username:$realm:$password")
        Log.d("DigestAuthenticator", "HA1: $ha1")

        val ha2 = md5("GET:$url")
        Log.d("DigestAuthenticator", "HA2: $ha2")

        val response = md5("$ha1:$nonce:$nc:$cnonce:$qop:$ha2")
        Log.d("DigestAuthenticator", "Digest response: $response")

        val authHeader = "Digest username=\"$username\", realm=\"$realm\", nonce=\"$nonce\", uri=\"$url\", " +
                "qop=$qop, nc=$nc, cnonce=\"$cnonce\", response=\"$response\""
        return authHeader
    }

    private fun md5(data: String): String {
        return MessageDigest.getInstance("MD5").digest(data.toByteArray()).joinToString("") {
            "%02x".format(it)
        }
    }
}
