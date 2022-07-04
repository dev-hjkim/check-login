package com.example.aop.board.controller;

import com.example.aop.aop.LoginCheck;
import com.example.aop.aop.UserSeqParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="/v1/boards")
@RequiredArgsConstructor
public class BoardController {
    final Logger logger = LoggerFactory.getLogger(BoardController.class);

    /**
     * access token을 받아 처리하는 api 예시
     *
     * @author hjkim
     * @param  userSeq
     */
    @LoginCheck
    @GetMapping(value="")
    public String tokenTest(@UserSeqParam String userSeq) {
        logger.info("tokenTest ::: {}", userSeq);
        return userSeq;
    }
}
