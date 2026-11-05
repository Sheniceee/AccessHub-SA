namespace AccessHub.Api.Models;
public class ServiceRequest
{
    public int Id { get; set; }
    public int UserId { get; set; }
    public int ServiceId { get; set; }
    public string Description { get; set; } = "";
    public string Priority { get; set; } = "Normal";
    public string Status { get; set; } = "Submitted";
    public DateTime CreatedDate { get; set; } = DateTime.UtcNow;
    public bool Synced { get; set; } = true;
    public User? User { get; set; }
    public Service? Service { get; set; }
}
