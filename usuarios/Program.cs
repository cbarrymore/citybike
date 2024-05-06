using Repositorio;
using usuarios.Controllers;
using usuarios.modelo;
using usuarios.Repositorio;
using usuarios.servicios;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

builder.Services.AddSingleton<IServicioUsuarios, ServicioUsuarios>();
builder.Services.AddSingleton<RepositorioUsuarios, RepositorioUsuariosMongoDB>();
builder.Services.AddSingleton<Repositorio<CodigoActivacion,string>, RepositorioCodigosActivacionMongoDB>();
builder.Services.AddControllers(options =>
{
    options.Filters.Add(typeof(ManejadorGlobalErrores));
});

var app = builder.Build();


// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
