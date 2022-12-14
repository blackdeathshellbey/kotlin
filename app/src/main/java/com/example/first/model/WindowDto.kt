import com.example.first.model.RoomDto

enum class Status { OPEN, CLOSED}

data class WindowDto(val id: Long,
                     val name: String,
                     val room: RoomDto,
                     val status: Status)