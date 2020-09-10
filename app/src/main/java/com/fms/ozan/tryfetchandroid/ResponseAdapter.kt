package com.fms.ozan.tryfetchandroid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


class ResponseAdapter(
    private var context: Context,
    private var responseModel: List<ResponseModel>
) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val rowView = inflater.inflate(R.layout.row_response, parent, false)

        val tvListID = rowView.findViewById(R.id.tv_list_id) as TextView
        val tvID = rowView.findViewById(R.id.tv__id) as TextView
        val tvName = rowView.findViewById(R.id.tv_name) as TextView

        val resObject = responseModel[position]

        val id = "ID: " + resObject.id
        val listId = "ListID: " + resObject.listId
        val name = "Name: " + resObject.name


        tvID.text = id
        tvListID.text = listId
        tvName.text = name

        return rowView
    }

    override fun getItem(position: Int): ResponseModel {

        return responseModel[position]
    }

    override fun getItemId(position: Int): Long {

        return position.toLong()
    }

    override fun getCount(): Int {

        return responseModel.size
    }
}