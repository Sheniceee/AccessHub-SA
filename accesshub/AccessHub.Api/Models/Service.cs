namespace AccessHub.Api.Models;
public class Service
{
    public int Id { get; set; }
    public string ServiceName { get; set; } = "";
    public string Description { get; set; } = "";
    public int ServiceCategoryId { get; set; }
    public ServiceCategory? ServiceCategory { get; set; }
    public ICollection<ServiceRequest> Requests { get; set; } = new List<ServiceRequest>();
}
