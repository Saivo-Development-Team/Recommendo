package com.saivo.recommendo.model.objects

import com.saivo.recommendo.model.infrastructure.WithId
import javax.persistence.Entity

@Entity
data class ProfileImage(
        val data: ByteArray,
        val fileName: String
) : WithId() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProfileImage

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}