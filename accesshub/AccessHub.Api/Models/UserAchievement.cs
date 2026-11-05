namespace AccessHub.Api.Models;
public class UserAchievement
{
    public int Id { get; set; }
    public int UserId { get; set; }
    public int AchievementId { get; set; }
    public DateTime EarnedDate { get; set; } = DateTime.UtcNow;
    public User? User { get; set; }
    public Achievement? Achievement { get; set; }
}
