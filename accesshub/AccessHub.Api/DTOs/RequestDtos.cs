namespace AccessHub.Api.DTOs;
public record CreateRequest(int ServiceId,string Description,string Priority);
public record UpdateRequestStatus(string Status);
public record RegisterFcmToken(string FcmToken);
