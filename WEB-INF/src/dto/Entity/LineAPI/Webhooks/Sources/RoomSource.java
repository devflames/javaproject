package dto.Entity.LineAPI.Webhooks.Sources;

import com.fasterxml.jackson.annotation.*;

import dto.Entity.LineAPI.Webhooks.*;

@JsonTypeName("room")
public class RoomSource extends Source {

	private String roomId;

	public String getRoomId() { return roomId; }
	public void setRoomId(String roomId) { this.roomId = roomId; }
}
