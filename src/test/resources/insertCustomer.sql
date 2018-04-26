insert into countries(name) values ('test');
insert into customers(name,streetAndNumber,city,state,postalCode,countryId)
values ('test','test','test','test','test',(select id from countries where name='test'));