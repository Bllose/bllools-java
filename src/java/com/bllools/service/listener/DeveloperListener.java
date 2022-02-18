package com.bllools.service.listener;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import com.bllools.util.CommonUtil;
import com.bllools.vo.Developer;

public class DeveloperListener extends AnalysisEventListener<Developer> {

    private final List<Developer> dataList = new ArrayList<>();

    @Override
    public void invoke(Developer data, AnalysisContext analysisContext) {
        if (data.getIsDev().equals("是") && data.getState().equals("正常")) {
            CommonUtil.log("DE工号:" + data.getW3() + ", DE姓名:" + data.getName());
            dataList.add(data);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        CommonUtil.log("编码人员信息收集完成，编码人员总数量为" + dataList.size());
    }

    public List<Developer> getDataList() {
        return dataList;
    }

}
