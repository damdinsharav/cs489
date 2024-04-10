select * from `cs489-apsd-lab5a-dental-sugery-db`.`dentists` order by lastName asc;

select a.appointmentId, a.appointmentDate, a.appointmentTime, p.firstName PatientFirstName, 
p.lastName PatientLastName
from `cs489-apsd-lab5a-dental-sugery-db`.`appointments` as a
left outer join `cs489-apsd-lab5a-dental-sugery-db`.`patients` as p
on a.patientId = p.patientId
where a.dentistId = 1;

select * from `cs489-apsd-lab5a-dental-sugery-db`.`appointments` a
where a.surgeryId = 1;

select * from `cs489-apsd-lab5a-dental-sugery-db`.`appointments` a
where a.patientId = 1 and a.appointmentDate = '2013-09-12';