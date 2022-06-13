select transfer_id, 
	transfer_type_desc, 
	transfer_status_desc, 
	tuf.username as user_from, 
	tut.username as user_to, 
	amount 
from transfer
join account as f on f.account_id = account_from
join account as t on t.account_id = account_to
join transfer_type using (transfer_type_id)
join transfer_status using (transfer_status_id)
join tenmo_user as tuf on f.user_id = tuf.user_id
join tenmo_user as tut on t.user_id = tut.user_id
where tuf.user_id = ?
or tut.user_id = ?;

insert into transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount)
values (1, 1, 2003, 2002, 100);

insert into tenmo_user (username, password_hash)
values ('abc', 'abc1');
select * from tenmo_user

insert into account (user_id, balance) 
values (1003, 1003);
select * from account