package com.pallav.todowithkoin.todoapp.di

import com.example.android.architecture.blueprints.todoapp.RepositoryModule
import com.pallav.todowithkoin.todoapp.addedittask.AddEditTaskContract
import com.pallav.todowithkoin.todoapp.addedittask.AddEditTaskFragment
import com.pallav.todowithkoin.todoapp.addedittask.AddEditTaskPresenter
import com.pallav.todowithkoin.todoapp.di.Properties.ARGUMENT_EDIT_TASK_ID
import com.pallav.todowithkoin.todoapp.di.Properties.CURRENT_FILTERING_KEY
import com.pallav.todowithkoin.todoapp.di.Properties.EXTRA_TASK_ID
import com.pallav.todowithkoin.todoapp.di.Properties.SHOULD_LOAD_DATA_FROM_REPO_KEY
import com.pallav.todowithkoin.todoapp.statistics.StatisticsContract
import com.pallav.todowithkoin.todoapp.statistics.StatisticsFragment
import com.pallav.todowithkoin.todoapp.statistics.StatisticsPresenter
import com.pallav.todowithkoin.todoapp.taskdetail.TaskDetailContract
import com.pallav.todowithkoin.todoapp.taskdetail.TaskDetailFragment
import com.pallav.todowithkoin.todoapp.taskdetail.TaskDetailPresenter
import com.pallav.todowithkoin.todoapp.tasks.TasksContract
import com.pallav.todowithkoin.todoapp.tasks.TasksFilterType
import com.pallav.todowithkoin.todoapp.tasks.TasksFragment
import com.pallav.todowithkoin.todoapp.tasks.TasksPresenter
import org.koin.dsl.module.applicationContext

/**
 * Koin main module
 */
val TodoAppModule = applicationContext {
    factory { TasksFragment() }
    factory { TasksPresenter(getProperty(CURRENT_FILTERING_KEY, TasksFilterType.ALL_TASKS), get()) as TasksContract.Presenter }

    factory { TaskDetailFragment() }
    factory { TaskDetailPresenter(getProperty(EXTRA_TASK_ID), get()) as TaskDetailContract.Presenter }

    factory { StatisticsFragment() }
    factory { StatisticsPresenter(get()) as StatisticsContract.Presenter }

    factory { AddEditTaskFragment() }
    factory { AddEditTaskPresenter(getProperty(ARGUMENT_EDIT_TASK_ID), get(), getProperty(SHOULD_LOAD_DATA_FROM_REPO_KEY, true)) as AddEditTaskContract.Presenter }
}


/**
 * Module list
 */
val todoAppModules = listOf(RepositoryModule, TodoAppModule)

object Properties {
    const val CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY"
    const val EXTRA_TASK_ID = "TASK_ID"
    const val ARGUMENT_EDIT_TASK_ID = "EDIT_TASK_ID"
    const val SHOULD_LOAD_DATA_FROM_REPO_KEY = "SHOULD_LOAD_DATA_FROM_REPO_KEY"
}
