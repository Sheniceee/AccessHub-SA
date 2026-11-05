namespace AccessHub.Api.Models;
public class ServiceCategory
{
    public int Id { get; set; }
    public string CategoryName { get; set; } = "";
    public ICollection<Service> Services { get; set; } = new List<Service>();
}
