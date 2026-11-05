using System.Security.Claims; using AccessHub.Api.Data; using AccessHub.Api.DTOs; using AccessHub.Api.Models; using AccessHub.Api.Services; using Microsoft.AspNetCore.Authorization; using Microsoft.AspNetCore.Mvc; using Microsoft.EntityFrameworkCore;
namespace AccessHub.Api.Controllers;
[ApiController][Authorize][Route("api/requests")]
public class RequestsController(AccessHubDbContext db,AchievementService achievements):ControllerBase
{
 private int UserId=>int.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier)!);
 [HttpGet] public async Task<IActionResult> Get(){return Ok(await db.Requests.Include(x=>x.Service).Where(x=>x.UserId==UserId).OrderByDescending(x=>x.CreatedDate).Select(x=>new{requestId=x.Id,serviceId=x.ServiceId,serviceName=x.Service!.ServiceName,description=x.Description,priority=x.Priority,status=x.Status,createdDate=x.CreatedDate,synced=x.Synced}).ToListAsync());}
 [HttpPost] public async Task<IActionResult> Create(CreateRequest request){if(!await db.Services.AnyAsync(x=>x.Id==request.ServiceId))return BadRequest(new{message="Service does not exist."});var e=new ServiceRequest{UserId=UserId,ServiceId=request.ServiceId,Description=request.Description.Trim(),Priority=request.Priority,Status="Submitted",Synced=true};db.Requests.Add(e);await db.SaveChangesAsync();await achievements.AwardRequestPoints(UserId);db.Notifications.Add(new Notification{UserId=UserId,Message=$"Request #{e.Id} was submitted successfully."});await db.SaveChangesAsync();return Ok(new{message="Request submitted successfully.",requestId=e.Id});}
 [HttpPut("{id}/status")] public async Task<IActionResult> Status(int id,UpdateRequestStatus request){var e=await db.Requests.FirstOrDefaultAsync(x=>x.Id==id && x.UserId==UserId);if(e==null)return NotFound();e.Status=request.Status;db.Notifications.Add(new Notification{UserId=e.UserId,Message=$"Request #{e.Id} status changed to {e.Status}."});await db.SaveChangesAsync();return Ok(new{message="Status updated."});}
}
