package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.button)
        val data: Data = Data.Builder().putString("key","Hello from activity!").build()

        val contraint = Constraints.Builder().setRequiresCharging(true).build()

        val worker = OneTimeWorkRequestBuilder<MyWorker>()
            .setInputData(data)
            .build()
        val worker2 = OneTimeWorkRequestBuilder<MyWorker2>()
            .setConstraints(contraint)
            .build()


        val list: ArrayList<WorkRequest> = ArrayList()
        list.add(worker)
        list.add(worker2)

        val worker3 = PeriodicWorkRequestBuilder<MyWorker2>(15, TimeUnit.MINUTES).build()

        // запускай каждые 20 минут в течение часа
        val worker4 = PeriodicWorkRequestBuilder<MyWorker2>(1, TimeUnit.HOURS, 20, TimeUnit.MINUTES).build()

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(worker.id).observe(
            this, Observer { data: WorkInfo? ->
                if(data != null) {
                    data.outputData.getString("key2")?.let { Log.d("RRR", it) }
                    Log.d("RRR", "${data.id} => ${data.state}")
                }
            }
        )

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(worker2.id).observe(
            this, Observer { data: WorkInfo? ->
                if(data != null) {
                    Log.d("RRR", "WORKER2: ${data.id} => ${data.state}")
                }
            }
        )


        btn.setOnClickListener {

            // запускаем воркеры параллельно!
            WorkManager.getInstance(this).enqueue(list)


            // запускаем воркеры последовательно!
            /*WorkManager.getInstance(this)
                .beginWith(worker)
                .then(worker2)
                .enqueue()
            */
            //WorkManager.getInstance(this).enqueue(worker)

        }
    }
}