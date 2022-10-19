package com.mashup.core.attendance.crew

import androidx.lifecycle.SavedStateHandle
import com.mashup.base.BaseViewModel
import com.mashup.data.dto.PlatformAttendanceResponse
import com.mashup.data.model.PlatformInfo
import com.mashup.data.repository.AttendanceRepository
import com.mashup.network.errorcode.BAD_REQUEST
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CrewAttendanceViewModel @Inject constructor(
    private val attendanceRepository: AttendanceRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val platformAttendance
        get() = savedStateHandle.getLiveData<PlatformInfo>(EXTRA_PLATFORM_KEY)

    val scheduleId
        get() = savedStateHandle.get<Int>(EXTRA_SCHEDULE_ID)

    private val _crewAttendanceState =
        MutableStateFlow<CrewAttendanceState>(CrewAttendanceState.Empty)
    val crewAttendanceState: StateFlow<CrewAttendanceState> =
        _crewAttendanceState

    init {
        getCrewAttendanceList()
    }

    fun getCrewAttendanceList() = mashUpScope {
        _crewAttendanceState.emit(CrewAttendanceState.Loading)
        val platformName = platformAttendance.value?.platform?.name?.uppercase()
        val scheduleId = scheduleId
        if (platformName == null || scheduleId == null) {
            handleErrorCode(BAD_REQUEST)
            return@mashUpScope
        }
        val response = attendanceRepository.getCrewAttendanceList(
            platformName = platformName,
            scheduleId = scheduleId
        )

        if (!response.isSuccess()) {
            handleErrorCode(response.code)
            return@mashUpScope
        }

        response.data?.run {
            _crewAttendanceState.emit(
                CrewAttendanceState.Success(response.data)
            )
        }
    }

    companion object {
        const val EXTRA_PLATFORM_KEY = "EXTRA_PLATFORM_KEY"
        const val EXTRA_SCHEDULE_ID = "EXTRA_SCHEDULE_ID"
    }

    override fun handleErrorCode(code: String) {
        mashUpScope {
            _crewAttendanceState.emit(CrewAttendanceState.Error(code))
        }
    }
}

sealed interface CrewAttendanceState {
    object Empty : CrewAttendanceState
    object Loading : CrewAttendanceState
    data class Success(val data: PlatformAttendanceResponse) : CrewAttendanceState
    data class Error(val code: String) : CrewAttendanceState
}