package com.njdc.abb.familyguard.util.socket

import com.njdc.abb.familyguard.util.Constants
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.SocketChannel

class SocketClient constructor(val mConfig: SocketConfig) {

    companion object {
        @JvmStatic
        fun create(config: SocketConfig): SocketClient = SocketClient(config)
    }

    private var mSocketChannel: SocketChannel? = null

    private var mObservable: Observable<DataWrapper>? = null

    fun connect(): Observable<DataWrapper> {
        mObservable = SocketObservable()
        return mObservable!!
    }

    fun disConnect() {
        mObservable?.let {
            if (it is SocketObservable) {
                it.close()
            }
        }
    }

    inner class SocketObservable : Observable<DataWrapper>() {

        lateinit var observerWrapper: ResultObserver

        override fun subscribeActual(observer: Observer<in DataWrapper>?) {
            observerWrapper = ResultObserver(observer)
            observer?.onSubscribe(observerWrapper)
            try {
                mSocketChannel = SocketChannel.open()
                // none blocking
                mSocketChannel!!.configureBlocking(false)
                val mSelector = Selector.open()
                mSocketChannel!!.register(mSelector, SelectionKey.OP_CONNECT)
                mSocketChannel!!.connect(InetSocketAddress(mConfig.mIp, mConfig.mPort ?: 1080))
                while (!observerWrapper.isDisposed) {
                    mSelector.select()
                    val keyIterator = mSelector.selectedKeys().iterator()
                    while (keyIterator.hasNext()) {
                        val selectionKey: SelectionKey = keyIterator.next()
                        keyIterator.remove()
                        if (selectionKey.isConnectable) {
                            val socketChannel = selectionKey.channel() as SocketChannel
                            if (socketChannel.isConnectionPending) {
                                socketChannel.finishConnect()
                                if (!mConfig.mRequest.isNullOrEmpty()) {
                                    val buffer = ByteBuffer.allocate(4096)
                                    buffer.put(mConfig.mRequest.toByteArray())
                                    buffer.flip()
                                    socketChannel.write(buffer)
                                }
                                observer?.onNext(DataWrapper(Constants.OPEN, ""))
                            }
                            socketChannel.register(mSelector, SelectionKey.OP_READ)
                        } else if (selectionKey.isReadable) {
                            val socketChannel = selectionKey.channel() as SocketChannel
                            val input = ByteBuffer.allocate(0xff)
                            val len = socketChannel.read(input)
                            if (len > 0) {
                                val response = String(input.array(), 0, len)
                                observer?.onNext(
                                    DataWrapper(
                                        Constants.CONNECTING,
                                        response
                                    )
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                close()
                observer?.onError(e)
            }
        }

        fun close() {
            observerWrapper.dispose()
        }
    }

    inner class ResultObserver internal constructor(private val observer: Observer<in DataWrapper>?) :
        Disposable {

        @Volatile
        private var disposed = false

        override fun dispose() {
            disposed = true
            mSocketChannel = null
            observer?.onNext(
                DataWrapper(
                    Constants.CLOSE,
                    ""
                )
            )
        }

        override fun isDisposed(): Boolean {
            return disposed
        }

    }
}