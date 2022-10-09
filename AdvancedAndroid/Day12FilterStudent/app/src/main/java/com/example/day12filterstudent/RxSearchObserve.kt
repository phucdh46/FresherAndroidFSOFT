package com.example.day12filterstudent

import android.widget.SearchView
import io.reactivex.rxjava3.core.Observable

object RxSearchObserve {
    fun fromView(searchView: SearchView): Observable<String> {
        return Observable.create { subscriber ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    subscriber.onNext(newText!!)
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    subscriber.onNext(query!!)
                    return true
                }
            })
        }
    }
}