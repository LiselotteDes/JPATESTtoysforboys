insert into countries(name) values('test');
insert into customers(name,streetAndNumber,city,state,postalCode,countryId) 
values ('test','test','test','test','test',(select id from countries where name='test'));
insert into orders(orderDate,requiredDate,comments,customerId,status)
values ('2003-01-01', '2003-10-01','test',(select id from customers where name='test'), 'waiting');
insert into productlines(name,description) values ('test', 'test');
insert into products(name,scale,description,quantityInStock,quantityInOrder,buyPrice,productlineId)
values ('test','test','test',10,5,100,(select id from productlines where name='test'));
insert into orderdetails(orderId,productId,quantityOrdered,priceEach)
values ((select id from orders where comments='test'),(select id from products where name='test'),2,200);