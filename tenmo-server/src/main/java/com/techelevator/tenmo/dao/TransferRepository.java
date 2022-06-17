package com.techelevator.tenmo.dao;
import com.techelevator.tenmo.model.Transfer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer> {

    Transfer findByTransferId(int id);


//    @Query(
//        value = """
//            select transfer_id,
//                transfer_type_desc,
//                transfer_status_desc,
//                tuf.username as user_from,
//                tut.username as user_to,
//                amount
//            from transfer
//            join account as f on f.account_id = account_from
//            join account as t on t.account_id = account_to
//            join transfer_type using (transfer_type_id)
//            join transfer_status using (transfer_status_id)
//            join tenmo_user as tuf on f.user_id = tuf.user_id
//            join tenmo_user as tut on t.user_id = tut.user_id
//            where tuf.user_id = :idone
//            or tut.user_id = 1001;""",
//        nativeQuery = true)
//    List<TransferDto> findAll(
//            @Param("idone") int idd1);

}
