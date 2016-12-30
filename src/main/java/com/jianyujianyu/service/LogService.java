package com.jianyujianyu.service;
import com.jianyujianyu.model.LinkEntity;
import com.jianyujianyu.model.LogEntity;
import com.jianyujianyu.model.UserEntity;
import com.jianyujianyu.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by aimreant on 12/17/16.
 */
@Transactional
@Service
public class LogService {

    @Autowired LogRepository logRepository;

    /**
     * Return logs about all users
     * Used by admin
     * @return
     */
    public List<LogEntity> getLogs(){
        List<LogEntity> logEntityList = logRepository.findAll();

        if(logEntityList.size() == 0) return logEntityList;

        Collections.reverse(logEntityList);

        Integer returnCount;
        if(logEntityList.size() >= 20){
            returnCount = 20;
        }else {
            returnCount = logEntityList.size();
        }
        return logEntityList.subList(0, returnCount);
    }

    public boolean createLog(String operation, LinkEntity linkByLinkId, UserEntity userByUserId){

        if(userByUserId != null) {
            LogEntity logEntity = new LogEntity(operation, linkByLinkId, userByUserId);
            logRepository.saveAndFlush(logEntity);
            return true;
        }

        return false;
    }

    /**
     * Get stat according by specific user
     * @param userByUserId
     * @return a string, which is a js array
     */
    public String stat(UserEntity userByUserId){
        List<LogEntity> logEntityList = logRepository.findByUserByUserId(userByUserId);
        HashMap<Integer, Integer> dayOperation = new HashMap<Integer, Integer>();
        Calendar cal = Calendar.getInstance();
        StringBuilder returnStr = new StringBuilder("[");

        Integer day = 0;

        for(LogEntity logEntity : logEntityList){
            cal.setTime(logEntity.getCreatedAt());
            day = cal.get(Calendar.DAY_OF_YEAR);
            if(dayOperation.containsKey(day)){
                dayOperation.replace(day, dayOperation.get(day)+1);
            }else{
                dayOperation.put(day, 1);
            }
        }

        for(Map.Entry<Integer, Integer> entry : dayOperation.entrySet()){
            returnStr.append(new StringBuffer("[" + entry.getKey() + "," + entry.getValue() + "],"));
        }

        returnStr.append("]");

        return returnStr.toString();
    }


    /**
     * Get stat according by all users
     * @return a string, which is a js array
     */
    public String statAll(){
        List<LogEntity> logEntityList = logRepository.findAll();
        HashMap<Integer, Integer> dayOperation = new HashMap<Integer, Integer>();
        Calendar cal = Calendar.getInstance();
        StringBuilder returnStr = new StringBuilder("[");

        Integer day = 0;

        for(LogEntity logEntity : logEntityList){
            cal.setTime(logEntity.getCreatedAt());
            day = cal.get(Calendar.DAY_OF_YEAR);
            if(dayOperation.containsKey(day)){
                dayOperation.replace(day, dayOperation.get(day)+1);
            }else{
                dayOperation.put(day, 1);
            }
        }

        for(Map.Entry<Integer, Integer> entry : dayOperation.entrySet()){
            returnStr.append(new StringBuffer("[" + entry.getKey() + "," + entry.getValue() + "],"));
        }

        returnStr.append("]");

        return returnStr.toString();
    }
}
