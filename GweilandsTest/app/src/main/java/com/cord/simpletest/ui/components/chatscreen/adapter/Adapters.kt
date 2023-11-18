package com.cord.simpletest.ui.components.chatscreen.adapter

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.cord.simpletest.R
import com.cord.simpletest.databinding.ImageRecyclerUiBinding
import com.cord.simpletest.models.Image

class adapters (var context: Activity?, var mList:ArrayList<Image>,
                var onclicks:(Int)-> Unit): RecyclerView.Adapter<adapters.myView>() {
        // var context:Context?=null
        // var layout:ViewDataBinding=DataBindingUtil.setContentView(context!!,view)

        class myView(var binding:ImageRecyclerUiBinding): RecyclerView.ViewHolder(binding.root){}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myView {
            var bindings= ImageRecyclerUiBinding.inflate(context!!.layoutInflater,parent,false)
            return myView(bindings)
        }

        override fun onBindViewHolder(holder: myView, position: Int) {
            var data=mList[position]
            holder.binding.apply {
                image.setImageURI(Uri.fromFile(data.xt_image))
                message.setText(data.title)
//                Glide.with(holder.itemView.context).load(data.xt_image).apply(
//                    RequestOptions().placeholder(R.drawable.progress_animation)
//                        .error((R.drawable.progress_animation)).centerCrop()
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .priority(Priority.HIGH)
//                        .transform(CenterInside(), RoundedCorners(10))
//                )
//                    .into(image)

            }
            holder.itemView.setOnClickListener {
                onclicks.invoke(position)
            }
            Log.e("akjxnkajsn","kjcnaknc $position")
        }

        override fun getItemCount(): Int {
            return mList.size
        }
    fun updateList(value: String) {
        var data= Image()
        data.title=value
        data.xt_image=null
        mList.add(data)
        notifyDataSetChanged()
    }
                }