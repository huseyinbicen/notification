package com.hayalet.notification.service.impl;

import com.hayalet.notification.domain.MailTemplate;
import com.hayalet.notification.dto.create.CreateMailTemplateDTO;
import com.hayalet.notification.repository.MailTemplateRespository;
import com.hayalet.notification.service.MailTemplateService;
import org.modelmapper.ModelMapper;
import org.modelmapper.ValidationException;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Service
@Transactional
public class MailTemplateServiceImpl implements MailTemplateService {
    @Autowired
    private MailTemplateRespository mailTemplateRespository;

    @Override
    public String saveMailTemplate(CreateMailTemplateDTO createMailTemplateDTO) {
        String result = "";
        try{
            MailTemplate checkMailTemplate = this.checkAndGetMailTemplate(createMailTemplateDTO.getCode());
            if (checkMailTemplate != null){
                MailTemplate mailTemplate = new ModelMapper().map(createMailTemplateDTO,MailTemplate.class);
                Long id = this.mailTemplateRespository.saveAndFlush(mailTemplate).getId();
                if (id != 0)
                    result = "Succesfull";
            }
            else
                result = "The row alredy exists";
        }catch (Exception e){
            System.out.println(e.getMessage().toString());
            result = "Failed" + e.getMessage();
        }
        return result;
    }

    @Override
    public MailTemplate checkAndGetMailTemplate(String mailTemplateCode) {
        if (StringUtils.isEmpty(mailTemplateCode)) {
            throw  new ValidationException(Arrays.asList(new ErrorMessage("Hata")));
        }
        MailTemplate mailTemplate = this.mailTemplateRespository.findOneByCode(mailTemplateCode);
        if (mailTemplate == null) {
            throw  new ValidationException(Arrays.asList(new ErrorMessage("Hata")));
        }
        return mailTemplate;
    }
}
