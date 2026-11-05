using AccessHub.Api.Models;
using Microsoft.EntityFrameworkCore;
namespace AccessHub.Api.Data;
public class AccessHubDbContext(DbContextOptions<AccessHubDbContext> options) : DbContext(options)
{
    public DbSet<User> Users => Set<User>();
    public DbSet<ServiceCategory> ServiceCategories => Set<ServiceCategory>();
    public DbSet<Service> Services => Set<Service>();
    public DbSet<ServiceRequest> Requests => Set<ServiceRequest>();
    public DbSet<Notification> Notifications => Set<Notification>();
    public DbSet<Achievement> Achievements => Set<Achievement>();
    public DbSet<UserAchievement> UserAchievements => Set<UserAchievement>();
    protected override void OnModelCreating(ModelBuilder b)
    {
        b.Entity<User>().HasIndex(x=>x.Email).IsUnique();
        b.Entity<Service>().HasOne(x=>x.ServiceCategory).WithMany(x=>x.Services).HasForeignKey(x=>x.ServiceCategoryId);
        b.Entity<ServiceRequest>().HasOne(x=>x.User).WithMany(x=>x.Requests).HasForeignKey(x=>x.UserId).OnDelete(DeleteBehavior.Restrict);
        b.Entity<ServiceRequest>().HasOne(x=>x.Service).WithMany(x=>x.Requests).HasForeignKey(x=>x.ServiceId).OnDelete(DeleteBehavior.Restrict);
        b.Entity<Notification>().HasOne(x=>x.User).WithMany().HasForeignKey(x=>x.UserId);
        b.Entity<UserAchievement>().HasOne(x=>x.User).WithMany().HasForeignKey(x=>x.UserId);
        b.Entity<UserAchievement>().HasOne(x=>x.Achievement).WithMany(x=>x.UserAchievements).HasForeignKey(x=>x.AchievementId);
    }
}
