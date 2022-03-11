package com.posse.android.softjet.view.main.viewModel

import com.posse.android.softjet.di.annotations.Interactor
import com.posse.android.softjet.model.AppState
import com.posse.android.softjet.model.DataSource
import com.posse.android.softjet.model.data.Data
import com.posse.android.softjet.model.data.Response
import com.posse.android.softjet.view.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(
    @Interactor private val interactor: DataSource<Response>
) : BaseViewModel<AppState<*>>() {

    var page = 1

    fun getStartData(isOnline: Boolean) {
        page = 1
        stateLiveData.value = AppState.Loading
        getMoreData(isOnline, 2)
    }

    fun getMoreData(isOnline: Boolean, pages: Int) {
        coroutineScope.launch {
            try {
                val dataList: MutableList<Data> = mutableListOf()
                val data = interactor.getData(isOnline, page)
                dataList.addAll(data.data)
                val lastPage =
                    if (data.total_pages < page + pages - 1) data.total_pages else page + pages - 1
                if (data.total_pages <= lastPage) {
                    for (i in page + 1..lastPage) {
                        val result = interactor.getData(isOnline, i)
                        dataList.addAll(result.data)
                    }
                    page = lastPage
                }
                val resultResponse =
                    Response(dataList, page, data.per_page, data.total, data.total_pages)
                stateLiveData.postValue(AppState.Success(resultResponse))
            } catch (e: Exception) {
                stateLiveData.postValue(AppState.Error(e))
            }
        }
    }
}