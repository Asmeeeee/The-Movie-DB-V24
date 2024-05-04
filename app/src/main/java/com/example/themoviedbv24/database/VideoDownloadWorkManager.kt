package com.example.themoviedbv24.database

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.themoviedbv24.workers.DownloadVideoWorker

class VideoDownloadWorkManager(private val context: Context) {

    private val workManager = WorkManager.getInstance(context)

    fun downloadVideo(videoUrl: String) {
        // Create a OneTimeWorkRequest to download the video
        val downloadRequest = OneTimeWorkRequestBuilder<DownloadVideoWorker>()
            .setInputData(workDataOf(DownloadVideoWorker.KEY_VIDEO_URL to videoUrl))
            .build()

        workManager.enqueue(downloadRequest)
    }
}