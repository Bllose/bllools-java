package com.bllools.java;

import org.apache.cxf.common.util.StringUtils;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CompareVoUtil {

    @Data
    static class compareResult{
        String targetField;
        String targetType;
        String originField;
        String originType;
    }

    /**
     * 如果存在两个比较近似的vo， 希望可以抽取相同部分抽象化。
     * 如果域比较多，通过该方法可以快速比较两对象域的差异
     * 
     * @param originVo 比对基准对象
     * @param targetVo 进行比较的vo对象
     * @return
     */
    public static List<compareResult> compare(Object originVo, Object targetVo){
        List<compareResult> resultList = new ArrayList<>();
        Field[] originFields = originVo.getClass().getDeclaredFields();
        Field[] targetFields = targetVo.getClass().getDeclaredFields();

        // 找出不同的， 源存在但是目标端没有的域
        for(Field curField: originFields){
            boolean same = false;
            String targetName = "";
            String targetType = "";

            String fieldName = curField.getName();
            String fieldType = curField.getType().getTypeName();
            for(Field searchedField: targetFields){
                String targetFieldName = searchedField.getName();
                if(fieldName.equals(targetFieldName)){
                    String targetFieldType = searchedField.getType().getTypeName();
                    if(fieldType.equals(targetFieldType)){
                        same = true;
                        break;
                    }else{
                        targetName = targetFieldName;
                        targetType = targetFieldType;
                    }
                }
            }

            if(!same) {
                compareResult result = new compareResult();
                result.originField = fieldName;
                result.originType = fieldType;
                if(!StringUtils.isEmpty(targetName)) {
                    result.targetField = targetName;
                    result.targetField = targetType;
                }
                resultList.add(result);
            }
        }

        // 专注找目标端有，但是源没有的域
        int amount = originFields.length;
        for(Field tarField: targetFields){
            String fieldName = tarField.getName();
            if(fieldName.equals("applyNo")){
                System.out.println("~!!");
            }
            String fieldType = tarField.getType().getTypeName();
            boolean same = false;

            for(int i = 0 ; i < amount; i ++){
                String originName = originFields[i].getName();
                if(fieldName.equals(originName)){
                    same = true;
                    break;
                }
            }

            if(!same){
                compareResult result = new compareResult();
                result.targetField = fieldName;
                result.targetType = fieldType;
                resultList.add(result);
            }
        }

        return resultList;
    }
}
