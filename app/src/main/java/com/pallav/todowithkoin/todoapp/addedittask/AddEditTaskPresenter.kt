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

import com.pallav.todowithkoin.todoapp.data.Task
import com.pallav.todowithkoin.todoapp.data.source.TasksDataSource
import com.pallav.todowithkoin.todoapp.di.Properties.SHOULD_LOAD_DATA_FROM_REPO_KEY
import org.koin.standalone.KoinComponent
import org.koin.standalone.setProperty

/**
 * Listens to user actions from the UI ([AddEditTaskFragment]), retrieves the data and updates
 * the UI as required.
 * @param taskId ID of the task to edit or null for a new task
 *
 * @param tasksRepository a repository of data for tasks
 *
 * @param view the add/edit view
 *
 * @param isDataMissing whether data needs to be loaded or not (for config changes)
 */
class AddEditTaskPresenter(
        private val taskId: String,
        val tasksRepository: TasksDataSource,
        override var isDataMissing: Boolean
) : AddEditTaskContract.Presenter, TasksDataSource.GetTaskCallback, KoinComponent {

    override lateinit var view: AddEditTaskContract.View

    override fun start() {
        if (taskId.isNotEmpty() && isDataMissing) {
            populateTask()
        }
    }

    override fun stop() {
        setProperty(SHOULD_LOAD_DATA_FROM_REPO_KEY, isDataMissing)
    }

    override fun saveTask(title: String, description: String) {
        if (taskId.isEmpty()) {
            createTask(title, description)
        } else {
            updateTask(title, description)
        }
    }

    override fun populateTask() {
        if (taskId.isEmpty()) {
            throw RuntimeException("populateTask() was called but task is new.")
        }
        tasksRepository.getTask(taskId, this)
    }

    override fun onTaskLoaded(task: Task) {
        // The view may not be able to handle UI updates anymore
        if (view.isActive) {
            view.setTitle(task.title)
            view.setDescription(task.description)
        }
        isDataMissing = false
    }

    override fun onDataNotAvailable() {
        // The view may not be able to handle UI updates anymore
        if (view.isActive) {
            view.showEmptyTaskError()
        }
    }

    private fun createTask(title: String, description: String) {
        val newTask = Task(title, description)
        if (newTask.isEmpty) {
            view.showEmptyTaskError()
        } else {
            tasksRepository.saveTask(newTask)
            view.showTasksList()
        }
    }

    private fun updateTask(title: String, description: String) {
        if (taskId.isEmpty()) {
            throw RuntimeException("updateTask() was called but task is new.")
        }
        tasksRepository.saveTask(Task(title, description, taskId))
        view.showTasksList() // After an edit, go back to the list.
    }
}
