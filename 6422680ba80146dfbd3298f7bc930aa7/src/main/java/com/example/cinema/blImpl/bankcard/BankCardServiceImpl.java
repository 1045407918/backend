package com.example.cinema.blImpl.bankcard;

import com.example.cinema.bl.bankcard.BankCardService;
import com.example.cinema.data.bankcard.BankCardMapper;
import com.example.cinema.po.BankCard;
import com.example.cinema.vo.BankCardForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BankCardServiceImpl implements BankCardService {
    @Autowired
    BankCardMapper bankCardMapper;

    @Override
    @Transactional
    public BankCard selectCardByNumber(int cardNumber){
        try {
            return bankCardMapper.selectCardByNumber(cardNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public ResponseVO changeCardBalance(BankCardForm bankCardForm){
        try {
            bankCardMapper.changeCardBalance(bankCardForm);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    };
}
