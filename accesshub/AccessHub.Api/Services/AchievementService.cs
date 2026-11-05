using AccessHub.Api.Data; using AccessHub.Api.Models; using Microsoft.EntityFrameworkCore;
namespace AccessHub.Api.Services;
public class AchievementService(AccessHubDbContext db)
{
 public async Task AwardRequestPoints(int userId){var user=await db.Users.FindAsync(userId);if(user==null)return;user.Points+=10;var earned=await db.UserAchievements.Where(x=>x.UserId==userId).Select(x=>x.AchievementId).ToListAsync();var ach=await db.Achievements.Where(x=>x.PointsRequired<=user.Points&&!earned.Contains(x.Id)).ToListAsync();foreach(var a in ach){db.UserAchievements.Add(new UserAchievement{UserId=userId,AchievementId=a.Id});db.Notifications.Add(new Notification{UserId=userId,Message=$"Achievement unlocked: {a.Name}"});}await db.SaveChangesAsync();}
}
