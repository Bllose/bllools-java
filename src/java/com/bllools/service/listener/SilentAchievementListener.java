package com.bllools.service.listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.bllools.util.CommonUtil;
import com.bllools.vo.UadpAchievement;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

public class SilentAchievementListener extends AnalysisEventListener<UadpAchievement> {

    private final List<UadpAchievement> dataList = new ArrayList<>();

    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private String thisMonth;

    public void init() {
        thisMonth = df.format(new Date()).substring(0, 7);
    }

    @Override
    public void invoke(UadpAchievement data, AnalysisContext analysisContext) {




        if (data.getRequirementType().contains("AR")
            && data.getEstimated() != null
            && StringUtils.isNotBlank(data.getDe())
            && StringUtils.isNotBlank(data.getDevelopTime())) {

            // 将日期转换为 本周周四的日期
            data.setDevelopTime(CommonUtil.getThursDay(data.getDevelopTime()));

            if (data.getDevelopTime().substring(0, 7).equals(thisMonth)) {
                String de = data.getDe();
                de = de.charAt(0) + de.substring(de.indexOf(" ") + 1);
                data.setDe(de.toLowerCase(Locale.US));
                data.setWeek(CommonUtil.getWeek(data.getDevelopTime()));

                dataList.add(data);
            }
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        CommonUtil.log("本月度单个文件中AR完成情况信息收集完成，本月度单个文件中AR完成情况信息总数量为" + dataList.size());
    }

    public List<UadpAchievement> getDataList() {
        return dataList;
    }
}
