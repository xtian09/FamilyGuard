package com.njdc.abb.familyguard.util.socket

class SocketConfig(
    val mIp: String?,
    val mPort: Int?,
    val mRequest: String?
) {

    private constructor(builder: Builder) : this(builder.mIp, builder.mPort, builder.mRequest)

    class Builder {
        var mIp: String? = null
            private set

        var mPort: Int? = null
            private set

        var mRequest: String? = null

        fun setIp(ip: String) = apply { this.mIp = ip }

        fun setPort(port: Int) = apply { this.mPort = port }

        fun setRequest(request: String?) = apply { this.mRequest = request }

        fun build() = SocketConfig(this)
    }
}