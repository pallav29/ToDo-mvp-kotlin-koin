/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pallav.todowithkoin.todoapp.addedittask

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.pallav.todowithkoin.todoapp.R
import com.pallav.todowithkoin.todoapp.util.showSnackBar
import org.koin.android.ext.android.inject

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
class AddEditTaskFragment : Fragment(), AddEditTaskContract.View {

    override val presenter by inject<AddEditTaskContract.Presenter>()

    override var isActive = false
        get() = isAdded

    private lateinit var title: TextView
    private lateinit var description: TextView

    override fun onResume() {
        super.onResume()
        presenter.view = this
        presenter.start()
    }

    override fun onPause() {
        presenter.stop()
//        releaseContext(Context.AddEditTask)
        super.onPause()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(activity!!.findViewById<FloatingActionButton>(R.id.fab_edit_task_done)) {
            setImageResource(R.drawable.ic_done)
            setOnClickListener {
                presenter.saveTask(title.text.toString(), description.text.toString())
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.addtask_frag, container, false)
        with(root) {
            title = findViewById(R.id.add_task_title)
            description = findViewById(R.id.add_task_description)
        }
        setHasOptionsMenu(true)
        return root
    }

    override fun showEmptyTaskError() {
        title.showSnackBar(getString(R.string.empty_task_message), Snackbar.LENGTH_LONG)
    }

    override fun showTasksList() {
        with(activity!!) {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    override fun setTitle(title: String) {
        this.title.text = title
    }

    override fun setDescription(description: String) {
        this.description.text = description
    }
}
