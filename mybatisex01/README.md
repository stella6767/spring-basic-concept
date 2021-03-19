
### MyBatis DB 설정

```sql
create table product(
	id int auto_increment primary key,
	name varchar(50),
    code varchar(50)
);



create table panmae(
	id int auto_increment primary key,
	productId int,
    username varchar(30)
);
```