package com.omercankoc.imagepost

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(
    private val userEMails : ArrayList<String>,
    private val userComments : ArrayList<String>,
    private val userImages : ArrayList<String>
    ) : RecyclerView.Adapter<RecyclerAdapter.PostHolder>()  {

    class PostHolder(view : View) : RecyclerView.ViewHolder(view){
        var recyclerEMailText : TextView? = null
        var recyclerCommentText : TextView? = null
        var recyclerImageView : TextView? = null

        // PostHolder sinifindan bir nesne olusturulurken cagirilacak ilk method.
        init {
            recyclerEMailText = view.findViewById(R.id.textViewEMail)
            recyclerCommentText = view.findViewById(R.id.textViewComment)
            //recyclerImageView = view.findViewById(R.id.imageViewDownloadImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_row,parent,false)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.recyclerEMailText?.text = userEMails[position]
        holder.recyclerCommentText?.text = userComments[position]
    }

    override fun getItemCount(): Int {
        return userEMails.size
    }
}