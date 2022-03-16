package com.elewamathtutoring.Models.My_Schedule

import java.io.Serializable

data class Body(
    val Pending_sessions: List<PendingSession>,
    val Today_sessions: List<today_session>,
    val Upcoming_sessions: List<upcoming_session>
):Serializable