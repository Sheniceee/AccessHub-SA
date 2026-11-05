using AccessHub.Api.Models; using Microsoft.EntityFrameworkCore;
namespace AccessHub.Api.Data;
public static class SeedData
{
 public static async Task SeedAsync(AccessHubDbContext db){
  if(!await db.ServiceCategories.AnyAsync()){db.ServiceCategories.AddRange(new ServiceCategory{CategoryName="Health"},new ServiceCategory{CategoryName="Education"},new ServiceCategory{CategoryName="Community"},new ServiceCategory{CategoryName="Government"});await db.SaveChangesAsync();}
  if(!await db.Services.AnyAsync()){var c=await db.ServiceCategories.ToListAsync();db.Services.AddRange(new Service{ServiceName="Clinic Information",Description="Find information about local clinic services.",ServiceCategoryId=c.First(x=>x.CategoryName=="Health").Id},new Service{ServiceName="Education Support",Description="Request education support.",ServiceCategoryId=c.First(x=>x.CategoryName=="Education").Id},new Service{ServiceName="Community Support",Description="Find community support services.",ServiceCategoryId=c.First(x=>x.CategoryName=="Community").Id},new Service{ServiceName="Public Service Information",Description="Get public service information.",ServiceCategoryId=c.First(x=>x.CategoryName=="Government").Id});await db.SaveChangesAsync();}
  if(!await db.Achievements.AnyAsync()){db.Achievements.AddRange(new Achievement{Name="First Request",Description="Submit your first service request.",PointsRequired=10},new Achievement{Name="Active Citizen",Description="Reach 50 points.",PointsRequired=50},new Achievement{Name="Community Champion",Description="Reach 100 points.",PointsRequired=100});await db.SaveChangesAsync();}
 }
}
