namespace AccessHub.Api.DTOs;
public record RegisterRequest(string FirstName,string Surname,string Email,string PhoneNumber,string Password);
public record LoginRequest(string Email,string Password);
public record ChangePasswordRequest(string CurrentPassword,string NewPassword);
public record GoogleLoginRequest(string GoogleSubjectId,string Email,string FirstName,string Surname);
