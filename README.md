# INTRASOFT

##Descripción 

Para este proyecto se uso mongoDB para guardar los datos. La razón para esto es que los activos fijos siempre estan en cambio por lo cual los activos fijos necesitan ser guardados en una base de datos que permita flexibilidad. Además, el tiempo de tiempo de respuesta en busquedas es rapido. 

Para utiliar mongoDB se utilizo docker y se bajo la imagen, los commandos a seguir para crear la imagen es: 
  sudo docker run -d --name some-mongo -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=mongoadmin -e MONGO_INITDB_ROOT_PASSWORD=secret mongo:4.0.4

Y para conectarse al mongoShell es: 
	sudo docker run -it --rm --link some-mongo:mongo mongo mongo --host mongo -u mongoadmin -p secret --authenticationDatabase admin some-db

Por último se utilizó spring boot. Para correr la aplicacion es necesario hacerle run en la clase IntrasoftApplication que tiene el tag @SpringBootApplication.

