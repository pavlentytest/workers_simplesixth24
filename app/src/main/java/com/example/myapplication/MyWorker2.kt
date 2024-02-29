package com.example.myapplication

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker2(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        Log.d("RRR","Start worker 2")
        Thread.sleep(10000)
        Log.d("RRR", "Finish worker 2")
        return Result.success()
    }
}