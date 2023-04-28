package com.glidline.myglidelinss

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.glidline.myglidelinss.model.User

class UserAdapter(
    private val userList: ArrayList<User>,
    private val context: Context,
    private val btnlistener :BtnClickListener
    ) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    companion object {
        var mClickListener: BtnClickListener? = null
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val user_name: TextView = view.findViewById(R.id.user_name)
        val user_email: TextView = view.findViewById(R.id.user_email)
        val user_mobile_number: TextView = view.findViewById(R.id.user_mobile_number)
        val user_address: TextView = view.findViewById(R.id.user_address)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.user_name.text = user.user_name
        holder.user_email.text = user.email
        holder.user_email.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:${user.email}")
            }
            holder.itemView.context.startActivity(Intent.createChooser(intent, "Send email"))
        }
        holder.user_mobile_number.text = user.mobile_number
        holder.user_address.text = user.street_address
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    open interface BtnClickListener {
        fun onBtnClick(position: Int, type: String)

    }
}
