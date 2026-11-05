namespace AccessHub.Api.Models;
public class Achievement
{
    public int Id { get; set; }
    public string Name { get; set; } = "";
    public string Description { get; set; } = "";
    public int PointsRequired { get; set; }
    public ICollection<UserAchievement> UserAchievements { get; set; } = new List<UserAchievement>();
}
