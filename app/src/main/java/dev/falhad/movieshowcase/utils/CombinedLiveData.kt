package dev.falhad.movieshowcase.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * CombinedLiveData is a helper class to combine results from multiple LiveData sources.
 * @param liveDataObjects Variable number of LiveData arguments.
 * @param combine   Function reference that will be used to combine all LiveData data.
 * @param R         The type of data returned after combining all LiveData data.
 * Usage:
 * CombinedLiveData<SomeType>(
 *     getLiveData1(),
 *     getLiveData2(),
 *     ... ,
 *     getLiveDataN()
 * ) { datas: List<Any?> ->
 *     // Use datas[0], datas[1], ..., datas[N] to return a SomeType value
 * }
 */
class CombinedLiveData<R>(
    vararg liveDataObjects: LiveData<*>,
    private val combine: (liveDataObjects: List<Any?>) -> R
) : MediatorLiveData<R>() {

    private val _dataObjects: MutableList<Any?> = MutableList(liveDataObjects.size) { null }

    init {
        for (i in liveDataObjects.indices) {
            super.addSource(liveDataObjects[i]) {
                _dataObjects[i] = it
                value = combine(_dataObjects)
            }
        }
    }
}