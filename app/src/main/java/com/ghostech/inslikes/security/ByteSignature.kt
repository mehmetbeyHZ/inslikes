package com.ghostech.inslikes.security

import org.json.JSONObject


class ByteSignature {
    var jsonObject: JSONObject = JSONObject()

    fun addParam(key: String, value: String): ByteSignature {
        try {
            jsonObject.put(key, value)
        } catch (e: Exception) {
        }
        return this
    }

    fun buildSignature(): String {
        return "SIGNATURE.$jsonObject"
    }

    fun followSignature(
        _csrftoken: String,
        user_id: String,
        _uid: String,
        device_id: String,
        _uuid: String
    ): String {
        addParam("_csrftoken", _csrftoken)
            .addParam("user_id", user_id)
            .addParam("radio_type", "wifi-none")
            .addParam("_uid", _uid)
            .addParam("device_id", device_id)
            .addParam("_uuid", _uuid)
        return buildSignature()
    }

    fun likeSignature(
        media_id: String,
        _csrftoken: String,
        _uid: String,
        _uuid: String
    ): String {
        addParam("delivery_class", "organic")
            .addParam("media_id", media_id)
            .addParam("_csrftoken", _csrftoken)
            .addParam("radio_type", "wifi-none")
            .addParam("_uid", _uid)
            .addParam("_uuid", _uuid)
            .addParam("is_carousel_bumped_post", "false")
            .addParam("container_module", "feed_short_url")
            .addParam("feed_position", "0")
        return buildSignature()
    }

    init {
        jsonObject = JSONObject()
    }
}
