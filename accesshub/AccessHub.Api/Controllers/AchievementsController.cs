using System.Security.Claims;
using AccessHub.Api.Data;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace AccessHub.Api.Controllers;

[ApiController]
[Authorize]
[Route("api/achievements")]
public class AchievementsController(AccessHubDbContext db) : ControllerBase
{
    [HttpGet]
    public async Task<IActionResult> Get()
    {
        var userId = int.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier)!);
        var earned = await db.UserAchievements.Where(x => x.UserId == userId).Select(x => x.AchievementId).ToListAsync();
        var list = await db.Achievements.OrderBy(x => x.PointsRequired).Select(x => new {
            achievementId=x.Id, achievementName=x.Name, description=x.Description,
            pointsRequired=x.PointsRequired, earned=earned.Contains(x.Id)
        }).ToListAsync();
        return Ok(list);
    }
}
