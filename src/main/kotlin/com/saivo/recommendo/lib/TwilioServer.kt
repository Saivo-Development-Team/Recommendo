package com.saivo.recommendo.lib

import com.twilio.Twilio
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber

object TwilioServer {

    private val TWILIO_NUMBER: String = System.getenv("TWILIO_NUMBER")
    private val TWILIO_USERNAME: String = System.getenv("TWILIO_USERNAME")
    private val TWILIO_PASSWORD: String = System.getenv("TWILIO_PASSWORD")

    init {
        Twilio.init(getUsername(), getPassword())
    }

    fun sendSms(message: String, number: String) = createMessage(message, number).status.name

    private fun createMessage(message: String, number: String): Message {
        return Message.creator(PhoneNumber(number), getSystemNumber(), message).create()
    }

    private fun getSystemNumber(): PhoneNumber {
        return PhoneNumber(TWILIO_NUMBER)
    }

    private fun getUsername(): String? {
        return TWILIO_USERNAME
    }

    private fun getPassword(): String? {
        return TWILIO_PASSWORD
    }
}
