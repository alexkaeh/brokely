select transfer_id, transfer_type_desc,	transfer_status_desc, tuf.username as user_from, tut.username as user_to, amount from transfer
join account as f on f.account_id = account_from 
join account as t on t.account_id = account_to 
join transfer_type using (transfer_type_id) 
join transfer_status using (transfer_status_id) 
join tenmo_user as tuf on f.user_id = tuf.user_id 
join tenmo_user as tut on t.user_id = tut.user_id
where tuf.user_id = ? or tut.user_id = ?;
