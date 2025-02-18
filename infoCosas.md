He renombrado App.java a BackendApplication.java
si tocas pom 
* mvn clean install
Ejecutar:  
*  mvn spring-boot:run  *
* DUDAS *
* ¿Crear carpetas?:
  { controller }
  { service }
  { repository }
  { model }


Crear modelos /service / repositorios de las entidades ...

Crear controladores

Enlazar BBDD con el proyecto

Guardar en los modelos ids de otras clases o se puede guadar el modelo entero


>>>>>>>>>>>>>>>>>>>>>> PREGUNTAS PARA LOS PROFES <<<<<<<<<<<<<<<<<<<<<
No tenemos las fotos en Static/images, pasa algo? -> Pasar todo lo estático a la carpeta static (css, js, imagenes...)

Cómo insertar una plantilla de sidebar con mustache. ¿Es igual que un header/footer? -> Se puede hacer sin problema, como si se 
incrustara cualquier otra plantilla


>>>>>>>>>>>>>>>PENDIENTE<<<<<<<<<<<<<<<<

Cambiar las rutas de las plantillas para que referencien a static (solo hay que ponerle una barra delante). Esto funciona si
la aplicación se lanza en un servidor springboot, por lo que hara falta crear un controlador que renderice la página

Estructurar el proyecto bien -> Mover todo lo estático a /static y corregir las rutas
Decidir que hacer con las plantillas de la carpeta "plantillas" -> Algunas se pueden reutilizar, otras no

Crear un footer, header y sidebar globales para poder incrustarlos en las páginas de forma genérica con mostachos