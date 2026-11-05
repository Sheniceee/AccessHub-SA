namespace AccessHub.Api.DTOs;
public record UpdateProfileRequest(string FirstName,string Surname,string PhoneNumber,string PreferredLanguage,bool NotificationEnabled);
