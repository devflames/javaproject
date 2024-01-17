package dto.Entity.LineAPI.Webhooks.Events;

import com.fasterxml.jackson.annotation.*;

import dto.Entity.LineAPI.Webhooks.*;

@JsonTypeName("unfollow")
public class UnfollowEvent extends Event {
}
