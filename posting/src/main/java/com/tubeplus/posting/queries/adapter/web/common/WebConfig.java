package com.tubeplus.posting.queries.adapter.web.common;

import com.tubeplus.posting.queries.adapter.web.controller.vo.PostingsSearchTypeReq;
import com.tubeplus.posting.queries.adapter.web.controller.vo.PostingsViewTypeReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    protected void addBoardFormatters(FormatterRegistry registry) {

        log.info("added board related formatters");
    }

    protected void addPostingFormatters(FormatterRegistry registry) {

        registry.addConverter(new PostingsViewTypeReq.PostingsViewTypeReqConverter());
        registry.addConverter(new PostingsSearchTypeReq.PostingsSearchTypeReqConverter());

        log.info("added posting related formatters");
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {

        log.info("WebMvcConfigurer add formatters");
        this.addBoardFormatters(registry);
        this.addPostingFormatters(registry);
    }
}
