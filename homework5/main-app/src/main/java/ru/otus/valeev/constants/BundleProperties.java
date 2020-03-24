package ru.otus.valeev.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BundleProperties {

    MESSAGE_ENTER("message.enter"),
    MESSAGE_SUCCESS("message.success"),
    MESSAGE_WRONG("message.wrong"),
    MESSAGE_END_GAME("message.endGame"),
    MESSAGE_ENTER_NAME("message.enterName"),
    MESSAGE_WRONG_NAME("message.wrongName"),
    MESSAGE_FILE_NOT_FOUND("message.fileNotFound"),
    MESSAGE_SUCCESS_RESULT("message.successResult"),
    MESSAGE_FAIL_RESULT("message.failResult"),
    MESSAGE_FAIL_ENTER_USERNAME("message.failEnterUserName"),
    ;

    private String propName;
}
