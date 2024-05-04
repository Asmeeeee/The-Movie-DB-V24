package com.example.themoviedbv24.workers

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream

class DownloadVideoWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val videoUrl = inputData.getString(KEY_VIDEO_URL) ?: return Result.failure()

        // Download the video
        val downloadedVideoPath = downloadVideo(videoUrl) ?: return Result.failure()

        // Save the downloaded video to storage
        val savedVideoPath = saveVideoToStorage(downloadedVideoPath) ?: return Result.failure()

        return Result.success()
    }

    private suspend fun downloadVideo(videoUrl: String): String? {
        Log.i("DownloadVideoWorker", "Downloading video from $videoUrl")
        return withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url(videoUrl)
                    .build()
                val response = client.newCall(request).execute()
                if (!response.isSuccessful) return@withContext null

                val videoFileName = "downloaded_video.mp4"
                val downloadedVideoFile = File(applicationContext.cacheDir, videoFileName)
                val outputStream = FileOutputStream(downloadedVideoFile)
                outputStream.use { output ->
                    output.write(response.body?.bytes())
                }

                downloadedVideoFile.absolutePath
            } catch (e: Exception) {
                null
            }
        }
    }

    private suspend fun saveVideoToStorage(downloadedVideoPath: String): String? {
        Log.i("DownloadVideoWorker", "Saving video to storage: "+downloadedVideoPath)
        return withContext(Dispatchers.IO) {
            try {
                val sourceFile = File(downloadedVideoPath)
                val downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val destinationFile = File(downloadsDirectory, "saved_video.mp4")
                sourceFile.copyTo(destinationFile, true)
                destinationFile.absolutePath
            } catch (e: Exception) {
                null
            }
        }
    }

    companion object {
        const val KEY_VIDEO_URL = "video_url"
    }
}