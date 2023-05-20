package com.lmkhant.foodrecipedb

import android.graphics.Color
import com.lmkhant.foodrecipedb.model.Meal
import org.junit.Test

import org.junit.Assert.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun serverTest() {
        val apiUrl = "https://www.themealdb.com/" // Replace with your API server URL

        try {
            val url = URL(apiUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val responseCode = connection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                println("API server is responding.")
            } else {
                println("API server is not responding. Response code: $responseCode")
            }
        } catch (e: Exception) {
            println("API server is not responding. Error: ${e.message}")
        }
    }

    @Test
    fun test_youtube_id(){
        getYouTubeId("https:\\/\\/www.youtube.com\\/watch?v=MHhLJqghY-o")
        assertEquals("MHhLJqghY-o",getYouTubeId("https:\\/\\/www.youtube.com\\/watch?v=MHhLJqghY-o"))
    }

    private fun getYouTubeId(youTubeUrl: String): String? {
        val pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"
        val compiledPattern: Pattern = Pattern.compile(pattern)
        val matcher: Matcher = compiledPattern.matcher(youTubeUrl)
        return if (matcher.find()) {
            matcher.group()
        }else return null
    }
}