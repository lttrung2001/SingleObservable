package com.ltbth.singleobservable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.core.SingleOnSubscribe
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var disposable: Disposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val singleObservable = getSingleObervable()
        val singleObserver = getSingleObserver()
        singleObservable.observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(singleObserver)
    }

    private fun getSingleObserver(): SingleObserver<Note> {
        return object: SingleObserver<Note> {
            override fun onSubscribe(d: Disposable) {
                Log.d("TAG", "onSubscribe");
                disposable = d;
            }

            override fun onSuccess(t: Note) {
                Log.d("TAG", "onSuccess: " + t.note);
            }

            override fun onError(e: Throwable) {
                Log.d("TAG", "onError: " + e.message);
            }
        }
    }

    private fun getSingleObervable(): Single<Note> {
        return Single.create {
            val note = Note(1,"hom nay nho nau com")
            it.onSuccess(note)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}