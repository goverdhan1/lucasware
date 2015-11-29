package com.lucas.services.messaging;

import org.springframework.stereotype.Service;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 6/13/14  Time: 1:07 PM
 * This Class provide the implementation for the functionality of..
 */

@Service
public class LucasServiceImpl implements LucasService {
    @Override
    public String processService(long time, String data) throws Exception {
        Thread.sleep(time);
        return data;
    }
}
