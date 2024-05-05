package ForMZ.Server.domain.user.service;

import ForMZ.Server.domain.user.dto.MailRes;

public interface MailSenderService {
    void makeRandomNumber();
    MailRes joinEmail(String email);
    void mailSend(String setFrom, String toMail, String title, String content);
}
