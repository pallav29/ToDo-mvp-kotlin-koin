/*
 * Copyright (C) 2017 The Android Open Source Project
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
package com.pallav.todowithkoin.todoapp.statistics

import com.pallav.todowithkoin.todoapp.BasePresenter
import com.pallav.todowithkoin.todoapp.BaseView

/**
 * This specifies the contract between the view and the presenter.
 */
interface StatisticsContract {

    interface View : BaseView<Presenter> {
        val isActive: Boolean

        fun setProgressIndicator(active: Boolean)

        fun showStatistics(numberOfIncompleteTasks: Int, numberOfCompletedTasks: Int)

        fun showLoadingStatisticsError()
    }

    interface Presenter : BasePresenter<View>
}
