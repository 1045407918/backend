package com.example.cinema.bl.bankcard;

import com.example.cinema.po.BankCard;
import com.example.cinema.vo.BankCardForm;
import com.example.cinema.vo.ResponseVO;
import org.apache.ibatis.annotations.Param;

public interface BankCardService {
    /**
     * 根据卡号选择银行卡
     * @param cardNumber
     * @return
     */
    BankCard selectCardByNumber(@Param("cardNumber") int cardNumber);

    /**
     * 修改银行卡余额
     * @param bankCardForm
     * @return
     */
    ResponseVO changeCardBalance(BankCardForm bankCardForm);
}
