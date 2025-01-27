package com.mashup.data.model

import com.mashup.core.model.AttendanceStatus
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * AttendanceInfoResponse랑 합치면 좋을 듯
 */
@JsonClass(generateAdapter = true)
data class MemberInfo(
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "memberId")
    val memberId: Int,
    @field:Json(name = "attendanceInfos")
    val attendanceInfos: List<AttendanceInfo>
) {
    private fun getAttendanceStatus(position: Int): AttendanceStatus {
        return attendanceInfos.getOrNull(position)?.status ?: AttendanceStatus.NOT_YET
    }

    fun getFinalAttendance(): AttendanceStatus {
        return when {
            getAttendanceStatus(0) == AttendanceStatus.ATTENDANCE &&
                getAttendanceStatus(1) == AttendanceStatus.ATTENDANCE -> {
                AttendanceStatus.ATTENDANCE
            }
            getAttendanceStatus(0) == AttendanceStatus.NOT_YET ||
                getAttendanceStatus(1) == AttendanceStatus.NOT_YET -> {
                AttendanceStatus.NOT_YET
            }
            getAttendanceStatus(0) == AttendanceStatus.ABSENT ||
                getAttendanceStatus(1) == AttendanceStatus.ABSENT -> {
                AttendanceStatus.ABSENT
            }
            else -> {
                AttendanceStatus.LATE
            }
        }
    }
}
