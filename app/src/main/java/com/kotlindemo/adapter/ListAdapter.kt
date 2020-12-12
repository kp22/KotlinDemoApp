package com.kotlindemo.adapter

import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.kotlindemo.main.R
import com.kotlindemo.model.DataModel
import java.util.*
import kotlin.concurrent.timerTask

class ListAdapter(var list: List<DataModel>) : RecyclerView.Adapter<ListAdapter.Viewholder>(),
    Runnable {

    lateinit var context: Context
    val TAG = ListAdapter::class.java.simpleName
    var handler: Handler = Handler()

    init {
        Log.e(TAG, "init call")
    }

    class Viewholder(view: View) : RecyclerView.ViewHolder(view) {
        var tvTitle: TextView
        var tvDesc: TextView
        val imgMain: ImageView
        val btnStartStop: Button

        init {
            tvTitle = view.findViewById(R.id.tvTitle)
            tvDesc = view.findViewById(R.id.tvDesc)
            imgMain = view.findViewById(R.id.imgMain)
            btnStartStop = view.findViewById(R.id.btnStartStop)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val vi = inflater.inflate(R.layout.item_list, null)
        return Viewholder(vi)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        Log.e(TAG, "onBindViewHolder: items pos  => $position")

        val value = list[position]
        holder.tvTitle.text = value.title
        holder.tvDesc.text = value.desc
        holder.imgMain.setImageResource(value.img)
        holder.tvDesc.text = "Count - " + value.count;

        if (value.isStart) {
            holder.tvDesc.setTextColor(context.resources.getColor(R.color.green))
            holder.btnStartStop.text = "Stop"
        } else {
            holder.tvDesc.setTextColor(context.resources.getColor(R.color.txtGray))
            holder.btnStartStop.text = "Start"
        }

        holder.btnStartStop.setOnClickListener {
            Log.e(TAG, "onBindViewHolder: pos => $position")
            val item = list[position];
            if (!item.isStart) {
                item.timer.scheduleAtFixedRate(
                    timerTask {
                        item.count++;
                        holder.tvDesc.text = "Count - " + list[position].count;
                    }, 0, 1000
                )
                item.isStart = true
                holder.btnStartStop.text = "Stop"
                holder.tvDesc.setTextColor(context.resources.getColor(R.color.green))
            } else {
                item.timer.cancel()
                item.timer = Timer()
                item.isStart = false
                holder.btnStartStop.text = "Start"
                holder.tvDesc.setTextColor(context.resources.getColor(R.color.txtGray))
            }
            startUpdate();
        }
    }

    private fun startUpdate() {

        if (isTimerRunning() == 1)
            handler.postDelayed(this, 1000)

        Handler().postDelayed(Runnable {
            if (isTimerRunning() == 0) {
                handler.removeCallbacks(this)
            }
        }, 1000)
    }


    private fun isTimerRunning(): Int {
        return list.filter { it.isStart }.size
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun run() {
        notifyDataSetChanged()
        handler.postDelayed(this, 1000)
    }

}