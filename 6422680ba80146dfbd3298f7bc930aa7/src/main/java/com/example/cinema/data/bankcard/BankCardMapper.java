package com.example.cinema.data.bankcard;

import com.example.cinema.po.BankCard;
import com.example.cinema.vo.BankCardForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BankCardMapper {
    BankCard selectCardByNumber(@Param("cardNumber") int cardNumber);

    void changeCardBalance(BankCardForm bankCardForm);
}
