using System.Security.Claims;
using AccessHub.Api.Data;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace AccessHub.Api.Controllers;

[ApiController]
[Authorize]
[Route("api/notifications")]
public class NotificationsController(AccessHubDbContext db) : ControllerBase
{
    private int UserId => int.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier)!);

    [HttpGet]
    public async Task<IActionResult> Get()
    {
        var result = await db.Notifications.Where(n => n.UserId == UserId)
            .OrderByDescending(n => n.CreatedDate)
            .Select(n => new { notificationId=n.Id, notificationMessage=n.Message, isRead=n.IsRead, createdDate=n.CreatedDate })
            .ToListAsync();
        return Ok(result);
    }

    [HttpPut("{id}/read")]
    public async Task<IActionResult> Read(int id)
    {
        var n = await db.Notifications.FirstOrDefaultAsync(x => x.Id == id && x.UserId == UserId);
        if (n is null) return NotFound();
        n.IsRead = true;
        await db.SaveChangesAsync();
        return Ok(new { message = "Notification marked as read." });
    }
}
