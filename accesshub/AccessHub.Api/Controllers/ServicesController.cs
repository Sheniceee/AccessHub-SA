using AccessHub.Api.Data; using Microsoft.AspNetCore.Mvc; using Microsoft.EntityFrameworkCore;
namespace AccessHub.Api.Controllers;
[ApiController][Route("api/services")]
public class ServicesController(AccessHubDbContext db):ControllerBase
{
 [HttpGet] public async Task<IActionResult> Get(string? search,int? categoryId){var q=db.Services.Include(x=>x.ServiceCategory).AsQueryable();if(!string.IsNullOrWhiteSpace(search))q=q.Where(x=>x.ServiceName.Contains(search)||x.Description.Contains(search));if(categoryId.HasValue)q=q.Where(x=>x.ServiceCategoryId==categoryId.Value);return Ok(await q.Select(x=>new{serviceId=x.Id,serviceName=x.ServiceName,category=x.ServiceCategory!.CategoryName,description=x.Description,categoryId=x.ServiceCategoryId}).ToListAsync());}
 [HttpGet("categories")] public async Task<IActionResult> Categories()=>Ok(await db.ServiceCategories.Select(x=>new{id=x.Id,name=x.CategoryName}).ToListAsync());
}
