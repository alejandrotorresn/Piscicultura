# Piscicultura
Diseño de una plataforma software que apoye los procesos de crianza en el área de la piscicultura.


# Creacion de sensores:



* Implementar la interface uis.brt.sensor.api.Sensor que posee 4 metodos
:
	1. start inicia la ejecucion del driver

	2. stop detiene la ejecucion del driver

	3. setBus inyecta la instacia del EventBus a utilizar para la comunicacion con la plataforma

	4. configure es llamado por la plataforma para configurar el sensor driver
* Incluir un archivo de configuracion llamamdo config.properties. Las propiedades se definen de tipo valor según esta [definición](https://en.wikipedia.org/wiki/.properties)

* Incluir un archivo llamado uis.brt.sensor.api.Sensor dentro de este archivo hay una unica linea con el nombre de la clase implementando el driver

* Empaquetar el codigo y los dos archivos descritos anteriormente en un archivo tipo jar


# Despliegue del proyecto:
En una carpeta de nombre HOME colocar:

*  middleware.core-(# de la version).jar
*  el script launcher.sh
*  una carpeta de nombre sensors y colocar:
	los archivos .jar de cada sensor a ejecutar


