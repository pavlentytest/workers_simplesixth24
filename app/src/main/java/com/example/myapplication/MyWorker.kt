package com.example.myapplication

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        Log.d("RRR","Start worker 1")
        Thread.sleep(5000)
        Log.d("RRR", "Finish worker 1")

        val result = inputData.getString("key")
        val data: Data = Data.Builder().putString("key2","Hello from worker1").build()

        return Result.success(data)
    }
}