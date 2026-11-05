using AccessHub.Api.Models;

namespace AccessHub.Api.Services;

public interface ITokenService
{
    string CreateToken(User user);
}
